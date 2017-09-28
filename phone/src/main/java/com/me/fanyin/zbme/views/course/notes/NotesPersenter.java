package com.me.fanyin.zbme.views.course.notes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.me.data.model.play.NoteClassDetail;
import com.me.data.model.play.NoteDetail;
import com.me.data.mvp.BasePersenter;
import com.me.data.remote.ApiClient;
import com.me.data.remote.ApiService;
import com.me.data.remote.rxjava.RxUtils;
import com.me.data.remote.utils.ParamsUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by wenpeng on 6/04/2017.
 */

public class NotesPersenter extends BasePersenter<NotesView> {

    @Inject
    NotesPersenter() {
    }

    /**
     * 上传图片
     */
    @Override
    public void getData() {
//        Disposable subscribe = ApiService.postImage(ParamsUtils.postImage(""))
//                .subscribe(new Consumer<ResponseBody>() {
//                               @Override
//                               public void accept(ResponseBody result) throws Exception {
//                                   String data=result.string();
//                               }
//                           }, ErrorHandlers.displayErrorConsumer(AppContext.getInstance())
//                );
//        addSubscription(subscribe);
    }

    public void postImage(final String path) {

        Disposable subscribe=Observable.just(path)
                .map(new Function<String, MyBitObj>() {
                    @Override
                    public MyBitObj apply(@NonNull String s) throws Exception {
                        File file=new File(s);
                        byte[] btyps;
                        Bitmap bitmap;
                        if(file.length()>1000*1024){
                            BitmapFactory.Options options=new BitmapFactory.Options();
                            options.inPreferredConfig=Bitmap.Config.ARGB_4444;
                            options.inSampleSize=4;
                            bitmap=compressByQuality(BitmapFactory.decodeFile(s,options),300*1024);
                            btyps=bitmap2Bytes(bitmap,Bitmap.CompressFormat.JPEG);

                            MyBitObj myBitObj = new MyBitObj();
                            myBitObj.setBytes(btyps);
                            myBitObj.setName(file.getName());

                            bitmap.recycle();
                            return myBitObj;
                        }else{
//                            bitmap=BitmapFactory.decodeFile(s);
//                            btyps=bitmap2Bytes(bitmap,Bitmap.CompressFormat.JPEG);
                            MyBitObj myBitObj = new MyBitObj();
                            return myBitObj;
                        }

                    }
                })
                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Consumer<MyBitObj>() {
                    @Override
                    public void accept(@NonNull MyBitObj myBitObj) throws Exception {
                        if(TextUtils.isEmpty(myBitObj.getName())){
                            getImageUrl(path);
                        }else{
                            getImageUrl(myBitObj);
                        }
                    }
                });
        addSubscription(subscribe);
    }

    public void getImageUrl(MyBitObj myBitObj){
        RequestBody requestFile =RequestBody.create(MediaType.parse("multipart/form-data"), myBitObj.getBytes());
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", myBitObj.getName(), requestFile);
        Call<String> call = ApiClient.getApiInterface().postImg(body,ParamsUtils.commonSignPramas());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String str = response.body();
                    JSONObject jpath=JSON.parseObject(str);
                    String path=jpath.getString("obj");
                    if(!TextUtils.isEmpty(path) && getMvpView()!=null){
                        getMvpView().postResult(path);
                    }
                } else {
                    Toast.makeText(getMvpView().context(), "数据异常", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                getMvpView().showPostImgError();
            }
        });
    }

    public void getImageUrl(String path){
        File file=new File(path);
        RequestBody requestFile =RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        Call<String> call = ApiClient.getApiInterface().postImg(body,ParamsUtils.commonSignPramas());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String str = response.body();
                    JSONObject jpath=JSON.parseObject(str);
                    String path=jpath.getString("obj");
                    if(!TextUtils.isEmpty(path) && getMvpView()!=null){
                        getMvpView().postResult(path);
                    }
                } else {
                    Toast.makeText(getMvpView().context(), "数据异常", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                getMvpView().showPostImgError();
            }
        });
    }

    public static class MyBitObj{
        private byte[] bytes;
        private String name;

        public byte[] getBytes() {
            return bytes;
        }

        public void setBytes(byte[] bytes) {
            this.bytes = bytes;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    /**
     * bitmap转byteArr
     *
     * @param bitmap bitmap对象
     * @param format 格式
     * @return 字节数组
     */
    public static byte[] bitmap2Bytes(final Bitmap bitmap, final Bitmap.CompressFormat format) {
        if (bitmap == null) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(format, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 按质量压缩
     *
     * @param src         源图片
     * @param maxByteSize 允许最大值字节数
     * @return 质量压缩压缩过的图片
     */
    public static Bitmap compressByQuality(final Bitmap src, final long maxByteSize) {
        return compressByQuality(src, maxByteSize, false);
    }

    /**
     * 按质量压缩
     *
     * @param src         源图片
     * @param maxByteSize 允许最大值字节数
     * @param recycle     是否回收
     * @return 质量压缩压缩过的图片
     */
    public static Bitmap compressByQuality(final Bitmap src, final long maxByteSize, final boolean recycle) {
        if (isEmptyBitmap(src) || maxByteSize <= 0) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int quality = 100;
        src.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        while (baos.toByteArray().length > maxByteSize && quality > 0) {
            baos.reset();
            src.compress(Bitmap.CompressFormat.JPEG, quality -= 5, baos);
        }
        if (quality < 0) return null;
        byte[] bytes = baos.toByteArray();
        if (recycle && !src.isRecycled()) src.recycle();
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * 判断bitmap对象是否为空
     *
     * @param src 源图片
     * @return {@code true}: 是<br>{@code false}: 否
     */
    private static boolean isEmptyBitmap(final Bitmap src) {
        return src == null || src.getWidth() == 0 || src.getHeight() == 0;
    }

    /**
     * 上传笔记
     */
    public void postComment(NoteDetail detail,String title,String content,String urls) {
        if(TextUtils.isEmpty(title) || TextUtils.isEmpty(content)){
            getMvpView().showError("标题，内容不能为空");
            return;
        }
        Disposable subscribe = ApiService.postNotes(ParamsUtils.postNotes(detail,title,content,urls))
                .compose(RxUtils.<String>retrofit())
                .subscribe(new Consumer<String>() {
                               @Override
                               public void accept(String result) throws Exception {
                                    getMvpView().showResult("笔记添加成功");
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
     * 上传笔记 ketang
     */
    public void postCommentClass(NoteClassDetail noteDetails, String title, String content, String files) {
        if(TextUtils.isEmpty(title) || TextUtils.isEmpty(content)){
            getMvpView().showError("标题，内容不能为空");
            return;
        }
        Disposable subscribe = ApiService.postNotesClass(ParamsUtils.postNotesClass(noteDetails,title,content,files))
                .compose(RxUtils.<String>retrofit())
                .subscribe(new Consumer<String>() {
                               @Override
                               public void accept(String result) throws Exception {
                                   getMvpView().showCourseResult(result);
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
     * 笔记跟新 ketang
     */
    public void updaNoteClass(String noteId,String title,String content,String files) {
        if(TextUtils.isEmpty(title) || TextUtils.isEmpty(content)){
            getMvpView().showError("标题，内容不能为空");
            return;
        }
        Disposable subscribe = ApiService.updaNoteClass(ParamsUtils.updateNotesClass(noteId,title,content,files))
                .compose(RxUtils.<String>retrofit())
                .subscribe(new Consumer<String>() {
                               @Override
                               public void accept(String result) throws Exception {
                                   getMvpView().showResult("笔记更新成功");
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
     * 笔记跟新
     */
    public void updaNote(NoteDetail detail,String title,String content,String urls) {
        if(TextUtils.isEmpty(title) || TextUtils.isEmpty(content)){
            getMvpView().showError("标题，内容不能为空");
            return;
        }
        Disposable subscribe = ApiService.updaNote(ParamsUtils.updateNotes(detail,title,content,urls))
                .compose(RxUtils.<String>retrofit())
                .subscribe(new Consumer<String>() {
                               @Override
                               public void accept(String result) throws Exception {
                                    getMvpView().showResult("笔记更新成功");
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

}
