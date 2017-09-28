package com.me.fanyin.zbme.views.course.notes;

import com.alibaba.fastjson.JSON;
import com.me.data.model.play.NoteClassDetail;
import com.me.data.model.play.NoteDetail;
import com.me.data.mvp.BasePersenter;
import com.me.data.remote.ApiService;
import com.me.data.remote.rxjava.RxUtils;
import com.me.data.remote.utils.ParamsUtils;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * Created by wenpeng on 6/04/2017.
 */

public class NotesDetailPersenter extends BasePersenter<NotesDetailView> {

    @Inject
    NotesDetailPersenter() {
    }

    @Override
    public void getData() {

    }

    /**
     * 获取笔记详情接口 试题
     */
    public void getNotesDetail(String noteId) {
        Disposable subscribe = ApiService.getNoteDetail(ParamsUtils.getNoteDetail(noteId))
                .compose(RxUtils.<String>retrofit()) //几种方式实现逻辑前置
                .subscribe(new Consumer<String>() {
                               @Override
                               public void accept(String result) throws Exception {
                                   NoteDetail noteDetail = reSetData(result);
                                   getMvpView().showResult(noteDetail);
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

    /**
     * 获取笔记详情接口  课堂
     */
    public void getNotesClassDetail(String noteId) {
        Disposable subscribe = ApiService.getNoteClassDetail(ParamsUtils.getNoteDetailClass(noteId))
                .compose(RxUtils.<String>retrofit	())
                .subscribe(new Consumer<String>() {
                               @Override
                               public void accept(String result) throws Exception {
                                   NoteClassDetail noteClassDetail = reSetClassData(result);
                                   getMvpView().showClassResult(noteClassDetail);
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

    /**
     * 笔记删除
     */
    public void delteNotes(String noteId) {
        Disposable subscribe = ApiService.delteNotes(ParamsUtils.delNote(noteId))
                .compose(RxUtils.<String>retrofit()) //几种方式实现逻辑前置
                .subscribe(new Consumer<String>() {
                               @Override
                               public void accept(String result) throws Exception {
                                   getMvpView().showResult("删除成功");
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

    /**
     * 笔记删除  课堂
     */
    public void delteNotesClass(String noteId) {
        Disposable subscribe = ApiService.delteNotesClass(ParamsUtils.delNoteClass(noteId))
                .compose(RxUtils.<String>retrofit()) //几种方式实现逻辑前置
                .subscribe(new Consumer<String>() {
                               @Override
                               public void accept(String result) throws Exception {
                                   getMvpView().classDelResult("删除成功");
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

    private NoteDetail reSetData(String data) {
        NoteDetail noteDetail=null;
        try{
            noteDetail=JSON.parseObject(data,NoteDetail.class);
        }catch (Exception e){

        }
        return noteDetail;
    }

    private NoteClassDetail reSetClassData(String data) {
        NoteClassDetail noteClassDetail=null;
        try{
            noteClassDetail=JSON.parseObject(data,NoteClassDetail.class);
        }catch (Exception e){

        }
        return noteClassDetail;
    }

}
