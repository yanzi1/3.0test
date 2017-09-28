package com.me.updatelib;

import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.dd.CircularProgressButton;
import com.me.updatelib.bean.DownLoadApkSizeInfo;
import com.me.updatelib.bean.UpdateInfo;
import com.me.updatelib.receiver.AppReplacedReceiver;
import com.me.updatelib.receiver.DownloadCompleteReceiver;
import com.me.updatelib.runnable.GetUpdateInfoRunnable;
import com.me.updatelib.runnable.QueryDownlaodProgressRunnable;
import com.me.updatelib.util.Utils;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by jjr on 2017/4/6.
 */

public class UpdateManager {
    private final String TAG = getClass().getSimpleName();

    private final Activity mContext;
    private final String checkUrl;
    private final boolean isHintNewVersion;
    private Handler messageTransferHandler;
    private int messageCode;
    private OnUpdateListener mOnUpdateListener;

    private boolean canMobileNetworkDownload;
    private DownloadManager mDownloadManager;
    private DownloadCompleteReceiver mDownloadCompleteReceiver;
    private Dialog mUpdateDialog;
    private CircularProgressButton update_dialog_update_cpb;
    private DownloadManager.Request mRequest;
    private ScheduledExecutorService mScheduledExecutorService;
    private AppReplacedReceiver mAppReplacedReceiver;
    private boolean isDownLoading = false;

    public UpdateManager(Builder builder) {
        this.mContext = builder.context;
        this.checkUrl = builder.checkUrl;
        this.canMobileNetworkDownload = builder.canMobileNetworkDownload;
        this.isHintNewVersion = builder.isHintNewVersion;
        this.messageTransferHandler = builder.messageTransferHandler;
        this.messageCode = builder.messageCode;
        mScheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    }

    public static class Builder {
        private Activity context;
        private String checkUrl;
        private boolean canMobileNetworkDownload = true;
        private boolean isHintNewVersion = true;
        private Handler messageTransferHandler;
        private int messageCode;

        public Builder(Activity context) {
            this.context = context;
        }

        /**
         * 检查是否有新版本App的URL接口路径
         *
         * @param checkUrl
         */
        public Builder checkUrl(String checkUrl) {
            this.checkUrl = checkUrl;
            return this;
        }

        /**
         * 当没有新版本时，是否Toast提示
         *
         * @param isHint
         * @return true提示，false不提示
         */
        public Builder isHintNewVersion(boolean isHint) {
            this.isHintNewVersion = isHint;
            return this;
        }

        /**
         * 设置handler已便于在不需要版本更新时告知所在activity
         *
         * @param messageTransferHandler
         * @param messageCode     消息传递时的标识
         * @return
         */
        public Builder putMessageTransferHandler(Handler messageTransferHandler, int messageCode) {
            this.messageTransferHandler = messageTransferHandler;
            this.messageCode = messageCode;
            return this;
        }

        /**
         * 是否允许移动网络下下载
         *
         * @param canMobileNetworkDownload
         */
        public Builder canMobileNetworkDownload(boolean canMobileNetworkDownload) {
            this.canMobileNetworkDownload = canMobileNetworkDownload;
            return this;
        }

        /**
         * 构造UpdateManager对象
         *
         * @return
         */
        public UpdateManager build() {
            return new UpdateManager(this);
        }
    }

    private static final int ZERO = 0;

