package com.me.fanyin.zbme.views.mine.settings;

import android.text.Editable;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.AbsoluteSizeSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.me.data.local.SharedPrefHelper;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.base.BaseMvpActivity;
import com.me.fanyin.zbme.widget.DialogManager;

import butterknife.BindView;
import butterknife.OnClick;

public class ResetPswActivity extends BaseMvpActivity<ResetPswView,ResetPswPresenter> implements ResetPswView {

    private static final String REGEX_PSW = "^.{4,20}$";

    @BindView(R.id.input_forward_psw_layout)
    View input_forward_psw_layout;
    @BindView(R.id.input_psw_layout)
    View input_psw_layout;
    @BindView(R.id.next_bt)
    Button next_bt;

    @BindView(R.id.input_forward_psw_et)
    EditText input_forward_psw_et;
    @BindView(R.id.input_forward_psw_visiable_iv)
    ImageView input_forward_psw_visiable_iv;
    @BindView(R.id.input_forward_error_tv)
    TextView input_forward_error_tv;


    @BindView(R.id.input_psw_et)
    EditText input_psw_et;
    @BindView(R.id.input_again_psw_et)
    EditText input_again_psw_et;
    @BindView(R.id.psw_visiable_iv1)
    ImageView psw_visiable_iv1;
    @BindView(R.id.psw_visiable_iv)
    ImageView psw_visiable_iv;
    @BindView(R.id.psw_error_tv)
    TextView psw_error_tv;


    private int progress=1;
    private String forwardPsw;
    private boolean isFPswVisiable;
    private boolean isAPswVisiable;
    private boolean isPswVisiable;

