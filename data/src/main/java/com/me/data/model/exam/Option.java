package com.me.data.model.exam;

import java.io.Serializable;


/**
 * 选项实体
 */
public class Option implements Serializable {
	// 题目选项
	// 1 选项名称：name
	// 2 选项描述：description
	
	private String optionFlag;// 选项名称
	private String optionContent;// 选项描述

	private String showWebView;//判断是否显示webview还是textview
	private int choseFlag;//当前是否被选中 1.选中

	public int getChoseFlag() {
		return choseFlag;
	}

	public void setChoseFlag(int choseFlag) {
		this.choseFlag = choseFlag;
	}

	public Option() {
		super();
	}

	public Option(String optionFlag, String description) {
		super();
		this.optionFlag = optionFlag;
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

	public String getOptionFlag() {
		return optionFlag;
	}

	public void setOptionFlag(String name) {
		this.optionFlag = name;
	}

	public String getOptionContent() {
		return optionContent;
	}

	public void setOptionContent(String optionContent) {
		this.optionContent = optionContent;
	}

	@Override
	public String toString() {
		return "QuestionOptionBean [name=" + optionFlag + ", description="
				+ optionContent + "]";
	}

}
