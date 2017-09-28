package com.me.fanyin.zbme.views.download;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.me.core.app.AppContext;
import com.me.core.utils.ToastBarUtils;
import com.me.data.common.Constants;
import com.me.data.local.SharedPrefHelper;
import com.me.data.model.play.CourseWare;
import com.me.data.remote.ApiInterface;
import com.me.data.remote.ApiService;
import com.me.data.remote.rxjava.RxUtils;
import com.me.data.remote.utils.LoggingInterceptor;
import com.me.data.remote.utils.ParamsUtils;
import com.me.fanyin.zbme.views.course.play.db.PlayParamsDB;
import com.me.fanyin.zbme.views.course.play.utils.StringUtil;
import com.me.rxdownload.RxDownloadManager;
import com.me.rxdownload.db.DownloadDao;
import com.me.rxdownload.entity.DownloadBundle;
import com.me.rxdownload.entity.DownloadEvent;
import com.me.rxdownload.utils.ParserUtils;
import com.me.rxdownload.utils.StorageBean;
import com.me.rxdownload.utils.StorageUtils;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 对 RxDownload 业务封装
 * Created by mayunfei on 17-4-6.
 */

public class DownloadManager {
    private RxDownloadManager rxDownloadManager;
    private String rootPath;
    private PlayParamsDB playParamsDB;

    private static DownloadManager instance;

    public static DownloadManager getInstance() {
        return instance;
    }

    public static void init(Context context) {
        RxDownloadManager rxDownloadManager = RxDownloadManager.getInstance();
        String downloadRootPath = null;
        /**
         * 如果有sd卡默认是先存在sd卡上的
         */
        if (!rxDownloadManager.isInit()) {
            //设置下载专用的
            Retrofit retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .baseUrl(ApiInterface.BASE_URL).client(new OkHttpClient.Builder()
                            .addInterceptor(new LoggingInterceptor())
                            .readTimeout(30, TimeUnit.SECONDS)
                            .writeTimeout(30, TimeUnit.SECONDS)
                            .connectTimeout(com.me.core.common.Constants.HTTP_TIME_OUT, TimeUnit.SECONDS).build()).build();
            downloadRootPath = SharedPrefHelper.getInstance().getDownloadRootPath();
            if (TextUtils.isEmpty(downloadRootPath)) {
                ArrayList<StorageBean> storageData = StorageUtils.getStorageData(context);
                if (storageData.size() == 1) {
                    downloadRootPath = storageData.get(0).getPath();
                } else if (storageData.size() > 1) {
                    //先查询是否有 sd卡
                    for (int i = 0; i < storageData.size(); i++) {
                        StorageBean storageBean = storageData.get(i);
                        if (storageBean.getRemovable() && storageBean.getMounted().equalsIgnoreCase("mounted")) { //可移除 挂在 是SD 卡
                            downloadRootPath = storageBean.getPath();
                            break;
                        }
                    }
                    //如果为空是没有可以用的sd卡
                    if (TextUtils.isEmpty(downloadRootPath)) {
                        for (int i = 0; i < storageData.size(); i++) {
                            StorageBean storageBean = storageData.get(i);
                            if (!storageBean.getRemovable()) { //可移除 挂在 是SD 卡
                                downloadRootPath = storageBean.getPath();
                                break;
                            }
                        }
                    }

                }
                SharedPrefHelper.getInstance().setDownloadRootPath(downloadRootPath);
            }

            rxDownloadManager.init(context, retrofit);
        }
        instance = new DownloadManager(rxDownloadManager, downloadRootPath);
    }


    public DownloadManager(RxDownloadManager rxDownloadManager, String rootPath) {
        this.rxDownloadManager = rxDownloadManager;
        this.rootPath = rootPath;
        playParamsDB = new PlayParamsDB(AppContext.getInstance());

    }

    private static String getUserId() {
        return SharedPrefHelper.getInstance().getUserId();
    }

    /**
     * 获取下载状态
     */
    public Observable<DownloadEvent> getDownloadEvent(final CourseWare courseWare) {
        return getDownloadEvent(getKey(courseWare));
    }


    public boolean isCWDownlaoded(CourseWare cw) {
        return RxDownloadManager.getInstance().isDownloadFinishedBy(getKey(cw));
    }

    public boolean isAddDownloadTask(CourseWare cw) {
        return RxDownloadManager.getInstance().isAddDownloadTask(getKey(cw));
    }

//    /**
//     * 下载视频
//     */
//    public Observable<?> download(final CourseWare courseWare) {
//        return download(courseWare, DownloadBundle.sd);
//    }

