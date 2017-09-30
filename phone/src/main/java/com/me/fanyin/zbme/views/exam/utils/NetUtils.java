/**
 * 
 */
package com.me.fanyin.zbme.views.exam.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * 
 * @author Xice
 *
 */
public class NetUtils {
	private static final String TAG = NetUtils.class.getName();

	/**
	 * 检测网络是否可用
	 */
	public static NetType checkNet(Context context) {
		NetType netType = NetType.NONE;
		try {
			// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
			ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				// 获取网络连接管理的对象
				NetworkInfo info = connectivity.getActiveNetworkInfo();

				if (info != null && info.isConnected()) {
					// 判断当前网络是否已经连接

					if (info.getState() == NetworkInfo.State.CONNECTED) {

						// 判断当前的接入点
						if (ConnectivityManager.TYPE_WIFI == info.getType()) {// wifi连接
							netType = NetType.WIFI;

						} else if (ConnectivityManager.TYPE_MOBILE == info.getType()) {// gprs方式连接

							String proxyHost = android.net.Proxy.getDefaultHost();
							if (proxyHost != null && !"".equals(proxyHost)) {// wap方式
								netType = NetType.GPRS_WAP;
							} else {// web方式
								netType = NetType.GPRS_WEB;
							}

						}

					}
				}
			}
		} catch (Throwable e) {
			Log.e(TAG, e.getMessage(), e);
		}

		return netType;
	}


}
