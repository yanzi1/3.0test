package com.me.fanyin.zbme.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.TextView;

import com.me.core.utils.StringUtils;
import com.me.fanyin.zbme.R;


/**
 * 透明的等待dialog
 */
public class LoadingDialog extends Dialog {


    public LoadingDialog(Context context) {
        super(context, R.style.CustomDialog);
        setContentView(R.layout.app_view_loading_dialog);
        setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
//        attributes.alpha = 0.7f;
        getWindow().setAttributes(attributes);
        setCancelable(false);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                dismiss();
                break;
        }
        return true;
    }


    public void setDialogMessage(String message) {
        show();
        if (!StringUtils.isEmpty(message)) {
            ((TextView) findViewById(R.id.app_loading_tv)).setText(message);
        }
    }
}