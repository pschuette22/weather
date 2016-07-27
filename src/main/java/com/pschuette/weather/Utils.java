package com.pschuette.weather;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author PSchuette Static utility class
 */
public class Utils {

	
	/**
	 * True if string is web safe
	 * @param s string in question
	 * @return true if s is web safe
	 */
	public static boolean isWebSafe(String s){
		return !(s==null || s.isEmpty());
	}
	
	/**
	 * Format a string the way Dark Sky API Expects
	 * @param timeInMillis
	 * @return
	 */
	public static String darkSkyTimeFormat(long timeInMillis){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		return format.format(new Date(timeInMillis));
	}
	
	private Utils() {
		throw new RuntimeException("Should not instantiate utils");
	}
}
