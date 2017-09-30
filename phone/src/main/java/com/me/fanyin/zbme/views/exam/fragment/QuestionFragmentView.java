package com.me.fanyin.zbme.views.exam.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.me.fanyin.zbme.views.exam.mvp.MvpView;

/**
 * Created by dell on 2016/5/4.
 */
public interface QuestionFragmentView extends MvpView {
    Bundle getArgumentData();
    Intent getTheIntent();
    void setNSListviewIsWebview(boolean isWebview);
    void setLocalAnswer(String localAnswer);
    void setRealAnswer(boolean choiceTypeIsNormal, boolean isTextView, String realAnswer);
    void setAnalyzeIsWebview(boolean isWebview, String analyze);
    void showRlevantPoint(boolean show);
    void showAnalyze(boolean showAnalyze);
    void showNormalQuestionOrNot(boolean isNormal);
    void setNSListViewHeight(ViewGroup.LayoutParams params);
    ListView getOptionListView();
    void setOptionListViewIsEnable(boolean isEnable);
    void setNotNormalTipShow(boolean isShow);
    void setShowAllAnalyze(boolean isShow);
    void setRelavantAnswers(boolean isShow);
    void setQuestionTypeName(boolean isWebview, String name);
    void setQuestionTitleName(boolean isNotWebview, String name);
    void setCompreQuestionTypeName(boolean isWebview, String name);
    void setCompreQuestionTitleName(boolean isNotWebview, String name);
    void refreshOptionAdapter();
    void initChildFragmentAdapter();
    void setChildVPPosition(int index);
    void setCompreHeight(RelativeLayout.LayoutParams lp);
    void setCustomRelativeLayoutHeight(RelativeLayout.LayoutParams lp);
    void setChildVPData();
    void showSolutions(boolean isShow, String solutions);
    String currentDate(); 
    String currentSubjectId(); 
    String currentExamid(); 
    void showData(); 
    void showNoData(); 
}
