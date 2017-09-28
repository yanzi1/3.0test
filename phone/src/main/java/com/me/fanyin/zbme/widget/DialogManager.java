package com.me.fanyin.zbme.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.IdRes;
import android.support.annotation.StyleRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.me.fanyin.zbme.R;


/**
 * Created by xd on 2017/4/6.
 */

public class DialogManager {
    public static final int EMAIL = 1;
    public static final int TELEPHONE = 2;

    private static Dialog dialog;

    /*****************************  user模块start  *****************************/
    public static void showGetBackPswDialog(Context context, final GetBackPswDialogListener listener, int imageRes, String content) {

        ViewGroup rootView = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.user_dialog_layout, null);
        ImageView iconIv = (ImageView) rootView.findViewById(R.id.user_dialog_icon_iv);
        if (imageRes != 0) {
            iconIv.setImageResource(imageRes);
        }
        TextView contentTv = (TextView) rootView.findViewById(R.id.user_dialog_content_tv);
        if (!TextUtils.isEmpty(content)) {
            contentTv.setVisibility(View.VISIBLE);
            contentTv.setText(content);
        }
        rootView.findViewById(R.id.user_dialog_gb_psw_layout).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.user_dialog_gb_psw_by_email_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setGetBackPswType(EMAIL);
                dialog.dismiss();
                dialog = null;
            }
        });
        rootView.findViewById(R.id.user_dialog_gb_psw_by_phone_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.setGetBackPswType(TELEPHONE);
                dialog.dismiss();
                dialog = null;
            }
        });

        View closeWindow = rootView.findViewById(R.id.user_dialog_close_window_iv);
        closeWindow.setVisibility(View.VISIBLE);
        closeWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialog = null;
            }
        });
        initDialog(context, rootView);

    }

    public static void showOneButtonDialog(Context context, final OneButtonDialogListener listener, int imageRes, String content, String btContent) {
        ViewGroup rootView = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.user_dialog_layout, null);
        ImageView iconIv = (ImageView) rootView.findViewById(R.id.user_dialog_icon_iv);
        if (imageRes != 0) {
            iconIv.setImageResource(imageRes);
        }

        TextView contentTv = (TextView) rootView.findViewById(R.id.user_dialog_content_tv);
        if (!TextUtils.isEmpty(content)) {
            contentTv.setVisibility(View.VISIBLE);
            contentTv.setText(content);
        }

        Button bt = (Button) rootView.findViewById(R.id.user_dialog_bt);
        if (!TextUtils.isEmpty(btContent))
            bt.setText(btContent);
        else
            bt.setText("确认");
        bt.setVisibility(View.VISIBLE);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.dialogBtClick();
                }
                dialog.dismiss();
                dialog = null;
            }
        });

        initDialog(context, rootView);
    }

    public static void showConfirmWithCancelDialog(Context context, final ConfirmWithCancelDialogListener clickListener, int imageRes, String content, String confirmText, String cancelText) {
        ViewGroup rootView = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.user_dialog_layout, null);
        TextView contentTv = (TextView) rootView.findViewById(R.id.user_dialog_content_tv);
        if (!TextUtils.isEmpty(content)) {
            contentTv.setVisibility(View.VISIBLE);
            contentTv.setText(content);
        }

        ImageView iconIv = (ImageView) rootView.findViewById(R.id.user_dialog_icon_iv);
        if (imageRes != 0) {
            iconIv.setImageResource(imageRes);
        }

        rootView.findViewById(R.id.user_dialog_confirm_with_cancel_layout).setVisibility(View.VISIBLE);

        Button confirmBt = (Button) rootView.findViewById(R.id.user_dialog_confirm_bt);
        if (TextUtils.isEmpty(confirmText)) {
            confirmText = "确认";
        }
        confirmBt.setText(confirmText);
        confirmBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.confirm();
                dialog.dismiss();
                dialog = null;
            }
        });

        Button cancelBt = (Button) rootView.findViewById(R.id.user_dialog_cancel_bt);
        if (TextUtils.isEmpty(cancelText)) {
            cancelText = "取消";
        }
        cancelBt.setText(cancelText);
        cancelBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.cancel();
                dialog.dismiss();
                dialog = null;
            }
        });

        initDialog(context, rootView);
    }

    public static Dialog getDialog() {
        return dialog;
    }

    /*****************************  user模块end  *****************************/

    public static void showOneButtonDialog(Context context, final OneButtonDialogListener listener, String content, @ColorRes int contentTextColorRes, String btnText) {
        showOneButtonDialog(context, listener, content, contentTextColorRes, btnText, false, "");
    }

    public static void showOneButtonDialog(Context context, final OneButtonDialogListener listener, String content, @ColorRes int contentTextColorRes, String btnText, boolean isShowTitle, String titleMsg) {
        showOneButtonDialog(context, listener, content, contentTextColorRes, btnText, isShowTitle, titleMsg, false, false);
    }

    public static void showOneButtonDialog(Context context, final OneButtonDialogListener listener, String content, @ColorRes int contentTextColorRes, String btnText, boolean isShowTitle, String titleMsg, boolean canceledOnTouchOutside, final boolean isOnKeyBackDismiss) {
        ViewGroup rootView = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.widget_text_with_one_btn_dialog, null);

        View outside_bg_v = rootView.findViewById(R.id.outside_bg_v);
        if (canceledOnTouchOutside) {
            outside_bg_v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    dialog = null;
                }
            });
        }

        TextView text_dialog_title_tv = (TextView) rootView.findViewById(R.id.text_dialog_title_tv);
        if (isShowTitle  && !TextUtils.isEmpty(titleMsg)) {
            text_dialog_title_tv.setVisibility(View.VISIBLE);
            text_dialog_title_tv.setText(titleMsg);
        } else {
            text_dialog_title_tv.setVisibility(View.GONE);
        }

        TextView text_dialog_content_tv = (TextView) rootView.findViewById(R.id.text_dialog_content_tv);
        if (!TextUtils.isEmpty(content)) {
            text_dialog_content_tv.setVisibility(View.VISIBLE);
            if (contentTextColorRes != 0)
                text_dialog_content_tv.setTextColor(ContextCompat.getColor(context, contentTextColorRes));
            text_dialog_content_tv.setText(content);
        }

        TextView text_dailog_btn_tv = (TextView) rootView.findViewById(R.id.text_dailog_btn_tv);
        if (TextUtils.isEmpty(btnText)) {
            btnText = "确认";
        }
        text_dailog_btn_tv.setText(btnText);
        text_dailog_btn_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.dialogBtClick();
                }
                dialog.dismiss();
                dialog = null;
            }
        });

        initDialog(context, rootView, R.style.user_dialog, canceledOnTouchOutside, isOnKeyBackDismiss);
    }

    public static void showConfirmWithCancelDialog(Context context, final ConfirmWithCancelDialogListener clickListener, String content, @ColorRes int contentTextColorRes, String confirmText, String cancelText) {
        showConfirmWithCancelDialog(context, clickListener, content, contentTextColorRes, confirmText, cancelText, false, null);
    }

    public static void showConfirmWithCancelDialog(Context context, final ConfirmWithCancelDialogListener clickListener, String content, @ColorRes int contentTextColorRes, String confirmText, String cancelText, boolean isShowTitle, String titleMsg) {
        showConfirmWithCancelDialog(context, clickListener, content, contentTextColorRes, confirmText, cancelText, isShowTitle, titleMsg, false, false);
    }

    public static void showConfirmWithCancelDialog(Context context, final ConfirmWithCancelDialogListener clickListener, String content, @ColorRes int contentTextColorRes, String confirmText, String cancelText, boolean isShowTitle, String titleMsg, boolean canceledOnTouchOutside, final boolean isOnKeyBackDismiss) {
        ViewGroup rootView = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.widget_text_with_confirm_cancel_dialog, null);

        View outside_bg_v = rootView.findViewById(R.id.outside_bg_v);
        if (canceledOnTouchOutside) {
            outside_bg_v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    dialog = null;
                }
            });
        }

        TextView text_dialog_title_tv = (TextView) rootView.findViewById(R.id.text_dialog_title_tv);
        if (isShowTitle  && !TextUtils.isEmpty(titleMsg)) {
            text_dialog_title_tv.setVisibility(View.VISIBLE);
            text_dialog_title_tv.setText(titleMsg);
        } else {
            text_dialog_title_tv.setVisibility(View.GONE);
        }

        TextView text_dialog_content_tv = (TextView) rootView.findViewById(R.id.text_dialog_content_tv);
        if (!TextUtils.isEmpty(content)) {
            text_dialog_content_tv.setVisibility(View.VISIBLE);
            if (contentTextColorRes != 0)
                text_dialog_content_tv.setTextColor(ContextCompat.getColor(context, contentTextColorRes));
            text_dialog_content_tv.setText(content);
        }

        TextView text_dailog_confirm_tv = (TextView) rootView.findViewById(R.id.text_dailog_confirm_tv);
        if (TextUtils.isEmpty(confirmText)) {
            confirmText = "确认";
        }
        text_dailog_confirm_tv.setText(confirmText);
        text_dailog_confirm_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialog = null;
                clickListener.confirm();

            }
        });

        TextView text_dailog_cancel_tv = (TextView) rootView.findViewById(R.id.text_dailog_cancel_tv);
        if (TextUtils.isEmpty(cancelText)) {
            cancelText = "取消";
        }
        text_dailog_cancel_tv.setText(cancelText);
        text_dailog_cancel_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.cancel();
                dialog.dismiss();
                dialog = null;
            }
        });

        initDialog(context, rootView, R.style.user_dialog, canceledOnTouchOutside, isOnKeyBackDismiss);
    }


    private static void setTextView(View view, @IdRes int id, String content) {
        ((TextView) view.findViewById(id)).setText(content);
    }

    public static Dialog showInflateDialog(Context context, ViewGroup rootView) {
        initDialog(context, rootView);
        return dialog;
    }

    /**
     * 显示微信公众号二维码图片的dialog
     */
    public static void showWeixinQrDialog(Context context, final OnChildClickListener listener) {
        ViewGroup rootView = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.widget_about_us_weixin_qr_dialog, null);
        final LinearLayout about_us_weixin_qr_rl = (LinearLayout) rootView.findViewById(R.id.about_us_weixin_qr_rl);
        final View about_us_bg_v = rootView.findViewById(R.id.outside_bg_v);
        about_us_weixin_qr_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.OnChildClick(about_us_weixin_qr_rl);
                }
