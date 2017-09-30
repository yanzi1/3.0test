package com.me.fanyin.zbme.views.exam.remote;

import com.me.core.utils.CryptoUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
 * 签名工具类
 *
 * @author xunice
 */
public class SignUtils {

    public final static String SALT = "9538b01d8d3e4c1ab3ba450adb3bea6a";

    public static String sign(String str) {
        String url = str;
        String[] urls = url.substring(url.indexOf("?") + 1).split("&");
        Arrays.sort(urls);
        url = "";
        for (int i = 0; i < urls.length; i++) {
            url = url + urls[i] + "&";
        }
        String sign = CryptoUtils.encrypt(url.substring(0, url.length() - 1) + SALT);
        url = str.substring(0, str.indexOf("?")) + "?" + url + "sign=" + sign;
        return url;
    }


    public static String sign(HashMap<String, String> params) {
        String param = "";
        for (Map.Entry<String, String> map : params.entrySet()) {
            param = param + map.getKey() + "=" + map.getValue() + "&";
        }
        param = param.substring(0, param.length() - 1);
        String[] urls = param.split("&");
        Arrays.sort(urls);

        String url = "";
        for (int i = 0; i < urls.length; i++) {
            url = url + urls[i] + "&";
        }
        String signStr = url.substring(0, url.length() - 1) + SALT;
        String sign = CryptoUtils.encrypt(signStr);
        return sign;
    }



    public static String encode(String text) {

        try {
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(text.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : result) {
                int number = b & 0xff;
                String hex = Integer.toHexString(number);
                if (hex.length() == 1) {
                    sb.append("0" + hex);
                } else {
                    sb.append(hex);
                }
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "";
    }


}
