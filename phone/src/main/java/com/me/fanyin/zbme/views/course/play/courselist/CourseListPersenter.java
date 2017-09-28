package com.me.fanyin.zbme.views.course.play.courselist;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.me.core.exception.ApiException;
import com.me.data.local.SharedPrefHelper;
import com.me.data.model.play.Course;
import com.me.data.model.play.CourseList;
import com.me.data.model.play.CourseListBean;
import com.me.data.model.play.CourseListData;
import com.me.data.model.play.Subject;
import com.me.data.mvp.BasePersenter;
import com.me.data.remote.ApiService;
import com.me.data.remote.rxjava.RxUtils;
import com.me.data.remote.utils.ParamsUtils;
import com.me.fanyin.zbme.views.course.play.db.SubjectDB;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * Created by wenpeng on 6/04/2017.
 */

public class CourseListPersenter extends BasePersenter<CourseListView> {

    private SubjectDB subjectDB;
    private String userId,examId;
    @Inject
    CourseListPersenter() {
        examId=SharedPrefHelper.getInstance().getExamId();
        userId=SharedPrefHelper.getInstance().getUserId();
        subjectDB=new SubjectDB();
    }

    /**
     * 获取课程详情接口
     */
    @Override
    public void getData() {
        String sSubjectId= SharedPrefHelper.getInstance().getMainSsubjectId();
        Disposable subscribe = ApiService.courseList(ParamsUtils.courseList(sSubjectId))
                .compose(RxUtils.<CourseListData>retrofit()) //几种方式实现逻辑前置
                .subscribe(new Consumer<CourseListData>() {
                               @Override
                               public void accept(CourseListData data) throws Exception {
                                   CourseListBean courseList = resetData(data);
                                   getMvpView().initAdapter(courseList.getCourseList());
                                   getMvpView().insertData(courseList);
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(@NonNull Throwable throwable) throws Exception {
                                   ApiException exception=(ApiException)throwable;
                                   if(exception.getCode()==29999){
                                       getMvpView().emptyData();
                                   }else{
                                       getMvpView().showDataError();
                                   }
                               }
                           }
                );
        addSubscription(subscribe);
    }

    private CourseListBean resetData(CourseListData data){
        List<Course> noStudy=data.getNoStudyList();
        List<Course> study=data.getStudyList();
        List<CourseList> courseLists=new ArrayList<>();

        CourseListBean courseListBean=new CourseListBean();

        if(study!=null && study.size()>0){
            CourseList courseList=new CourseList();
            courseList.setCourseType("1");
            courseList.setCourseTypeName("已学习");
            courseList.setCourseItems(study);
            courseLists.add(courseList);
        }
        if(noStudy!=null && noStudy.size()>0){
            CourseList courseListno=new CourseList();
            courseListno.setCourseType("0");
            courseListno.setCourseTypeName("未学习");
            courseListno.setCourseItems(noStudy);
            courseLists.add(courseListno);
        }
        courseListBean.setCourseList(courseLists);
        return courseListBean;
    }

    /**
     * 获取课程科目列表
     */
    public void getSubjects() {
        Disposable subscribe = ApiService.subjectList(ParamsUtils.subjectList(examId))
                .compose(RxUtils.<String>retrofit()) //几种方式实现逻辑前置
                .subscribe(new Consumer<String>() {
                               @Override
                               public void accept(String subjects) throws Exception {
                                   preSubjectDatas(subjects);
                                   insertSubjectData(subjects);
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

    private void insertSubjectData(String data){
        Subject subjectData = subjectDB.find(userId,examId);

        Subject subject=new Subject();
        subject.setUserId(userId);
        subject.setExamId(examId);
        subject.setContent(data);

        if (subjectData == null) {//数据库中不存在  插入数据库
            boolean flag = subjectDB.insert(subject);
            if (!flag) {
                subjectDB.insert(subject);
            }
        } else {//存在 更新数据库
            subjectData.setContent(data);
            subjectDB.update(subjectData);
        }
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

    /**
     * 继续播放接口
     */
    public void continPlay() {
        String subjectId = SharedPrefHelper.getInstance().getMainSubjectId();
        Disposable subscribe = ApiService.playContinue(ParamsUtils.playContinue(subjectId))
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
                });
        addSubscription(subscribe);
    }

    private void preData(String data) {
        try {
            org.json.JSONObject body = new org.json.JSONObject(data);

            String couseId = body.optString("couseId");
            String lectureId = body.optString("lectureId");
            String lectureName = body.optString("lectureName");
            String sSubjectId = body.optString("sSubjectId");
            getMvpView().continuePlay(couseId,lectureId,lectureName,sSubjectId);
        } catch (Exception e) {

        }

    }

}
