package com.me.fanyin.zbme.views.course.play.utils;

import android.annotation.SuppressLint;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * 解密工具类
 * @author xunice
 *
 */
public class AESHelper {
	private static AESHelper aesHelper;
	private IvParameterSpec ivParameterSpec;
	private SecretKeySpec secretKeySpec;
	private Cipher cipher;
    static final String KEY_ALGORITHM = "AES";
    static final String CIPHER_ALGORITHM_ECB = "AES/ECB/PKCS5Padding";
    /* 
     */
    static final String CIPHER_ALGORITHM_CBC = "AES/CBC/PKCS5Padding";
    /*  
     * AES/CBC/NoPadding 要求 
     * 密钥必须是16位的；Initialization vector (IV) 必须是16位 
     * 待加密内容的长度必须是16的倍数，如果不是16的倍数，就会出如下异常： 
     * javax.crypto.IllegalBlockSizeException: Input length not multiple of 16 bytes 
     *  
     *  由于固定了位数，所以对于被加密数据有中文的, 加、解密不完整 
     *   
     *  可 以看到，在原始数据长度为16的整数n倍时，假如原始数据长度等于16*n，则使用NoPadding时加密后数据长度等于16*n， 
     *  其它情况下加密数据长 度等于16*(n+1)。在不足16的整数倍的情况下，假如原始数据长度等于16*n+m[其中m小于16]， 
     *  除了NoPadding填充之外的任何方 式，加密数据长度都等于16*(n+1). 
     */  
    static final String CIPHER_ALGORITHM_CBC_NoPadding = "AES/CBC/NoPadding";
      
    static SecretKey secretKey;
       
    public static final String KEY = "c0801aee855e0073",IV = "e17fcbc5f9302e20";
    
    public static synchronized AESHelper getInstance(){
    	if(aesHelper == null){
    		aesHelper = new AESHelper();
    	}
    	return aesHelper;
    }
    
    private AESHelper(){
    	try {
    		ivParameterSpec = new IvParameterSpec(asBin(IV));
    		secretKeySpec = new SecretKeySpec(asBin(KEY), "AES");
			cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
		}  catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
    }
    
    public String getDecyptStr(String encrypted){
    	try {
    		//ENCRYPT_MODE
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
			byte[] decryptedBytes = cipher.doFinal(encrypted.getBytes());
			return new String(decryptedBytes);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
    	return "";
    }
    
    public static byte[] encrypt(byte[] data, byte[] key, byte[] ivs) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            byte[] finalIvs = new byte[16];
            int len = ivs.length > 16 ? 16 : ivs.length;
            System.arraycopy(ivs, 0, finalIvs, 0, len);
            IvParameterSpec ivps = new IvParameterSpec(finalIvs);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivps);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @SuppressLint("NewApi")
    public static byte[] decrypt(byte[] data, byte[] key, byte[] ivs) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            IvParameterSpec ivps = new IvParameterSpec(ivs);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivps);
            return cipher.doFinal(data);

            /**
             * Cipher c = Cipher.getInstance("AES");
    			SecretKeySpec key = new SecretKeySpec(salt.getBytes(), "AES");
    			c.init(Cipher.DECRYPT_MODE, key);
    			byte[] decordedValue = Base64.decode(message.getBytes(), Base64.NO_OPTIONS);
    			byte[] decValue = c.doFinal(decordedValue);
    			String decryptedValue = new String(decValue);
    			String decoded=new String(com.dharani.android.legalplex.BusinessLayer.Base64.decode(decryptedValue, com.dharani.android.legalplex.BusinessLayer.Base64.NO_OPTIONS));
    			return decoded;
             */
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
    /**
     * 将16进制字符串转换成字节数组 
     * @param src 
     * @return 
     */  
    private static byte[] asBin(String src) {
        if (src.length() < 1)  
            return null;  
        byte[] encrypted = new byte[src.length() / 2];  
        for (int i = 0; i < src.length() / 2; i++) {  
            int high = Integer.parseInt(src.substring(i * 2, i * 2 + 1), 16);//取高位字节
            int low = Integer.parseInt(src.substring(i * 2 + 1, i * 2 + 2), 16);//取低位字节
            encrypted[i] = (byte) (high * 16 + low);  
        }  
        return encrypted;  
    } 
    
    
}
