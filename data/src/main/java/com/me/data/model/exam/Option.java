package com.me.data.model.exam;

import java.io.Serializable;


/**
 * 选项实体
 */
public class Option implements Serializable {
	// 题目选项
	// 1 选项名称：name
	// 2 选项描述：description
	
	private String name;// 选项名称
	private String optionContent;// 选项描述

	private String showWebView;//判断是否显示webview还是textview

	public Option() {
		super();
	}

	public Option(String name, String description) {
		super();
		this.name = name;
		this.optionContent = description;
	}
	/**
	 * 新添加的
	 */
	public String getShowWebView() {
		return showWebView;
	}

	public void setShowWebView(String showWebView) {
		this.showWebView = showWebView;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOptionContent() {
		return optionContent;
	}

	public void setOptionContent(String optionContent) {
		this.optionContent = optionContent;
	}

	@Override
	public String toString() {
		return "QuestionOptionBean [name=" + name + ", description="
				+ optionContent + "]";
	}

}
