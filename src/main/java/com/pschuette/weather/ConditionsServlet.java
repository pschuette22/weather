package com.pschuette.weather;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


@SuppressWarnings("serial")
public class ConditionsServlet extends HttpServlet {

	private static final String SUPPORTED_ORIGINS = "*";
	private static final String SUPPORTED_METHODS = "GET, OPTIONS";
	private static final long YEAR_IN_MILLIS = 365*24*60*60*1000;
	// private static final String SUPPORTED_HEADERS = "";

	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// pre-flight request processing
		resp.setHeader("Access-Control-Allow-Origin", SUPPORTED_ORIGINS);
		resp.setHeader("Access-Control-Allow-Methods", SUPPORTED_METHODS);
		// resp.setHeader("Access-Control-Allow-Headers", SUPPORTED_HEADERS);
	}

	/**
	 * GET json conditions for given location
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Make sure the proper params were sent
		String sLat = req.getParameter("lat");
		String sLng = req.getParameter("lng");
		if (Utils.isWebSafe(sLat) && Utils.isWebSafe(sLng)) {
			System.out.println("sLat: " + sLat + ", sLng: " + sLng);
			// JSON response following condition requests
			JSONObject jsonResp = new JSONObject();
			// Request current conditions
			JSONHelper helper = new JSONHelper(
					"https://api.forecast.io/forecast/def176538956da1b9adcf3bc55749d0f/" + sLat + "," + sLng);
			try {
				helper.doGet();
				// Print for curiosity
				System.out.println(helper.getJsonResponse().toString());
				
				// Get the conditions
				JSONObject jCurrent = helper.getJSONObject("currently");
				JSONObject jsonRespCurrent = new JSONObject();
				jsonRespCurrent.put("icon", jCurrent.get("icon"));
				jsonRespCurrent.put("precipProbability", jCurrent.get("precipProbability"));
				jsonRespCurrent.put("temperature", jCurrent.get("temperature"));
				jsonRespCurrent.put("humidity", jCurrent.get("humidity"));
				// Add the currently response object
				jsonResp.put("currently", jsonRespCurrent);
				
				// Get conditions for the next 8 hours
				JSONArray jHourly = helper.getJSONObject("hourly").getJSONArray("data");
				JSONArray jsonRespHourly = new JSONArray();
				for(int i=0; i< 8; i++){
					JSONObject hObj = (JSONObject) jHourly.get(i);
					JSONObject jsonRespHour = new JSONObject();
					jsonRespHour.put("time", hObj.getLong("time"));
					jsonRespHour.put("temperature", hObj.getString("temperature"));
					jsonRespHour.put("precipProbability", hObj.getString("precipProbability"));
					jsonRespHourly.put(i, jsonRespHour);
				}
				// Add the hourly response object
				jsonResp.put("hourly", jsonRespHourly);
				
				// Get conditions for the next week
				JSONArray jDaily = helper.getJSONObject("daily").getJSONArray("data");
				JSONArray jsonRespDaily = new JSONArray();
				for(int i=0; i< 7; i++){
					JSONObject dObj = (JSONObject) jDaily.get(i);
					JSONObject jsonRespDay = new JSONObject();
					jsonRespDay.put("icon", dObj.getString("icon"));
					jsonRespDay.put("temperature", dObj.getString("temperature"));
					jsonRespDay.put("temperatureMin", dObj.get("temperatureMin"));
					jsonRespDay.put("temperatureMax", dObj.get("temperatureMax"));
					jsonRespDay.put("precipProbability", dObj.getString("precipProbability"));
					jsonRespDaily.put(i, jsonRespDay);
				}
				jsonResp.put("daily", jsonRespDaily);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				return;
			}

			// Build historic conditions

			// If there was an email attached, save the search history
			resp.getWriter().write(jsonResp.toString());
			resp.setStatus(HttpServletResponse.SC_OK);
		} else {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}

}