    @Override
    protected void initView() {
        mToolbar.setTitleText("修改密码");
        input_forward_psw_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)){
                    next_bt.setEnabled(true);
                }else {
                    next_bt.setEnabled(false);
                }
                input_forward_error_tv.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        CharSequence psw_et_hint = input_psw_et.getHint();
        SpannableString spannableString=new SpannableString(psw_et_hint);
        spannableString.setSpan(new AbsoluteSizeSpan(12,true)
                ,3, psw_et_hint.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        input_psw_et.setHint(spannableString);
        input_psw_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s) && !TextUtils.isEmpty(input_again_psw_et.getText().toString())
                        && s.length()>3 && input_again_psw_et.getText().toString().length()>3){
                    next_bt.setEnabled(true);
                }else {
                    next_bt.setEnabled(false);
                }
                psw_error_tv.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        input_again_psw_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s) && !TextUtils.isEmpty(input_psw_et.getText().toString())
                        && s.length()>3 && input_psw_et.getText().toString().length()>3){
                    next_bt.setEnabled(true);
                }else {
                    next_bt.setEnabled(false);
                }
                psw_error_tv.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        InputFilter[] inputFilters = {new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (dstart==20 || end >=21 || end + dstart>=21){
                        DialogManager.showOneButtonDialog(ResetPswActivity.this, new DialogManager.OneButtonDialogListener() {
                            @Override
                            public void dialogBtClick() {

                            }
                        },"密码长度不能超过20个字符",0,null);
                        return "";
                }
                return null;
            }
        }};
        input_psw_et.setFilters(inputFilters);
        input_again_psw_et.setFilters(inputFilters);

    }

    @Override
    protected void initData() {
        switch (progress){
            case 1:
                next_bt.setEnabled(false);
                input_forward_psw_layout.setVisibility(View.VISIBLE);
                input_psw_layout.setVisibility(View.GONE);
                break;
            case 2:
                next_bt.setEnabled(false);
                next_bt.setText("确认修改");
                input_forward_psw_layout.setVisibility(View.GONE);
                input_psw_layout.setVisibility(View.VISIBLE);

                break;
        }

    }

    @OnClick({R.id.next_bt,R.id.input_forward_psw_visiable_iv,R.id.psw_visiable_iv,R.id.psw_visiable_iv1})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.next_bt:
                switch (progress){
                    case 1:
                        forwardPsw = input_forward_psw_et.getText().toString();
                        if (forwardPsw.matches(REGEX_PSW)){
                            if (forwardPsw.equals(SharedPrefHelper.getInstance().getLoginPassword())){
                                progress=2;
                                initData();
                            }else{
                                input_forward_error_tv.setText("密码输入错误");
                                input_forward_error_tv.setVisibility(View.VISIBLE);
                            }
                        }else{
                            input_forward_error_tv.setText("密码格式不正确");
                            input_forward_error_tv.setVisibility(View.VISIBLE);
                        }
                        break;
                    case 2:
                        boolean isRegisterNotOk=false;
                        String settingPswErrorStr="";
                        String oldPswText = input_forward_psw_et.getText().toString();
                        String pswText = input_psw_et.getText().toString();
                        String againPswText = input_again_psw_et.getText().toString();
                        if (!TextUtils.isEmpty(pswText)){
                            if (pswText.contains(" ")){
                                psw_error_tv.setVisibility(View.VISIBLE);
                                psw_error_tv.setText("密码不能包含空格");
                                return;
                            }
                            if (!TextUtils.isEmpty(againPswText)){
                                if (pswText.matches(REGEX_PSW) || againPswText.matches(REGEX_PSW)){
                                    if (pswText.equals(againPswText)){
                                        if (pswText.equals(oldPswText)){
                                            isRegisterNotOk=true;
                                            settingPswErrorStr="不能使用原密码";
                                        }else{
                                            mPresenter.modifyPsw(SharedPrefHelper.getInstance().getUserId()
                                                    ,oldPswText,pswText);
                                        }
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
                            psw_error_tv.setVisibility(View.VISIBLE);
                            psw_error_tv.setText(settingPswErrorStr);
                        }
                        break;
                }
                break;
            case R.id.input_forward_psw_visiable_iv:
                if (isFPswVisiable){
                    input_forward_psw_et.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isFPswVisiable =false;
                    input_forward_psw_visiable_iv.setImageResource(R.drawable.login_closedeye_icon);
                }else{
                    input_forward_psw_et.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isFPswVisiable =true;
                    input_forward_psw_visiable_iv.setImageResource(R.drawable.login_openeye_icon);
                }
                input_forward_psw_et.setSelection(input_forward_psw_et.getText().toString().length());
                break;
            case R.id.psw_visiable_iv:
                if (isAPswVisiable){
                    input_again_psw_et.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isAPswVisiable =false;
                    psw_visiable_iv.setImageResource(R.drawable.login_closedeye_icon);
                }else{
                    input_again_psw_et.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isAPswVisiable =true;
                    psw_visiable_iv.setImageResource(R.drawable.login_openeye_icon);
                }
                input_again_psw_et.setSelection(input_again_psw_et.getText().toString().length());
                break;
            case R.id.psw_visiable_iv1:
                if (isPswVisiable){
                    input_psw_et.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isPswVisiable =false;
                    psw_visiable_iv1.setImageResource(R.drawable.login_closedeye_icon);
                }else{
                    input_psw_et.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isPswVisiable =true;
                    psw_visiable_iv1.setImageResource(R.drawable.login_openeye_icon);
                }
                input_psw_et.setSelection(input_psw_et.getText().toString().length());
                break;
        }
    }

    @Override
    public void modifySuccess() {
        String content="恭喜您修改密码成功";
        DialogManager.showOneButtonDialog(this, new DialogManager.OneButtonDialogListener() {
            @Override
            public void dialogBtClick() {
                finish();
            }
        },content,0,"确认");
    }

    @Override
    public void showError(String message) {
        psw_error_tv.setText(message);
        psw_error_tv.setVisibility(View.VISIBLE);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_reset_psw;
    }

    @Override
    protected void initInject() {
        getAppComponent().inject(this);
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

}
