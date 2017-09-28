package com.me.share.utils;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

import com.me.share.bean.ShareResponseBean;
import com.me.share.callback.ShareCallBack;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by xd on 2017/3/28.
 */

public class ShareUtils implements Handler.Callback, PlatformActionListener {
    public static final int SHARE_SUCCESS=0;
    public static final int SHARE_CANCEL=1;
    public static final int SHARE_FAIL=2;
    public static final int WXTYPE_MOMENTS=1;
    public static final int WXTYPE_WECHAT=2;
    public static final int LOGIN_PLATFORM_WECHAT=1;
    public static final int LOGIN_PLATFORM_QQ=2;
    public static final int LOGIN_PLATFORM_WEIBO=3;


    public ShareUtils(ShareCallBack callBackListener){
        this.callBackListener=callBackListener;
    }

    private Handler handler=new Handler(Looper.getMainLooper(),this);

    private ShareCallBack callBackListener;

    /**
     * 设置分享回调
     * @param callBackListener
     */
    public void setShareCallBackListener(ShareCallBack callBackListener){
        this.callBackListener=callBackListener;
    }

    /**
     * 分享到微博（纯文本）
     * @param text
     */
    public void shareToWeiboText(String text){
        shareToWeiboImageAndText(text,null,null,null);
    }

    /**
     * 分享到微博（图文）
     * @param text
     * @param imageUrl 网络图片URL
     * @param imagePath 本地图片URL
     */
    public void shareToWeiboImageAndText(String text,String url, String imageUrl,String imagePath){
        Platform platform = ShareSDK.getPlatform(SinaWeibo.NAME);
        final Platform.ShareParams params=new Platform.ShareParams();

        if (!TextUtils.isEmpty(imagePath)){
            params.setImagePath(imagePath);
        }
        if (!TextUtils.isEmpty(imageUrl)){
            params.setImageUrl(imageUrl);
        }
        if (!TextUtils.isEmpty(url)){
            if (!hasClient(SinaWeibo.NAME))
                params.setUrl(url);
            else
                text+=url;
        }
        params.setText(text);
//        if (platform.isAuthValid()){
            platform.setPlatformActionListener(this);
            platform.share(params);
//        }else{
//            platform.setPlatformActionListener(new PlatformActionListener() {
//                @Override
//                public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
//                    platform.setPlatformActionListener(ShareUtils.this);
//                    platform.share(params);
//                }
//
//                @Override
//                public void onError(Platform platform, int i, Throwable throwable) {
//                    ShareUtils.this.onError(platform,i,throwable);
//                }
//
//                @Override
//                public void onCancel(Platform platform, int i) {
//                    ShareUtils.this.onCancel(platform,i);
//                }
//            });
//            platform.SSOSetting(false);
//            platform.showUser(null);
//        }

    }

    /**
     * 分享到QQ（图片）
     * @param imageUrl 网络图片URL
     * @param imagePath 本地图片URL
     */
    public void shareToQQImage(String imageUrl,String imagePath){
        shareToQQImageAndText(null,null,null,imageUrl,imagePath);
    }

    /**
     * 分享到QQ（链接）
     * @param title 标题
     * @param titleUrl 标题url
     * @param text 内容
     * @param imageUrl 网络图片URL
     * @param imagePath 本地图片URL
     */
    public void shareToQQImageAndText(String title, String titleUrl, String text
            , String imageUrl, String imagePath) {
        shareToQQMusic(title,titleUrl,text,imageUrl,imagePath,null);
    }

    /**
     * 分享到QQ（音乐）
     * @param title
     * @param titleUrl
     * @param text
     * @param imageUrl
     * @param imagePath
     * @param musicUrl
     */
    public void shareToQQMusic(String title, String titleUrl, String text
            , String imageUrl, String imagePath,String musicUrl){
        Platform platform = ShareSDK.getPlatform(QQ.NAME);
        Platform.ShareParams params=new Platform.ShareParams();

        if (!TextUtils.isEmpty(title)){
            params.setTitle(title);
        }
        if (!TextUtils.isEmpty(titleUrl)){
            params.setTitleUrl(titleUrl);
        }
        if (!TextUtils.isEmpty(text)){
            params.setText(text);
        }
        if (!TextUtils.isEmpty(imagePath)){
            params.setImagePath(imagePath);
        }
        if (!TextUtils.isEmpty(imageUrl)){
            params.setImageUrl(imageUrl);
        }
        if (!TextUtils.isEmpty(musicUrl)){
            params.setMusicUrl(musicUrl);
        }
        platform.setPlatformActionListener(this);
        platform.share(params);
    }

