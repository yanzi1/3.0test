package com.me.fanyin.zbme.views.course.play.audition;

import android.os.Message;
import android.util.Base64;

import com.me.data.common.Constants;
import com.me.data.local.SharedPrefHelper;
import com.me.data.model.play.CourseWare;
import com.me.data.mvp.BasePersenter;
import com.me.data.remote.ApiService;
import com.me.data.remote.rxjava.RxUtils;
import com.me.data.remote.utils.ParamsUtils;
import com.me.fanyin.zbme.views.course.play.db.PlayParamsDB;
import com.me.fanyin.zbme.views.course.play.utils.AESHelper;
import com.me.fanyin.zbme.views.course.play.utils.FileUtil;
import com.me.fanyin.zbme.views.course.play.utils.SignUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

/**
 * Created by wenpeng on 2016/5/30 0030.
 */
public class AuditionPlayPersenter extends BasePersenter<AuditionPlayView> {

    private String userId;
    private PlayParamsDB playParamsDB;
    CourseWare courseWare;

    @Inject
    AuditionPlayPersenter() {
    }

    @Override
    public void attachView(AuditionPlayView mvpView) {
        super.attachView(mvpView);
        playParamsDB=new PlayParamsDB(getMvpView().context());
        userId= SharedPrefHelper.getInstance().getUserId();
    }

    @Override
    public void getData() {
        getMvpView().showLoading();
        courseWare=getMvpView().getCw();

        Disposable subscribe = ApiService.getPlayUrl(ParamsUtils.getInstance().getPlayPath(courseWare.getId()))
                .compose(RxUtils.<String>retrofit()) //几种方式实现逻辑前置
                .subscribe(new Consumer<String>() {
                               @Override
                               public void accept(String data) throws Exception {
                                   preData(data);
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

    private void preData(String data) {
        try {
            JSONObject body=new JSONObject(data);
//            String unique=body.getString("cwID");
//            if(!unique.equals(courseWare.getId())){
//                return;
//            }
            JSONObject video = body.getJSONObject("video");
            JSONObject sd = video.getJSONObject("sd");

            JSONArray one = sd.optJSONArray("1.0");

            /**
             *设置sd（标清）不同倍速url
             */
            List<String> sdone = new ArrayList();
            for (int i = 0; i < one.length(); i++) {
                String rrr = (String) one.get(i);
                sdone.add(rrr);
            }

            JSONObject cipherkeyVo = body.getJSONObject("cipherkeyDeal");

            String app = cipherkeyVo.optString("app");
            String type = cipherkeyVo.optString("type");
            String vid = cipherkeyVo.optString("vid");
            String vkey = cipherkeyVo.optString("key");
            String vcode = cipherkeyVo.optString("code");
            String message = cipherkeyVo.optString("message");

            playParamsDB = new PlayParamsDB(getMvpView().context());
            playParamsDB.add(userId, courseWare.getId(), app, type, vid, vkey, vcode, message);
            courseWare.setMobileVideoUrl(sdone.get(0));
            String jm = new String(AESHelper.decrypt(Base64.decode(vkey, Base64.DEFAULT),
                    SignUtils.getKey(app, vid, Integer.parseInt(type)).getBytes(),
                    SignUtils.getIV(vid).getBytes()));
            courseWare.setJm(jm);
            downloadM3U8(sdone.get(0),jm);
        }catch (Exception e){
            getMvpView().showError("analysis failed");
        }
    }

    public void downloadM3U8(final String path, final String key) {

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

                  int index = path.lastIndexOf("/") + 1;
                  String rootUrl = path.substring(0, index);
                  getKeyTxt(rootUrl, getPath(courseWare) + "online.m3u8", key);
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
     * 获取并保存Key到文件中
     *
     * @param urlHead
     */
    public void getKeyTxt(final String urlHead, final String m3u8Path, final String keyText) {

        try {
            File file = new File(getPath(courseWare) + "keyText.txt");
            if (!file.exists()) {
                file.createNewFile();
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(keyText);
                fileWriter.flush();
                fileWriter.close();
            }
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
                    line_copy = keyPart[0] + Constants.M3U8_KEY_SUB +getKeyPath(courseWare) +"keyText.txt\"," + keyPart[2];
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
            Message msg=Message.obtain();
            msg.obj=courseWare;
            msg.what=66;
            getMvpView().getHandler().sendMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getPath(CourseWare courseWare) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        String desPath = FileUtil.getKeyPath(getMvpView().context());

        return desPath + userId + "_" +"sanning_"+ courseWare.getId() + "/";

    }

    private String getKeyPath(CourseWare courseWare) {
        String userId = SharedPrefHelper.getInstance().getUserId();

        return userId + "_" +"sanning_"+ courseWare.getId() + "/";

    }
}
