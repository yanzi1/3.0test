package com.me.rxdownload.utils;

import com.me.rxdownload.download.DownloadApi;
import com.me.rxdownload.entity.DownloadBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * Created by mayunfei on 17-3-28.
 */

public class ParserUtils {

    static final String EXTINF_TAG_PREFIX = "#EXTINF";
    static final String NEW_LINE_CHAR = "\\r?\\n";
    public static final String M3U8_PATH = "m3u8";
    public static final String LECTURE_PATH = "lecture";
    public static final String LECTURE_NAME = "lecture.html";
    public static final String M3U8_NAME = "video.m3u8";
    public static final String MP3_NAME = "video.mp3";
    public static final long PART_MP3 = 800 * 1024;// 800kb

    /**
     * 解析html
     */
    public static Observable<DownloadBean> htmlParser(DownloadApi downloadApi, final String url,
                                                      final String path) {
        return getResponseBody(downloadApi, url).flatMap(
                new Function<ResponseBody, ObservableSource<DownloadBean>>() {
                    @Override
                    public ObservableSource<DownloadBean> apply(@NonNull final ResponseBody responseBody)
                            throws Exception {
                        return Observable.create(new ObservableOnSubscribe<DownloadBean>() {
                            @Override
                            public void subscribe(ObservableEmitter<DownloadBean> emitter)
                                    throws Exception {
                                String childPath = childPath(path, LECTURE_PATH);

                                String html = responseBody.string();

                                List<String> list = new ArrayList<>();
                                Document doc = Jsoup.parse(html);
                                Elements csss = doc.select("link");
                                Elements jss = doc.select("script");
                                Elements imgs = doc.select("img");
                                int index = url.lastIndexOf("/");
                                String rootUrl = url.substring(0, index + 1);

                                for (Element element : csss) {
                                    String result = element.attr("href");
                                    String path = toAbsolutePath(rootUrl, result);

                                    if (!list.contains(path))
                                        list.add(toAbsolutePath(rootUrl, result));
                                    // 替换文件为相对路径
                                    element.attr("href", result.substring(result.lastIndexOf("/") + 1));
                                }
                                for (Element element : jss) {
                                    String result = element.attr("src");
                                    String path = toAbsolutePath(rootUrl, result);
                                    if (!list.contains(path)) list.add(path);
                                    // 替换文件为相对路径
                                    element.attr("src", result.substring(result.lastIndexOf("/") + 1));
                                }
                                for (Element element : imgs) {
                                    String result = element.attr("src");
                                    String path = toAbsolutePath(rootUrl, result);
                                    if (!list.contains(path)) list.add(path);
                                    // 替换文件为相对路径
                                    element.attr("src", result.substring(result.lastIndexOf("/") + 1));
                                }

                                if (!saveFile(LECTURE_NAME, childPath, doc.html())) {
                                    emitter.onError(new Exception("can not write html file"));
                                    return;
                                }
                                for (String itemUrl : list) {
                                    emitter.onNext(DownloadBean.newBuilder()
                                            .fileName(FileUtils.getFileNameFromUrl(itemUrl))
                                            .path(childPath)
                                            .url(itemUrl)
                                            .priority(DownloadBean.PRIORITY_LOW)
                                            .build());
                                }


                                emitter.onComplete();
                            }
                        });
                    }
                });
    }

    private static Observable<ResponseBody> getResponseBody(DownloadApi downloadApi, String url) {
        return downloadApi.download(url)
                .toObservable()
                .flatMap(new Function<Response<ResponseBody>, ObservableSource<ResponseBody>>() {
                    @Override
                    public ObservableSource<ResponseBody> apply(
                            @NonNull final Response<ResponseBody> response) throws Exception {
                        return Observable.create(new ObservableOnSubscribe<ResponseBody>() {
                            @Override
                            public void subscribe(ObservableEmitter<ResponseBody> emitter)
                                    throws Exception {
                                if (response.isSuccessful()) {
                                    emitter.onNext(response.body());
                                    emitter.onComplete();
                                } else {
                                    emitter.onError(new HttpException(response));
                                }
                            }
                        });
                    }
                });
    }


//    public static Observable<DownloadBean> mp3Parser(DownloadApi downloadApi, final String url, final String path) {
//        return downloadApi.download(url)
//                .toObservable()
//                .flatMap(new Function<Response<ResponseBody>, ObservableSource<Long>>() {
//                    @Override
//                    public ObservableSource<Long> apply(@NonNull final Response<ResponseBody> response) throws Exception {
//                        return new Observable<Long>() {
//                            @Override
//                            protected void subscribeActual(Observer<? super Long> emitter) {
//                                if (response.isSuccessful()) {
//                                    emitter.onNext(response.body().contentLength());
//                                    emitter.onComplete();
//                                } else {
//                                    emitter.onError(new HttpException(response));
//                                }
//                            }
//                        };
//                    }
//                }).flatMap(new Function<Long, ObservableSource<DownloadBean>>() {
//                    @Override
//                    public ObservableSource<DownloadBean> apply(@NonNull Long length) throws Exception {
//
//
//                        long count = length / PART_MP3;
//                        long end = length % PART_MP3;
//                        //将mp3分段 分解任务
//                        return null;
//                    }
//                });
//    }

