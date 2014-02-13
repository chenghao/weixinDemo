package com.hao.weixin.menu.pojo;

/**
 * 普通按钮（子按钮）
 * 
 * 这里对子菜单是这样定义的：没有子菜单的菜单项，有可能是二级菜单项，也有可能是不含二级菜单的一级菜单。
 * 这类子菜单项一定会包含四个属性：type、name、url和key
 * 只有type为view时才有url属性。
 * 只有type为click时才有key属性。
 */
public class CommonButton extends Button {
	
	private String type;
	private String key;
	private String url;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
