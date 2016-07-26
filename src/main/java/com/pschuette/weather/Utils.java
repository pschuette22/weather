package com.pschuette.weather;

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
	
	private Utils() {
		throw new RuntimeException("Should not instantiate utils");
	}
}
