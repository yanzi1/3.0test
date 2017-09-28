package com.me.fanyin.zbme.views.course.play.fragment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.me.data.mvp.BasePersenter;
import com.me.data.remote.ApiService;
import com.me.data.remote.rxjava.RxUtils;
import com.me.data.remote.utils.ParamsUtils;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;


/**
 * Created by wenpeng on 6/04/2017.
 */

public class CourseCommentPersenter extends BasePersenter<CourseCommentView> {

    @Inject
    CourseCommentPersenter() {
    }

    /**
     * 获取课程详情接口
     */
    @Override
    public void getData() {

    }

    public void postComment(String cwId,String comment,String cwStarts,String diffStars,String teachStarts){

        Disposable subscribe = ApiService.postComment(ParamsUtils.postComment(cwId,comment,cwStarts,diffStars,teachStarts))
                .compose(RxUtils.<ResponseBody>ioMain())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody result) throws Exception {
                        String data=result.string();
                        JSONObject object=JSON.parseObject(data);
                        String code=object.getString("code");
                        if(code.equals("0")){
                            getMvpView().showResult(object.getString("msg"));
                        }else{
                            getMvpView().showError(object.getString("msg"));
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        getMvpView().showError(throwable.getMessage());
                    }
                });
        addSubscription(subscribe);
    }

}
