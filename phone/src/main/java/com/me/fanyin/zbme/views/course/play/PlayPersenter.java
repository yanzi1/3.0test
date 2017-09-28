package com.me.fanyin.zbme.views.course.play;

import android.os.Message;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.me.core.app.AppContext;
import com.me.core.exception.ApiException;
import com.me.data.common.Constants;
import com.me.data.local.SharedPrefHelper;
import com.me.data.model.play.BaseBean;
import com.me.data.model.play.CourseDetail;
import com.me.data.model.play.CourseWare;
import com.me.data.model.play.CwClarity;
import com.me.data.model.play.Opera;
import com.me.data.mvp.BasePersenter;
import com.me.data.remote.ApiClient;
import com.me.data.remote.ApiService;
import com.me.data.remote.rxjava.RxUtils;
import com.me.data.remote.utils.ParamsUtils;
import com.me.fanyin.zbme.views.course.play.db.CourseDetailDB;
import com.me.fanyin.zbme.views.course.play.db.OperaDB;
import com.me.fanyin.zbme.views.course.play.db.PlayParamsDB;
import com.me.fanyin.zbme.views.course.play.utils.AESHelper;
import com.me.fanyin.zbme.views.course.play.utils.FileUtil;
import com.me.fanyin.zbme.views.course.play.utils.SignUtils;
import com.me.fanyin.zbme.views.download.DownloadManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by wenpeng on 6/04/2017.
 */

public class PlayPersenter extends BasePersenter<PlayView> {

    private PlayParamsDB playParamsDB;
    private OperaDB operaDB;
    private String userId;
    private CourseDetailDB courseDetailDB;
    private String courseId;


    @Inject
    public PlayPersenter() {
        userId = SharedPrefHelper.getInstance().getUserId();
        courseDetailDB = new CourseDetailDB();
        operaDB = new OperaDB(AppContext.getInstance());
        playParamsDB = new PlayParamsDB(AppContext.getInstance());
    }

    @Override
    public void attachView(PlayView mvpView) {
        super.attachView(mvpView);
    }