    public void shareToQzoneStory(String text,String imagePath,String imageUrl
            ,String site,String siteUrl){
        shareToQzoneImageAndText(null,null,text,imagePath,imageUrl,site,siteUrl);
    }

    public void shareToQzoneText(String title,String titleUrl,String text
            ,String site,String siteUrl){
        shareToQzoneImageAndText(title,titleUrl,text,null,null,site,siteUrl);

    }

    public void shareToQzoneImageAndText(String title,String titleUrl,String text
            ,String imagePath,String imageUrl,String site,String siteUrl){
        Platform platform = ShareSDK.getPlatform(QZone.NAME);
        Platform.ShareParams params=new Platform.ShareParams();

        if (!TextUtils.isEmpty(title)){
            params.setTitle(title);
        }
        if (!TextUtils.isEmpty(titleUrl)){
            params.setTitleUrl(titleUrl);
        }
        if (!TextUtils.isEmpty(text)){
            params.setText(text);
        }
        if (!TextUtils.isEmpty(imagePath)){
            params.setImagePath(imagePath);
        }
        if (!TextUtils.isEmpty(imageUrl)){
            params.setImageUrl(imageUrl);
        }
        if (!TextUtils.isEmpty(site)){
            params.setSite(site);
        }
        if (!TextUtils.isEmpty(siteUrl)){
            params.setSiteUrl(siteUrl);
        }
        platform.setPlatformActionListener(this);
        platform.share(params);
    }

    public void shareToWechatText(int wxtype,String title,String text){
        shareToWechat(wxtype,Platform.SHARE_TEXT,title,text,null,null,null,null,null,null,null);
    }

    public void shareToWechatImage(int wxtype,String title,String text
            ,String imageUrl,String imagePath,Bitmap imageData){
        shareToWechat(wxtype,Platform.SHARE_IMAGE,title,text,imageUrl,imagePath,imageData,null,null,null,null);
    }

    public void shareToWechatEmoji(int wxtype,String title,String text
            ,String imageUrl,String imagePath, Bitmap imageData){
        shareToWechat(wxtype,Platform.SHARE_EMOJI,title,text,imageUrl,imagePath,imageData,null,null,null,null);
    }

    public void shareToWechatVideo(int wxtype,String title,String text
            ,String imageUrl,String imagePath,Bitmap imageData,String url){
        shareToWechat(wxtype,Platform.SHARE_VIDEO,title,text,imageUrl,imagePath,imageData,url,null,null,null);
    }

    public void shareToWechatWebPage(int wxtype,String title,String text
            ,String imageUrl,String imagePath,Bitmap imageData,String url){
        shareToWechat(wxtype,Platform.SHARE_WEBPAGE,title,text,imageUrl,imagePath,imageData,url,null,null,null);
    }

    public void shareToWechatMusic(int wxtype,String title,String text
            ,String imageUrl,String imagePath,Bitmap imageData,String url,String musicUrl){
        shareToWechat(wxtype,Platform.SHARE_MUSIC,title,text,imageUrl,imagePath,imageData,url,musicUrl,null,null);
    }

    public void shareToWechatApps(int wxtype,String title,String text
            ,String imageUrl,String imagePath,Bitmap imageData,String url,String filePath,String extInfo){
        shareToWechat(wxtype,Platform.SHARE_APPS,title,text,imageUrl,imagePath,imageData,url,null,filePath,extInfo);
    }

    public void shareToWechatFile(int wxtype,String title,String text
            ,String imageUrl,String imagePath,Bitmap imageData,String url,String filePath){
        shareToWechat(wxtype,Platform.SHARE_FILE,title,text,imageUrl,imagePath,imageData,url,null,filePath,null);
    }

