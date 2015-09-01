package com.swm.bean;

import java.util.Map;

/**
 * @author Vikranth
 *
 */
public class Cluster implements Comparable<Cluster> {

	private String key;

	private double totalPs;

	private double maxPs;

	private String category;

	private Map<String, Object> data;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public double getTotalPs() {
		return totalPs;
	}

	public void setTotalPs(double totalPs) {
		this.totalPs = totalPs;
	}

	public double getMaxPs() {
		return maxPs;
	}

	public void setMaxPs(double maxPs) {
		this.maxPs = maxPs;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public int compareTo(Cluster o) {

		return (this.totalPs < o.getTotalPs() ? -1 : (this.totalPs == o
				.getTotalPs() ? 0 : 1));
	}

	@Override
	public String toString() {
		return "Cluster [key=" + key + ", totalPs=" + totalPs + ", maxPs="
				+ maxPs + ", category=" + category + ", data=" + data + "]";
	}
}
