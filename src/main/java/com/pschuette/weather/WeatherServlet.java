package com.pschuette.weather;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author PSchuette
 *
 *         Servlet page for serving the weather application. This page is just
 *         used to map url to jsp page. /weather.jsp is automatically mapped
 */

@SuppressWarnings("serial")
public class WeatherServlet extends HttpServlet {

	/**
	 * 
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Forward get request to weather page
		getServletConfig().getServletContext().getRequestDispatcher("/weather.jsp").forward(req, resp);

	}

}