    public static final int CHECK_UPDATE_INFO = 0x1;
    public static final int MESSAGE_TRANSFER = 0x2;
    public static final int PROGRESS_DOWNLOAD_APK = 0x3;
    public static final int ERROR_DOWNLOAD_APK = 0x4;
    public static final int COMPLETE_DOWNLOAD_APK = 0x5;
    public static final int COMPLETE_REPLACED_APK = 0x6;
    public static final int NETWORK_CHAGED = 0x7;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CHECK_UPDATE_INFO: {
                    UpdateInfo updateInfo = (UpdateInfo) msg.obj;
                    checkUpdateInfo(updateInfo);
                    break;
                }
                case MESSAGE_TRANSFER: {
                    messageTransferHandler.obtainMessage(messageCode).sendToTarget();
                    break;
                }
                case ERROR_DOWNLOAD_APK: {
                    isDownLoading = !isDownLoading;
                    clearDownLoadFile();
                    if (update_dialog_update_cpb != null) {
                        update_dialog_update_cpb.setProgress(-1);
                    }
                    break;
                }
                case PROGRESS_DOWNLOAD_APK: {
                    DownLoadApkSizeInfo downLoadApkSizeInfo = (DownLoadApkSizeInfo) msg.obj;
                    int percent = (int) (1.0f * downLoadApkSizeInfo.getSizeHadDownLoaded() / downLoadApkSizeInfo.getSizeTotal() * 100);
                    update_dialog_update_cpb.setProgress(percent);

                    if (mOnUpdateListener != null) {
                        mOnUpdateListener.onDownloading(downLoadApkSizeInfo.getSizeTotal(), downLoadApkSizeInfo.getSizeHadDownLoaded());
                    }
                    break;
                }
                case COMPLETE_DOWNLOAD_APK: {
                    isDownLoading = !isDownLoading;
//                    registerAppReplacedReceiver();
                    long downloadApkId = (long) msg.obj;
                    if (mUpdateDialog != null && !mUpdateDialog.isShowing()) {
                        installApk(downloadApkId);
                    }

                    if (mOnUpdateListener != null) {
                        mOnUpdateListener.onFinshDownload(downloadApkId, mDownloadManager.getUriForDownloadedFile(downloadApkId));
                    }
                    unregisterDownloadCompleteReceiver();
                    break;
                }
                case COMPLETE_REPLACED_APK: {
                    clearDownLoadFile();
//                    unregisterAppReplacedReceiver();
                    break;
                }
            }
        }
    };

    private void checkUpdateInfo(UpdateInfo updateInfo) {
        if (updateInfo != null) {
            if (Integer.parseInt(updateInfo.getVersionCode()) > Utils.getPackageInfo(mContext).versionCode) {
                showUpdateDialog(updateInfo);
            } else {
                mScheduledExecutorService.shutdownNow();
                if (isHintNewVersion) {
                    Utils.showToast(mContext, mContext.getString(R.string.current_latest_version));
                }
                mHandler.obtainMessage(UpdateManager.MESSAGE_TRANSFER).sendToTarget();
            }
            if (mOnUpdateListener != null) {
                mOnUpdateListener.onFinishCheck(updateInfo);
            }
        }
    }

    private void installApk(long downloadApkId) {
        if (mDownloadManager == null) {
            mDownloadManager = ((DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE));
        }
        String pathByUri = Utils.getPathByUri(mContext, mDownloadManager.getUriForDownloadedFile(downloadApkId));
        if (!TextUtils.isEmpty(pathByUri) && Utils.compare(mContext.getApplicationContext(), Utils.getPackageInfo(mContext, pathByUri))) {
            Uri apkUri;
            File apkFile = new File(pathByUri);
            if (Build.VERSION.SDK_INT < 19) {
                apkUri = mDownloadManager.getUriForDownloadedFile(downloadApkId);
            } else if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 24){
                apkUri = Uri.fromFile(apkFile);
            } else {
                //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
                apkUri = FileProvider.getUriForFile(mContext, "com.dongao.kaoqian.phone.fileprovider", apkFile);
            }
            Utils.installApk(mContext, apkUri);
        } else {
            Log.e(TAG, "下载的APK文件版本错误");
            mHandler.obtainMessage(ERROR_DOWNLOAD_APK).sendToTarget();
        }
    }

    /**
     * 显示更新版本的对话框
     *
     * @param updateInfo 从服务器获取的最新版本的信息
     */
    private void showUpdateDialog(final UpdateInfo updateInfo) {
        ViewGroup rootView = (ViewGroup) LayoutInflater.from(mContext).inflate(R.layout.update_dialog, null);
        final ImageView update_dialog_close_iv = (ImageView) rootView.findViewById(R.id.update_dialog_close_iv);
        TextView update_dialog_description_tv = (TextView) rootView.findViewById(R.id.update_dialog_description_tv);
        update_dialog_update_cpb = (CircularProgressButton) rootView.findViewById(R.id.update_dialog_update_cpb);
        if (Boolean.parseBoolean(updateInfo.getIsUpdate())) {//强制更新
            update_dialog_close_iv.setVisibility(View.GONE);
        } else {//非强制更新
            update_dialog_close_iv.setVisibility(View.VISIBLE);
            update_dialog_close_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mUpdateDialog.dismiss();
                }
            });
        }
        if (mDownloadManager == null) {
            mDownloadManager = ((DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE));
        }
        if (Utils.getDownloadId(mContext) != -1L) {
            String pathByUri = Utils.getPathByUri(mContext, mDownloadManager.getUriForDownloadedFile(Utils.getDownloadId(mContext)));
            //比较本地下载的apk与服务器的apk版本号
            if (!TextUtils.isEmpty(pathByUri) && Utils.compare(updateInfo.getVersionCode(), Utils.getPackageInfo(mContext, pathByUri))) {
                update_dialog_update_cpb.setProgress(100);
            } else {
                clearDownLoadFile();
                update_dialog_update_cpb.setProgress(0);
            }
        }
        update_dialog_update_cpb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (update_dialog_update_cpb.getProgress() == 0 && !isDownLoading) {
                    isDownLoading = !isDownLoading;
                    downloadApk(updateInfo);
                } else if (update_dialog_update_cpb.getProgress() == 100) {
                    installApk(Utils.getDownloadId(mContext));
                } else if (update_dialog_update_cpb.getProgress() == -1) {
                    update_dialog_update_cpb.setProgress(0);
                    isDownLoading = !isDownLoading;
                    downloadApk(updateInfo);
                }
