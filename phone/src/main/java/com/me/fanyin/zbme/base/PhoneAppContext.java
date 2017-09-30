package com.me.fanyin.zbme.base;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.me.core.app.AppContext;
import com.me.core.utils.NetStateChangeReceiver;
import com.me.core.utils.NetWorkUtils;
import com.me.core.utils.ToastBarUtils;
import com.me.data.common.Constants;
import com.me.data.local.SharedPrefHelper;
import com.me.data.model.exam.Question;
import com.me.fanyin.zbme.views.course.play.utils.FileUtil;
import com.me.fanyin.zbme.views.course.play.utils.NanoHTTPD;
import com.me.fanyin.zbme.views.download.DownloadManager;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.File;
import java.util.List;

/**
 * Created by fzw on 2017/4/14 0014.
 */

public class PhoneAppContext extends AppContext implements NetStateChangeReceiver.NetStateChangeObserver {

    private Handler handler;

    //题库
    private List<Question> questionlist;
    //答题报告中的数据
    private List<Question> allList;//所有的题--题冒题只是一个题
    private List<Question> errorList;//错误的题包括题冒题
    private String scanExamId;//扫描的examId
    private String scanSubjectId;//扫描的subjectId
    private boolean isScanTakIn=false;//只有扫描的时候才为true，出来后设为false
    private String scanExaminationId;//扫描的试卷ID
    private String scanTypeId;//扫描的试卷类型的ID

    @Override
    public void onCreate() {
        super.onCreate();
        startApplicationService();
        DownloadManager.init(this);
        NetStateChangeReceiver.registerReceiver(this);
        NetStateChangeReceiver.registerObserver(this);
        CrashReport.initCrashReport(getApplicationContext(), "1d5a200bdb", false);
    }



    @Override
    public void onTerminate() {
        super.onTerminate();
        NetStateChangeReceiver.unregisterReceiver(this);
        httpserver.stop();
    }

    @Override
    public void onNetDisconnected() {
        int downloadingCount = DownloadManager.getInstance().getDownloadingCount();
        if (downloadingCount > 0) {
            ToastBarUtils.showToast(this, "网络出现异常，已暂停下载");
            Log.i("×××××××××××××××××××","××××××××××××××××××× 网络出现异常，已暂停下载");
            DownloadManager.getInstance().pauseAll();
        }
    }

    @Override
    public void onNetConnected(String network) {
        int downloadingCount = DownloadManager.getInstance().getDownloadingCount();
        if (downloadingCount > 0) {
            switch (network) {
                case NetWorkUtils.NETWORK_TYPE_2G:
                case NetWorkUtils.NETWORK_TYPE_3G:
                case NetWorkUtils.NETWORK_TYPE_WAP:
                case NetWorkUtils.NETWORK_TYPE_UNKNOWN:
                    if (SharedPrefHelper.getInstance().canUseIn4G()) {  //34g 可用
                        ToastBarUtils.showToast(this, "当前已切换至非wifi网络，已为您暂停下载");
                    }
//                    else {
//                        ToastBarUtils.showToast(this, "您已设置不允许非wi-fi环境下下载");
//                    }
                    Log.i("×××××××××××××××××××","××××××××××××××××××× 当前已切换至非wifi网络，已为您暂停下载 暂停所有");
                    DownloadManager.getInstance().pauseAll();
                    break;
                case NetWorkUtils.NETWORK_TYPE_DISCONNECT:

                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 启动NanoHTTP服务
     */
    private NanoHTTPD httpserver;

    private void startApplicationService() {

        // 启动HTTP服务
        if (TextUtils.isEmpty(FileUtil.getKeyPath(this))) {
            Toast.makeText(this, "当前没有SD卡存储，下载课程功能无法使用", Toast.LENGTH_LONG)
                    .show();
        }
        try {
            int port = Constants.SERVER_PORT;
            if (httpserver == null) {
                httpserver = new NanoHTTPD(port, new File(FileUtil.getKeyPath(this)), getApplicationContext());
            }
            System.out.println("代理服务启动成功");
        } catch (Exception e) {

        }
    }

    public void setAllList(List<Question> allList) {
        this.allList = allList;
    }

    public List<Question> getAllList() {
        return allList;
    }

    public List<Question> getQuestionlist() {
        return questionlist;
    }

    public void setQuestionlist(List<Question> questionlist) {
        this.questionlist = questionlist;
    }

    public List<Question> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<Question> errorList) {
        this.errorList = errorList;
    }

    public String getScanExamId() {
        return scanExamId;
    }

    public void setScanExamId(String scanExamId) {
        this.scanExamId = scanExamId;
    }

    public String getScanSubjectId() {
        return scanSubjectId;
    }

    public void setScanSubjectId(String scanSubjectId) {
        this.scanSubjectId = scanSubjectId;
    }

    public boolean isScanTakIn() {
        return isScanTakIn;
    }

    public void setScanTakIn(boolean scanTakIn) {
        isScanTakIn = scanTakIn;
    }

    public String getScanExaminationId() {
        return scanExaminationId;
    }

    public void setScanExaminationId(String scanExaminationId) {
        this.scanExaminationId = scanExaminationId;
    }

    public String getScanTypeId() {
        return scanTypeId;
    }

    public void setScanTypeId(String scanTypeId) {
        this.scanTypeId = scanTypeId;
    }

    public boolean getIsScanTakIn() {
        return isScanTakIn;
    }

    public void setIsScanTakIn(boolean isScanTakIn) {
        this.isScanTakIn = isScanTakIn;
    }

}
