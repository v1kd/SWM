package com.swm.processor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.TwitterException;

import com.swm.bean.Cluster;
import com.swm.bean.Feature;
import com.swm.bean.Post;
import com.swm.bean.Tweet;
import com.swm.dao.SWMDAO;
import com.swm.dao.TwitterDAO;
import com.swm.dao.impl.SWMDAOImpl;
import com.swm.dao.impl.TwitterDAOImpl;
import com.swm.util.TweetUtil;

/**
 * @author vicky
 *
 */
/**
 * @author Vikranth
 *
 */
public class SWMProcessor {

	private TwitterDAO twitter = new TwitterDAOImpl();

	private SWMDAO mongo = new SWMDAOImpl();

	private Map<String, Feature> featureSet = ((SWMDAOImpl) mongo)
			.getFeatureMap();

	/**
	 * 
	 */
	public void processData() {

		List<Tweet> tweets;
		ResponseList<Status> response;

		System.out.println(this.featureSet);

		try {
			response = twitter.get1DayTweets();

			tweets = TweetUtil.convertStatusResponse(response, this.featureSet);

			for (Tweet tweet : tweets) {
				System.out.println(tweet);
			}

			mongo.storeTweets(tweets);
		} catch (TwitterException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @return clusters
	 */
	public List<Cluster> getClusters() {
		
		List<Post> posts;
		List<Cluster> clusters = null;

		Map<String, Cluster> clusterMap;

		try {

			posts = mongo.getPosts();
			clusterMap = TweetUtil.convertPostToCluster(posts, featureSet);

			clusters = new ArrayList<Cluster>(clusterMap.values());

			mongo.storeClusters(clusters);

			Collections.sort(clusters);
			Collections.reverse(clusters);

		} catch (Exception e) {
			
		}
		
		return clusters;

	}

	public Map<String, Feature> getFeatureSet() {
		return featureSet;
	}

	public void setFeatureSet(Map<String, Feature> featureSet) {
		this.featureSet = featureSet;
	}


}
