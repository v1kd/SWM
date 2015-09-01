package com.swm.bean;

import java.util.List;

public class Feature {

	private String name;

	private String category;

	private List<String> alias;

	public List<String> getAlias() {
		return alias;
	}

	public void setAlias(List<String> alias) {
		this.alias = alias;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Feature [name=" + name + ", category=" + category + ", alias="
				+ alias + "]";
	}

}
