package com.swm.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import twitter4j.HashtagEntity;
import twitter4j.Status;
import twitter4j.UserMentionEntity;

import com.swm.bean.Cluster;
import com.swm.bean.Feature;
import com.swm.bean.Post;
import com.swm.bean.Tweet;
import com.swm.constants.Constants;

/**
 * @author Vikranth
 *
 */
public final class TweetUtil {

	/**
	 * 
	 */
	private static final float RETWEET_COUNT_WEIGHT = .7F;

	/**
	 * 
	 */
	private static final float FAV_COUNT_WEIGHT = .3F;

	/**
	 * 
	 */
	private static final long HOUR_IN_MILLI = 60 * 60 * 1000;

	/**
	 * 
	 */
	private static final float TIME_DECAY_WEIGHT = .05F;


	private TweetUtil() {

	}
	
	/**
	 * @param list
	 * @return finalList
	 */
	public static List<Tweet> convertStatusResponse(List<Status> list,
			Map<String, Feature> featureSetMap) {

		List<Tweet> finalList = new ArrayList<Tweet>();
		Date currentTime = new Date();
		Tweet tweet;
		
		Set<String> tempSet = featureSetMap.keySet();

		String[] featureKeys = new String[tempSet.size()];

		int i = 0;
		for (Iterator<String> iterator = tempSet.iterator(); iterator
				.hasNext();) {
			featureKeys[i++] = iterator.next();
		}

		List<String> hashTags;
		List<String> refineHashTags;
		Feature feature;
		
		String formatString;

		System.out.println(Arrays.toString(featureKeys));

		if (null != list && list.size() > 0) {
			for (Status status : list) {
				tweet = new Tweet(status);
				finalList.add(tweet);
				tweet.setScore(calculateTweetScore(status, currentTime));

				formatString = tweet.getStatus().getText().toLowerCase();

				// get feature set
				hashTags = getHashTagsUserMents(tweet);

				for (String tag : hashTags) {
					feature = featureSetMap.get(tag.toLowerCase().trim());
					if (feature != null) {
						tweet.getTags().add(feature.getName());
					} else {// refine edges
						refineHashTags = refineHashes(tag);

						for (String refineTag : refineHashTags) {
							feature = featureSetMap.get(refineTag.toLowerCase()
									.trim());
							if (feature != null) {
								tweet.getTags().add(feature.getName());
							}
						}
					}
				}

				// try with strings
				for (int j = 0; j < featureKeys.length; j++) {
					if (containsWord(formatString, featureKeys[j].toLowerCase())) {
						feature = featureSetMap.get(featureKeys[j]);
						if (null != feature) {
							tweet.getTags().add(feature.getName());
						}
					}
				}
			}
		}
		
		return finalList;
		
	}

	/**
	 * @param posts
	 * @return clusterMap
	 */
	public static Map<String, Cluster> convertPostToCluster(List<Post> posts, Map<String, Feature> featureSet) {

		Map<String, Cluster> clusterMap = new HashMap<String, Cluster>();
		Cluster cluster;
		Feature feature;
		Map<String, Object> map;

		for (Post post : posts) {
			for (String tag : post.getTags()) {
				feature = featureSet.get(tag);


				if (null == feature) {
					continue;
				}

				cluster = clusterMap.get(tag);

				if (null == cluster) {
					cluster = new Cluster();
					cluster.setMaxPs(post.getPs());
					cluster.setTotalPs(post.getPs());
					cluster.setKey(tag);
					cluster.setCategory(feature.getCategory());

					map = new HashMap<String, Object>();

					cluster.setData(map);
					map.put("id", post.getId());
					map.put("timestamp", post.getCreatedAt());
					map.put("likes", post.getLikes());
					map.put("spread", post.getSpread());
					map.put("data", post.getData());

					clusterMap.put(tag, cluster);
				} else {
					cluster.setTotalPs(cluster.getTotalPs() + post.getPs());
					if (cluster.getMaxPs() < post.getPs()) {
						cluster.setMaxPs(post.getPs());

						map = cluster.getData();
						map.put("id", post.getId());
						map.put("timestamp", post.getCreatedAt());
						map.put("likes", post.getLikes());
						map.put("spread", post.getSpread());
						map.put("data", post.getData());
					}
				}
			}
		}

		return clusterMap;
	}

	/**
	 * @param status
	 * @return score
	 * 
	 *         W(tweet) = (p * Rcount + q * Fcount ) * T
	 */
	private static double calculateTweetScore(final Status status,
 Date current) {
		
		double score = 0;
		
		score = ((status.getRetweetCount() * RETWEET_COUNT_WEIGHT) + 
 (status
				.getFavoriteCount() * FAV_COUNT_WEIGHT))
				* timeDecay(current, status.getCreatedAt());
		return score;
	}

	/**
	 * @param current
	 * @param past
	 * @return timeDecay
	 */
	private static double timeDecay(Date current, Date past) {
		
		double t = -(1 + getHours(current, past) * TIME_DECAY_WEIGHT);
		
		double timeDecay = Math.pow(Math.E, t);

		return timeDecay;
	}

	private static double getHours(Date current, Date past) {
		return ((double) (current.getTime() - past.getTime())) / HOUR_IN_MILLI;
	}


	/**
	 * @param tweet
	 * @return hashs
	 */
	public static List<String> getHashTagsUserMents(Tweet tweet) {

		List<String> tags = new ArrayList<String>();

		if (null == tweet) {
			return tags;
		}

		for (HashtagEntity hste : tweet.getStatus().getHashtagEntities()) {
			tags.add(hste.getText());
		}

		for (UserMentionEntity usrme : tweet.getStatus()
				.getUserMentionEntities()) {
			tags.add(usrme.getText());
		}

		return tags;
	}

	/**
	 * @param hash
	 * @return list
	 * 
	 *         WIvENG => [WI, ENG], Split this way, IPL2015 => IPL
	 */
	private static List<String> refineHashes(String hash) {

		List<String> split = new ArrayList<String>();
		String[] array;

		// WIvsENG -> WI, ENG
		if (Constants.VS_PATTERN.matcher(hash).matches()) {
			// find for vs or v
			array =
			hash.replaceAll(
					"([A-Za-z0-9]*)(v|vs)([A-Z][A-Za-z0-9]*)","$1^$3")
					.split("\\^");
		} else {
			// replace num with empty char
			array = new String[] { hash.replaceAll("\\d", "") };
		}

		for (int i = 0; i < array.length; i++) {
			split.add(array[i]);
		}

		return split;
	}

	private static boolean containsWord(String sentence, String word) {

		int index = -1;
		int len = sentence.length();

		boolean first = false;
		boolean last = false;

		index = sentence.indexOf(word);
		int lastIndex = index + word.length() - 1;

		if (index < 0) {
			return false;
		}
		
		char c;

		if (index == 0) {
			first = true;
		} else {
			c = sentence.charAt(index - 1);
			if (c == ' ' || c == '.' || c == ',' || c == '?' || c == '!') { // word
																			// delims
				first = true;
			}
		}


		if (lastIndex == len - 1) {
			last = true;
		} else {
			c = sentence.charAt(lastIndex + 1);
			if (c == ' ' || c == '.' || c == ',' || c == '?' || c == '!') { // word
																			// delims
				last = true;
			}
		}

		// check first

		return first && last;
	}


}

