package com.me.data.remote.utils;

import com.me.data.remote.ApiInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ImageUrlUtils {
    private ImageUrlUtils() {
    }

    /**
     * 检查图片相对地址
     */
    public static String checkUrl(String url) {
        if(url == null){
            return "";
        }
        if (url.toLowerCase().startsWith("http://") || url.toLowerCase().startsWith("https://")) {
            return url;
        } else {
            return ApiInterface.BASE_IMAGE_URL + url;
        }
    }

    /**
     * 得到网页中图片的地址
     */
    public static List<String> getImgStr(String htmlStr){
        String img="";
        Pattern p_image;
        Matcher m_image;
        List<String> pics = new ArrayList<String>();

        String regEx_img = "<img.*src=(.*?)[^>]*?>"; //图片链接地址
        p_image = Pattern.compile
                (regEx_img,Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(htmlStr);
        while(m_image.find()){
            img = img + "," + m_image.group();
            Matcher m  = Pattern.compile("src=\"?(.*?)(\"|>|\\s+)").matcher(img); //匹配src
            while(m.find()){
                pics.add(m.group(1));
            }
        }
        return pics;
    }
}
