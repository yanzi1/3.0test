package com.me.fanyin.zbme.views.user;


import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.me.core.utils.ToastBarUtils;
import com.me.data.model.user.UserBean;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.base.BaseMvpFragment;

import butterknife.BindView;


/**
 * 登录
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends BaseMvpFragment<LoginView,LoginPersenter> implements LoginView{


    @BindView(R.id.username)
    AutoCompleteTextView username_edittext;

    @BindView(R.id.password)
    EditText password_edittext;

    @BindView(R.id.login_sign_in_btn)
    Button login_sign_in_btn;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.user_login_fragment;
    }

    @Override
    public void initView() {
        login_sign_in_btn.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    protected void initInject() {
        getAppComponent().inject(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_sign_in_btn:
                mPresenter.getData();
                break;
        }
    }

    @Override
    public void showLoading() {
        loadingDialog.show();
    }

    @Override
    public void hideLoading() {
        loadingDialog.dismiss();
    }

    @Override
    public void showError(String message) {
       // Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
        ToastBarUtils.show(getActivity(),message);
    }

    @Override
    public Activity context() {
        return getActivity();
    }

    @Override
    public void loginSuccess(UserBean userBean) {

    }

    @Override
    public void thirdPartySuccess() {

    }
}
