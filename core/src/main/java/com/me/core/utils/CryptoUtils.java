package com.me.core.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * 编码/加密工具类
 * @author xunice
 *
 */
public class CryptoUtils extends BaseUtils{

	public CryptoUtils(){
		super();
	}

	/**
	 * md5编码 
	 * @param input 
	 * @return result
	 */
	public static String md5HexDigest(String input){
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

		char[] charArray = input.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++) {
			byteArray[i] = (byte) charArray[i];
		}
		byte[] md5Bytes = md5.digest(byteArray);

		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	/**
	 * Used to build output as Hex
	 */
	private static final char[] DIGITS_LOWER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
			'e', 'f'                        };
	/**
	 * MD5加密
	 * @param str  要加密的字符
	 * @return 加密后的字符
	 */
	public static String MD5(String str) {
		if (str == null) {
			return null;
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(str.getBytes());
			return new String(encodeHex(messageDigest.digest()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected static char[] encodeHex(final byte[] data) {
		final int l = data.length;
		final char[] out = new char[l << 1];
		// two characters form the hex value.
		for (int i = 0, j = 0; i < l; i++) {
			out[j++] = DIGITS_LOWER[(0xF0 & data[i]) >>> 4];
			out[j++] = DIGITS_LOWER[0x0F & data[i]];
		}
		return out;
	}


	/**
	 * md5加密
	 * @param key
	 * @return
	 */
	public static String encrypt(final String key) {
		// md.digest() 该函数返回值为存放哈希值结果的byte数组
		MessageDigest md5;
		StringBuffer hexValue = new StringBuffer();
		try {
			md5 = MessageDigest.getInstance("MD5");
			byte[] md5Bytes = md5.digest(key.getBytes());
			for (int i = 0; i < md5Bytes.length; i++) {
				int val = ((int) md5Bytes[i]) & 0xff;
				if (val < 16) {
					hexValue.append("0");
				}
				hexValue.append(Integer.toHexString(val));
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return hexValue.toString();
	}
}
