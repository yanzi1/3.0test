package com.me.fanyin.zbme.views.mine.settings;

import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.me.data.common.Constants;
import com.me.data.local.SharedPrefHelper;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.base.BaseMvpActivity;
import com.me.fanyin.zbme.widget.DialogManager;
import com.me.fanyin.zbme.widget.PhoneEditText;
import com.me.fanyin.zbme.widget.VerificationEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ResetEmailOrPhoneActivity extends BaseMvpActivity<ResetEmailOrPhoneView,ResetEmailOrPhonePresenter> implements ResetEmailOrPhoneView {
    private static final String REGEX_EMAIL = "^\\w+([-.+]\\w+)*\\@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    @BindView(R.id.next_bt)
    Button next_bt;

    @BindView(R.id.your_phone_or_email_layout)
    View your_phone_or_email_layout;
    @BindView(R.id.your_phone_or_email_content_tv)
    TextView your_phone_or_email_content_tv;
    @BindView(R.id.your_phone_or_email_tv)
    TextView your_phone_or_email_tv;

    @BindView(R.id.input_phone_or_email_layout)
    View input_phone_number_layout;
    @BindView(R.id.input_phone_or_email_et)
    PhoneEditText input_phone_or_email_et;
    @BindView(R.id.phone_or_email_error_tv)
    TextView phone_or_email_error_tv;
    @BindView(R.id.phone_or_email_clear_iv)
    View phone_or_email_clear_iv;

    @BindView(R.id.input_verification_code_layout)
    View input_verification_code_layout;
    @BindView(R.id.verification_code_pn_tv)
    TextView verification_code_pn_tv;
    @BindView(R.id.verification_code_et)
    VerificationEditText verification_code_et;
    @BindView(R.id.verification_code_time_tv)
    TextView verification_code_time_tv;
    @BindView(R.id.verification_code_error_tv)
    TextView verification_code_error_tv;



    private boolean isEmail;
    private int progress;
    private boolean needAdd;
    private String phoneOrEmail;

    private List<CountDownTimer> countDownTimers;

    @Override
    protected void initView() {
        countDownTimers=new ArrayList<>();
        isEmail = getIntent().getBooleanExtra("isEmail", false);
        needAdd = getIntent().getBooleanExtra("needAdd", false);
        String titleText="";
        if (needAdd){
            progress =2;
            if (isEmail){
                titleText="新增邮箱";
            }else{
                titleText="新增手机号";
            }
        }else{
            progress =1;
            if (isEmail){
                titleText="修改邮箱";
            }else {
                titleText = "修改手机号";
            }
        }

        mToolbar.setTitleText(titleText);


        input_phone_or_email_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isEmail){
                    if (!TextUtils.isEmpty(s) && s.toString().trim().matches(REGEX_EMAIL)){
                        next_bt.setEnabled(true);
                    }else{
                        next_bt.setEnabled(false);
                    }
                }else{
                    if (!TextUtils.isEmpty(s) && s.length()==13 ){
                        String[] pss=s.toString().split(" ");
                        StringBuilder sb=new StringBuilder();
                        for (String ps : pss) {
                            sb.append(ps);
                        }
                        String ps=sb.toString();
                        if (ps.matches(Constants.REGEX_PHONE))
                            next_bt.setEnabled(true);
                    }else{
                        next_bt.setEnabled(false);
                    }
                }
                if (s.length()>0){
                    phone_or_email_clear_iv.setVisibility(View.VISIBLE);
                }else{
                    phone_or_email_clear_iv.setVisibility(View.INVISIBLE);
                }
                phone_or_email_error_tv.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        InputFilter[] phoneInputFilters = {new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if ((dend <= dstart && end+dest.length() >=14) || dstart==13 || end >=14 || end + dstart>=14){
                    DialogManager.showOneButtonDialog(ResetEmailOrPhoneActivity.this, new DialogManager.OneButtonDialogListener() {
                        @Override
                        public void dialogBtClick() {

                        }
                    },getString(R.string.phone_format_error),0,null);
                    return "";
                }
                return null;
            }
        }};
        if (!isEmail){
            input_phone_or_email_et.setFilters(phoneInputFilters);
        }else{
            input_phone_or_email_et.setPhoneFormat(false);
        }

        verification_code_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s) && s.length()==6){
                    next_bt.setEnabled(true);
                }else{
                    next_bt.setEnabled(false);
                }
                verification_code_error_tv.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void initData() {

        switch (progress){
            case 1:{
                your_phone_or_email_layout.setVisibility(View.VISIBLE);
                input_verification_code_layout.setVisibility(View.GONE);
                input_phone_number_layout.setVisibility(View.GONE);
                if (isEmail){
                    next_bt.setText("更改邮箱");
                    your_phone_or_email_tv.setText("您的邮箱：");
                    your_phone_or_email_content_tv.setText(SharedPrefHelper.getInstance().getUserEmail());
                }else{
                    next_bt.setText("更改手机号");
                    your_phone_or_email_tv.setText("您的手机号：");
                    your_phone_or_email_content_tv.setText(SharedPrefHelper.getInstance().getUserPhoneNumber());
                }
            }
                break;
            case 2: {
                your_phone_or_email_layout.setVisibility(View.GONE);
                input_verification_code_layout.setVisibility(View.GONE);
                input_phone_number_layout.setVisibility(View.VISIBLE);
                next_bt.setText("下一步");
                next_bt.setEnabled(false);
                if (isEmail){
                    input_phone_or_email_et.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                    input_phone_or_email_et.setHint("请输入邮箱");
                }else{
                    input_phone_or_email_et.setHint("请输入手机号");
                }
            }
                break;
            case 3:{
                your_phone_or_email_layout.setVisibility(View.GONE);
                input_verification_code_layout.setVisibility(View.VISIBLE);
                input_phone_number_layout.setVisibility(View.GONE);
                next_bt.setEnabled(false);
                next_bt.setText("确认修改");
                if (needAdd){
                    next_bt.setText("确认添加");
                }
                verification_code_time_tv.setEnabled(false);
                verification_code_time_tv.setTextColor(ContextCompat.getColor(this,R.color.text_color_primary_dark));
                CountDownTimer countDownTimer=new CountDownTimer(60000, 1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {
                        verification_code_time_tv.setText((int)(millisUntilFinished/1000)+"s后可重新发送");
                    }

                    @Override
                    public void onFinish() {
                        verification_code_time_tv.setEnabled(true);
                        verification_code_time_tv.setText(Html.fromHtml("<u>重新发送验证码</u>"));
                        verification_code_time_tv.setTextColor(ContextCompat.getColor(ResetEmailOrPhoneActivity.this,R.color.color_primary));
                    }
                }.start();
                countDownTimers.add(countDownTimer);
            }
                break;
        }
    }

    @OnClick({R.id.next_bt,R.id.phone_or_email_clear_iv,R.id.verification_code_time_tv})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.next_bt:
                switch (progress){
                    case 1:{
                        progress=2;
                        initData();
                    }
                        break;
                    case 2:{
                        if (!isEmail){
                            phoneOrEmail = input_phone_or_email_et.getFormatText();
                            if (phoneOrEmail.matches(Constants.REGEX_PHONE)){
                                if (phoneOrEmail.equals(SharedPrefHelper.getInstance().getUserPhoneNumber())){
                                    requestVerifyCodeFail("新手机号与原手机号相同");
                                    return;
                                }
                                //TODO 请求验证码
                                mPresenter.requestVerificationCode(phoneOrEmail, Constants.USER_SENT_TYPE_PHONE,Constants.USER_TYPE_MODIFY_PHONE);
                            }else{
                                phone_or_email_error_tv.setVisibility(View.VISIBLE);
                                phone_or_email_error_tv.setText(R.string.phone_format_incorrect);
                            }
                        }else{
                            phoneOrEmail = input_phone_or_email_et.getText().toString().trim();
                            if (phoneOrEmail.equals(SharedPrefHelper.getInstance().getUserEmail())){
                                requestVerifyCodeFail("新邮箱与原邮箱相同");
                                return;
                            }
                            //TODO 请求验证码
                            mPresenter.requestVerificationCode(phoneOrEmail, Constants.USER_SENT_TYPE_EMAIL,Constants.USER_TYPE_MODIFY_EMAIL);
                        }
                    }
                        break;
                    case 3:{
                        mPresenter.verifyTheCode(phoneOrEmail,verification_code_et.getText().toString());
                    }
                        break;
                }
                break;

            case R.id.phone_or_email_clear_iv:
                input_phone_or_email_et.setText("");
                break;

            case R.id.verification_code_time_tv:
                verification_code_et.setText("");
                //TODO 获取验证码
                if (isEmail){
                    //TODO 请求验证码
                    mPresenter.requestVerificationCode(phoneOrEmail, Constants.USER_SENT_TYPE_EMAIL,Constants.USER_TYPE_MODIFY_EMAIL);
                }else{
                    //TODO 请求验证码
                    mPresenter.requestVerificationCode(phoneOrEmail, Constants.USER_SENT_TYPE_PHONE,Constants.USER_TYPE_MODIFY_PHONE);
                }
                break;
        }
    }


    @Override
    public void requestVerifyCodeSuccess() {
        progress=3;
        if (!verification_code_pn_tv.getText().toString().contains(phoneOrEmail))
            verification_code_pn_tv.setText(verification_code_pn_tv.getText().toString()+phoneOrEmail);
        initData();
    }

    @Override
    public void requestVerifyCodeFail(String message) {
        if (progress==2){
            phone_or_email_error_tv.setVisibility(View.VISIBLE);
            phone_or_email_error_tv.setText(message);
        }else{
            verification_code_error_tv.setVisibility(View.VISIBLE);
            verification_code_error_tv.setText(message);
        }

    }

    @Override
    public void modifySuccess() {
        String content="恭喜您修改成功";
        if(needAdd){
            content="恭喜您新增成功";
        }
        DialogManager.showOneButtonDialog(this, new DialogManager.OneButtonDialogListener() {
            @Override
            public void dialogBtClick() {
                if (isEmail)
                    SharedPrefHelper.getInstance().setUserEmail(phoneOrEmail);
                else
                    SharedPrefHelper.getInstance().setUserPhoneNumber(phoneOrEmail);
                finish();
            }
        },content,0,"确认");
    }

    @Override
    public void modifyFail(String message) {
        verification_code_error_tv.setVisibility(View.VISIBLE);
        verification_code_error_tv.setText(message);
    }

    @Override
    public void verifyTheCodeSuccess() {
        if (isEmail){
            mPresenter.modifyEmail(SharedPrefHelper.getInstance().getUserId()
                    ,phoneOrEmail,verification_code_et.getText().toString());
        }else{
            mPresenter.modifyPhone(SharedPrefHelper.getInstance().getUserId()
                    ,phoneOrEmail,verification_code_et.getText().toString());
        }
    }

    @Override
    public void verifyTheCodeFail(String msg) {
        verification_code_error_tv.setVisibility(View.VISIBLE);
        verification_code_error_tv.setText(msg);
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
        String content="您还未修改成功，是否确认退出？";
        if(needAdd)
            content="您还未添加成功，是否确认退出？";
        DialogManager.showConfirmWithCancelDialog(this, new DialogManager.ConfirmWithCancelDialogListener() {
            @Override
            public void confirm() {
                onBackPressed();
            }

            @Override
            public void cancel() {

            }
        },content,0,null,null);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_reset_email_or_phone;
    }

    @Override
    protected void initInject() {
        getAppComponent().inject(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for (CountDownTimer t : countDownTimers) {
            if (t!=null)
                t.cancel();
        }
        countDownTimers.clear();
    }
}
