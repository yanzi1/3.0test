package com.me.fanyin.zbme.views.download;

import com.me.data.model.play.CourseWare;
import com.me.data.mvp.MvpView;

import java.util.List;


public interface MyDownloadCourseView extends MvpView {
    void showData(List<CourseWare> courseWares);
    void showEdit();
    void showNormal();


    void deleteSuccess(List<String> select);

    void deleteFail();
}
