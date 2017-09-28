package com.me.fanyin.zbme.views.user;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.UnderlineSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.me.core.exception.ApiException;
import com.me.core.utils.DensityUtils;
import com.me.core.utils.NetWorkUtils;
import com.me.data.common.Constants;
import com.me.data.common.Statistics;
import com.me.data.event.LoginNeedCloseEvent;
import com.me.data.event.LoginSuccessEvent;
import com.me.data.local.SharedPrefHelper;
import com.me.data.model.user.UserBean;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.base.BaseMvpActivity;
import com.me.fanyin.zbme.base.CommonWebViewActivity;
import com.me.fanyin.zbme.views.MainActivity;
import com.me.fanyin.zbme.widget.DialogManager;
import com.me.fanyin.zbme.widget.PhoneEditText;
import com.me.fanyin.zbme.widget.VerificationEditText;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseMvpActivity<RegisterView,RegisterPresenter> implements RegisterView {
    private static final String REGEX_PSW = "^.{4,20}$";

    @BindView(R.id.status_bar_fix)
    View status_bar_fix;
    @BindView(R.id.register_input_number_tv)
    TextView register_input_number_tv;
    @BindView(R.id.register_input_verification_code_tv)
    TextView register_input_verification_code_tv;
    @BindView(R.id.register_set_psw_tv)
    TextView register_set_psw_tv;
    @BindView(R.id.register_input_cell_phone_number_layout)
    View register_input_cell_phone_number_layout;
    @BindView(R.id.register_input_verification_code_layout)
    View register_input_verification_code_layout;
    @BindView(R.id.register_setting_psw_layout)
    View register_setting_psw_layout;
    @BindView(R.id.register_cell_phone_number_et)
    PhoneEditText register_cell_phone_number_et;
    @BindView(R.id.register_cell_phone_number_clear_iv)
    View register_cell_phone_number_clear_iv;
    @BindView(R.id.register_agree_terms_layout)
    View register_agree_terms_layout;
    @BindView(R.id.register_agree_terms_cb)
    CheckBox register_agree_terms_cb;
    @BindView(R.id.register_agree_terms_tv)
    View register_agree_terms_tv;
    @BindView(R.id.register_verification_code_pn_tv)
    TextView register_verification_code_pn_tv;
    @BindView(R.id.register_verification_code_et)
    VerificationEditText register_verification_code_et;
    @BindView(R.id.register_verification_code_time_tv)
    TextView register_verification_code_time_tv;
    @BindView(R.id.register_verification_code_error_tv)
    TextView register_verification_code_error_tv;
    @BindView(R.id.register_psw_et)
    EditText register_psw_et;
    @BindView(R.id.register_psw_visiable_iv)
    ImageView register_psw_visiable_iv;
    @BindView(R.id.register_again_psw_et)
    EditText register_again_psw_et;
    @BindView(R.id.register_password_visiable_iv)
    ImageView register_password_visiable_iv;
    @BindView(R.id.register_setting_psw_error_tv)
    TextView register_setting_psw_error_tv;
    @BindView(R.id.register_next_bt)
    Button register_next_bt;
    @BindView(R.id.register_already_register_tv)
    TextView register_already_register_tv;
    @BindView(R.id.contact_service_tv)
    TextView contact_service_tv;

    private int registerProgress=1;
    private String cell_phone_number;
    private boolean isPswVisiable;
    private List<CountDownTimer> countDownTimerList;
    private boolean isAgainPswVisiable;

    @Override
    public void initView() {
        mToolbar.setTitleText(getString(R.string.register_title_new_user_register));
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            setTranslucentStatus();
        }

        countDownTimerList=new ArrayList<>();
//        register_cell_phone_number_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus){
//                    register_cell_phone_number_clear_iv.setVisibility(View.VISIBLE);
//                }else {
//                    register_cell_phone_number_clear_iv.setVisibility(View.INVISIBLE);
//                }
//            }
//        });
        register_cell_phone_number_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!TextUtils.isEmpty(s) && s.length()==13 ){
                    String[] pss=s.toString().split(" ");
                    StringBuilder sb=new StringBuilder();
                    for (String ps : pss) {
                        sb.append(ps);
                    }
                    String ps=sb.toString();
                    if (ps.matches(Constants.REGEX_PHONE))
                        register_next_bt.setEnabled(true);
                }else{
                    register_next_bt.setEnabled(false);
                }
                if (s.length()>0){
                    register_cell_phone_number_clear_iv.setVisibility(View.VISIBLE);
                }else{
                    register_cell_phone_number_clear_iv.setVisibility(View.INVISIBLE);
                }
                register_already_register_tv.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        InputFilter[] phoneInputFilters = {new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if ((dend <= dstart && end+dest.length() >=14) || dstart==13 || end >=14 || end + dstart>=14){
                    DialogManager.showOneButtonDialog(RegisterActivity.this, new DialogManager.OneButtonDialogListener() {
                        @Override
                        public void dialogBtClick() {

                        }
                    },getString(R.string.phone_format_error),0,null);
                    return "";
                }
                return null;
            }
        }};
        register_cell_phone_number_et.setFilters(phoneInputFilters);


        register_agree_terms_cb.setChecked(true);

        register_verification_code_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s) && s.length()==6){
                    register_next_bt.setEnabled(true);
                }else{
                    register_next_bt.setEnabled(false);
                }
                register_verification_code_error_tv.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        CharSequence psw_et_hint = register_psw_et.getHint();
        SpannableString spannableString=new SpannableString(psw_et_hint);
        spannableString.setSpan(new AbsoluteSizeSpan(12,true)
                ,3, psw_et_hint.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        register_psw_et.setHint(spannableString);
        register_psw_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s) && !TextUtils.isEmpty(register_again_psw_et.getText().toString())
                        && s.length()>3 && register_again_psw_et.getText().length()>3){
                    register_next_bt.setEnabled(true);
                }else {
                    register_next_bt.setEnabled(false);
                }
                register_setting_psw_error_tv.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        register_again_psw_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s) && !TextUtils.isEmpty(register_psw_et.getText().toString())
                        && s.length()>3 && register_psw_et.getText().length()>3){
                    register_next_bt.setEnabled(true);
                }else {
                    register_next_bt.setEnabled(false);
                }
                register_setting_psw_error_tv.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        InputFilter[] inputFilters = {new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (dstart==20 || end >=21 || end + dstart>=21){
                    DialogManager.showOneButtonDialog(RegisterActivity.this, new DialogManager.OneButtonDialogListener() {
                        @Override
                        public void dialogBtClick() {

                        }
                    },"密码长度不能超过20个字符",0,null);
                    return "";
                }
                return null;
            }
        }};
        register_psw_et.setFilters(inputFilters);
        register_again_psw_et.setFilters(inputFilters);


        SpannableString ss=new SpannableString(contact_service_tv.getText());
        ss.setSpan(new UnderlineSpan(),0,contact_service_tv.getText().length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        contact_service_tv.setText(ss);
    }

    @Override
    public void initData() {
        switch (registerProgress){
            case 1:
                register_next_bt.setText("下一步");
                register_cell_phone_number_et.setText("");
                register_input_cell_phone_number_layout.setVisibility(View.VISIBLE);
                register_input_verification_code_layout.setVisibility(View.GONE);
                register_setting_psw_layout.setVisibility(View.GONE);
                register_input_number_tv.setTextColor(ContextCompat.getColor(this,R.color.color_primary));
                register_input_verification_code_tv.setTextColor(ContextCompat.getColor(this,R.color.text_color_primary));
                register_set_psw_tv.setTextColor(ContextCompat.getColor(this,R.color.text_color_primary));
                break;
            case 2:
                register_verification_code_et.setText("");
                register_verification_code_et.requestFocus();
                register_verification_code_time_tv.setEnabled(false);
                register_verification_code_time_tv.setTextColor(ContextCompat.getColor(RegisterActivity.this,R.color.text_color_primary_dark));
                CountDownTimer countDownTimer=new CountDownTimer(60000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        register_verification_code_time_tv.setText((int)(millisUntilFinished/1000)+"s后可重新发送");
                    }

                    @Override
                    public void onFinish() {
                        register_verification_code_time_tv.setEnabled(true);
                        register_verification_code_time_tv.setText(Html.fromHtml("<u>重新发送验证码</u>"));
                        register_verification_code_time_tv.setTextColor(ContextCompat.getColor(RegisterActivity.this,R.color.color_primary));
                    }
                };
                countDownTimer.start();
                countDownTimerList.add(countDownTimer);
                register_next_bt.setEnabled(false);
                register_input_cell_phone_number_layout.setVisibility(View.GONE);
                register_input_verification_code_layout.setVisibility(View.VISIBLE);
                register_setting_psw_layout.setVisibility(View.GONE);
                register_input_number_tv.setTextColor(ContextCompat.getColor(this,R.color.text_color_primary));
                register_input_verification_code_tv.setTextColor(ContextCompat.getColor(this,R.color.color_primary));
                register_set_psw_tv.setTextColor(ContextCompat.getColor(this,R.color.text_color_primary));
                if (!register_verification_code_pn_tv.getText().toString().contains(cell_phone_number))
                    register_verification_code_pn_tv.setText(register_verification_code_pn_tv.getText().toString()+cell_phone_number);
                break;
            case 3:
                register_psw_et.setText("");
                register_again_psw_et.setText("");
                stopCountDownTimer();
                register_psw_et.requestFocus();
                register_next_bt.setEnabled(false);
                register_input_cell_phone_number_layout.setVisibility(View.GONE);
                register_input_verification_code_layout.setVisibility(View.GONE);
                register_setting_psw_layout.setVisibility(View.VISIBLE);
                register_input_number_tv.setTextColor(ContextCompat.getColor(this,R.color.text_color_primary));
                register_input_verification_code_tv.setTextColor(ContextCompat.getColor(this,R.color.text_color_primary));
                register_set_psw_tv.setTextColor(ContextCompat.getColor(this,R.color.color_primary));
                break;
        }
    }

    @OnClick({R.id.register_cell_phone_number_clear_iv,R.id.register_agree_terms_tv
            ,R.id.register_psw_visiable_iv,R.id.register_password_visiable_iv
            ,R.id.register_next_bt,R.id.register_agree_terms_layout
            ,R.id.register_verification_code_time_tv,R.id.contact_service_tv})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_cell_phone_number_clear_iv:
                register_cell_phone_number_et.setText("");
                break;
            case R.id.register_agree_terms_layout:
                if (register_agree_terms_cb.isChecked())
                    register_agree_terms_cb.setChecked(false);
                else
                    register_agree_terms_cb.setChecked(true);
                break;
            case R.id.register_agree_terms_tv:
                Bundle bundle=new Bundle();
                bundle.putString("url","http://m.dongao.com/app/agreement/agreement.html");
                bundle.putString("title","用户协议");
                gotoActivity(CommonWebViewActivity.class,false,bundle);
                break;
            case R.id.register_psw_visiable_iv:
                if (isPswVisiable){
                    register_psw_et.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isPswVisiable =false;
                    register_psw_visiable_iv.setImageResource(R.drawable.login_closedeye_icon);
                }else{
                    register_psw_et.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isPswVisiable =true;
                    register_psw_visiable_iv.setImageResource(R.drawable.login_openeye_icon);
                }
                register_psw_et.setSelection(register_psw_et.getText().toString().length());
                break;
            case R.id.register_password_visiable_iv:
                if (isAgainPswVisiable){
                    register_again_psw_et.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isAgainPswVisiable =false;
                    register_password_visiable_iv.setImageResource(R.drawable.login_closedeye_icon);
                }else{
                    register_again_psw_et.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isAgainPswVisiable =true;
                    register_password_visiable_iv.setImageResource(R.drawable.login_openeye_icon);
                }
                register_again_psw_et.setSelection(register_again_psw_et.getText().toString().length());
                break;
            case R.id.register_next_bt:
                if (registerProgress== 1){
                    cell_phone_number = register_cell_phone_number_et.getFormatText();
                    if (!TextUtils.isEmpty(cell_phone_number)){
                        if (register_agree_terms_cb.isChecked()){
                            if (cell_phone_number.matches(Constants.REGEX_PHONE)){
                                //TODO 请求验证码
                                mPresenter.requestVerificationCode(cell_phone_number,Constants.USER_SENT_TYPE_PHONE,Constants.USER_TYPE_REGISTER);
                            }else{
                                register_already_register_tv.setVisibility(View.VISIBLE);
                                register_already_register_tv.setText(getString(R.string.phone_format_incorrect));
                            }
                        }else{
                            DialogManager.showOneButtonDialog(this, new DialogManager.OneButtonDialogListener() {
                                @Override
                                public void dialogBtClick() {

                                }
                            },"请您先同意《东奥用户注册条款》再进行下一步的操作",0,"确认");
                        }
                    }else{
                        Toast.makeText(this,"手机号不能为空",Toast.LENGTH_SHORT).show();
                    }
                }else if (registerProgress==2){
                    //TODO 验证验证码
                    mPresenter.verifyTheCode(cell_phone_number,register_verification_code_et.getText().toString());
                }else {
                    boolean isRegisterNotOk=false;
                    String settingPswErrorStr="";
                    String pswText = register_psw_et.getText().toString();
                    String againPswText = register_again_psw_et.getText().toString();
                    if (!TextUtils.isEmpty(pswText)){
                        if (pswText.contains(" ")){
                            register_setting_psw_error_tv.setVisibility(View.VISIBLE);
                            register_setting_psw_error_tv.setText("密码不能包含空格");
                            return;
                        }
                        if (!TextUtils.isEmpty(againPswText)){

                            if (pswText.matches(REGEX_PSW) || againPswText.matches(REGEX_PSW)){
                                if (pswText.equals(againPswText)){
                                    //TODO 注册
                                    MobclickAgent.onEvent(this, Statistics.PROFILE_REGIST);
                                    mPresenter.register(cell_phone_number,pswText,register_verification_code_et.getText().toString());
                                }else{
                                    isRegisterNotOk=true;
                                    settingPswErrorStr=getString(R.string.setting_psw_inconsistent_error);
                                }
                            }else{
                                isRegisterNotOk=true;
                                settingPswErrorStr=getString(R.string.setting_psw_format_error);
                            }
                        }else{
                            isRegisterNotOk=true;
                            settingPswErrorStr=getString(R.string.agian_psw_is_null_error);
                        }
                    }else{
                        isRegisterNotOk=true;
                        settingPswErrorStr=getString(R.string.psw_is_null_error);
                    }

                    if (isRegisterNotOk){
                        register_setting_psw_error_tv.setVisibility(View.VISIBLE);
                        register_setting_psw_error_tv.setText(settingPswErrorStr);
                    }
                }
                break;
            case R.id.register_verification_code_time_tv:
                register_verification_code_et.setText("");
                mPresenter.requestVerificationCode(cell_phone_number,Constants.USER_SENT_TYPE_PHONE,Constants.USER_TYPE_REGISTER);
                break;
            case R.id.contact_service_tv:
                break;
        }
    }

    @Override
    public void requestVerifyCodeSuccess() {
        registerProgress=2;
        initData();
    }

    @Override
    public void requestVerifyCodeFail(Throwable exception) {
        String msg="网络错误";
        if (!NetWorkUtils.isNetworkAvailable(this))
            msg=getString(R.string.app_nonetwork_message);
        if (registerProgress==1){
            if (exception instanceof ApiException){
                ApiException apiException= (ApiException) exception;
                if (apiException.getCode()==10010){
                    DialogManager.showConfirmWithCancelDialog(this, new DialogManager.ConfirmWithCancelDialogListener() {
                        @Override
                        public void confirm() {
                            DialogManager.showGetBackPswDialog(RegisterActivity.this, new DialogManager.GetBackPswDialogListener() {
                                @Override
                                public void setGetBackPswType(int type) {
                                    Bundle bundle=new Bundle();
                                    bundle.putInt("getBackPswType",type);
                                    gotoActivity(GetBackPswActivity.class,true,bundle);
                                }
                            },R.drawable.user_dialog_happy_icon,"请选择你要找回的方式");
                        }

                        @Override
                        public void cancel() {
                            finish();
                        }
                    },"您的手机号已注册东奥用户",0,"找回密码","立即登录");
                    return;
                }
            }
        }

        if(exception instanceof ApiException)
            msg=exception.getMessage();
        register_already_register_tv.setVisibility(View.VISIBLE);
        register_already_register_tv.setText(msg);
    }

    @Override
    public void verifyTheCodeSuccess() {
        registerProgress=3;
        initData();
        register_next_bt.setText(getString(R.string.register));
    }

    @Override
    public void verifyTheCodeFail(String message) {
        register_verification_code_error_tv.setVisibility(View.VISIBLE);
        register_verification_code_error_tv.setText(message);
    }

    @Override
    public void registerSuccess(final UserBean userBean) {
        DialogManager.showOneButtonDialog(this, new DialogManager.OneButtonDialogListener() {
            @Override
            public void dialogBtClick() {
                SharedPrefHelper.getInstance()
                        .setAccessToken(userBean.getAccessToken());
                SharedPrefHelper.getInstance()
                        .setLoginUsername(userBean.getUsername());
                SharedPrefHelper.getInstance()
                        .setUserEmail(userBean.getEmail());
                SharedPrefHelper.getInstance()
                        .setUserPhoneNumber(userBean.getMobilePhone());
                SharedPrefHelper.getInstance()
                        .setLoginPassword(register_psw_et.getText().toString().trim());
                SharedPrefHelper.getInstance()
                        .setUserId(userBean.getId());
                SharedPrefHelper.getInstance()
                        .setUserAvatarImageUrl(userBean.getAvatarImageUrl());
                SharedPrefHelper.getInstance()
                        .setLevelName(userBean.getLevelName());
                SharedPrefHelper.getInstance()
                        .setLevel(userBean.getLevel());
                SharedPrefHelper.getInstance()
                        .setNowIntegral(userBean.getNowIntegral());
                SharedPrefHelper.getInstance()
                        .setGrowthValue(userBean.getGrowthValue());
                SharedPrefHelper.getInstance().setIsLogin(true);

                EventBus.getDefault().post(new LoginSuccessEvent());
                Intent toPageIntent = getIntent();
                String toPageName = toPageIntent.getStringExtra(Constants.LOGIN_SUCCESS_TO_PAGE_NAME);
                if (!TextUtils.isEmpty(toPageName)){
                    toPageIntent.setClassName(RegisterActivity.this,toPageName);
                    if (toPageIntent.getBooleanExtra(Constants.IS_TOKEN_ERROR,false)){
                        toPageIntent.putExtra("tag",MainActivity.TAG_MAIN);
                    }
                    startActivity(toPageIntent);
                    EventBus.getDefault().post(new LoginNeedCloseEvent());
                    finish();
                }else{
                    gotoActivity(MainActivity.class);
                }
            }
        }, "恭喜您注册成功！\r\n快去探索吧！",0,"立即探索");
    }

    @Override
    public void registerFial(Throwable throwable) {
        if (!NetWorkUtils.isNetworkAvailable(this)){
            register_setting_psw_error_tv.setText(getString(R.string.app_nonetwork_message));
            register_setting_psw_error_tv.setVisibility(View.VISIBLE);
            return;
        }

        if (!(throwable instanceof ApiException) && !(throwable instanceof HttpException) ){
            register_setting_psw_error_tv.setText("网络错误");
            register_setting_psw_error_tv.setVisibility(View.VISIBLE);
            return;
        }

        DialogManager.showOneButtonDialog(this, new DialogManager.OneButtonDialogListener() {
            @Override
            public void dialogBtClick() {
                registerProgress=1;
                initData();
            }
        },"注册出现问题，\r\n请您重新注册。",0,"重新注册");
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public boolean onSupportNavigateUp() {
        quitRegister();
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            quitRegister();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void quitRegister(){
        DialogManager.showConfirmWithCancelDialog(this, new DialogManager.ConfirmWithCancelDialogListener() {
            @Override
            public void confirm() {
                onBackPressed();
            }

            @Override
            public void cancel() {

            }
        },"您还未注册成功，\r\n是否确认退出？",0,null,null);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_register;
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

    private void stopCountDownTimer() {
        if (countDownTimerList!=null && countDownTimerList.size()>0){
            for (CountDownTimer c: countDownTimerList) {
                if (c!=null)
                    c.cancel();
            }
            countDownTimerList.clear();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopCountDownTimer();
    }


    @Override
    protected boolean setStatusBarVisiable() {
        return false;
    }
}
