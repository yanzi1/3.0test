package com.me.data.model.exam;

import java.io.Serializable;


/**
 * 相关知识点实体
 * @Time 2016/05/26
 */
public class RelevantPoint implements Serializable {

	private String kPointTitle;// 知识点名称
	private String kPointUrl;// 知识点的url

	public RelevantPoint() {
		super();
	}

	public RelevantPoint(String kPointTitle, String optionContent) {
		super();
		this.kPointTitle = kPointTitle;
		this.kPointUrl = optionContent;
	}

	public String getkPointTitle() {
		return kPointTitle;
	}

	public void setkPointTitle(String kPointTitle) {
		this.kPointTitle = kPointTitle;
	}

	public String getkPointUrl() {
		return kPointUrl;
	}

	public void setkPointUrl(String kPointUrl) {
		this.kPointUrl = kPointUrl;
	}
}
