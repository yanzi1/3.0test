package com.me.fanyin.zbme.views.exam.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.me.fanyin.zbme.views.exam.mvp.MvpView;

/**
 * Created by dell on 2016/5/4.
 */
public interface QuestionCompreFragmentView extends MvpView {
    Bundle getArgumentData();
    Intent getTheIntent();
    void setNSListviewIsWebview(boolean isWebview);
    void setLocalAnswer(String localAnswer);
    void setRealAnswer(boolean choiceTypeIsNormal, boolean isTextView, String realAnswer);
    void setAnalyzeIsWebview(boolean isWebview, String analyze);
    void showRlevantPoint(boolean show);
    void showAnalyze(boolean showAnalyze);
    void showNormalQuestionOrNot(boolean isNormal);
    ListView getOptionListView();
    void setOptionListViewIsEnable(boolean isEnable);
    void setNotNormalTipShow(boolean isShow);
    void setShowAllAnalyze(boolean isShow);
    void setRelavantAnswers(boolean isShow);
    void setQuestionTypeName(String name);
    void setQuestionTitleName(boolean isNotWebview, String name);
    void refreshOptionAdapter();
    void showSolutions(boolean isShow, String solutions);
}
