package com.swm.constants;

import java.util.regex.Pattern;

/**
 * @author vicky
 *
 */
public class Constants {
	/**
	 * 
	 */
	public static final String CONSUMER_KEY = "XXX";
	/**
	 * 
	 */
	public static final String CONSUMER_SECRET = "XXX";

	// Create a Pattern object
	public static final Pattern VS_PATTERN = Pattern
			.compile("^[A-Za-z0-9]*(v|vs)[A-Z][A-Za-z0-9]*$");

}
