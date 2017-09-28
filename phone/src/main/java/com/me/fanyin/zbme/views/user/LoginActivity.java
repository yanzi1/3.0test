package com.me.fanyin.zbme.views.user;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.me.core.utils.DensityUtils;
import com.me.core.utils.NetWorkUtils;
import com.me.data.common.Constants;
import com.me.data.common.Statistics;
import com.me.data.event.LoginNeedCloseEvent;
import com.me.data.local.SharedPrefHelper;
import com.me.data.model.user.UserBean;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.base.BaseMvpActivity;
import com.me.data.event.LoginSuccessEvent;
import com.me.fanyin.zbme.views.MainActivity;
import com.me.fanyin.zbme.widget.DialogManager;
import com.me.share.callback.ShareCallBack;
import com.me.share.utils.ShareUtils;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;

/**
 * 登录
 */
public class LoginActivity extends BaseMvpActivity<LoginView,LoginPersenter> implements LoginView, ShareCallBack {
    @BindView(R.id.status_bar_fix)
    View status_bar_fix;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.login_username_et)
    EditText login_username_et;
    @BindView(R.id.login_password_et)
    EditText login_password_et;
    @BindView(R.id.login_username_clear_iv)
    View login_username_clear_iv;
    @BindView(R.id.login_password_visiable_iv)
    ImageView login_password_visiable_iv;
    @BindView(R.id.login_error_tv)
    TextView login_error_tv;
    @BindView(R.id.login_confirm_bt)
    Button login_confirm_bt;
    @BindView(R.id.login_register_tv)
    View login_register_tv;
    @BindView(R.id.login_forget_psw_tv)
    View login_forget_psw_tv;
    @BindView(R.id.login_weibo_bt)
    View login_weibo_bt;

    private boolean isPswVisiable;

    @Override
    public void initView() {
        EventBus.getDefault().register(this);

        mToolbar.setTitleText(getString(R.string.title_activity_login));

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            setTranslucentStatus();
        }

