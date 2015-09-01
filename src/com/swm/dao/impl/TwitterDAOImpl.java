package com.swm.dao.impl;

import java.util.Date;

import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import com.swm.dao.TwitterDAO;

/**
 * @author vicky
 *
 */
public class TwitterDAOImpl implements TwitterDAO {
	
	/**
	 * 
	 */
	private static final int NUM_HOURS_TWEETS = 12;

	/**
	 * 
	 */
	private static final long DAY_TIME_MILLISECONDS = NUM_HOURS_TWEETS * 60 * 60 * 1000L;

	/**
	 * 
	 */
	public Twitter twitter = TwitterFactory.getSingleton();


	public ResponseList<Status> get1DayTweets() throws TwitterException {

		Date currentTime = new Date();

		Paging paging = new Paging();
		paging.setCount(200);

		ResponseList<Status> response = twitter.getHomeTimeline(paging);

		while (currentTime.getTime()
				- response.get(response.size() - 1).getCreatedAt().getTime() < DAY_TIME_MILLISECONDS) {

			paging.setMaxId((long) response.get(response.size() - 1).getId()
 - 1);
			response.addAll(twitter.getHomeTimeline(paging));

		}

		return response;
	}

}
