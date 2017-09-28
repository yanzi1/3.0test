package com.me.fanyin.zbme.views.main.fragemnt;

import android.support.v7.widget.RecyclerView;

import com.me.data.model.BaseRes;
import com.me.data.model.main.StudentsEvaluateInfo;
import com.me.data.mvp.BasePersenter;
import com.me.data.remote.ApiService;
import com.me.data.remote.rxjava.RxUtils;
import com.me.data.remote.rxjava.SimpleRxSubscriber;
import com.me.fanyin.zbme.views.main.activity.adapter.StudentsAppraiseAdapter;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by jjr on 2017/5/17.
 */

public class FreeCourseFragmentPresenter extends BasePersenter<FreeCourseFragmentView> {

    private StudentsAppraiseAdapter mAdapter;

    @Inject
    FreeCourseFragmentPresenter() {

    }

    @Override
    public void getData() {
        Disposable subscribe = ApiService.getStudentsEvaluateInfo(getMvpView().getLectureId(), 1, 3)
                .compose(RxUtils.<BaseRes<StudentsEvaluateInfo>>ioMain())
                .compose(RxUtils.<BaseRes<StudentsEvaluateInfo>>showLoading(getMvpView()))
                .compose(RxUtils.<StudentsEvaluateInfo>retrofit())
                .subscribeWith(new SimpleRxSubscriber<StudentsEvaluateInfo>(getMvpView()) {
                    @Override
                    public void doOnNext(StudentsEvaluateInfo studentsEvaluateInfo) {
                        if (studentsEvaluateInfo != null) {
                            getMvpView().resetView(studentsEvaluateInfo);
                        }
                    }

                    @Override
                    public void onApiError(int code, String msg) {
                        getMvpView().resetView(null);
                    }

                    @Override
                    public void doOnComplete() {

                    }
                });
        addSubscription(subscribe);
    }

    public StudentsAppraiseAdapter getAdapter(RecyclerView recyclerView) {
        mAdapter = new StudentsAppraiseAdapter();
        mAdapter.bindToRecyclerView(recyclerView);
        return mAdapter;
    }
}
