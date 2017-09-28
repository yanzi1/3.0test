package com.me.rxdownload;

import android.content.Context;

import com.me.rxdownload.db.DownloadDao;
import com.me.rxdownload.db.IDownloadDB;
import com.me.rxdownload.download.DownloadApi;
import com.me.rxdownload.download.DownloadTask;
import com.me.rxdownload.download.ServiceHelper;
import com.me.rxdownload.entity.DownloadBean;
import com.me.rxdownload.entity.DownloadBundle;
import com.me.rxdownload.entity.DownloadEvent;
import com.me.rxdownload.utils.ParserUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * 下载管理
 * Created by yunfei on 17-3-25.
 */

public class RxDownloadManager {

    public static final int MAX_DOWNLOAD_COUNT = 1;

    private ServiceHelper serviceHelper;
    private DownloadApi downloadApi;
    private boolean isInit;
    private IDownloadDB downloadDB;

    public boolean isInit() {
        return isInit;
    }


    private static RxDownloadManager instance = new RxDownloadManager();

    private RxDownloadManager() {
    }

    public static RxDownloadManager getInstance() {
        return instance;
    }

    public synchronized void init(Context context, Retrofit retrofit) {
        serviceHelper = new ServiceHelper(context);
        downloadApi = retrofit.create(DownloadApi.class);
        isInit = true;
        downloadDB = DownloadDao.getSingleton(context);
    }

//    /**
//     * @param key  查找唯一值
//     * @param path 地址
//     * @param m3u8 文件url
//     * @param html 文件url
//     * @return
//     */
//    public Observable<?> addDownloadTask(final String key, final String path, String m3u8, String html, final int userId) {
//        return Observable.merge(ParserUtils.m3u8Parser(downloadApi, m3u8, path),
//                ParserUtils.htmlParser(downloadApi, html, path))
////        return ParserUtils.m3u8Parser(downloadApi, m3u8, path)
//                .toList().toObservable().flatMap(new Function<List<DownloadBean>, ObservableSource<?>>() {
//                    @Override
//                    public ObservableSource<?> apply(@NonNull List<DownloadBean> downloadBeen)
//                            throws Exception {
//                        DownloadBundle downloadBundle = new DownloadBundle();
//                        downloadBundle.setPath(path);
//                        downloadBundle.setKey(key);
//                        downloadBundle.setUserId(userId);
//                        downloadBundle.setDownloadList(downloadBeen);
//                        downloadBundle.setTotalSize(downloadBeen.size());
//                        return addDownloadTask(new DownloadTask(downloadBundle));
//                    }
//                }).subscribeOn(Schedulers.io());
//    }

    //    /**
//     * @param key  查找唯一值
//     * @param path 地址
//     * @param m3u8 文件url
//     * @param html 文件url
//     * @return
//     */
//    public Observable<?> addDownloadTask(final String key, final String path, String m3u8, String html, final String args0, final String args1, final String args2, final String where0, final String where1, final String where2, final String where3) {
//        return Observable.merge(ParserUtils.m3u8Parser(downloadApi, m3u8, path),
//                ParserUtils.htmlParser(downloadApi, html, path))
////        return ParserUtils.m3u8Parser(downloadApi, m3u8, path)
//                .toList().toObservable().flatMap(new Function<List<DownloadBean>, ObservableSource<?>>() {
//                    @Override
//                    public ObservableSource<?> apply(@NonNull List<DownloadBean> downloadBeen)
//                            throws Exception {
//                        DownloadBundle downloadBundle = new DownloadBundle();
//                        downloadBundle.setPath(path);
//                        downloadBundle.setKey(key);
//                        downloadBundle.setWhere0(where0);
//                        downloadBundle.setWhere1(where1);
//                        downloadBundle.setWhere2(where2);
//                        downloadBundle.setWhere3(where3);
//                        downloadBundle.setArgs0(args0);
//                        downloadBundle.setArgs1(args1);
//                        downloadBundle.setArgs2(args2);
//                        downloadBundle.setDownloadList(downloadBeen);
//                        downloadBundle.setTotalSize(downloadBeen.size());
//                        return addDownloadTask(new DownloadTask(downloadBundle));
//                    }
//                }).subscribeOn(Schedulers.io());
//    }
    public ObservableSource<?> addDownloadTask(String path, String mobileDownloadUrl, String mobileLectureUrl, String mobileVideoMP3, final DownloadBundle downloadBundle) {
        return Observable.merge(
                ParserUtils.m3u8Parser(downloadApi, mobileDownloadUrl, path),
                ParserUtils.htmlParser(downloadApi, mobileLectureUrl, path),
                ParserUtils.mp3Parser(downloadApi, mobileVideoMP3, path))
                .toList().toObservable()
                .flatMap(new Function<List<DownloadBean>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@io.reactivex.annotations.NonNull List<DownloadBean> downloadBeen) throws Exception {
                        downloadBundle.setDownloadList(downloadBeen);
                        downloadBundle.setTotalSize(downloadBeen.size());
                        return addDownloadTask(new DownloadTask(downloadBundle));
                    }
                }).subscribeOn(Schedulers.io());
    }

