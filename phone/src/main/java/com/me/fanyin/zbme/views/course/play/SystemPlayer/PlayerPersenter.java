package com.me.fanyin.zbme.views.course.play.SystemPlayer;

import com.me.data.common.Constants;
import com.me.data.local.SharedPrefHelper;
import com.me.data.model.play.CourseWare;
import com.me.data.mvp.BasePersenter;
import com.me.data.remote.ApiService;
import com.me.data.remote.rxjava.RxUtils;
import com.me.data.remote.utils.ParamsUtils;
import com.me.fanyin.zbme.views.course.play.utils.FileUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * Created by wenpeng on 6/04/2017.
 */

public class PlayerPersenter extends BasePersenter<PlayerView> {

    @Inject
    PlayerPersenter() {
    }

    @Override
    public void getData() {
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
     * 获取并保存Key到文件中
     *
     * @param urlHead
     */
    public void getKeyTxt(final String urlHead, final String m3u8Path,final String keyText,CourseWare cw) {

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
                    line_copy = keyPart[0] + Constants.M3U8_KEY_SUB +getKeyPath(cw) +"keyText.txt\"," + keyPart[2];
//                    line_copy = keyPart[0] + ",URI=\""+keyText+"\"," + keyPart[2];
                } else if (line1.contains(".ts") && !line1.contains("http:") && isNeedKey) {
//                    line_copy = urlHead + line_copy;//补全ts的网络路径
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
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String getPath(CourseWare courseWare) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        String desPath = FileUtil.getKeyPath(getMvpView().context());

        return desPath + userId + "_" + courseWare.getsSubjectId() + "_" + courseWare.getClassId() + "_" + courseWare.getId() + "/";

    }

    private String getKeyPath(CourseWare courseWare) {
        String userId = SharedPrefHelper.getInstance().getUserId();

        return userId + "_" + courseWare.getsSubjectId() + "_" + courseWare.getClassId() + "_" + courseWare.getId() + "/";

    }

}
