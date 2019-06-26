package com.czj.imoocnews;

public class NewsBean {
	public String name;
	public String Title;
	public String picSmall;
	
	public NewsBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "NewsBean [name=" + name + ", Title=" + Title + ", picSmall="
				+ picSmall + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getPicSmall() {
		return picSmall;
	}

	public void setPicSmall(String picSmall) {
		this.picSmall = picSmall;
	}

}
