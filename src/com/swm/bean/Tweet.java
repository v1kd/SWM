package com.swm.bean;

import java.util.HashSet;
import java.util.Set;

import twitter4j.Status;

/**
 * @author vicky
 *
 */
public class Tweet {

	/**
	 * @param status
	 */
	public Tweet(Status status) {
		//
		this.status = status;
	}

	/**
	 * 
	 */
	private Status status;

	/**
	 * 
	 */
	private double score;

	/**
	 * 
	 */
	private Set<String> tags = new HashSet<String>();

	/**
	 * @return status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * @return score
	 */
	public double getScore() {
		return score;
	}

	/**
	 * @param score
	 */
	public void setScore(double score) {
		this.score = score;
	}

	public Set<String> getTags() {
		return tags;
	}

	public void setTags(Set<String> tags) {
		this.tags = tags;
	}

	@Override
	public String toString() {
		return "Tweet [score=" + score + ", status=" + status.getText()
				+ ", likes=" + status.getFavoriteCount() + ", spread="
				+ status.getRetweetCount()
				+ ", tags="
				+ tags + "]";
	}

}
