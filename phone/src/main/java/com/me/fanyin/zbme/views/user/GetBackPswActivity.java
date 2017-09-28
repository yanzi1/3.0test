package com.me.fanyin.zbme.views.user;

import android.os.Build;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.me.core.exception.ApiException;
import com.me.core.utils.NetWorkUtils;
import com.me.data.common.Constants;
import com.me.data.common.Statistics;
import com.me.data.model.user.GetBackPwUserInfoBean;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.base.BaseMvpActivity;
import com.me.fanyin.zbme.widget.DialogManager;
import com.me.fanyin.zbme.widget.PhoneEditText;
import com.me.fanyin.zbme.widget.VerificationEditText;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class GetBackPswActivity extends BaseMvpActivity<GetBackPswView,GetBackPswPresenter> implements GetBackPswView{
    private static final int EMAIL=1;
    private static final int TELEPHONE=2;
    private static final String REGEX_EMAIL = "^\\w+([-.+]\\w+)*\\@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    private static final String REGEX_PSW = "^.{4,20}$";

    @BindView(R.id.status_bar_fix)
    View status_bar_fix;
    @BindView(R.id.gb_psw_input_number_tv)
    TextView gb_psw_input_number_tv;
    @BindView(R.id.gb_psw_input_verification_code_tv)
    TextView gb_psw_input_verification_code_tv;
    @BindView(R.id.gb_psw_verification_user_tv)
    TextView gb_psw_verification_user_tv;
    @BindView(R.id.gb_psw_reset_psw_tv)
    TextView gb_psw_reset_psw_tv;
    @BindView(R.id.gb_psw_input_cell_phone_number_layout)
    View gb_psw_input_cell_phone_number_layout;
    @BindView(R.id.gb_psw_cell_phone_number_et)
    PhoneEditText gb_psw_cell_phone_number_et;
    @BindView(R.id.gb_psw_cell_phone_number_clear_iv)
    View gb_psw_cell_phone_number_clear_iv;
    @BindView(R.id.gb_psw_input_verification_code_layout)
    View gb_psw_input_verification_code_layout;
    @BindView(R.id.gb_psw_verification_code_pn_tv)
    TextView gb_psw_verification_code_pn_tv;
    @BindView(R.id.gb_psw_verification_code_et)
    VerificationEditText gb_psw_verification_code_et;
    @BindView(R.id.gb_psw_verification_code_time_tv)
    TextView gb_psw_verification_code_time_tv;
    @BindView(R.id.gb_psw_verification_code_error_tv)
    TextView gb_psw_verification_code_error_tv;
    @BindView(R.id.gb_psw_verification_user_layout)
    View gb_psw_verification_user_layout;
    @BindView(R.id.gb_psw_verification_check_user_layout)
    LinearLayout gb_psw_verification_check_user_layout;
    @BindView(R.id.gb_psw_resetting_psw_layout)
    View gb_psw_resetting_psw_layout;
    @BindView(R.id.gb_psw_psw_et)
    EditText gb_psw_psw_et;
    @BindView(R.id.gb_psw_psw_visiable_iv)
    ImageView gb_psw_psw_visiable_iv;
    @BindView(R.id.gb_psw_again_psw_et)
    EditText gb_psw_again_psw_et;
    @BindView(R.id.gb_psw_password_visiable_iv)
    ImageView gb_psw_password_visiable_iv;
    @BindView(R.id.gb_psw_setting_psw_error_tv)
    TextView gb_psw_setting_psw_error_tv;
    @BindView(R.id.gb_psw_next_bt)
    Button gb_psw_next_bt;
    @BindView(R.id.gb_psw_phone_or_email_error_tv)
    TextView gb_psw_phone_or_email_error_tv;
    @BindView(R.id.contact_service_tv)
    TextView contact_service_tv;

    private int getBackPswProgress=1;
    private List<View> userViews;
    private List<CountDownTimer> countDownTimerList;
    protected int method;
    private boolean isPswVisiable;
    private int userCount=1;
    private int userPos=-1;
    private String phoneOrEmail;
    private GetBackPwUserInfoBean getBackPwUserInfoBean;
    private boolean isAgainPswVisiable;

    @Override
    protected void initView() {
        method=getIntent().getIntExtra("getBackPswType",TELEPHONE);
        mToolbar.setTitleText(getString(R.string.get_back_psw));
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            setTranslucentStatus();
        }


        userViews=new ArrayList<>();
        countDownTimerList=new ArrayList<>();

        if (method==EMAIL){
            gb_psw_cell_phone_number_et.setHint("请输入邮箱");
            gb_psw_input_number_tv.setText("输入邮箱");
            gb_psw_cell_phone_number_et.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        }
//        gb_psw_cell_phone_number_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus)
//                    gb_psw_cell_phone_number_clear_iv.setVisibility(View.VISIBLE);
//                else
//                    gb_psw_cell_phone_number_clear_iv.setVisibility(View.INVISIBLE);
//            }
//        });

        CharSequence psw_et_hint = gb_psw_psw_et.getHint();
        SpannableString spannableString=new SpannableString(psw_et_hint);
        spannableString.setSpan(new AbsoluteSizeSpan(12,true)
                ,3, psw_et_hint.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        gb_psw_psw_et.setHint(spannableString);

        gb_psw_cell_phone_number_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (method==EMAIL){
                    if (!TextUtils.isEmpty(s) && s.toString().trim().matches(REGEX_EMAIL)){
                        gb_psw_next_bt.setEnabled(true);
                    }else{
                        gb_psw_next_bt.setEnabled(false);
                    }
                }else{
                    if (!TextUtils.isEmpty(s) && s.length()==13  ){
                        String[] pss=s.toString().split(" ");
                        StringBuilder sb=new StringBuilder();
                        for (String ps : pss) {
                            sb.append(ps);
                        }
                        String ps=sb.toString();
                        if (ps.matches(Constants.REGEX_PHONE))
                            gb_psw_next_bt.setEnabled(true);
                    }else{
                        gb_psw_next_bt.setEnabled(false);
                    }
                }
                if (s.length()>0){
                    gb_psw_cell_phone_number_clear_iv.setVisibility(View.VISIBLE);
                }else{
                    gb_psw_cell_phone_number_clear_iv.setVisibility(View.INVISIBLE);
                }
                gb_psw_phone_or_email_error_tv.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        InputFilter[] phoneInputFilters = {new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if ((dend <= dstart && end+dest.length() >=14) || dstart==13 || end >=14 || end + dstart>=14){
                    DialogManager.showOneButtonDialog(GetBackPswActivity.this, new DialogManager.OneButtonDialogListener() {
                        @Override
                        public void dialogBtClick() {

                        }
                    },getString(R.string.phone_format_error),0,null);
                    return "";
                }
                return null;
            }
        }};
        if (method == TELEPHONE){
            gb_psw_cell_phone_number_et.setFilters(phoneInputFilters);
        }else{
            gb_psw_cell_phone_number_et.setPhoneFormat(false);
        }


        gb_psw_verification_code_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s) && s.length()==6){
                    gb_psw_next_bt.setEnabled(true);
                }else{
                    gb_psw_next_bt.setEnabled(false);
                }
                gb_psw_verification_code_error_tv.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        gb_psw_psw_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s) && !TextUtils.isEmpty(gb_psw_again_psw_et.getText())
                        && s.length()>3 && gb_psw_again_psw_et.getText().length()>3){
                    gb_psw_next_bt.setEnabled(true);
                }else {
                    gb_psw_next_bt.setEnabled(false);
                }
                gb_psw_setting_psw_error_tv.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        gb_psw_again_psw_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s) && !TextUtils.isEmpty(gb_psw_psw_et.getText())
                        && s.length()>3 && gb_psw_psw_et.getText().length()>3){
                    gb_psw_next_bt.setEnabled(true);
                }else {
                    gb_psw_next_bt.setEnabled(false);
                }
                gb_psw_setting_psw_error_tv.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        InputFilter[] inputFilters = {new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (dstart==20 || end >=21 || end + dstart>=21){
                    DialogManager.showOneButtonDialog(GetBackPswActivity.this, new DialogManager.OneButtonDialogListener() {
                        @Override
                        public void dialogBtClick() {

                        }
                    },"密码长度不能超过20个字符",0,null);
                    return "";
                }
                return null;
            }
        }};
        gb_psw_psw_et.setFilters(inputFilters);
        gb_psw_again_psw_et.setFilters(inputFilters);


        SpannableString ss=new SpannableString(contact_service_tv.getText());
        ss.setSpan(new UnderlineSpan(),0,contact_service_tv.getText().length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        contact_service_tv.setText(ss);
    }

    @Override
    protected void initData() {
        switch (getBackPswProgress){
            case 1:
                gb_psw_cell_phone_number_et.setText("");
                gb_psw_next_bt.setText("下一步");
                gb_psw_input_number_tv.setTextColor(ContextCompat.getColor(this,R.color.color_primary));
                gb_psw_input_verification_code_tv.setTextColor(ContextCompat.getColor(this,R.color.text_color_primary));
                gb_psw_verification_user_tv.setTextColor(ContextCompat.getColor(this,R.color.text_color_primary));
                gb_psw_reset_psw_tv.setTextColor(ContextCompat.getColor(this,R.color.text_color_primary));
                gb_psw_input_cell_phone_number_layout.setVisibility(View.VISIBLE);
                gb_psw_input_verification_code_layout.setVisibility(View.GONE);
                gb_psw_verification_user_layout.setVisibility(View.GONE);
                gb_psw_resetting_psw_layout.setVisibility(View.GONE);
                break;
            case 2:
                gb_psw_verification_code_et.setText("");
                gb_psw_verification_code_et.requestFocus();
                gb_psw_verification_code_time_tv.setEnabled(false);
                gb_psw_verification_code_time_tv.setTextColor(ContextCompat.getColor(GetBackPswActivity.this,R.color.text_color_primary_dark));
                CountDownTimer countDownTimer = new CountDownTimer(60000, 1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {
                        gb_psw_verification_code_time_tv.setText((int)(millisUntilFinished/1000)+"s后可重新发送");
                    }

                    @Override
                    public void onFinish() {
                        gb_psw_verification_code_time_tv.setEnabled(true);
                        gb_psw_verification_code_time_tv.setText(Html.fromHtml("<u>重新发送验证码</u>"));
                        gb_psw_verification_code_time_tv.setTextColor(ContextCompat.getColor(GetBackPswActivity.this,R.color.color_primary));
                    }
                }.start();
                countDownTimerList.add(countDownTimer);
                gb_psw_next_bt.setEnabled(false);
                gb_psw_input_number_tv.setTextColor(ContextCompat.getColor(this,R.color.text_color_primary));
                gb_psw_input_verification_code_tv.setTextColor(ContextCompat.getColor(this,R.color.color_primary));
                gb_psw_verification_user_tv.setTextColor(ContextCompat.getColor(this,R.color.text_color_primary));
                gb_psw_reset_psw_tv.setTextColor(ContextCompat.getColor(this,R.color.text_color_primary));
                gb_psw_input_cell_phone_number_layout.setVisibility(View.GONE);
                gb_psw_input_verification_code_layout.setVisibility(View.VISIBLE);
                gb_psw_verification_user_layout.setVisibility(View.GONE);
                gb_psw_resetting_psw_layout.setVisibility(View.GONE);
                break;
            case 3:
                userViews.clear();
                gb_psw_verification_check_user_layout.removeAllViews();
                InputMethodManager imp3 = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imp3.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
                stopCountDownTimer();
                gb_psw_next_bt.setEnabled(false);
                gb_psw_input_number_tv.setTextColor(ContextCompat.getColor(this,R.color.text_color_primary));
                gb_psw_input_verification_code_tv.setTextColor(ContextCompat.getColor(this,R.color.text_color_primary));
                gb_psw_verification_user_tv.setTextColor(ContextCompat.getColor(this,R.color.color_primary));
                gb_psw_reset_psw_tv.setTextColor(ContextCompat.getColor(this,R.color.text_color_primary));
                gb_psw_input_cell_phone_number_layout.setVisibility(View.GONE);
                gb_psw_input_verification_code_layout.setVisibility(View.GONE);
                gb_psw_verification_user_layout.setVisibility(View.VISIBLE);
                gb_psw_resetting_psw_layout.setVisibility(View.GONE);

                for (int i = 0; i < userCount; i++) {
                    View view = getLayoutInflater().inflate(R.layout.gb_psw_verifitcation_check_user_layout,null);
                    view.setTag(i);
                    View bt = view.findViewById(R.id.login_weibo_bt);
                    bt.setTag(i);
                    bt.setOnClickListener(this);
                    ((TextView)view.findViewById(R.id.user_name_tv)).setText(getBackPwUserInfoBean.getUsername());
                    if (userCount==1){
                        ((CheckBox) view.findViewById(R.id.login_weibo_icon_iv)).setChecked(true);
                        gb_psw_next_bt.setEnabled(true);
                    }
                    userViews.add(view);
                    gb_psw_verification_check_user_layout.addView(view);
                }
                break;
            case 4:
                gb_psw_again_psw_et.setText("");
                gb_psw_psw_et.setText("");
                InputMethodManager imp4 = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imp4.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
                gb_psw_psw_et.requestFocus();
                gb_psw_next_bt.setEnabled(false);
                gb_psw_input_number_tv.setTextColor(ContextCompat.getColor(this,R.color.text_color_primary));
                gb_psw_input_verification_code_tv.setTextColor(ContextCompat.getColor(this,R.color.text_color_primary));
                gb_psw_verification_user_tv.setTextColor(ContextCompat.getColor(this,R.color.text_color_primary));
                gb_psw_reset_psw_tv.setTextColor(ContextCompat.getColor(this,R.color.color_primary));
                gb_psw_input_cell_phone_number_layout.setVisibility(View.GONE);
                gb_psw_input_verification_code_layout.setVisibility(View.GONE);
                gb_psw_verification_user_layout.setVisibility(View.GONE);
                gb_psw_resetting_psw_layout.setVisibility(View.VISIBLE);
                break;
        }
    }

    @OnClick({R.id.gb_psw_next_bt,R.id.gb_psw_cell_phone_number_clear_iv
            ,R.id.gb_psw_password_visiable_iv,R.id.gb_psw_psw_visiable_iv
            ,R.id.gb_psw_verification_code_time_tv,R.id.contact_service_tv})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.gb_psw_next_bt:
                if (getBackPswProgress==1){
                    if (method==TELEPHONE){
                        phoneOrEmail = gb_psw_cell_phone_number_et.getFormatText();
                        if (phoneOrEmail.matches(Constants.REGEX_PHONE)){
                            //TODO 请求验证码
                            mPresenter.requestVerificationCode(phoneOrEmail, Constants.USER_SENT_TYPE_PHONE,Constants.USER_TYPE_GET_BACK_PW);
                        }else{
                            gb_psw_phone_or_email_error_tv.setVisibility(View.VISIBLE);
                            gb_psw_phone_or_email_error_tv.setText(R.string.phone_format_incorrect);
                        }
                    }else{
                        phoneOrEmail = gb_psw_cell_phone_number_et.getText().toString().trim();
                        //TODO 请求验证码
                        mPresenter.requestVerificationCode(phoneOrEmail, Constants.USER_SENT_TYPE_EMAIL,Constants.USER_TYPE_GET_BACK_PW);
                    }

                }else if (getBackPswProgress==2){
                    //TODO 校验验证码
                    mPresenter.verifyTheCode(phoneOrEmail,gb_psw_verification_code_et.getText().toString());

                }else if (getBackPswProgress==3){
                    for (int i = 0; i < userCount; i++) {
                        if (((CheckBox)userViews.get(i).findViewById(R.id.login_weibo_icon_iv)).isChecked()){
                            userPos =(int)userViews.get(i).getTag();
                            break;
                        }
                    }
                    getBackPswProgress=4;
                    initData();
                    gb_psw_next_bt.setText(getString(R.string.reset_psw));
                }else{
                    boolean isRegisterNotOk=false;
                    String settingPswErrorStr="";
                    String pswText = gb_psw_psw_et.getText().toString();
                    String againPswText = gb_psw_again_psw_et.getText().toString();
                    if (!TextUtils.isEmpty(pswText)){
                        if (pswText.contains(" ")){
                            gb_psw_setting_psw_error_tv.setVisibility(View.VISIBLE);
                            gb_psw_setting_psw_error_tv.setText("密码不能包含空格");
                            return;
                        }
                        if (!TextUtils.isEmpty(againPswText)){
                            if (pswText.matches(REGEX_PSW) || againPswText.matches(REGEX_PSW)){
                                if (pswText.equals(againPswText)){
                                    String sendType="";
                                    if (method==TELEPHONE){
                                        sendType=Constants.USER_SENT_TYPE_PHONE;
                                        MobclickAgent.onEvent(this, Statistics.PROFILE_FINDPASSWORD_PHONE);
                                    }else{
                                        sendType=Constants.USER_SENT_TYPE_EMAIL;
                                        MobclickAgent.onEvent(this, Statistics.PROFILE_FINDPASSWORD_MAIL);
                                    }
                                    //TODO 找回密码
                                    mPresenter.getBackPw(phoneOrEmail,sendType,pswText,gb_psw_verification_code_et.getText().toString());
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
                        gb_psw_setting_psw_error_tv.setVisibility(View.VISIBLE);
                        gb_psw_setting_psw_error_tv.setText(settingPswErrorStr);
                    }
                }
                break;
            case R.id.gb_psw_cell_phone_number_clear_iv:
                gb_psw_cell_phone_number_et.setText("");
                break;
            case R.id.gb_psw_password_visiable_iv:
                if (isAgainPswVisiable){
                    gb_psw_again_psw_et.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isAgainPswVisiable =false;
                    gb_psw_password_visiable_iv.setImageResource(R.drawable.login_closedeye_icon);
                }else{
                    gb_psw_again_psw_et.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isAgainPswVisiable =true;
                    gb_psw_password_visiable_iv.setImageResource(R.drawable.login_openeye_icon);
                }
                gb_psw_again_psw_et.setSelection(gb_psw_again_psw_et.getText().toString().length());
                break;
            case R.id.gb_psw_psw_visiable_iv:
                if (isPswVisiable){
                    gb_psw_psw_et.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isPswVisiable=false;
                    gb_psw_psw_visiable_iv.setImageResource(R.drawable.login_closedeye_icon);
                }else{
                    gb_psw_psw_et.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isPswVisiable=true;
                    gb_psw_psw_visiable_iv.setImageResource(R.drawable.login_openeye_icon);
                }
                gb_psw_psw_et.setSelection(gb_psw_psw_et.getText().toString().length());
                break;

            case R.id.gb_psw_verification_code_time_tv:
                gb_psw_verification_code_et.setText("");
                //TODO 获取验证码
                if (method==TELEPHONE){
                    //TODO 请求验证码
                    mPresenter.requestVerificationCode(phoneOrEmail, Constants.USER_SENT_TYPE_PHONE,Constants.USER_TYPE_GET_BACK_PW);
                }else{
                    //TODO 请求验证码
                    mPresenter.requestVerificationCode(phoneOrEmail, Constants.USER_SENT_TYPE_EMAIL,Constants.USER_TYPE_GET_BACK_PW);
                }
                break;

            case R.id.login_weibo_bt:
                int pos = (int) v.getTag();
                for (int i = 0; i < userCount; i++) {
                    if (i==pos){
                        ((CheckBox)userViews.get(i).findViewById(R.id.login_weibo_icon_iv)).setChecked(true);
                    }else{
                        ((CheckBox)userViews.get(i).findViewById(R.id.login_weibo_icon_iv)).setChecked(false);
                    }
                }
                gb_psw_next_bt.setEnabled(true);
                break;

            case R.id.contact_service_tv:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    gotoServiceNtalker(appContext, Constants.SETTING_ID, "", null);
                } else {
//                    gotoServiceNtalker(appContext, Constants.SETTING_ID, "", null);
                }
                break;
        }
    }


    @Override
    public void requestVerifyCodeSuccess() {
        getBackPswProgress=2;
        if (!gb_psw_verification_code_pn_tv.getText().toString().contains(phoneOrEmail))
            gb_psw_verification_code_pn_tv.setText(gb_psw_verification_code_pn_tv.getText().toString()+phoneOrEmail);
        initData();
    }

    @Override
    public void requestVerifyCodeFail(String message) {
        if (getBackPswProgress==1){
            gb_psw_phone_or_email_error_tv.setVisibility(View.VISIBLE);
            gb_psw_phone_or_email_error_tv.setText(message);
        }else{
            gb_psw_verification_code_error_tv.setVisibility(View.VISIBLE);
            gb_psw_verification_code_error_tv.setText(message);
        }
    }

    @Override
    public void verifyTheCodeSuccess() {
        //TODO 获取对应的用户
        if (method==TELEPHONE){
            mPresenter.reqeuestAccountInfoByPhone(phoneOrEmail);
        }else{
            mPresenter.reqeuestAccountInfoByEmail(phoneOrEmail);
        }
    }

    @Override
    public void verifyTheCodeFail(String message) {
        gb_psw_verification_code_error_tv.setVisibility(View.VISIBLE);
        gb_psw_verification_code_error_tv.setText(message);

    }


    @Override
    public void returnAccountInfo(GetBackPwUserInfoBean getBackPwUserInfoBean) {
        this.getBackPwUserInfoBean =getBackPwUserInfoBean;
        getBackPswProgress=3;
        initData();
    }

    @Override
    public void getBackPwSuccess() {
        DialogManager.showOneButtonDialog(this, new DialogManager.OneButtonDialogListener() {
            @Override
            public void dialogBtClick() {
                gotoActivity(LoginActivity.class);
            }
        }, "恭喜您找回密码成功！\r\n快去探索吧！",0,"立即登录");
    }

    @Override
    public void getBackPswFail(Throwable throwable) {
        if (!NetWorkUtils.isNetworkAvailable(this)){
            gb_psw_setting_psw_error_tv.setText(getString(R.string.app_nonetwork_message));
            gb_psw_setting_psw_error_tv.setVisibility(View.VISIBLE);
            return;
        }

        if (!(throwable instanceof ApiException) && !(throwable instanceof HttpException) ){
            gb_psw_setting_psw_error_tv.setText("网络错误");
            gb_psw_setting_psw_error_tv.setVisibility(View.VISIBLE);
            return;
        }

        DialogManager.showOneButtonDialog(this, new DialogManager.OneButtonDialogListener() {
            @Override
            public void dialogBtClick() {
                getBackPswProgress=1;
                initData();
            }
        },"找回密码遇到问题，\r\n" +
                "请您重新找回。",0,"重新找回");
    }

    @Override
    public void showError(String message) {


    }

    @Override
    public boolean onSupportNavigateUp() {
        quitGetBackPsw();
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            quitGetBackPsw();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void quitGetBackPsw(){
        DialogManager.showConfirmWithCancelDialog(this, new DialogManager.ConfirmWithCancelDialogListener() {
            @Override
            public void confirm() {
                onBackPressed();
            }

            @Override
            public void cancel() {

            }
        },"您还未找回密码，\r\n是否确认退出？",0,null,null);
    }


    public void setTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //当状态栏透明后，内容布局会上移，这里使用一个和状态栏高度相同的view来修正内容区域
            status_bar_fix.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight()));
        }
    }

    public int getStatusBarHeight(){
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_get_back_psw;
    }

    @Override
    protected void initInject() {
        getAppComponent().inject(this);
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