//    public ObservableSource<?> addDownloadTask(final String key, final String path, String mobileDownloadUrl, String mobileLectureUrl, String mobileVideoMP3, final String examId, final String subjectId, final String classId, final String sectionId, final String cwId, final int type) {
////        return Observable.merge(
////                ParserUtils.m3u8Parser(downloadApi, mobileDownloadUrl, path),
////                ParserUtils.htmlParser(downloadApi, mobileLectureUrl, path),
////                ParserUtils.mp3Parser(downloadApi, mobileVideoMP3, path)
////        )
//
//        return ParserUtils.mp3Parser(downloadApi, mobileVideoMP3, path)
//                .toList().toObservable().flatMap(new Function<List<DownloadBean>, ObservableSource<?>>() {
//                    @Override
//                    public ObservableSource<?> apply(@NonNull List<DownloadBean> downloadBeen)
//                            throws Exception {
//                        DownloadBundle downloadBundle = new DownloadBundle();
//                        downloadBundle.setPath(path);
//                        downloadBundle.setKey(key);
//                        downloadBundle.setExamId(Integer.parseInt(examId));
//                        downloadBundle.setsSubjectId(Integer.parseInt(subjectId));
//                        downloadBundle.setClassId(Integer.parseInt(classId));
//                        downloadBundle.setSessionId(Integer.parseInt(sectionId));
//                        downloadBundle.setCwId(Integer.parseInt(cwId));
//                        downloadBundle.setType(type);
//                        downloadBundle.setDownloadList(downloadBeen);
//                        downloadBundle.setTotalSize(downloadBeen.size());
//                        return addDownloadTask(new DownloadTask(downloadBundle));
//                    }
//                }).subscribeOn(Schedulers.io());
//    }


