package com.me.fanyin.zbme.views.course.play.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Created by fzw on 2017/7/20 0020.
 */

public class MyWebView  extends WebView{

    public boolean isHaveTable = false;

    public MydViewPager myViewPager;

    public MyWebView(Context context){
        super(context);
    }

    public MyWebView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        getSettings().setJavaScriptEnabled(true);
        final DisplayMetrics dm = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(dm);
        addJavascriptInterface(new JavaScriptInterface(context,this),"imagelistner");
        setWebChromeClient(new MyWebViewClient());
    }

    public void setMyViewPager(MydViewPager viewPager){
        this.myViewPager = viewPager;
    }

    public static class MyWebViewClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if(newProgress == 100){
                view.loadUrl("javascript:(function(){" +
                        "function touchMyTable()"+
                        "{"+
                        "window.imagelistner.openTable(this.innerHTML);"+
                        "}"+
                        "var tables = document.getElementsByTagName(\"table\");" +
                        "for(var i=0;i<tables.length;i++)" +
                        "{" +
                        "tables[i].addEventListener(\"touchstart\",touchMyTable,true)"+
                        "}" +
                        "})()");
            }
        }

    }

    private void addListener(WebView webView) {



    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_UP:
                if(myViewPager != null){
                    myViewPager.isIntercept = true;
                    myViewPager.isSplideTable = false;
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    public static class JavaScriptInterface {

        private Context context;
        private MyWebView webView;

        public JavaScriptInterface(Context context,MyWebView webView) {
            this.context = context;
            this.webView = webView;
        }

        @JavascriptInterface
        public void openTable(String body){
            webView.isHaveTable = true;
            if(webView.myViewPager != null){
                webView.myViewPager.isSplideTable = true;
                webView.myViewPager.isIntercept = false;
            }
        }

    }


}
