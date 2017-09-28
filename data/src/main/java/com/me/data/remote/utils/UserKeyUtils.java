package com.me.data.remote.utils;

import com.me.core.utils.CryptoUtils;


/**
 * 购物车Userkey
 * @author xunice
 *
 */
public class UserKeyUtils {

	public final static String SALT = "9538b01d8d3e4c1ab3ba450adb3bea6a";

	public static String userkey(String userId){

		return CryptoUtils.encrypt(userId+SALT);
	}
}
