package com.me.fanyin.zbme.views.course.play.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 解密工具类
 * @author xunice
 *
 */
public class SignUtils {
	
	/**
	 * 获取时间戳
	 * @return
	 */
	private static String getTimeStamp(){
//		return System.currentTimeMillis()/1000 + "";
		return "123";
	}


    private static byte [] getHash(String password) {
        MessageDigest digest = null ;
        try {
            digest = MessageDigest. getInstance( "SHA-256");
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        digest.reset();
        return digest.digest(password.getBytes());
    }
    /**
	 * 获取KEY
	 * @return
     * int type,
	 */
    public static String getKey(String userId, String vid, int type) {
        //TODO
        byte [] data = getHash(userId + vid);
        String encryptStr = String.format("%0" + (data.length * 2) + "X", new BigInteger(1, data));
        String encrypt = "";
        if(encryptStr.length()>44){
            if(type == 1) {
                encrypt = encrypt + encryptStr.substring(8, 16).toLowerCase();
                encrypt = encrypt + encryptStr.substring(20, 28).toLowerCase();
            }else{
                encrypt = encrypt + encryptStr.substring(16, 32).toLowerCase();
                encrypt = encrypt + encryptStr.substring(40, 56).toLowerCase();
            }
        }
//        if(encryptStr.length()>44 && type == 1){
//        	encrypt = encrypt + encryptStr.substring(9,17).toLowerCase();
//        	encrypt = encrypt + encryptStr.substring(21,29).toLowerCase();
//        }else if(encryptStr.length()>44 && type == 2){
//            encrypt = encrypt + encryptStr.substring(17,33).toLowerCase();
//            encrypt = encrypt + encryptStr.substring(41,57).toLowerCase();
//        }
        return encrypt;
    }
    
    /**
     * 获取IV
     * @return
     */
    public static String getIV(String videoId){
    	String course = "/interface/drm.php?vid=" + videoId;
    	byte [] data = getHash(course);
        String encryptStr = String.format("%0" + (data.length * 2) + "X", new BigInteger(1, data));
        String encrypt = "";
        if(encryptStr.length()>44){
        	encrypt = encrypt + encryptStr.substring(4,12).toLowerCase();
        	encrypt = encrypt + encryptStr.substring(36,44).toLowerCase();
        }
        return encrypt;
    }
    
}
