package com.me.fanyin.zbme.widget;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.me.core.utils.ToastBarUtils;
import com.me.data.model.main.MainDetailItemBean;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.views.main.event.ShareResultEvent;
import com.me.share.callback.ShareCallBack;
import com.me.share.utils.ShareUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by jjr on 2017/4/19.
 */

public class ShareDialog extends AppCompatDialog implements ShareCallBack {

    @BindView(R.id.share_dialog_weixin_ll)
    LinearLayout share_dialog_weixin_ll;
    @BindView(R.id.share_dialog_sinaweibo_ll)
    LinearLayout share_dialog_sinaweibo_ll;
    @BindView(R.id.share_dialog_qqzone_ll)
    LinearLayout share_dialog_qqzone_ll;
    @BindView(R.id.share_dialog_qq_ll)
    LinearLayout share_dialog_qq_ll;
    @BindView(R.id.share_dialog_pengyouquan_ll)
    LinearLayout share_dialog_pengyouquan_ll;
    @BindView(R.id.share_dialog_cancel_tv)
    TextView share_dialog_cancel_tv;
    private MainDetailItemBean detailItemBean;
    private ShareUtils shareUtils;
    private Activity mActivity;

    public ShareDialog(@NonNull Activity activity) {
        this(activity, 0);
    }

    public ShareDialog(@NonNull Activity activity, @StyleRes int themeResId) {
        this(activity, themeResId, null);
    }

    public ShareDialog(@NonNull Activity activity, @StyleRes int themeResId, MainDetailItemBean detailItemBean) {
        super(activity, themeResId);
        this.mActivity = activity;
        this.detailItemBean = detailItemBean;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shareUtils = new ShareUtils(this);
        View view = getLayoutInflater().inflate(R.layout.widget_share_dialog, null);
        setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        Window window = getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = mActivity.getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        // 设置显示位置
        onWindowAttributesChanged(wl);

        initViews(view);
    }

    private void initViews(View view) {
        ButterKnife.bind(this, view);
        share_dialog_weixin_ll.setBackgroundResource(R.drawable.selectable_item_background);
        share_dialog_sinaweibo_ll.setBackgroundResource(R.drawable.selectable_item_background);
        share_dialog_qqzone_ll.setBackgroundResource(R.drawable.selectable_item_background);
        share_dialog_qq_ll.setBackgroundResource(R.drawable.selectable_item_background);
        share_dialog_pengyouquan_ll.setBackgroundResource(R.drawable.selectable_item_background);
        share_dialog_cancel_tv.setBackgroundResource(R.drawable.selectable_item_background);
    }

    @OnClick({R.id.share_dialog_weixin_ll, R.id.share_dialog_sinaweibo_ll, R.id.share_dialog_qqzone_ll, R.id.share_dialog_qq_ll, R.id.share_dialog_pengyouquan_ll, R.id.share_dialog_cancel_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.share_dialog_weixin_ll:
                shareUtils.shareToWechatWebPage(ShareUtils.WXTYPE_WECHAT, detailItemBean.getTitle(), detailItemBean.getDes(), detailItemBean.getImage(), null, null, detailItemBean.getLink().contains("http://") ? detailItemBean.getLink() : "http://" + detailItemBean.getLink());
                break;
            case R.id.share_dialog_sinaweibo_ll:
                shareUtils.shareToWeiboImageAndText(detailItemBean.getTitle() + "\n" + detailItemBean.getDes(), (detailItemBean.getLink().contains("http://") ? detailItemBean.getLink() : "http://" + detailItemBean.getLink()), /*detailItemBean.getImage()*/null, null);
                break;
            case R.id.share_dialog_qqzone_ll:
                shareUtils.shareToQzoneImageAndText(detailItemBean.getTitle(), detailItemBean.getLink().contains("http://") ? detailItemBean.getLink() : "http://" + detailItemBean.getLink(), detailItemBean.getDes(), null, detailItemBean.getImage(), "会计云课堂", "http://www.dongao.com");
                break;
            case R.id.share_dialog_qq_ll:
                shareUtils.shareToQQImageAndText(detailItemBean.getTitle(), detailItemBean.getLink().contains("http://") ? detailItemBean.getLink() : "http://" + detailItemBean.getLink(), detailItemBean.getDes(), detailItemBean.getImage(), null);
                break;
            case R.id.share_dialog_pengyouquan_ll:
                shareUtils.shareToWechatWebPage(ShareUtils.WXTYPE_MOMENTS, detailItemBean.getTitle(), detailItemBean.getDes(), detailItemBean.getImage(), null, null, detailItemBean.getLink().contains("http://") ? detailItemBean.getLink() : "http://" + detailItemBean.getLink());
                break;
            case R.id.share_dialog_cancel_tv:
                EventBus.getDefault().post(new ShareResultEvent());
                break;
        }
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        ToastBarUtils.showToast(mActivity, "分享成功");
        EventBus.getDefault().post(new ShareResultEvent());
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        if ((platform.getName().equals(Wechat.NAME) || platform.getName().equals(WechatMoments.NAME)) && throwable.getClass().getName().equals("cn.sharesdk.wechat.utils.WechatClientNotExistException")) {
            ToastBarUtils.showToast(mActivity, "请下载手机微信客户端");
        } else if ((platform.getName().equals(QZone.NAME) || throwable.getClass().getName().equals("cn.sharesdk.tencent.qzone.QQClientNotExistException"))) {
            ToastBarUtils.showToast(mActivity, "请下载QQ空间客户端");
        } else {
            ToastBarUtils.showToast(mActivity, "分享失败");
        }
        EventBus.getDefault().post(new ShareResultEvent());
    }

    @Override
    public void onCancel(Platform platform, int i) {
        ToastBarUtils.showToast(mActivity, "分享已取消");
        EventBus.getDefault().post(new ShareResultEvent());
    }

    @Override
    public void checkIsRegister(Platform platform) {

    }
}
