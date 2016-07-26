package com.pschuette.weather;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.googlecode.objectify.ObjectifyService;
import com.pschuette.weather.data.Search;

public class OfyHelper implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
		// Register the search item 
		ObjectifyService.register(Search.class);
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// Not current invoked by appengine
		
	}

	
}
