package com.pschuette.weather.data;

import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 *
 * @author PSchuette
 * 
 *         Search entity used for maintaining search history
 */
@Entity
public class Search {

	/**
	 * Search item identifier
	 */
	@Id
	Long id;

	/**
	 * Date the search was performed
	 */
	@Index
	public Date date;

	/**
	 * Email of the person who did the search
	 */
	@Index
	public String email;

	/**
	 * Search query
	 */
	public String placeTitle;
	public Double latitude;
	public Double longitude;

	/**
	 * No Arg constructor as required by objectify
	 */
	public Search() {
		this.date = new Date();
	}

	/**
	 * Construct a search item
	 * 
	 * @param email
	 *            user who performed search
	 * @param query
	 *            search input
	 */
	public Search(String email, String placeTitle, Double latitude, Double longitude) {
		this();
		this.email = email;
		this.placeTitle = placeTitle;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Long getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public String getEmail() {
		return email;
	}

	public String getPlaceTitle() {
		return placeTitle;
	}

	public Double getLatitude() {
		return latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	
	
	
}