//        login_username_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus){
//                    login_username_clear_iv.setVisibility(View.VISIBLE);
//                }else{
//                    login_username_clear_iv.setVisibility(View.INVISIBLE);
//                }
//            }
//        });

        login_password_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s) && !TextUtils.isEmpty(login_username_et.getText())){
                    login_confirm_bt.setEnabled(true);
                }else{
                    login_confirm_bt.setEnabled(false);
                }

                login_error_tv.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        login_username_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s) && !TextUtils.isEmpty(login_password_et.getText())){
                    login_confirm_bt.setEnabled(true);
                }else{
                    login_confirm_bt.setEnabled(false);
                }
                if (s.length()>0){
                    login_username_clear_iv.setVisibility(View.VISIBLE);
                }else{
                    login_username_clear_iv.setVisibility(View.INVISIBLE);
                }
                login_error_tv.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void initData() {
        String loginUsername = SharedPrefHelper.getInstance().getLoginUsername();
        if (!TextUtils.isEmpty(loginUsername)){
            login_username_et.setText(loginUsername);
            login_username_et.setSelection(loginUsername.length());
        }
    }

    @OnClick({R.id.login_username_clear_iv,R.id.login_password_visiable_iv
            ,R.id.login_confirm_bt,R.id.login_register_tv
            ,R.id.login_forget_psw_tv,R.id.login_weibo_bt})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_username_clear_iv:
                login_username_et.setText("");
                break;
            case R.id.login_password_visiable_iv:
                if (isPswVisiable){
                    login_password_et.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isPswVisiable =false;
                    login_password_visiable_iv.setImageResource(R.drawable.login_closedeye_icon);
                }else{
                    login_password_et.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isPswVisiable =true;
                    login_password_visiable_iv.setImageResource(R.drawable.login_openeye_icon);
                }
                login_password_et.setSelection(login_password_et.getText().toString().length());
                break;
            case R.id.login_confirm_bt:
                String username = login_username_et.getText().toString().trim();
                String password = login_password_et.getText().toString().trim();
                if (TextUtils.isEmpty(username)){
                    login_error_tv.setVisibility(View.VISIBLE);
                    login_error_tv.setText("账号不能为空");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    login_error_tv.setVisibility(View.VISIBLE);
                    login_error_tv.setText("密码不能为空");
                    return;
                }
                //TODO 登录操作
                MobclickAgent.onEvent(this, Statistics.PROFILE_LOGIN_PHONE);
                mPresenter.login(username,password,NetWorkUtils.getIPAddress(this));
                break;
            case R.id.login_register_tv:
                Intent toPageIntent = getIntent();
                toPageIntent.setClass(this,RegisterActivity.class);
                startActivity(toPageIntent);
                break;
            case R.id.login_forget_psw_tv:
                DialogManager.showGetBackPswDialog(this, new DialogManager.GetBackPswDialogListener() {
                    @Override
                    public void setGetBackPswType(int type) {
                        Bundle bundle=new Bundle();
                        bundle.putInt("getBackPswType",type);
                        gotoActivity(GetBackPswActivity.class,false,bundle);
                    }
                },R.drawable.user_dialog_happy_icon,"请选择你要找回的方式");
                break;
            case R.id.login_weibo_bt:
                MobclickAgent.onEvent(this, Statistics.PROFILE_LOGIN_WEIBO);
                ShareUtils shareUtils=new ShareUtils(this);
                shareUtils.platformLogin(ShareUtils.LOGIN_PLATFORM_WEIBO);
                showDialogLoading();
                break;
        }
    }

    @Override
    public void loginSuccess(UserBean userBean) {
        loginNtalker(userBean.getId(), userBean.getUsername(), 0);
        toPage();
    }

    /**
     *  @params userid: 登录用户的id，只能输入数字、英文字母和“@”、“.”、“_”三种字符。长度小于40,并且不能重复,
     *                   相同的userid会造成会话同步,切记不可重复 【必填】
     *  @params username: 登录用户名，只能包含字母、汉字、数字、_、@、.的字符串。长度小于32,显示于PC客服端,如未填写,
     *                      系统随机会随机生成一个用户名, 如:客人9527
     *  @params userlevel: 登录用户的等级，普通用户“0”，VIP用户传“1”。默认写 0 【必填】
     *  @return  int   0 表示登录成功, 其他数值,参照错误码修改, 如在登录错误的情况下,咨询客服,用户为游客状态,即非登录用户咨询.
     */
    private int loginNtalker(String userid, String username, int userlevel) {
        return 0;
    }

    private void toPage() {
        EventBus.getDefault().post(new LoginSuccessEvent());
        Intent toPageIntent = getIntent();
        String toPageName = toPageIntent.getStringExtra(Constants.LOGIN_SUCCESS_TO_PAGE_NAME);
        if (!TextUtils.isEmpty(toPageName)){
            toPageIntent.setClassName(this,toPageName);
            if (toPageIntent.getBooleanExtra(Constants.IS_TOKEN_ERROR,false)){
                toPageIntent.putExtra("tag", MainActivity.TAG_MAIN);
            }
            startActivity(toPageIntent);
        }
        finish();
    }

    @Override
    public void showError(String message) {
        login_error_tv.setVisibility(View.VISIBLE);
        login_error_tv.setText(message);
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        String id="";
        for (Map.Entry<String, Object> entry: hashMap.entrySet()){
            if (entry!=null){
                if (entry.getKey().equals("idstr")){
                    id= (String) entry.getValue();
                }
                if (entry.getKey().equals("avatar_large")){
                    SharedPrefHelper.getInstance().setUserAvatarImageUrl((String) entry.getValue());
                }
            }
        }
        mPresenter.thirdPartyLogin(id, NetWorkUtils.getIPAddress(this));
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        hideDialogLoading();
        Log.d("hh",platform.getName()+"登录失败 "+throwable.getMessage());

    }

    @Override
    public void onCancel(Platform platform, int i) {
        hideDialogLoading();
    }

    @Override
    public void checkIsRegister(Platform platform) {
        String idstr = platform.getDb().getUserId();
        SharedPrefHelper.getInstance().setUserAvatarImageUrl(platform.getDb().getUserIcon());
        mPresenter.thirdPartyLogin(idstr,NetWorkUtils.getIPAddress(this));
    }

    @Override
    public void thirdPartySuccess() {
        SharedPrefHelper.getInstance().setIsThirdPartyLogin(true);
        toPage();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.user_login_activity;
    }

    @Override
    protected void initInject() {
        getAppComponent().inject(this);
    }


    public void setTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //当状态栏透明后，内容布局会上移，这里使用一个和状态栏高度相同的view来修正内容区域
            status_bar_fix.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtils.getStatusBarHeight(context())));
        }
    }

    @Override
    protected boolean setStatusBarVisiable() {
        return false;
    }

    @Subscribe
    public void needClose(LoginNeedCloseEvent event){
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        boolean isTokenError = getIntent().getBooleanExtra(Constants.IS_TOKEN_ERROR, false);
        if (isTokenError){
            Intent intent=new Intent(this,MainActivity.class);
            intent.putExtra("tag",MainActivity.TAG_MAIN);
            startActivity(intent);
            return true;
        }
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK ){
            boolean isTokenError = getIntent().getBooleanExtra(Constants.IS_TOKEN_ERROR, false);
            if (isTokenError){
                Intent intent=new Intent(this,MainActivity.class);
                intent.putExtra("tag",MainActivity.TAG_MAIN);
                startActivity(intent);
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }
}