    /**
     * 下载视频
     *
     * @param type 高清 普清
     */
    public Observable<?> download(final CourseWare courseWare, int type) {
        return parseDownloadPause(courseWare, type)
                .flatMap(new Function<DownloadData, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull DownloadData downloadData) throws Exception {
                        return addDownloadTask(downloadData); //下载
                    }
                });
    }

    /**
     * 是否有足够空间
     */
    public boolean haveEnoughSpace() {
        int downloadingCount = rxDownloadManager.getDownloadingCount(getUserId());
        String path = SharedPrefHelper.getInstance().getDownloadRootPath();
        long availableSize = StorageUtils.getAvailableSize(path);
        return availableSize > (Constants.MAX_CACHE_SPACE * (1 + downloadingCount));
    }

    /**
     * 下载视频
     *
     * @param quaity 高清 普清
     */
    public Observable<?> download(final CourseWare courseWare, String quaity) {

        if (isAddDownloadTask(courseWare)) {
            return Observable.create(new ObservableOnSubscribe<Object>() {
                @Override
                public void subscribe(ObservableEmitter<Object> e) throws Exception {
                    e.onNext(new Object());
                    e.onComplete();
                }
            });
        }

        int type = DownloadBundle.cif;
        switch (quaity) {
            case "流畅":
                type = DownloadBundle.cif;
                break;
            case "标清":
                type = DownloadBundle.sd;
                break;
            case "高清":
                type = DownloadBundle.hd;
                break;
        }
        return parseDownloadPause(courseWare, type)
                .flatMap(new Function<DownloadData, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull DownloadData downloadData) throws Exception {
                        return addDownloadTask(downloadData); //下载
                    }
                });
    }

    /**
     * 暂停
     */
    public Observable<?> pause(CourseWare courseWare) {
        return rxDownloadManager.pause(getKey(courseWare));
    }

    /**
     * 获取 本地http地址
     */
    public Single<String> getHttpRootPath(CourseWare courseWare) {
        return getDownloadPath(courseWare).map(new Function<String, String>() {
            @Override
            public String apply(@NonNull String downloadPath) throws Exception {
                downloadPath = downloadPath.substring(0, downloadPath.lastIndexOf("/"));
                return downloadPath.substring(0, downloadPath.lastIndexOf("/"));
            }
        });
    }

    /**
     * 获取 本地http地址
     */
    public String getHttpRootPathString(CourseWare courseWare) {
        String downloadPath = rxDownloadManager.getDownloadPathByKeyString(getKey(courseWare));
        downloadPath = downloadPath.substring(0, downloadPath.lastIndexOf("/"));
        return downloadPath.substring(0, downloadPath.lastIndexOf("/"));
    }

    /**
     * 获取m3u8 具体位置
     */
    public Single<String> getM3u8PathObs(CourseWare courseWare) {
        return getDownloadPath(courseWare)
                .map(new Function<String, String>() {
                    @Override
                    public String apply(@NonNull String rootPath) throws Exception {
                        return getM3u8Path(rootPath);
                    }
                });
    }

    /**
     * 获取m3u8 具体位置
     */
    public String getM3u8Path(CourseWare courseWare) {

        String rootPath = rxDownloadManager.getDownloadPathByKeyString(getKey(courseWare));

        return getM3u8Path(rootPath);
    }

    /**
     * 获取mp3 具体位置
     */
    public String getMp3Path(CourseWare courseWare) {

        String rootPath = rxDownloadManager.getDownloadPathByKeyString(getKey(courseWare));

        return getMp3Path(rootPath);
    }

    /**
     * 获得 讲义html 具体位置
     */
    public String getLecturePath(CourseWare courseWare) {
        String rootPath = rxDownloadManager.getDownloadPathByKeyString(getKey(courseWare));
        return getLecturePath(rootPath);
    }

    /**
     * 获得 讲义html 具体位置
     */
    public Single<String> getLecturePathObs(CourseWare courseWare) {
        return getDownloadPath(courseWare)
                .map(new Function<String, String>() {
                    @Override
                    public String apply(@NonNull String rootPath) throws Exception {
                        return getLecturePath(rootPath);
                    }
                });
    }

