package com.swm.dao;


import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.TwitterException;


/**
 * @author vicky
 *
 */
public interface TwitterDAO {


	/**
	 * @return status
	 * @throws TwitterException
	 */
	public ResponseList<Status> get1DayTweets() throws TwitterException;

}
