package com.me.fanyin.zbme.widget;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.me.data.common.Constants;
import com.me.data.local.SharedPrefHelper;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.base.BaseActivity;
import com.me.fanyin.zbme.views.MainActivity;
import com.me.fanyin.zbme.views.user.LoginActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class TokenErrorActivity extends BaseActivity {

    @BindView(R.id.text_dialog_content_tv)
    TextView contentTv;
    @BindView(R.id.text_dailog_btn_tv)
    TextView confirmTv;
    @BindView(R.id.rootlayout)
    View rootlayout;

    @Override
    protected void initView() {
        rootlayout.setBackgroundResource(android.R.color.transparent);
    }

    @Override
    protected void initData() {
        contentTv.setText("该账户已在其他设备登录，为了您的账户安全，请您重新登录");
    }

    @OnClick(R.id.text_dailog_btn_tv)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.text_dailog_btn_tv:
                SharedPrefHelper.getInstance().logout();
                SharedPrefHelper.getInstance().setExamSubjectSubListJson("");
                gotoActivity(MainActivity.class);
                Intent tokenErrorIntent=new Intent(this, LoginActivity.class);
                tokenErrorIntent.putExtra(Constants.LOGIN_SUCCESS_TO_PAGE_NAME,"com.dongao.kaoqian.phone.views.MainActivity");
                tokenErrorIntent.putExtra(Constants.IS_TOKEN_ERROR,true);
                startActivity(tokenErrorIntent);
//                gotoActivity(MainActivity.class);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_token_error;
    }

}