    /**
     * 获取课程详情接口
     */
    @Override
    public void getData() {
        courseId = getMvpView().getCourseId();
        Disposable subscribe = ApiService.playDetail(ParamsUtils.playDetail(courseId))
                .compose(RxUtils.<CourseDetail>retrofit()) //几种方式实现逻辑前置
                .subscribe(new Consumer<CourseDetail>() {
                               @Override
                               public void accept(CourseDetail courseDetail) throws Exception {
                                   CourseDetail reCourseDetail = reSetDatas(courseDetail);

                                   getMvpView().initAdapter(reCourseDetail);
                                   insertData(reCourseDetail);
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(@NonNull Throwable throwable) throws Exception {
                                   getMvpView().showCwsError(throwable.getMessage());
                               }
                           }
                );
        addSubscription(subscribe);
    }

    /**
     * 收藏课程接口
     */
    public void collectCw(CourseWare cw) {
        Disposable subscribe = ApiService.collectCw(ParamsUtils.collectCw(cw.getId()))
                .compose(RxUtils.<String>retrofit()) //几种方式实现逻辑前置
                .subscribe(new Consumer<String>() {
                               @Override
                               public void accept(String result) throws Exception {
                                   getMvpView().collectCwResult();
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
     * 取消收藏课程接口
     */
    public void delCollectCw(CourseWare cw) {
        Disposable subscribe = ApiService.delCollectCw(ParamsUtils.collectCw(cw.getId()))
                .compose(RxUtils.<String>retrofit()) //几种方式实现逻辑前置
                .subscribe(new Consumer<String>() {
                               @Override
                               public void accept(String result) throws Exception {
                                   getMvpView().delCwResult();
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
     * 课程是否收藏接口
     */
    public void getIsCollect(CourseWare cw) {
        Disposable subscribe = ApiService.isCollect(ParamsUtils.isCollectCw(cw.getId()))
                .compose(RxUtils.<String>retrofit()) //几种方式实现逻辑前置
                .subscribe(new Consumer<String>() {
                               @Override
                               public void accept(String result) throws Exception {
                                   getMvpView().isCollectResult(result);
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
     * 设置每个课件的sSubjectId，classId
     *
     * @param courseDetail
     * @return
     */
    private CourseDetail reSetDatas(CourseDetail courseDetail) {
        String sSId = courseDetail.getsSubjectId();
        String sSname = courseDetail.getsSubjectName();
        String picPath = courseDetail.getPicPath();
        String className = courseDetail.getName();
        String id = courseDetail.getId();
        for (int i = 0; i < courseDetail.getResultList().size(); i++) {
            for (int k = 0; k < courseDetail.getResultList().get(i).getPcClientCourseWareList().size(); k++) {
                courseDetail.getResultList().get(i).getPcClientCourseWareList().get(k).setsSubjectId(sSId);
                courseDetail.getResultList().get(i).getPcClientCourseWareList().get(k).setClassId(id);
                courseDetail.getResultList().get(i).getPcClientCourseWareList().get(k).setPicPath(picPath);
                courseDetail.getResultList().get(i).getPcClientCourseWareList().get(k).setsSubjectName(sSname);
                courseDetail.getResultList().get(i).getPcClientCourseWareList().get(k).setClassName(className);
            }
        }
        return courseDetail;
    }

    /**
     * 缓存课程详情的数据
     *
     * @param courseDetail
     */
    private void insertData(CourseDetail courseDetail) {
        CourseDetail courseDetailInDB = courseDetailDB.find(userId, courseDetail.getsSubjectId(), courseDetail.getId());

        courseDetail.setContentJson(JSON.toJSONString(courseDetail));
        courseDetail.setUserId(userId);

        if (courseDetailInDB == null) {//数据库中不存在  插入数据库
            boolean flag = courseDetailDB.insert(courseDetail);
            if (!flag) {
                courseDetailDB.insert(courseDetail);
            }
        } else {//存在 更新数据库
            courseDetailInDB.setContentJson(JSON.toJSONString(courseDetail));
            courseDetailDB.update(courseDetailInDB);
        }
    }

    /**
     * 我会了
     */
    public void changeStatus(String nodeId, final int postion) {
        Disposable subscribe = ApiService.changeStatus(ParamsUtils.getInstance().changeStatus(nodeId))
                .compose(RxUtils.<String>retrofit()) //几种方式实现逻辑前置
                .subscribe(new Consumer<String>() {
                               @Override
                               public void accept(String data) throws Exception {
                                   getMvpView().studyKnow(postion);
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
     * 获取播放地址接口
     *
     * @param flag //是否为mp3请求
     */
    public void getPlayUrl(final CourseWare courseWare, final boolean flag) {
        Disposable subscribe = ApiService.getPlayUrl(ParamsUtils.getInstance().getPlayPath(courseWare.getId()))
                .compose(RxUtils.<String>retrofit()) //几种方式实现逻辑前置
                .subscribe(new Consumer<String>() {
                               @Override
                               public void accept(String data) throws Exception {
                                   preData(data, courseWare, flag);
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
     * 获取播放地址接口
     *
     * @param flag //是否为mp3请求
     */
    public void getSmartPlayUrl(final CourseWare courseWare, final boolean flag) {
        Disposable subscribe = ApiService.getSmartPlayUrl(ParamsUtils.getInstance().getSmartPlayPath(courseWare.getId()))
                .compose(RxUtils.<String>retrofit()) //几种方式实现逻辑前置
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                               @Override
                               public void accept(String data) throws Exception {
                                   preData(data, courseWare, flag);
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(@NonNull Throwable throwable) throws Exception {
                                   ApiException exception = (ApiException) throwable;
                                   String code = exception.getCode() + "";
                                   if (code.equals("20016")) {
                                       getMvpView().showEmptyPlay(exception.getMsg());
                                   } else {
                                       getMvpView().showError(throwable.getMessage());
                                   }
                               }
                           }
                );
        addSubscription(subscribe);
    }

    /**
     * 获取智能讲义地址接口
     */
    private void getLectureUrl(String lectureId) {
        Disposable subscribe = ApiService.getSmartLectureUrl(ParamsUtils.getInstance().getSmartLecturePath(lectureId))
                .compose(RxUtils.<String>retrofit()) //几种方式实现逻辑前置
                .subscribe(new Consumer<String>() {
                               @Override
                               public void accept(String data) throws Exception {
                                   getMvpView().showSmartContent(data);
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
     * 解析播放地址返回数据
     *
     * @params flag 是否播放mp3
     */
    private CourseWare cw;

    private void preData(String data, CourseWare courseWare, boolean flag) {
        try {
            JSONObject body = new JSONObject(data);
            String unique = body.getString("lectureID");
            String nodeId = body.getString("nodeId");
//            getLectureUrl(unique);

            if (getMvpView().isSmart()) {
                if (!nodeId.equals(courseWare.getId())) {
                    return;
                }
                getMvpView().showSmartContent(unique);
                courseWare.setLectureId(unique);
            } else {
                if (!unique.equals(courseWare.getId())) {
                    return;
                }
            }

            JSONObject video = body.getJSONObject("video");
            JSONObject audio = body.getJSONObject("audio");

//            String lecture = body.optString("handOut");

            String scheme = body.optString("scheme");
            String[] qualitys = scheme.split("\\|");
            qualitys = reSetScheme(qualitys);
            List<CwClarity> claritys = new ArrayList<>();
            for (int i = 0; i < qualitys.length; i++) {
                CwClarity cwc = new CwClarity();
                if (qualitys[i].equals("sd")) {
                    cwc.setName("标清");
                } else if (qualitys[i].equals("cif")) {
                    cwc.setName("流畅");
                } else if (qualitys[i].equals("hd")) {
                    cwc.setName("高清");
                }
                JSONObject sdd = video.getJSONObject(qualitys[i]);
                JSONArray uri = sdd.optJSONArray("1.0");
                String path = (String) uri.get(0);
                cwc.setPath(path);
                claritys.add(cwc);
            }

            JSONObject jsmp = audio.getJSONObject("sd");
            JSONArray mpuri = jsmp.optJSONArray("1.0");
            courseWare.setMobileVideoMP3((String) mpuri.get(0));

            JSONObject cipherkeyVo = body.getJSONObject("cipherkeyDeal");

            String app = cipherkeyVo.optString("app");
            String type = cipherkeyVo.optString("type");
            String vid = cipherkeyVo.optString("vid");
            String vkey = cipherkeyVo.optString("key");
            String vcode = cipherkeyVo.optString("code");
            String message = cipherkeyVo.optString("message");

            playParamsDB.add(userId, courseWare.getId(), app, type, vid, vkey, vcode, message);
            courseWare.setMobileLectureUrl(ParamsUtils.getLectureUrl(courseWare.getId()));
            courseWare.setClaritys(claritys);
//            courseWare.setMobileVideoUrl(sdone.get(0));
//            courseWare.setMobileVideoUrlLD(cf_one.get(0));
            String jm = new String(AESHelper.decrypt(Base64.decode(vkey, Base64.DEFAULT),
                    SignUtils.getKey(app, vid, Integer.parseInt(type)).getBytes(),
                    SignUtils.getIV(vid).getBytes()));
            courseWare.setJm(jm);
            if (flag) {
                getMvpView().startPlayVideo(courseWare, false, true);
            } else {
                chooseVideo(claritys, jm, courseWare);
            }
        } catch (Exception e) {
            getMvpView().showError("analysis failed");
        }
    }

    private String[] reSetScheme(String[] qualitys) {
        String[] reScheme;
        if (qualitys.length == 3) {
            reScheme = new String[3];
            reScheme[0] = "cif";
            reScheme[1] = "sd";
            reScheme[2] = "hd";
        } else if (qualitys.length == 2) {
            reScheme = new String[2];
            List<String> listA = Arrays.asList(qualitys);
//            List<String> listB = new ArrayList<String>(listA);
            if (!listA.contains("cif")) {
                reScheme[0] = "sd";
                reScheme[1] = "hd";
            } else if (!listA.contains("sd")) {
                reScheme[0] = "cif";
                reScheme[1] = "hd";
            } else {
                reScheme[0] = "cif";
                reScheme[1] = "sd";
            }
        } else {
            return qualitys;
        }

        return reScheme;
    }

    private void chooseVideo(List<CwClarity> claritys, String jm, CourseWare courseWare) {
        CwClarity cwClarity = null;
        if (SharedPrefHelper.getInstance().getPlayQuaity().equals("高清")) {
            for (int i = 0; i < claritys.size(); i++) {
                String name = claritys.get(i).getName();
                if (name.equals("高清")) {
                    courseWare.setQualityName("高清");
                    cwClarity = claritys.get(i);
                    break;
                }
            }
        } else if (SharedPrefHelper.getInstance().getPlayQuaity().equals("标清")) {
            for (int i = 0; i < claritys.size(); i++) {
                String name = claritys.get(i).getName();
                if (name.equals("标清")) {
                    courseWare.setQualityName("标清");
                    cwClarity = claritys.get(i);
                    break;
                }
            }
        } else {
            for (int i = 0; i < claritys.size(); i++) {
                String name = claritys.get(i).getName();
                if (name.equals("流畅")) {
                    courseWare.setQualityName("流畅");
                    cwClarity = claritys.get(i);
                    break;
                }
            }
        }

        if (cwClarity == null) {
            courseWare.setQualityName(claritys.get(0).getName());
            courseWare.setMobileVideoUrl(claritys.get(0).getPath());
            downloadM3U8(courseWare, claritys.get(0).getPath(), jm);
        } else {
            courseWare.setQualityName(cwClarity.getName());
            courseWare.setMobileVideoUrl(cwClarity.getPath());
            downloadM3U8(courseWare, cwClarity.getPath(), jm);
        }
    }

    public void downloadCw(CourseWare courseWare) {
        Disposable disposable = DownloadManager.getInstance().download(courseWare, SharedPrefHelper.getInstance().getPlayQuaity())
                .debounce(1000, TimeUnit.MILLISECONDS)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        getMvpView().showDialogLoading();
                    }
                })
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        getMvpView().hideDialogLoading();
                    }
                })
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        getMvpView().showError("正在下载");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        if (throwable instanceof SocketTimeoutException) {
                            getMvpView().showError("下载地址请求超时");
                        } else {
                            getMvpView().showError(throwable.getMessage());
                        }
                    }
                });
        addSubscription(disposable);

    }

    public void downloadM3U8(final CourseWare courseWare, final String path, final String key) {
        cw = courseWare;

        Disposable subscribe = ApiService.getM3U8(path)
                .compose(RxUtils.<ResponseBody>ioMain())
                .subscribe(new Consumer<ResponseBody>() {
                               @Override
                               public void accept(ResponseBody response) throws Exception {
                                   String data = response.string();

                                   String m3u8path = getPath(courseWare);
                                   File des = new File(m3u8path);
                                   if (!des.exists()) {
                                       des.mkdirs();
                                   }

                                   File videoFile = new File(m3u8path + "online.m3u8");
                                   FileWriter wf = new FileWriter(videoFile);
                                   wf.write(data);
                                   wf.close();
//                  Message msg = Message.obtain();
//                  msg.obj = path + "&" + key;
//                  handler.sendMessage(msg);

                                   int index = path.lastIndexOf("/") + 1;
                                   String rootUrl = path.substring(0, index);
                                   getKeyTxt(rootUrl, getPath(cw) + "online.m3u8", key);
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

//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            String data = (String) msg.obj;
//            String[] strs = data.split("&");
//            String path = strs[0];
//            String key = strs[1];
//            int index = path.lastIndexOf("/") + 1;
//            String rootUrl = path.substring(0, index);
//
//            getKeyTxt(rootUrl, getPath(cw) + "online.m3u8", key);
//        }
//    };

    /**
     * 获取并保存Key到文件中
     *
     * @param urlHead
     */
    public void getKeyTxt(final String urlHead, final String m3u8Path, final String keyText) {

        try {
            File file = new File(getPath(cw) + "keyText.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(keyText);
            fileWriter.flush();
            fileWriter.close();

            String keyUrl = null;
            FileOutputStream fos = null;
            File m3u8File = new File(m3u8Path);
            FileReader fileReader = new FileReader(m3u8File);
            BufferedReader reader11 = new BufferedReader(fileReader);
            String line1 = null;
            StringBuffer stringBuffer1 = new StringBuffer();
            String line_copy = null;
            boolean isNeedKey = false;//是否需要进行加解密
            while ((line1 = reader11.readLine()) != null) {
                line_copy = line1;
                if (line1.contains("EXT-X-KEY"))
                    isNeedKey = true;
                if (line1.contains("EXT-X-KEY") && !line1.contains(Constants.M3U8_KEY_SUB)) {
                    //如果当前视频需要进行解密，则修改m3u8文件中EXT-X-KEY对应的值指向本地key文件，来标示需要进行解密
                    String[] keyPart = line1.split(",");
                    String keyUrll = keyPart[1].split("URI=")[1].trim();
                    keyUrl = keyUrll.substring(1, keyUrll.length() - 1);
                    line_copy = keyPart[0] + Constants.M3U8_KEY_SUB + getKeyPath(cw) + "keyText.txt\"," + keyPart[2];
//                    line_copy = keyPart[0] + ",URI=\""+keyText+"\"," + keyPart[2];
                } else if (line1.contains(".ts") && !line1.contains("http:") && isNeedKey) {
                    line_copy = urlHead + line_copy;//补全ts的网络路径
                }
                stringBuffer1.append(line_copy);
                stringBuffer1.append("\r\n");
            }
            reader11.close();
            FileWriter writer = new FileWriter(m3u8File, false);
            BufferedWriter writer2 = new BufferedWriter(writer);
            writer2.write(stringBuffer1.toString());
            writer2.flush();
            writer2.close();

            Message msg = Message.obtain();
            msg.obj = cw;
            getMvpView().getHandler().sendMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getPath(CourseWare courseWare) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        String desPath = FileUtil.getKeyPath(AppContext.getInstance());

        return desPath + userId + "_" + courseWare.getsSubjectId() + "_" + courseWare.getClassId() + "_" + courseWare.getId() + "/";
    }

    private String getKeyPath(CourseWare courseWare) {
        String userId = SharedPrefHelper.getInstance().getUserId();

        return userId + "_" + courseWare.getsSubjectId() + "_" + courseWare.getClassId() + "_" + courseWare.getId() + "/";
    }

    public void postOperat() {
        final List<Opera> operas = operaDB.findOperas(userId);
        if (operas == null || operas.size() == 0) {
            return;
        }

        Call<String> call = ApiClient.getApiInterface().postOperates(ParamsUtils.getInstance().postDatas(operas));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String obj = response.body();
                    BaseBean baseBean = JSON.parseObject(obj, BaseBean.class);
                    if (baseBean != null) {
                        if (baseBean.getResult().getCode() == 1) {
                            operaDB.deleteAll();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                getMvpView().hideLoading();
                Toast.makeText(getMvpView().context(), "请检查网络是否连接", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String[] ips;//解析的ip集合
    String domainame;
    String playUrl;

    public void analysisIp(final CourseWare courseWare) {
        try {
            playUrl = courseWare.getMobileVideoUrl();
            domainame = getTopDomainWithoutSubdomain(playUrl);
        } catch (Exception e) {

        }
        if (TextUtils.isEmpty(domainame)) {
            return;
        }
        String s = encrypt(domainame);
        String ip = "http://119.29.29.29/d?id=245&dn=" + s;
        Disposable subscribe = ApiService.getM3U8(ip)
                .compose(RxUtils.<ResponseBody>ioMain())
                .subscribe(new Consumer<ResponseBody>() {
                               @Override
                               public void accept(ResponseBody response) throws Exception {
                                   String t = response.string();

                                   t = t.replaceAll("(\r\n|\r|\n|\n\r)", "");
                                   t = decrypt(t);
                                   if (!TextUtils.isEmpty(t)) {
                                       ips = t.split(";");
                                       if (!TextUtils.isEmpty(ips[0])) {
                                           operaDB.add(courseWare, "playError", ips[0], System.currentTimeMillis() + "");
                                       }
                                   }
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(@NonNull Throwable throwable) throws Exception {
//                    getMvpView().showError(throwable.getMessage());
                               }
                           }
                );
        addSubscription(subscribe);
    }

    public static String getTopDomainWithoutSubdomain(String url)
            throws MalformedURLException {

        String host = new URL(url).getHost().toLowerCase();// 此处获取值转换为小写
        Pattern pattern = Pattern
                .compile("[^//]*?\\.(com|cn|net|org|biz|info|cc|tv)");
        Matcher matcher = pattern.matcher(host);
        while (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    /**
     * 域名加密
     *
     * @param domain
     * @return
     */
    public String encrypt(String domain) {
        String crypt = "";
        try {
            //初始化密钥
            SecretKeySpec keySpec = new SecretKeySpec("i_PqWF_o".getBytes("utf-8"), "DES");
            //选择使用 DES 算法，ECB 方式，填充方式为 PKCS5Padding
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            //初始化
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            //获取加密后的字符串
            byte[] encryptedString = cipher.doFinal(domain.getBytes("utf-8"));
            crypt = bytesToHexString(encryptedString);
        } catch (Exception e) {
            return crypt;
        }
        return crypt;
    }

    /**
     * 2进制byte[]转成16进制字符串
     *
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 域名解密
     *
     * @param data
     * @return
     */
    public String decrypt(String data) {
        String decrypt = "";
        try {
            SecretKeySpec keySpec = new SecretKeySpec("i_PqWF_o".getBytes("utf-8"), "DES");
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] decrypts = cipher.doFinal(hexStringToByte(data));
            decrypt = new String(decrypts, "utf-8");
        } catch (Exception e) {
            return decrypt;
        }
        return decrypt;
    }

    /**
     * 16进制字符串转成byte[]
     *
     * @param hex
     * @return
     */
    public static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }

    private static byte toByte(char c) {
        byte b = (byte) "0123456789abcdef".indexOf(c);
        return b;
    }

    public void isCommented(CourseWare courseWare) {
        if (courseWare == null) {
            return;
        }
        Disposable subscribe = ApiService.isCommented(ParamsUtils.isCommented(courseWare.getId()))
                .compose(RxUtils.<ResponseBody>ioMain())
                .subscribe(new Consumer<ResponseBody>() {
                               @Override
                               public void accept(ResponseBody result) throws Exception {
                                   String data = result.string();
                                   JSONObject body = new JSONObject(data);
                                   String obj = body.getString("obj");
                                   if (obj.equals("true")) {
                                       getMvpView().openComment();
                                   } else {
                                       Toast.makeText(AppContext.getInstance(), body.getString("msg"), Toast.LENGTH_SHORT).show();
                                   }
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
