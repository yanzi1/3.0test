package com.me.updatelib.util;


import com.me.updatelib.bean.UpdateInfo;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * Created by jijiangrui on 2017/4/6.
 */
public class JSONHandler {

    public static UpdateInfo toUpdateInfo(InputStream is) throws Exception {
        if (is == null){
            return null;
        }
        String byteData = new String(readStream(is));

        JSONObject data = new JSONObject(byteData);
        if (Boolean.parseBoolean(data.optString("success"))) {
            String bodyStr = data.optString("obj");

            JSONObject body = new JSONObject(bodyStr);
            UpdateInfo updateInfo = new UpdateInfo();
            updateInfo.setId(body.optString("id"));
            updateInfo.setLoadAddress(body.optString("loadAddress"));
            updateInfo.setReleaseDate(body.optString("releaseDate"));
            updateInfo.setAppId(body.optString("appId"));
            updateInfo.setDevicesType(body.optString("devicesType"));
            updateInfo.setDescription(body.optString("description"));
            UpdateInfo.AppprojectBean appprojectBean = new UpdateInfo.AppprojectBean();
            appprojectBean.setAppName(body.optJSONObject("appproject").optString("appName"));
            updateInfo.setAppproject(appprojectBean);
            updateInfo.setVersionCode(body.optString("versionCode"));
            updateInfo.setExplainAddress(body.optString("explainAddress"));
            updateInfo.setCreateDate(body.optString("createDate"));
            updateInfo.setVersion(body.optString("version"));
            updateInfo.setIsUpdate(body.optString("isUpdate"));

            return updateInfo;
        } else {
            return null;
        }
    }

    private static byte[] readStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        byte [] array=new byte[1024];
        int len = 0;
        while( (len = inputStream.read(array)) != -1){
            outputStream.write(array,0,len);
        }
        inputStream.close();
        outputStream.close();
        return outputStream.toByteArray();
    }

}
