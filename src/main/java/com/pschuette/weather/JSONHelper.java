package com.pschuette.weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * 
 * @author PSchuette
 *
 *
 *         Helper class for fetching/ parsing json
 */
public class JSONHelper {

	private String url;
	private JSONObject jReq;
	private JSONObject jResp;

	/**
	 * Construct a json helper
	 * 
	 * @param url
	 *            to fetch json from
	 */
	public JSONHelper(String url) {
		this.url = url;
		this.jReq = new JSONObject();
	}

	public void putRequestParam(String key, String value) throws JSONException {
		this.jReq.put(key, value);
	}

	/**
	 * Make the json request
	 * 
	 * @return @true on success
	 * @throws IOException
	 * @throws JSONException
	 */
	public boolean doGet() throws IOException, JSONException {

		// [START example]
	    URL url = new URL(this.url);
	    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
	    StringBuffer json = new StringBuffer();
	    String line;

	    while ((line = reader.readLine()) != null) {
	      json.append(line);
	    }
	    reader.close();
	    jResp = new JSONObject(json.toString());

		return true;
	}

	/**
	 * Get a given string from the response object
	 * 
	 * @param key
	 * @return
	 * @throws JSONException
	 */
	public String getString(String key) throws JSONException {
		return jResp.getString(key);
	}

	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public JSONObject getJSONObject(String key) {
		return jResp.getJSONObject(key);
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public JSONArray getJSONArray(String key){
		return jResp.getJSONArray(key);
	}
	
	/**
	 * Get the json object response
	 * 
	 * @return
	 */
	public JSONObject getJsonResponse() {
		return jResp;
	}

}
