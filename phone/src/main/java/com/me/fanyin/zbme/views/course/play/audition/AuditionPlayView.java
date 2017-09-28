package com.me.fanyin.zbme.views.course.play.audition;

import android.os.Handler;

import com.me.data.model.play.CourseWare;
import com.me.data.mvp.MvpView;

/**
 * Created by fengzongwei on 2016/5/30 0030.
 */
public interface AuditionPlayView extends MvpView {
    void playError(String msg);
    CourseWare getCw();
    void startPlay(CourseWare cw);
    Handler getHandler();
}