//    /**
//     * 获得本地m3u8 http 映射地址
//     */
//    public static String getLocalPlayUrl(CourseWare courseWare) {
//        return "http://localhost:" + Constants.SERVER_PORT + File.separator + SharedPrefHelper.getInstance().getUserId() + File.separator + getKey(courseWare) + File.separator + ParserUtils.M3U8_PATH + File.separator + ParserUtils.M3U8_NAME;
//    }
//
//    /**
//     * 获得本地讲义 http 映射地址
//     */
//    public static String getLocalLectrue(CourseWare courseWare) {
//        return "http://localhost:" + Constants.SERVER_PORT + File.separator + SharedPrefHelper.getInstance().getUserId() + File.separator + getKey(courseWare) + File.separator + ParserUtils.LECTURE_PATH + File.separator + ParserUtils.LECTURE_NAME;
//    }


    public Observable<?> pause(String key) {
        return rxDownloadManager.pause(key);
    }

    /**
     * 获得用户所有下载 包括 已经下载
     *
     * @return
     */
    public Observable<List<DownloadBundle>> getDownloadBundleByUserId() {
        return rxDownloadManager.getDownloadBundleByUserId(getUserId());
    }

    /**
     * 获得 已经下载
     *
     * @return
     */
    public Observable<List<DownloadBundle>> getFinishedBundle() {
        return rxDownloadManager.getFinishedBundleByUserId(getUserId());
    }

    /**
     * 获得 已经下载
     *
     * @return
     */
    public Observable<List<DownloadDao.DownloadResultByClassId>> getDownloadFinishedGroupByClassId() {
        return rxDownloadManager.getDownloadFinishedGroupByClassId(getUserId());
    }


    /**
     * 获得正在现在
     *
     * @return
     */
    public Observable<List<DownloadBundle>> getDownloadingBundle() {
        return rxDownloadManager.getDownloadingBundle(getUserId());
    }

    /**
     * 暂停所有
     *
     * @return
     */
    public Observable<List<DownloadBundle>> getDownloadingBundleNoUserId() {
        return rxDownloadManager.getDownloadingBundleAll();
    }

    /**
     * 获得正在现在下载个数
     */
    public int getDownloadingCount() {

        return rxDownloadManager.getDownloadingCount(getUserId());
    }

    /**
     * 修改下载根目录
     */
    public Observable<Boolean> setRootDownloadPath(final String path) {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                SharedPrefHelper.getInstance().setDownloadRootPath(path);
                rootPath = path;
                emitter.onNext(true);
                emitter.onComplete();
            }
        });
    }

    @NonNull
    private String getPath(String userId, String key) {
        if (rootPath == null) {
            throw new IllegalArgumentException("找不到存储卡");
        }
        return getDownloadPath(rootPath, userId, key);
    }

    public Observable<?> addDownloadTask(final DownloadBundle downloadBundle) {
        return rxDownloadManager.addDownloadTask(downloadBundle);
    }

    public void pauseAll() {
        getDownloadingBundleNoUserId()
                .flatMap(new Function<List<DownloadBundle>, ObservableSource<DownloadBundle>>() {
                    @Override
                    public ObservableSource<DownloadBundle> apply(@io.reactivex.annotations.NonNull List<DownloadBundle> downloadBundleList) throws Exception {
                        return Observable.fromIterable(downloadBundleList);
                    }
                }).flatMap(new Function<DownloadBundle, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(@io.reactivex.annotations.NonNull DownloadBundle downloadBundle) throws Exception {
                return pause(downloadBundle.getKey());
            }
        }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(@io.reactivex.annotations.NonNull Object o) throws Exception {
                Logger.i("××××××××××××××××××× 暂停所有");
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@io.reactivex.annotations.NonNull Throwable throwable) throws Exception {
                Log.e("pause all error",throwable.toString());
            }
        });
    }


    /**
     * 获得下载路径
     */
    @NonNull
    public String getDownladRoot(String rootPath) {
        if (Build.VERSION.SDK_INT >= 19) {
            getFilesDirs(AppContext.getInstance());
        }
        final String dir = rootPath + "/Android/data/" + AppContext.getInstance().getPackageName() + "/files/Download/";
        final File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }

        return file.getAbsolutePath();
    }

    @NonNull
    private String getDownloadPath(String rootPath, String userId, String key) {
        if (Build.VERSION.SDK_INT >= 19) {
            getFilesDirs(AppContext.getInstance());
        }
        final String dir = rootPath + "/Android/data/" + AppContext.getInstance().getPackageName() + "/files/Download/" + userId + "/" + key + "/";
        final File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }

        return file.getAbsolutePath();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void getFilesDirs(Context context) {
        try {
            final File[] dirs = context.getExternalFilesDirs(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Observable<DownloadEvent> getDownloadEvent(String key) {
        return rxDownloadManager.getDownloadEvent(key);
    }

    public Observable<?> delete(final String key) {
        return rxDownloadManager.delete(key);
    }

//    public Observable<List<Object>> deleteByExamIdSubjectIdClassId(String examId, String subjectId, String classId) {
//        return rxDownloadManager.deleteByExamIdSubjectIdClassId(getUserId(), examId, subjectId, classId);
//    }

    public Observable<List<Object>> deleteFinishedByExamIdSubjectIdClassId(String examId, String subjectId, String classId) {
        return rxDownloadManager.deleteFinishedByExamIdSubjectIdClassId(getUserId(), examId, subjectId, classId);
    }

    public static String getKey(CourseWare courseWare) {
        return getKey(courseWare.getsSubjectId(), courseWare.getClassId(), courseWare.getSectionId(), courseWare.getId());
    }

    private static String getKey(String subjectId, String classid, String sessionId, String cwid) {
        return getUserId() + "_" + subjectId + "_" + classid + "_" + sessionId + "_" + cwid;
    }

    private Observable<?> addDownloadTask(final DownloadData downloadData) {
        final String key = getKey(downloadData.courseWare);
        return Observable.just(downloadData.courseWare)
                .flatMap(new Function<CourseWare, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@io.reactivex.annotations.NonNull CourseWare courseWare) throws Exception {

                        DownloadBundle downloadBundle = new DownloadBundle();
                        downloadBundle.setKey(key);
                        downloadBundle.setType(downloadData.type);
                        downloadBundle.setPath(getPath(getUserId(), key));
                        downloadBundle.setExamId(StringUtil.toInt(courseWare.getExamId(), 0));
                        downloadBundle.setsSubjectId(StringUtil.toInt(courseWare.getsSubjectId(), 0));
                        downloadBundle.setCwId(StringUtil.toInt(courseWare.getId(), 0));
                        downloadBundle.setClassId(StringUtil.toInt(courseWare.getClassId(), 0));
                        downloadBundle.setSessionId(StringUtil.toInt(courseWare.getSectionId(), 0));
                        downloadBundle.setCwStr(JSON.toJSONString(courseWare));
                        downloadBundle.setUserId(StringUtil.toInt(getUserId(), 0));
                        downloadBundle.setsSubjectName(courseWare.getsSubjectName());
                        downloadBundle.setClassName(courseWare.getClassName());
                        downloadBundle.setCwName(courseWare.getLectureOrder() + " " + courseWare.getName());
                        downloadBundle.setSort(courseWare.getSort());

                        return rxDownloadManager.addDownloadTask(getPath(getUserId(), key)
                                , downloadData.courseWare.getMobileDownloadUrl()
                                , downloadData.courseWare.getMobileLectureUrl()
                                , downloadData.courseWare.getMobileVideoMP3(), downloadBundle);

//                        return rxDownloadManager.addDownloadTask(key,getPath(getUserId(),key),downloadData.courseWare.getMobileDownloadUrl()
//                                ,downloadData.courseWare.getMobileLectureUrl()
//                                ,downloadData.courseWare.getMobileVideoMP3()
//                                ,downloadData.courseWare.getExamId()
//                                ,downloadData.courseWare.getsSubjectId()
//                                ,downloadData.courseWare.getClassId()
//                                ,downloadData.courseWare.getSectionId()
//                                ,downloadData.courseWare.getCwId()
//                                ,downloadData.type
//                        );
                    }
                });
//                .map(new Function<CourseWare, String>() {
//                    @Override
//                    public String apply(@NonNull CourseWare courseWare) throws Exception {
//                        return JSON.toJSONString(courseWare);
//                    }
//                }).flatMap(new Function<String, ObservableSource<?>>() {
//                    @Override
//                    public ObservableSource<?> apply(@NonNull final String courseWareJson) throws Exception {
//                        //
//                        return Observable.just(1).map(new Function<Integer, String>() {
//                            @Override
//                            public String apply(@NonNull Integer integer) throws Exception {
//                                return SharedPrefHelper.getInstance().getUserId();
//                            }
//                        }).flatMap(new Function<String, ObservableSource<?>>() {
//                            @Override
//                            public ObservableSource<?> apply(@NonNull String user_id) throws Exception {
//
//                                return rxDownloadManager.addDownloadTask(key,getPath(user_id,key),downloadData.courseWare.getMobileDownloadUrl()
//                                        ,downloadData.courseWare.getMobileLectureUrl()
//                                        ,downloadData.courseWare.getMobileVideoMP3()
//                                        ,downloadData.courseWare.getExamId()
//                                        ,downloadData.courseWare.getsSubjectId()
//                                        ,downloadData.courseWare.getClassId()
//                                        ,downloadData.courseWare.getSectionId()
//                                        ,downloadData.courseWare.getCwId()
//                                        ,downloadData.type
//                                        );
////                                return rxDownloadManager.addDownloadTask(key, getPath(user_id, key), courseWare.getMobileDownloadUrl(), courseWare.getMobileLectureUrl()
////                                        , "subject", "class", courseWareJson, user_id, courseWare.getsSubjectId(), courseWare.getClassId(), courseWare.getCwId());
//                            }
//                        });
////                        return addDownloadTask(key, "https://md1.dongaocloud.com/2b52/2b61/a43/077/46fe337ecb80e243a3283dd222061f58/video.m3u8",courseWare.getMobileLectureUrl());
//                    }
//                });
    }

    public Observable<List<DownloadBundle>> getDownloadedBundleBySubjectAndClass(final String subjectid, final String classid) {
        return rxDownloadManager.getDownloadedBundleBySubjectAndClass(getUserId(), subjectid, classid);
    }

    private Single<String> getDownloadPath(CourseWare courseWare) {
        return getDownloadPathByKey(getKey(courseWare));
    }

    private Single<String> getDownloadPathByKey(final String key) {

        return rxDownloadManager.getDownloadPathByKey(key);
    }


    private String getM3u8Path(String rootPath) {
        return rootPath + File.separator + ParserUtils.M3U8_PATH + File.separator + ParserUtils.M3U8_NAME;
    }

    private String getMp3Path(String rootPath) {
        return rootPath + File.separator + ParserUtils.M3U8_PATH + File.separator + ParserUtils.MP3_NAME;
    }

    private String getLecturePath(String rootPath) {
        return rootPath + File.separator + ParserUtils.LECTURE_PATH + File.separator + ParserUtils.LECTURE_NAME;
    }


    /**
     * 解析下载地址
     */
    private Observable<DownloadData> parseDownloadPause(final CourseWare courseWare, final int videoType) {
        return ApiService.getDownloadUrl(courseWare.getId())
                .compose(RxUtils.<String>retrofit()) //几种方式实现逻辑前置
                .map(new Function<String, DownloadData>() {
                    @Override
                    public DownloadData apply(@NonNull String obj) throws Exception {
                        //获取下载路径
                        JSONObject body = new JSONObject(obj);
                        JSONObject video = body.optJSONObject("video");
                        JSONObject playType = null;
                        switch (videoType) {
                            case DownloadBundle.cif://流畅
                                playType = video.optJSONObject("cif");
                                break;
                            case DownloadBundle.sd://标清
                                playType = video.optJSONObject("sd");
                                break;
                            case DownloadBundle.hd://高清
                                playType = video.optJSONObject("hd");
                                break;
                        }

                        if (playType == null || playType.optJSONArray("1.0") == null) {
                            playType = video.optJSONObject("sd");
                            if (playType == null || playType.optJSONArray("1.0") == null) {
                                playType = video.optJSONObject("cif");
                            }
                            ToastBarUtils.showToast(AppContext.getInstance(), "该视频暂无您所选清晰度，已为您下载其他格式");
                        }


                        JSONArray audiolist = body.optJSONObject("audio").optJSONObject("sd").optJSONArray("1.0");//sd hd
                        if (audiolist != null && audiolist.length() > 0) {
                            String mp3Url = audiolist.getString(0);
                            courseWare.setMobileVideoMP3(mp3Url);
                        }

                        JSONArray one = playType.optJSONArray("1.0");
                        if (one != null && one.length() > 0) {
                            String url = one.getString(0);
                            courseWare.setMobileDownloadUrl(url);
                            courseWare.setMobileLectureUrl(ParamsUtils.getLectureUrl(courseWare.getId()));

                            JSONObject cipherkeyVo = body.optJSONObject("cipherkeyDeal");
                            if (cipherkeyVo != null) {
                                String app = cipherkeyVo.optString("app");
                                String type = cipherkeyVo.optString("type");
                                String vid = cipherkeyVo.optString("vid");
                                String vkey = cipherkeyVo.optString("key");
                                String vcode = cipherkeyVo.optString("code");
                                String message = cipherkeyVo.optString("message");

                                playParamsDB.add(SharedPrefHelper.getInstance().getUserId(), courseWare.getId(), app, type, vid, vkey, vcode, message);

                                DownloadData downloadData = new DownloadData();
                                downloadData.courseWare = courseWare;
                                downloadData.type = videoType;
                                return downloadData;
                            } else {
                                throw new Exception("解析地址错误");
                            }
                        } else {
                            return null;
                        }

                    }
                })

                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).toObservable();
    }


    private static class DownloadData {
        private CourseWare courseWare;
        private int type;
    }

}
