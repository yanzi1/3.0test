package com.me.fanyin.zbme.views.main.view.bean;

public class MainNews {
	private String id;
	private String tittle;

	public MainNews(String id, String tittle) {
		this.id = id;
		this.tittle = tittle;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTittle() {
		return tittle;
	}

	public void setTittle(String tittle) {
		this.tittle = tittle;
	}

}
