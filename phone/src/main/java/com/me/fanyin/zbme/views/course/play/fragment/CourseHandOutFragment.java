package com.me.fanyin.zbme.views.course.play.fragment;


import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.me.core.utils.NetWorkUtils;
import com.me.data.local.SharedPrefHelper;
import com.me.data.model.play.CourseWare;
import com.me.data.remote.utils.ParamsUtils;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.views.course.play.PlayActivity;
import com.me.fanyin.zbme.views.course.play.utils.StringUtil;
import com.me.fanyin.zbme.views.course.play.widget.MyWebView;
import com.me.fanyin.zbme.views.course.play.widget.WVJBWebViewClient;
import com.me.fanyin.zbme.views.download.DownloadManager;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 我的课程中的 课程讲义
 */
public class CourseHandOutFragment extends Fragment {
    protected MyWebView webView;
    protected ProgressBar pb_progress;
    private static final String mimeType = "text/html";
    private static final String encoding = "utf-8";
    @BindView(R.id.app_view_iv)
    ImageView appViewIv;
    @BindView(R.id.app_message_tv)
    TextView appMessageTv;
    @BindView(R.id.network_error_layout)
    RelativeLayout networkErrorLayout;
    Unbinder unbinder;
    private String urlString;
    private PlayActivity playActivity;
    private WebJSObject webJSObject = new WebJSObject();
    private CourseWare courseWare;
    private MyWebViewClient myWebViewClient;

    public CourseHandOutFragment() {
    }

    public static CourseHandOutFragment getInstance(String url) {
        CourseHandOutFragment f = new CourseHandOutFragment();
        Bundle args = new Bundle();
        args.putString("webUrl", url);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        playActivity = (PlayActivity) getActivity();
        urlString = getArguments().getString("webUrl");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.play_course_info_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        webView = (MyWebView) view.findViewById(R.id.couseinfo_webView);
        webView.setMyViewPager(playActivity.viewpager);
        pb_progress = (ProgressBar) view.findViewById(R.id.couseinfo_pb_progress);
        initWebView();
        initErrorOrLoadingUI();
        if(!TextUtils.isEmpty(urlString)){
            loadUrl();
        }
        return view;
    }

    private void initErrorOrLoadingUI() {
        if (NetWorkUtils.isConnected(getActivity())) {
            appViewIv.setImageResource(R.mipmap.img_special_dataerror);
            appMessageTv.setText(getResources().getString(R.string.app_error_message));
        } else {
            appViewIv.setImageResource(R.mipmap.img_special_nonetwork);
            appMessageTv.setText(getResources().getString(R.string.app_nonetwork_message));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    void initWebView() {
//        webView.setWebViewClient(new WebViewClient());//不打开新的应用

        //进度条
        webView.setWebChromeClient(new MyWebView.MyWebViewClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    pb_progress.setVisibility(View.GONE);
                    if (playActivity.player.mVideoView != null) {
                        callTime(playActivity.player.mVideoView.getCurrentPosition() / 1000, "1");
                    }
                    playActivity.handler.sendEmptyMessage(99);
                }
            }
        });
        webView.getSettings().setDefaultTextEncodingName("UTF-8");
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setSaveFormData(false);
        webView.getSettings().setSupportZoom(false);
        // 开启JavaScript支持
        webView.getSettings().setJavaScriptEnabled(true);

        String ua = webView.getSettings().getUserAgentString();
        webView.getSettings().setUserAgentString(ua + ";dongao/android/PayH5");
//        webView.addJavascriptInterface(webJSObject, "myObject");
        myWebViewClient = new MyWebViewClient(webView);
        webView.setWebViewClient(myWebViewClient);

