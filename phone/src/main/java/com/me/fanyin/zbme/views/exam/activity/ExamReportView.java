package com.me.fanyin.zbme.views.exam.activity;


import android.content.Intent;

import com.me.fanyin.zbme.base.PhoneAppContext;
import com.me.fanyin.zbme.views.exam.mvp.MvpView;

/**
 * @author wyc
 * 答题报告UI 
 */
public interface ExamReportView extends MvpView {
  void intent();
  void out();
  Intent getWayIntent();
  void setRightNumber(int number);
  void setErrorNumber(int number);
  void setUseTime(String time);
  void setScore(String score);
  void setVisible(int examTag);
  PhoneAppContext getAppContext();
  void showPingJia(String str);
  
}