    public void shareToWechat(int wxtype, int shareType, String title, String text
            , String imageUrl, String imagePath, Bitmap imageData, String url
            , String musicUrl, String filePath, String extInfo){
        String pfStr="";
        if (wxtype==WXTYPE_MOMENTS){
            pfStr=WechatMoments.NAME;
        }else if (wxtype==WXTYPE_WECHAT){
            pfStr=Wechat.NAME;
        }else{
            throw new RuntimeException("this wxtype is invalid");
        }
//        if (shareType > Platform.SHARE_EMOJI || shareType < Platform.SHARE_TEXT || shareType==3){
//            throw new RuntimeException("this type is invalid");
//        }
        Platform platform = ShareSDK.getPlatform(pfStr);
        Platform.ShareParams params=new Platform.ShareParams();
        params.setShareType(shareType);

        if (!TextUtils.isEmpty(title)){
            params.setTitle(title);
        }
        if (!TextUtils.isEmpty(text)){
            params.setText(text);
        }
        if (!TextUtils.isEmpty(imagePath)){
            params.setImagePath(imagePath);
        }
        if (!TextUtils.isEmpty(imageUrl)){
            params.setImageUrl(imageUrl);
        }
        if (imageData!=null){
            params.setImageData(imageData);
        }
        if (!TextUtils.isEmpty(url)){
            params.setUrl(url);
        }
        if (!TextUtils.isEmpty(musicUrl)){
            params.setMusicUrl(musicUrl);
        }
        if (!TextUtils.isEmpty(filePath)){
            params.setFilePath(filePath);
        }
        if (!TextUtils.isEmpty(extInfo)){
            params.setExtInfo(extInfo);
        }
        platform.setPlatformActionListener(this);
        platform.share(params);
    }


    public boolean hasClient(String platform){
        return ShareSDK.getPlatform(platform).isClientValid();
    }

    //---------- login ---------------
    public void platformLogin(int platformType){
        String pfStr="";
        if (platformType==LOGIN_PLATFORM_QQ){
            pfStr=QQ.NAME;
        }else if (platformType==LOGIN_PLATFORM_WECHAT){
            pfStr=Wechat.NAME;
        }else if (platformType==LOGIN_PLATFORM_WEIBO){
            pfStr=SinaWeibo.NAME;
        }else{
            throw new RuntimeException("platformType is invalid");
        }

        Platform platform = ShareSDK.getPlatform(pfStr);
        if (platform.isAuthValid()){
            String userId = platform.getDb().getUserId();
            if (userId!=null){
                if (callBackListener!=null){
                    callBackListener.checkIsRegister(platform);
                }
                return;
            }
        }
        platform.setPlatformActionListener(this);
        platform.SSOSetting(false);
        platform.showUser(null);
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (callBackListener!=null){
            ShareResponseBean shareBean= (ShareResponseBean) msg.obj;
            switch (shareBean.getStatus()){
                case SHARE_SUCCESS:
                    callBackListener.onComplete(shareBean.getPlatform()
                            ,shareBean.getAction(),shareBean.getHashMap());
                    break;
                case SHARE_CANCEL:
                    callBackListener.onCancel(shareBean.getPlatform(),shareBean.getAction());
                    break;
                case SHARE_FAIL:
                    callBackListener.onError(shareBean.getPlatform()
                            ,shareBean.getAction(),shareBean.getThrowable());
                    break;
            }
        }
        return true;
    }


    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        Message m=new Message();
        m.obj=new ShareResponseBean(SHARE_SUCCESS,platform,i,hashMap);
        handler.sendMessage(m);
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        Message m=new Message();
        m.obj=new ShareResponseBean(SHARE_FAIL,platform,i,throwable);
        handler.sendMessage(m);
    }

    @Override
    public void onCancel(Platform platform, int i) {
        Message m=new Message();
        m.obj=new ShareResponseBean(SHARE_CANCEL,platform,i);
        handler.sendMessage(m);
    }
}
