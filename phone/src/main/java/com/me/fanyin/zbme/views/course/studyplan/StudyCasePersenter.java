package com.me.fanyin.zbme.views.course.studyplan;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.me.core.exception.ApiException;
import com.me.data.local.SharedPrefHelper;
import com.me.data.model.play.StudyPlanBean;
import com.me.data.model.play.Subject;
import com.me.data.mvp.BasePersenter;
import com.me.data.remote.ApiService;
import com.me.data.remote.rxjava.RxUtils;
import com.me.data.remote.utils.ParamsUtils;
import com.me.fanyin.zbme.views.course.play.db.ClassHomeDB;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * Created by wenpeng on 6/04/2017.
 */

public class StudyCasePersenter extends BasePersenter<StudyCaseView> {

    private ClassHomeDB homeDB;
    @Inject
    StudyCasePersenter() {
        homeDB=new ClassHomeDB();
    }

    /**
     * 学习计划接口
     */
    @Override
    public void getData() {

    }

    public void getPlanData(String date){
        String subjectId=SharedPrefHelper.getInstance().getIntelSubjectId();
        Disposable subscribe = ApiService.studyPlan(ParamsUtils.studyPlan(subjectId,date))
                .compose(RxUtils.<StudyPlanBean>retrofit()) //几种方式实现逻辑前置
                .subscribe(new Consumer<StudyPlanBean>() {
                               @Override
                               public void accept(StudyPlanBean result) throws Exception {
                                   getMvpView().showDatas(result);
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(@NonNull Throwable throwable) throws Exception {
                                   ApiException exception=(ApiException)throwable;
                                   getMvpView().showError(exception.getMessage());
                               }
                           }
                );
        addSubscription(subscribe);
    }

    /**
     * 获取课程科目列表
     */
    public void getSubjects() {
        String examId=SharedPrefHelper.getInstance().getIntelExamId();
        Disposable subscribe = ApiService.subjectSmartList(ParamsUtils.subjectList(examId))
                .compose(RxUtils.<String>retrofit()) //几种方式实现逻辑前置
                .subscribe(new Consumer<String>() {
                               @Override
                               public void accept(String subjects) throws Exception {
                                   preSubjectDatas(subjects);
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(@NonNull Throwable throwable) throws Exception {
                                   getMvpView().showError(throwable.getMessage());
                               }
                           }
                );
        addSubscription(subscribe);
    }

    private void preSubjectDatas(String datas) {
        try {
            if(!TextUtils.isEmpty(datas)){
                List<Subject> list=JSON.parseArray(datas, Subject.class);
                getMvpView().subjectDatas(list);
            }else{
                getMvpView().emptyData();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
