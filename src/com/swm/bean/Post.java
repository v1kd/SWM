package com.swm.bean;

import java.util.Date;
import java.util.List;

/**
 * @author Vikranth
 *
 */
public class Post {

	private long id;

	private String source;

	private Object data;

	private Date createdAt;

	private List<String> tags;

	private long likes;

	private long spread;

	private double ps;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public long getLikes() {
		return likes;
	}

	public void setLikes(long likes) {
		this.likes = likes;
	}

	public long getSpread() {
		return spread;
	}

	public void setSpread(long spread) {
		this.spread = spread;
	}

	public double getPs() {
		return ps;
	}

	public void setPs(double ps) {
		this.ps = ps;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", source=" + source + ", createdAt="
				+ createdAt + ", tags=" + tags + ", likes=" + likes
				+ ", spread=" + spread + ", ps=" + ps + "]";
	}

}