//    public Observable<?> addDownloadTask(String url, String path) {
//        DownloadBean bean = DownloadBean.newBuilder()
//                .url(url)
//                .fileName(FileUtils.getFileNameFromUrl(url))
//                .path(path)
//                .build();
//
//        List<DownloadBean> downloadBeanList = new ArrayList<>();
//        downloadBeanList.add(bean);
//        DownloadBundle downloadBundle = new DownloadBundle();
//        downloadBundle.setKey(url);
//        downloadBundle.setDownloadList(downloadBeanList);
//        downloadBundle.setTotalSize(downloadBeanList.size());
//        return addDownloadTask(new DownloadTask(downloadBundle));
//    }

    public Observable<?> addDownloadTask(DownloadBundle downloadBundle) {
        DownloadTask task = new DownloadTask(downloadBundle);
        return addDownloadTask(task);
    }

    public Observable<?> addDownloadTask(final DownloadTask downloadTask) {
        downloadTask.init(downloadApi); //TODO remove
        return serviceHelper.addTask(downloadTask).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<DownloadEvent> getDownloadEvent(String key) {
        return serviceHelper.getDownloadEvent(key).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<?> pause(String key) {
        return serviceHelper.pause(key).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<?> delete(String key) {
        return serviceHelper.delete(key).observeOn(AndroidSchedulers.mainThread());
    }
    public Observable<List<Object>> deleteFinishedByExamIdSubjectIdClassId(final String userId, final String examId, final String subjectId, final String classId) {

        return Observable.just(1)
                .flatMap(new Function<Integer, ObservableSource<DownloadBundle>>() {
                    @Override
                    public ObservableSource<DownloadBundle> apply(@NonNull Integer integer) throws Exception {
                        List<DownloadBundle> downloadBundles = downloadDB.getDownloadBundledBySubjectAndClass(userId, subjectId, classId);
                        return Observable.fromIterable(downloadBundles);
                    }
                }).flatMap(new Function<DownloadBundle, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(@NonNull DownloadBundle downloadBundle) throws Exception {
                return serviceHelper.delete(downloadBundle.getKey());
            }
        }).toList().toObservable().observeOn(AndroidSchedulers.mainThread());


//        return Observable.just(1)
//                .map(new Function<Integer, Boolean>() {
//                    @Override
//                    public Boolean apply(@io.reactivex.annotations.NonNull Integer integer) throws Exception {
//                        List<DownloadBundle> downloadBundles = downloadDB.getDownloadBundleByExamIdSubjectIdClassId(userId, examId, subjectId, classId);
//                        for (DownloadBundle bundle:downloadBundles){
//                            downloadDB.delete(bundle.getKey());
//                            FileUtils.deleteDir(bundle.getPath());
//                        }
//                        return true;
//                    }
//                }).subscribeOn(Schedulers.io());
    }

    public Observable<List<DownloadBundle>> getAllDownloadBundle() {
        return Observable.just(1).map(new Function<Integer, List<DownloadBundle>>() {
            @Override
            public List<DownloadBundle> apply(@io.reactivex.annotations.NonNull Integer integer) throws Exception {
                return downloadDB.getAllDownloadBundle();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<DownloadBundle>> getDownloadBundleByUserId(final String userId) {
        return Observable.just(1)
                .map(new Function<Integer, List<DownloadBundle>>() {
                    @Override
                    public List<DownloadBundle> apply(@io.reactivex.annotations.NonNull Integer integer) throws Exception {
                        return downloadDB.getDownloadBundleByUserId(userId);
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<DownloadBundle>> getDownloadedBundleBySubjectAndClass(final String userId, final String subjectId, final String classId) {
        return Observable.just(1)
                .map(new Function<Integer, List<DownloadBundle>>() {
                    @Override
                    public List<DownloadBundle> apply(@io.reactivex.annotations.NonNull Integer integer) throws Exception {
                        return downloadDB.getDownloadBundledBySubjectAndClass(userId, subjectId, classId);
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<DownloadBundle>> getFinishedBundleByUserId(final String userId) {
        return Observable.just(1)
                .map(new Function<Integer, List<DownloadBundle>>() {
                    @Override
                    public List<DownloadBundle> apply(@io.reactivex.annotations.NonNull Integer integer) throws Exception {
                        return downloadDB.getFinishedBundleByUserId(userId);
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<DownloadDao.DownloadResultByClassId>> getDownloadFinishedGroupByClassId(final String userId) {
        return Observable.just(1)
                .map(new Function<Integer, List<DownloadDao.DownloadResultByClassId>>() {
                    @Override
                    public List<DownloadDao.DownloadResultByClassId> apply(@io.reactivex.annotations.NonNull Integer integer) throws Exception {
                        return downloadDB.getDownloadFinishedGroupByClassId(userId);
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<DownloadBundle>> getDownloadingBundle(final String userId) {
        return Observable.just(1)
                .map(new Function<Integer, List<DownloadBundle>>() {
                    @Override
                    public List<DownloadBundle> apply(@io.reactivex.annotations.NonNull Integer integer) throws Exception {
                        return downloadDB.getDownloadingBundleByUserId(userId);
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


    public int getDownloadingCount(final String userId){
        return downloadDB.getDownloadingNotPauseBundleByUserId(userId).size();

    }

    public Single<String> getDownloadPathByKey(final String key) {

        return Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(SingleEmitter<String> e) throws Exception {
                e.onSuccess(downloadDB.getDownloadPathByKey(key));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public String getDownloadPathByKeyString(final String key){
        return downloadDB.getDownloadPathByKey(key);
    }


    public boolean isDownloadFinishedBy(String key) {

        return downloadDB.isDownloadFinishedBy(key);
    }


    public boolean isAddDownloadTask(String key) {
        return downloadDB.existsDownloadBundle(key);
    }

    public Observable<List<DownloadBundle>> getDownloadingBundleAll() {
        return Observable.just(1)
                .map(new Function<Integer, List<DownloadBundle>>() {
                    @Override
                    public List<DownloadBundle> apply(@io.reactivex.annotations.NonNull Integer integer) throws Exception {
                        return downloadDB.getDownloadingBundleNoUser();
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