        webView.clearCache(true);
        webView.clearHistory();
    }

    public void setCourseHandOutUrl(String courseInfoUrl) {
        if (pb_progress == null) {
            return;
        }
        if (networkErrorLayout != null) {
            networkErrorLayout.setVisibility(View.GONE);
        }
        pb_progress.setVisibility(View.VISIBLE);
        urlString = courseInfoUrl;
        loadUrl();
    }

    private void loadUrl() {
        if (courseWare != null) {
            if (courseWare.getId().equals(playActivity.courseWare.getId())) {
                pb_progress.setVisibility(View.GONE);
                return;
            }
        }
        courseWare = playActivity.courseWare;
        if (courseWare == null)
            return;
        if (webView != null) {
            if (NetWorkUtils.isConnected(getActivity())) {
                int type=NetWorkUtils.getNetworkType(getActivity());
                if(type== ConnectivityManager.TYPE_WIFI){ //wifi
                    if(playActivity.isFromSmart){
                        webView.loadUrl(urlString);
                    }else{
                        webView.loadUrl(ParamsUtils.getLectureUrl(courseWare.getId()));
                    }
                }else if(type==ConnectivityManager.TYPE_MOBILE){  //流量
                    boolean isDownload = DownloadManager.getInstance().isCWDownlaoded(courseWare);
                    if(isDownload){
                        String lecturePath=DownloadManager.getInstance().getLecturePath(courseWare);
                        File lectrueFile = new File(lecturePath);
                        if (lectrueFile.exists()) {
                            urlString = "file://" + lecturePath;
                            webView.loadUrl(urlString);
                        } else {
                            if(playActivity.isFromSmart){
                                webView.loadUrl(urlString);
                            }else{
                                webView.loadUrl(ParamsUtils.getLectureUrl(courseWare.getId()));
                            }
                        }
                    }else{
                        if(playActivity.isFromSmart){
                            webView.loadUrl(urlString);
                        }else{
                            webView.loadUrl(ParamsUtils.getLectureUrl(courseWare.getId()));
                        }
                    }
                }
            } else {
                String lecturePath = DownloadManager.getInstance().getLecturePath(courseWare);
                File lectrueFile = new File(lecturePath);
                if (lectrueFile.exists()) {
                    urlString = "file://" + lecturePath;
                    webView.loadUrl(urlString);
                } else {
                    showNetError();
                }
            }
//            urlString="http://cloudclass.api.test.com/courseApi/V4/handout?lectureId=401&deviceType=1&appName=da-cloudclass-app&appVersion=1.0.0&uniqueId=51dd3d3b2a5e1413&model=MHA-AL00&osType=android&osVersion=7.0";
//            urlString="http://m.dongao.com/app/lecture/jiangyi.html";
//            if (urlString.contains("http")) {
//                //判断有没有网络
//                if (NetWorkUtils.isNetworkAvailable(getActivity())) {
//                    webView.loadUrl(urlString);
//                } else {
//                    String lecturePath = DownloadManager.getInstance().getLecturePath(courseWare);
//                    File lectrueFile = new File(lecturePath);
//                    if (lectrueFile.exists()) {
//                        urlString = "file://" + lecturePath;
//                        webView.loadUrl(urlString);
//                    } else {
////                        webView.loadDataWithBaseURL("", "请检查网络连接", mimeType, encoding, "");
//                        showNetError();
//                    }
//                }
//            } else {
//                String lecturePath = DownloadManager.getInstance().getLecturePath(courseWare);
//                File lectrueFile = new File(lecturePath);
//                if (lectrueFile.exists()) {
//                    urlString = "file://" + lecturePath;
//                    webView.loadUrl(urlString);
//                } else {
////                    webView.loadDataWithBaseURL("", "讲义加载失败", mimeType, encoding, "");
//                    showNetError();
//                }
//            }
        }
    }


    public class WebJSObject {

        public void jumpLecture(long time) {
            if (SharedPrefHelper.getInstance().isLectureSync()) {
                if (webView != null) {
                    webView.loadUrl("javascript:jumpLecture("
                            + time + ")");
                }
            }
        }

        @JavascriptInterface
        public void jumpTime(final int time) {
            if (SharedPrefHelper.getInstance().isLectureSync()) {
                playActivity.webSeekTo(time * 1000);
            }
        }
    }

    class MyWebViewClient extends WVJBWebViewClient {
        public MyWebViewClient(WebView webView) {
            super(webView, new WVJBHandler() {
                @Override
                public void request(Object data, WVJBResponseCallback callback) {
                    callback.callback("Response for message from Android!");
                }
            });

            send("", new WVJBResponseCallback() {
                @Override
                public void callback(Object data) {
                }
            });

            registerHandler("setLectureQuestion", new WVJBHandler() {       //提问
                @Override
                public void request(Object data, WVJBResponseCallback callback) {
                    playActivity.gotoAsk(data);
                }
            });

            registerHandler("setLectureNote", new WVJBHandler() {
                @Override
                public void request(Object data, WVJBResponseCallback callback) {   //添加笔记
                    playActivity.gotoNote(data);
                }
            });

            registerHandler("setLectureTime", new WVJBHandler() {   //时间跳转
                @Override
                public void request(Object data, WVJBResponseCallback callback) {
                    String result = data.toString();
                    com.alibaba.fastjson.JSONObject object = JSON.parseObject(result);
                    String seconds = object.getString("time");
                    String type = object.getString("type");
                    playActivity.setPlayIstart();
                    if (type.equals("0")) { //pause
                        playActivity.playPause();
                    } else if (type.equals("1")) {   //start
                        playActivity.playStart();
                    } else if (type.equals("2")) {
                        if (!TextUtils.isEmpty(seconds)) {
                            long seekTime = StringUtil.getLongMills(seconds);
                            playActivity.lectureTime = seekTime + 10000;
                            playerSeekTo(seekTime);
                        }
                    }
                }
            });
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
//            mEmptyViewLayout.showLoading();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
//            mEmptyViewLayout.showContentView();
        }
    }

    public void callNote(JSONObject object) {
        String webNote = object.toString();
        myWebViewClient.callHandler("getLectureNote", webNote, new WVJBWebViewClient.WVJBResponseCallback() {
            @Override
            public void callback(Object data) {
            }
        });
    }

    public void callTime(long time, String type) {
        JSONObject object = null;
        try {
            object = new JSONObject();
            object.put("time", time + "");
            object.put("type", type);
        } catch (Exception e) {

        }
        String webTime = object.toString();
        if (myWebViewClient != null) {
            myWebViewClient.callHandler("getLectureTime", webTime, new WVJBWebViewClient.WVJBResponseCallback() {
                @Override
                public void callback(Object data) {
                }
            });
        }
    }

    private void playerSeekTo(long time) {
        playActivity.seekTo(time);
    }

    /**
     * 设置当前讲义的进度，与播放器同步
     *
     * @param position 当前播放器播放到的时间
     */
    public void setWebViewProgress(long position) {
        webJSObject.jumpLecture(position);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void showNetError() {
        if (pb_progress == null) {
            return;
        }
        pb_progress.setVisibility(View.GONE);
        networkErrorLayout.setVisibility(View.VISIBLE);
//        webView.loadDataWithBaseURL("", "网络不可用 请检查网络连接", mimeType, encoding, "");
    }
}
