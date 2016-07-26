package com.pschuette.weather.data;

import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 *
 * @author PSchuette 
 * 
 * 		Search entity used for maintaining search history
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
	Date date;

	/**
	 * Email of the person who did the search
	 */
	@Index
	String email;

	/**
	 * Search query
	 */
	@Index
	String query;

	/**
	 * Construct a search item
	 * 
	 * @param email
	 *            user who performed search
	 * @param query
	 *            search input
	 */
	public Search(String email, String query) {
		this.date = new Date();
		this.email = email;
		this.query = query;
	}

}
