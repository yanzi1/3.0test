package com.me.fanyin.zbme.views.main.activity;

import android.content.Intent;
import android.os.Bundle;

import com.me.core.app.AppManager;
import com.me.data.common.Constants;
import com.me.data.common.Statistics;
import com.me.data.local.SharedPrefHelper;
import com.me.data.model.BaseRes;
import com.me.data.model.main.CaptureResultBean;
import com.me.data.model.play.CourseWare;
import com.me.data.mvp.BasePersenter;
import com.me.data.remote.ApiService;
import com.me.data.remote.rxjava.RxUtils;
import com.me.data.remote.rxjava.SimpleRxSubscriber;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.views.course.play.audition.AuditionPlayActivity;
import com.me.fanyin.zbme.views.main.event.SetCourseListEvent;
import com.me.fanyin.zbme.views.user.LoginActivity;
import com.umeng.analytics.MobclickAgent;

import org.apache.http.util.TextUtils;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by jjr on 2017/5/23.
 */

class CapturePresenter extends BasePersenter<CaptureView> {

    private static final int NO_LOGIN_COURSE = 20002;//课程扫码未登录
    private static final int NO_PERMISSION_COURSE = 20003;//课程无权限
    private static final int NO_RELEVANCE_COURSE = 20014;//二维码未关联课程

    private static final int NO_RELEVANCE_EXAMINATION = 30302;//二维码未关联试卷
    private static final int NO_LOGIN_EXAMINATION = 30304;//试卷扫码未登录
    private static final int NO_PERMISSION_EXAMINATION = 30305;//试卷无权限

    @Inject
    CapturePresenter() {

    }

    @Override
    public void getData() {

    }

    public void getData(String qrUrl) {
        Disposable subscribe = ApiService.captureQrCode(qrUrl)
                .compose(RxUtils.<BaseRes<CaptureResultBean>>ioMain())
                .compose(RxUtils.<BaseRes<CaptureResultBean>>showDialogLoading(getMvpView()))
                .compose(RxUtils.<CaptureResultBean>retrofit())
                .compose(RxUtils.<CaptureResultBean>hideDialogLoading(getMvpView()))
                .subscribeWith(new SimpleRxSubscriber<CaptureResultBean>(getMvpView()) {

                    @Override
                    public void doOnNext(CaptureResultBean captureResultBean) {
                        if (captureResultBean.getExaminationVo() != null || captureResultBean.getCourseVo() != null) {
                            if (captureResultBean.getExaminationVo() != null) {
                                MobclickAgent.onEvent(getMvpView().context(), Statistics.SCANNER_EXERCISE);
                                return;
                            }
                            if (captureResultBean.getCourseVo() != null && captureResultBean.getCourseVo().size() != 0 && captureResultBean.getCourseVo().get(0) != null) {
                                if (captureResultBean.getCourseVo().size() == 1) {
                                    CourseWare courseWare = new CourseWare();
                                    courseWare.setId(captureResultBean.getCourseVo().get(0).getId() + "");
                                    courseWare.setName(captureResultBean.getCourseVo().get(0).getName());
                                    courseWare.setTotalTime(captureResultBean.getCourseVo().get(0).getTotalTime());
                                    courseWare.setStartTime(captureResultBean.getCourseVo().get(0).getStartTime());
                                    courseWare.setEndTime(captureResultBean.getCourseVo().get(0).getEndTime());
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("cw", courseWare);
                                    gotoActivity(AuditionPlayActivity.class, false, bundle);
                                    MobclickAgent.onEvent(getMvpView().context(), Statistics.SCANNER_PLAY);
                                } else {
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable(Constants.COURSE_ID, new SetCourseListEvent(captureResultBean.getCourseVo()));
                                    gotoActivity(CaptureCourseListActivity.class, false, bundle);
                                }
                            }
                        }
                    }

                    @Override
                    public void onApiError(int code, String msg) {
                        switch (code) {
                            case NO_LOGIN_COURSE:
                            case NO_LOGIN_EXAMINATION:
                                gotoActivity(LoginActivity.class, false, null);
                                break;
                            case NO_RELEVANCE_COURSE:
                                getMvpView().showError(msg);
                                getMvpView().resumeCameraPreview();
                                break;
                            case NO_PERMISSION_COURSE:
                                gotoActivity(CaptureCourseListActivity.class, false, null);
                                break;
                            case NO_RELEVANCE_EXAMINATION:
                                getMvpView().showError(msg);
                                getMvpView().resumeCameraPreview();
                                break;
                            case NO_PERMISSION_EXAMINATION:
                                MobclickAgent.onEvent(getMvpView().context(), Statistics.SCANNER_ACTIVE);
                                break;
                        }
                    }

                    @Override
                    public void onNetWorkError() {
                        getMvpView().showError(getMvpView().context().getString(R.string.app_nonetwork_message));
                        getMvpView().resumeCameraPreview();
                    }

                    @Override
                    public void doOnComplete() {

                    }
                });
        addSubscription(subscribe);
    }

    public void gotoActivity(Class<?> clz) {
        gotoActivity(clz, false, null);
    }

    public void gotoActivity(Class<?> clz, String className) {
        gotoActivity(clz, false, null, className);
    }

    public void gotoActivity(Class<?> clz, boolean isCloseCurrentActivity, Bundle ex) {
        gotoActivity(clz, isCloseCurrentActivity, ex, null);
    }

    public void gotoActivity(Class<?> clz, boolean isCloseCurrentActivity, Bundle ex, String className) {
        Intent intent = new Intent(getMvpView().context(), clz);
        if (!TextUtils.isEmpty(className)) intent.putExtra(Constants.LOGIN_SUCCESS_TO_PAGE_NAME, className);
        if (ex != null) intent.putExtras(ex);
        getMvpView().context().startActivity(intent);
        if (isCloseCurrentActivity) {
            AppManager.getAppManager().finishActivity(getMvpView().context());
        }
    }

    public void gotoActivityAfterCheckLogin(Class<?> clz, boolean afterLoginToActiity) {
        if (SharedPrefHelper.getInstance().isLogin()) {
            gotoActivity(clz);
        } else if (afterLoginToActiity) {
            gotoActivity(LoginActivity.class, clz.getName());
        } else {
            gotoActivity(LoginActivity.class);
        }
    }
}
