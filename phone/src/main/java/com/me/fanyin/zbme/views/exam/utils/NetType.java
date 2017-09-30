package com.me.fanyin.zbme.views.exam.utils;


/**
 * 网络链接类型
 * @author Xice
 *
 */
public enum NetType {
	
	WIFI("WIFI", true), // WIFI链接
	GPRS_WEB("GPRS WEB", true), // GPRS_WEB链接
	GPRS_WAP("GPRS WAP", true), // GPRS_WAP链接
	NONE("无连接", false), // 无连接
	;

	public String desc;// 网络连接描述
	public boolean available; // 是否可用

	//
	private NetType(String desc, boolean available) {
		this.desc = desc;
		this.available = available;
	}

	public String getDesc() {
		return desc;
	}

	public boolean isAvailable() {
		return available;
	}
}
