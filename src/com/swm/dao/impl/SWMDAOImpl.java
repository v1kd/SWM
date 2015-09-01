package com.swm.dao.impl;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bson.BSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.swm.bean.Cluster;
import com.swm.bean.Feature;
import com.swm.bean.Post;
import com.swm.bean.Tweet;
import com.swm.dao.SWMDAO;

public class SWMDAOImpl implements SWMDAO {

	/**
	 * 
	 */
	private MongoClient client;

	/**
	 * 
	 */
	private DB db;

	/**
	 * 
	 */
	private List<Feature> features;

	/**
	 * 
	 */
	private Map<String, Feature> featureMap = new HashMap<String, Feature>();

	/**
	 * 
	 */
	public SWMDAOImpl() {
		try {
			connect();

		} catch (Exception e) {
		}
	}

	@Override
	public void storeTweets(List<Tweet> tweets) throws Exception {

		DBCollection collection = db.getCollection("TwitterSchema");
		BasicDBObject document;

		if (null == this.client) {
			throw new Exception("DB not connected");
		} else if (null == tweets || tweets.isEmpty()) {
			return;
		}

		for (Tweet tweet : tweets) {


			document = new BasicDBObject();
			document.put("source", "Twitter");
			document.put("id", tweet.getStatus().getId());
			document.put("data", tweet.getStatus().getText());
			document.put("timestamp", tweet.getStatus().getCreatedAt());
			document.put("tags", tweet.getTags());
			document.put("comments", null);
			document.put("likes", tweet.getStatus().getFavoriteCount());
			document.put("spread", tweet.getStatus().getRetweetCount());
			document.put("ps", tweet.getScore());

			collection.insert(document);
		}

	}

	@Override
	public void storeClusters(List<Cluster> clusters) throws Exception {

		DBCollection collection = db.getCollection("Cluster");
		BasicDBObject document;

		if (null == this.client) {
			throw new Exception("DB not connected");
		} else if (null == clusters || clusters.isEmpty()) {
			return;
		}

		for (Cluster cluster : clusters) {

			document = new BasicDBObject();
			document.put("key", cluster.getKey());
			document.put("totalPs", cluster.getTotalPs());
			document.put("maxPs", cluster.getMaxPs());
			document.put("maxPs", cluster.getCategory());
			document.put("post", cluster.getData());

			collection.insert(document);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Post> getPosts() throws Exception {
		
		List<Post> posts = new ArrayList<Post>();
		Post post;
		DBCollection collection = db.getCollection("TwitterSchema");
		DBCursor cursor = collection.find();
		DBObject obj;
		
		while (cursor.hasNext()) {
			obj = cursor.next();
			post = new Post();

			post.setSource((String) obj.get("source"));
			post.setId((long) obj.get("id"));
			post.setData(obj.get("data"));
			post.setCreatedAt((Date) obj.get("timestamp"));
			post.setTags((List<String>) obj.get("tags"));
			post.setLikes((int) obj.get("likes"));
			post.setSpread((int) obj.get("spread"));
			post.setPs((double) obj.get("ps"));

			posts.add(post);
		}

		return posts;
	}


	/**
	 * @throws Exception
	 */
	private void connect() throws Exception {
		MongoCredential credential = MongoCredential.createCredential("SWM",
				"SWM", "CSE591".toCharArray());
		try {
			setClient(new MongoClient(new ServerAddress("localhost"),
					Arrays.asList(credential)));

			this.db = this.client.getDB("SWM");
			
			this.getFeatureSet();
		} catch (UnknownHostException e) {
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	private void getFeatureSet() {
		
		List<Feature> features = new ArrayList<Feature>();
		Feature feature;
		
		DBCollection collection = this.db.getCollection("Features");
		DBCursor cursor = collection.find();
		DBObject obj;
		BSONObject bson;
		Collection<Object> vals;
		String alias;
		
		List<String> aliases;
		String key;

		while(cursor.hasNext()) {
			obj = cursor.next();

			feature = new Feature();
			features.add(feature);

			key = (String) obj.get("key");
			feature.setName(key);
			feature.setCategory((String) obj.get("category"));

			bson = (BSONObject) obj.get("alias");
			vals = (Collection<Object>) bson.toMap().values();

			aliases = new ArrayList<String>();

			this.featureMap.put(key, feature);

			for (Iterator<Object> iterator = vals.iterator(); iterator
					.hasNext();) {
				alias = ((String) iterator.next());
				aliases.add(alias);
				this.featureMap.put(alias.toLowerCase(), feature);
			}

			feature.setAlias(aliases);

		}
		
		this.features = features;

	}

	public MongoClient getClient() {
		return client;
	}

	public void setClient(MongoClient client) {
		this.client = client;
	}

	public List<Feature> getFeatures() {
		return features;
	}

	public void setFeatures(List<Feature> features) {
		this.features = features;
	}

	public Map<String, Feature> getFeatureMap() {
		return featureMap;
	}

	public void setFeatureMap(Map<String, Feature> featureMap) {
		this.featureMap = featureMap;
	}

}