//                gotBrowserDownload(updateInfo);
            }
        });
        update_dialog_description_tv.setText(updateInfo.getDescription());
        mUpdateDialog = new Dialog(mContext, R.style.UpdateDialog);
        mUpdateDialog.setCanceledOnTouchOutside(false);
        mUpdateDialog.setCancelable(!Boolean.parseBoolean(updateInfo.getIsUpdate()));
        mUpdateDialog.setContentView(rootView);
        DisplayMetrics display = mContext.getResources().getDisplayMetrics();
        WindowManager.LayoutParams lp = mUpdateDialog.getWindow().getAttributes();
        lp.width = display.widthPixels; //设置宽度
        lp.height = display.heightPixels; //设置高度
        mUpdateDialog.getWindow().setAttributes(lp);
        mUpdateDialog.show();
    }

    private void gotBrowserDownload(UpdateInfo updateInfo) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(updateInfo.getLoadAddress());
        intent.setData(content_url);
        mContext.startActivity(intent);
    }

    /**
     * 检查app是否有新版本，check之前先Builer所需参数
     */
    public void check() {
        check(null);
    }

    public void check(OnUpdateListener listener) {
        if (listener != null) {
            mOnUpdateListener = listener;
        }
        if (mContext == null) {
            Log.e("NullPointerException", "The context must not be null.");
            return;
        }
        if (!Utils.isNetworkUrl(checkUrl)) {
            Log.e("UrlException", "The url isn't a network url");
            return;
        }
        getNewVersion();
    }

    private void getNewVersion() {
        if (mOnUpdateListener != null) {
            mOnUpdateListener.onStartCheck(checkUrl);
        }
        mScheduledExecutorService.execute(new GetUpdateInfoRunnable(mHandler, checkUrl));
        Log.i("checkUrl", "getNewVersion(UpdateManager.java:340)" + checkUrl);
    }

    /**
     * 下载最新版本的APK文件
     *
     * @param updateInfo
     */
    private void downloadApk(UpdateInfo updateInfo) {
        if (!Utils.canDownloadState(mContext)) {
            Utils.showToast(mContext, mContext.getString(R.string.open_download_service));
            Utils.showDownloadSetting(mContext);
            return;
        }
        if (mDownloadManager == null) {
            mDownloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        }

        DownloadManager.Request request = getRequest(updateInfo);

        registerDownloadCompleteReceiver();//注册下载完成的广播接收者

        if (request != null) {
            long downloadId = mDownloadManager.enqueue(request);


            if (mOnUpdateListener != null) {
                mOnUpdateListener.onStartDownload(downloadId);
            }

            Utils.setDownloadId(mContext.getApplicationContext(), downloadId);
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(downloadId);

            mScheduledExecutorService.execute(new QueryDownlaodProgressRunnable(mHandler, mDownloadManager, query));
//            mScheduledExecutorService.shutdown();
        }
    }

    private DownloadManager.Request getRequest(UpdateInfo updateInfo) {
        if (mRequest == null) {

            if (!Utils.isNetworkUrl(updateInfo.getLoadAddress())) {
                Log.e("UrlException", "getRequest(UpdateManager.java:383)" + "The url isn't a network url");
                return null;
            }

            mRequest = new DownloadManager.Request(Uri.parse(updateInfo.getLoadAddress()));

            mRequest.setMimeType("application/vnd.android.package-archive");

            //file:///storage/emulated/0/Android/data/com.dongao.updateDemo/files/Download/UpdateDemo_2.0.0.apk
            mRequest.setDestinationInExternalFilesDir(mContext, Environment.DIRECTORY_DOWNLOADS, updateInfo.getAppproject().getAppName() + ".apk");//设置文件存放目录和文件名 

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                mRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
            }
            mRequest.setTitle(mContext.getString(R.string.update_version));
            mRequest.setDescription(updateInfo.getAppproject().getAppName() + "    版本:" + updateInfo.getVersion());

            mRequest.setMimeType("application/vnd.android.package-archive");

            if (!canMobileNetworkDownload) {
                mRequest.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
            }
            mRequest.setAllowedOverRoaming(true);//允许移动网络下漫游
            mRequest.allowScanningByMediaScanner();

            mRequest.setVisibleInDownloadsUi(true);
        }
        return mRequest;
    }

    private void clearDownLoadFile() {
        if (mDownloadManager == null) {
            mDownloadManager = ((DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE));
        }
        mDownloadManager.remove(Utils.getDownloadId(mContext));
        Utils.setDownloadId(mContext, -1L);
    }

    /**
     * 注册下载完成的广播接收者
     */
    private void registerDownloadCompleteReceiver() {
        if (mDownloadCompleteReceiver == null) {
            IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
            mDownloadCompleteReceiver = new DownloadCompleteReceiver(mHandler);
            mContext.registerReceiver(mDownloadCompleteReceiver, filter);
        }
    }

    /**
     * 反注册下载完成的广播接收者
     */
    private void unregisterDownloadCompleteReceiver() {
        mContext.unregisterReceiver(mDownloadCompleteReceiver);
    }

    /**
     * 注册App替换安装完成的广播接收者
     */
    private void registerAppReplacedReceiver() {
        if (mAppReplacedReceiver == null) {
            IntentFilter filter = new IntentFilter(Intent.ACTION_PACKAGE_REPLACED);
            filter.addDataScheme("package");
            mAppReplacedReceiver = new AppReplacedReceiver(mHandler);
            mContext.registerReceiver(mAppReplacedReceiver, filter);
        }
    }

    /**
     * 反注册App替换安装完成的广播接收者
     */
    private void unregisterAppReplacedReceiver() {
        mContext.unregisterReceiver(mAppReplacedReceiver);
    }

}
