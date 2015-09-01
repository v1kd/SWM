package com.swm.dao;

import java.util.List;

import com.swm.bean.Cluster;
import com.swm.bean.Post;
import com.swm.bean.Tweet;

/**
 * @author Vikranth
 *
 */
public interface SWMDAO {
	
	/**
	 * @param tweets
	 * @throws Exception
	 */
	public void storeTweets(List<Tweet> tweets) throws Exception;

	/**
	 * @return posts
	 * @throws Exception
	 */
	public List<Post> getPosts() throws Exception;

	/**
	 * @param clusters
	 * @throws Exception
	 */
	public void storeClusters(List<Cluster> clusters) throws Exception;

}