    public static Observable<DownloadBean> m3u8Parser(DownloadApi downloadApi, final String url,
                                                      final String path) {

        return getResponseBody(downloadApi, url).flatMap(
                new Function<ResponseBody, ObservableSource<DownloadBean>>() {
                    @Override
                    public ObservableSource<DownloadBean> apply(@NonNull final ResponseBody responseBody)
                            throws Exception {
                        return Observable.create(new ObservableOnSubscribe<DownloadBean>() {
                            @Override
                            public void subscribe(ObservableEmitter<DownloadBean> emitter)
                                    throws Exception {
                                String childPath = childPath(path, M3U8_PATH);
                                String m3u8Str = responseBody.string();
                                if (!saveFile(M3U8_NAME, childPath, m3u8Str)) {
                                    emitter.onError(new Exception("can not write m3u8 file"));
                                    return;
                                }
                                Scanner scanner = new Scanner(m3u8Str).useDelimiter(EXTINF_TAG_PREFIX);
                                if (scanner.hasNext()) {
                                    String info = scanner.next();
                                    L.i("m3u8 info");
                                }

                                while (scanner.hasNext()) {
                                    String next = scanner.next();
                                    String[] item = next.split(NEW_LINE_CHAR);
                                    String videoUrl = item[1];
                                    if (!videoUrl.toLowerCase().contains("http://") || !videoUrl.toLowerCase()
                                            .contains("https://")) {
                                        videoUrl = url.substring(0, url.lastIndexOf("/") + 1) + videoUrl;
                                    }

                                    emitter.onNext(DownloadBean.newBuilder()
                                            .fileName(FileUtils.getFileNameFromUrl(videoUrl))
                                            .path(childPath)
                                            .url(videoUrl)
                                            .build());
                                }
                                emitter.onComplete();
                            }
                        });
                    }
                });
    }

    public static Observable<DownloadBean> mp3Parser(DownloadApi downloadApi, final String url, final String path) {
        return getResponseBody(downloadApi, url).flatMap(new Function<ResponseBody, ObservableSource<DownloadBean>>() {
            @Override
            public ObservableSource<DownloadBean> apply(@NonNull final ResponseBody responseBody) throws Exception {
                return Observable.create(new ObservableOnSubscribe<DownloadBean>() {
                    @Override
                    public void subscribe(ObservableEmitter<DownloadBean> emitter) throws Exception {
                        long totalSize = responseBody.contentLength();
                        long count = totalSize / PART_MP3;
                        long remainder = totalSize % PART_MP3;

                        String childPath = childPath(path, M3U8_PATH);
                        for (int i = 0; i < count; i++) {
                            setMp3DownloadBean(emitter, url, childPath, i * PART_MP3, PART_MP3);
                        }

                        if (remainder > 0) {
                            setMp3DownloadBean(emitter, url, childPath, count * PART_MP3, remainder);
                        }
                        emitter.onComplete();
                    }
                });
            }
        });
    }

    private static void setMp3DownloadBean(ObservableEmitter<DownloadBean> emitter, String url, String path, long startSize, long totalSize) {
        emitter.onNext(DownloadBean.newBuilder()
                .fileName(MP3_NAME)
                .path(path)
                .startSize(startSize)
                .totalSize(totalSize)
                .multiple_type(DownloadBean.TYPE_MULTIPLE)
                .url(url)
                .build());
    }

    private static String toAbsolutePath(String rootUrl, String url) {
        String result = "";
        if (url.startsWith("http://") || url.startsWith("https://")) {
            result = url;
        } else {
            result = rootUrl + url;
        }
        return result;
    }

    /**
     * 写入字符串
     */
    private static boolean saveFile(String name, String path, String content) {
        PrintWriter printWriter = null;
        File file = new File(path, name);
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            printWriter = new PrintWriter(file);
            printWriter.print(content);
            printWriter.flush();
            printWriter.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            if (file.exists()) {
                file.delete();
            }
            return false;
        }
    }

    private static String childPath(String path, String childPath) {
        File file = new File(path + File.separator + childPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }
}
