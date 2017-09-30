package com.me.fanyin.zbme.views.exam;


import android.content.Intent;
import android.support.v4.view.ViewPager;

import com.me.fanyin.zbme.base.PhoneAppContext;
import com.me.fanyin.zbme.views.exam.mvp.MvpView;
import com.me.fanyin.zbme.widget.EmptyViewLayout;

public interface ExamView extends MvpView {
  void intent();
  void out();
  Intent getWayIntent();
  void showTotalNumber(String totalNumber);
  void showCurrentNumber(int currentNumber);
  void showExaminationTittle(String title);
  ViewPager getViewPager();
  void showBottomTittle(int type);
  void showBottomTittlePress(int type);
  void showTopTittle(String title);
  void updateQuestionItemAdapter();
  void showExamTime(String time);
  void finishActivity();
  void setVPIndex(int position);
  void vpNotifyData();
  void intentExamReportActivity();
  void showTopTitleRight(boolean isShow);
  void showExamPagerBottom(boolean isShow);
  void showExamPagerBottomReportLayou(boolean isShow);
  PhoneAppContext getAppContext();
  void showExamTimeOrNot(boolean isShow);
  void showExamBottomRight(int tag);
  void showBackPress();
  EmptyViewLayout getEmptyView();
  void progressStatus(int status);
  void showDuoxuanGuide(boolean isShow);
}