//                dialog.dismiss();
//                dialog = null;
            }
        });
        about_us_bg_v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialog = null;
            }
        });

        initDialog(context, rootView, R.style.user_dialog, true, true);
    }

    /**
     * 首页广告页面弹出逻辑
     *
     * @param context
     * @param imgUrl   图片地址
     * @param listener 按钮点击回调
     */
    public static void showADDialog(final Context context, String imgUrl, final ConfirmWithCancelDialogListener listener) {
        View view = LayoutInflater.from(context).inflate(R.layout.widgert_ad_dialog, null);

        ImageView iv_ad_close = (ImageView) view.findViewById(R.id.iv_ad_close);
        iv_ad_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.cancel();
                }
                dialog.dismiss();
                dialog = null;
            }
        });
        ImageView iv_ad_link = (ImageView) view.findViewById(R.id.iv_ad_link);
        Glide.with(context).load(imgUrl)
//                    .placeholder(R.mipmap.main_type_icon_banner_img)
//                    .error(R.mipmap.img_default_course_book)
                .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存全尺寸
                .fitCenter()
                .into(iv_ad_link);
        iv_ad_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.confirm();
                }
                dialog.dismiss();
            }
        });
        initDialog(context, view, R.style.adDialogStyle, false, false);
    }

    private static void initDialog(Context context, View rootView) {
        initDialog(context, rootView, R.style.user_dialog);
    }

    private static void initDialog(Context context, View rootView, @StyleRes int styleRes) {
        initDialog(context, rootView, R.style.user_dialog, false);
    }

    private static void initDialog(Context context, View rootView, @StyleRes int styleRes, boolean canceledOnTouchOutside) {
        initDialog(context, rootView, R.style.user_dialog, false, false);
    }

    private static void initDialog(Context context, View rootView, @StyleRes int styleRes, boolean canceledOnTouchOutside, final boolean isOnKeyBackDismiss) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }

        dialog = new Dialog(context, styleRes);
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
        dialog.setCancelable(isOnKeyBackDismiss);
        dialog.setContentView(rootView);
        getLayoutParams(context, dialog);
        dialog.show();
    }

    private static void getLayoutParams(Context context, Dialog dialog) {
        DisplayMetrics display = context.getResources().getDisplayMetrics();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = display.widthPixels; //设置宽度
        lp.height = display.heightPixels; //设置高度
        dialog.getWindow().setAttributes(lp);

    }

    public interface GetBackPswDialogListener {
        void setGetBackPswType(int type);
    }

    public interface OneButtonDialogListener {
        void dialogBtClick();
    }

    public interface ConfirmWithCancelDialogListener {
        void confirm();

        void cancel();
    }

    public interface OnChildClickListener {
        void OnChildClick(View view);
    }
}
