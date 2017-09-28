package com.me.fanyin.zbme.views.course;

import com.alibaba.fastjson.JSON;
import com.me.data.local.SharedPrefHelper;
import com.me.data.model.play.ClassHomeBean;
import com.me.data.mvp.BasePersenter;
import com.me.data.remote.ApiService;
import com.me.data.remote.rxjava.RxUtils;
import com.me.data.remote.utils.ParamsUtils;
import com.me.fanyin.zbme.views.course.play.db.ClassHomeDB;

import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * Created by wenpeng on 6/04/2017.
 */

public class CoursePersenter extends BasePersenter<CourseView> {

    private ClassHomeDB homeDB;
    @Inject
    CoursePersenter() {
        homeDB=new ClassHomeDB();
    }

    /**
     * 课堂首页接口
     */
    @Override
    public void getData() {
        Disposable subscribe = ApiService.classHome(ParamsUtils.classHome())
                .compose(RxUtils.<ClassHomeBean>retrofit()) //几种方式实现逻辑前置
                .doOnNext(new Consumer<ClassHomeBean>() {
                    @Override
                    public void accept(@NonNull ClassHomeBean result) throws Exception {
                        insertClassHome(result);
                    }
                })
                .compose(RxUtils.<ClassHomeBean>ioMain())
                .subscribe(new Consumer<ClassHomeBean>() {
                               @Override
                               public void accept(ClassHomeBean result) throws Exception {
                                   getMvpView().showHomeData(result);

                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(@NonNull Throwable throwable) throws Exception {
                                   getMvpView().showError(throwable.getMessage());
                               }
                           });
        addSubscription(subscribe);
    }

    private void insertClassHome(ClassHomeBean result){
        String userId=SharedPrefHelper.getInstance().getUserId();
        String examId=SharedPrefHelper.getInstance().getExamId();
        ClassHomeBean homeBean = homeDB.find(userId,examId);

        result.setUserId(userId);
        result.setExamId(examId);
        result.setContent(JSON.toJSONString(result));

        if (homeBean == null) {//数据库中不存在  插入数据库
            boolean flag = homeDB.insert(result);
            if (!flag) {
                homeDB.insert(result);
            }
        } else {//存在 更新数据库
            homeBean.setContent(JSON.toJSONString(result));
            homeDB.update(homeBean);
        }
    }

    /**
     * 继续播放接口
     */
    public void continPlay() {
        String examId = SharedPrefHelper.getInstance().getExamId();
        Disposable subscribe = ApiService.classPlayContinue(ParamsUtils.classPlayContinue(examId))
                .compose(RxUtils.<String>retrofit()) //几种方式实现逻辑前置
                .subscribe(new Consumer<String>() {
                               @Override
                               public void accept(String result) throws Exception {
                                   preData(result);
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(@NonNull Throwable throwable) throws Exception {
                                   getMvpView().continuePlayError();
                               }
                           }
                );
        addSubscription(subscribe);
    }

    private void preData(String data) {
        try {
            JSONObject body = new JSONObject(data);

            String couseId = body.optString("couseId");
            String lectureId = body.optString("lectureId");
            String lectureName = body.optString("lectureName");
            String sSubjectId = body.optString("sSubjectId");
            getMvpView().continuePlay(couseId,lectureId,lectureName,sSubjectId);
        } catch (Exception e) {

        }

    }
}
