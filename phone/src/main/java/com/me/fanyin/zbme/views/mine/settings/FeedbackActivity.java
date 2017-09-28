package com.me.fanyin.zbme.views.mine.settings;

import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.me.core.app.AppManager;
import com.me.core.utils.StringUtils;
import com.me.core.utils.ToastBarUtils;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.base.BaseMvpActivity;
import com.me.fanyin.zbme.widget.DialogManager;

import butterknife.BindView;

/**
 * Created by jjr on 2017/5/19.
 */

public class FeedbackActivity extends BaseMvpActivity<FeedbackView, FeedbackPresenter> implements FeedbackView {

    @BindView(R.id.feedback_title_edt)
    EditText feedback_title_edt;
    @BindView(R.id.feedback_content_edt)
    EditText feedback_content_edt;
    @BindView(R.id.feedback_words_number_tv)
    TextView feedback_words_number_tv;
    @BindView(R.id.feedback_submit_btn)
    Button feedback_submit_btn;

    @Override
    protected void initInject() {
        getAppComponent().inject(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.feedback_submit_btn:
                mPresenter.getData();
                break;
        }
    }

    @Override
    public void showError(String message) {
        ToastBarUtils.showToast(this, message);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.mine_feedback_activity;
    }

    @Override
    protected void initView() {
        mToolbar.setTitleText("意见反馈");
        feedback_title_edt.setFilters(new InputFilter[]{ new StringUtils.SpecialCharFilter() , new InputFilter.LengthFilter(30)});
        feedback_title_edt.addTextChangedListener(new TextChangedListener(){
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    feedback_submit_btn.setEnabled(false);
                } else if (s.length() > 0 && !TextUtils.isEmpty(getFeedbackContent())) {
                    feedback_submit_btn.setEnabled(true);
                }
                if (s.length() == 30) {
                    showError("最多输入30字");
                }
            }
        });
        feedback_content_edt.setFilters(new InputFilter[]{ new StringUtils.SpecialCharFilter() , new InputFilter.LengthFilter(500)});
        feedback_content_edt.addTextChangedListener(new TextChangedListener(){
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    feedback_submit_btn.setEnabled(false);
                    feedback_words_number_tv.setTextColor(ContextCompat.getColor(FeedbackActivity.this, R.color.text_color_primary_hint));
                } else if (s.length() > 0 && !TextUtils.isEmpty(getFeedbackTitle())) {
                    feedback_submit_btn.setEnabled(true);
                }
                if (s.length() == 500) {
                    feedback_words_number_tv.setTextColor(ContextCompat.getColor(FeedbackActivity.this, R.color.color_accent));
                    showError("最多输入500字");
                } else {
                    feedback_words_number_tv.setTextColor(ContextCompat.getColor(FeedbackActivity.this, R.color.color_accent_2));
                }
                feedback_words_number_tv.setText(s.length() + "");
            }
        });
        feedback_submit_btn.setOnClickListener(this);

    }

    @Override
    protected void initData() {

    }

    @Override
    public void submitSuccessful() {
        DialogManager.showOneButtonDialog(FeedbackActivity.this, new DialogManager.OneButtonDialogListener() {
            @Override
            public void dialogBtClick() {
                AppManager.getAppManager().finishActivity(FeedbackActivity.this);
            }
        }, "您的反馈已提交成功,感谢您的反馈", 0, "确认");
    }

    @Override
    public String getFeedbackTitle() {
        return feedback_title_edt.getText().toString().trim();
    }

    @Override
    public String getFeedbackContent() {
        return feedback_content_edt.getText().toString().trim();
    }

    static class TextChangedListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
