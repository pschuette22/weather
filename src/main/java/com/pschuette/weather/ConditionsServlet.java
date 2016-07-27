package com.pschuette.weather;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

@SuppressWarnings("serial")
public class ConditionsServlet extends HttpServlet {

	private static final String SUPPORTED_ORIGINS = "*";
	private static final String SUPPORTED_METHODS = "GET, OPTIONS";
	private static final long YEAR_IN_MILLIS = 365 * 24 * 60 * 60 * 1000;
	private static final int YEARS_IN_HISTORY = 10;

	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// pre-flight request processing
		resp.setHeader("Access-Control-Allow-Origin", SUPPORTED_ORIGINS);
		resp.setHeader("Access-Control-Allow-Methods", SUPPORTED_METHODS);
	}

	private static final String testJson = "{\"timezone\":\"America/Denver\",\"flags\":{\"isd-stations\":[\"720533-99999\",\"720534-99999\",\"720538-99999\",\"724768-24051\",\"999999-94074\"],\"units\":\"us\",\"sources\":[\"isd\"]},\"currently\":{\"summary\":\"Clear\",\"icon\":\"clear-day\",\"cloudCover\":0.03,\"visibility\":10,\"apparentTemperature\":22.32,\"precipIntensity\":0,\"temperature\":30.23,\"dewPoint\":21.71,\"time\":1454887266,\"windSpeed\":8.61,\"humidity\":0.7,\"windBearing\":99,\"precipProbability\":0},\"longitude\":-105.0782066,\"latitude\":40.5787719,\"hourly\":{\"summary\":\"Clear throughout the day.\",\"icon\":\"clear-day\",\"data\":[{\"summary\":\"Clear\",\"icon\":\"clear-night\",\"cloudCover\":0,\"visibility\":10,\"apparentTemperature\":36.85,\"precipIntensity\":0,\"temperature\":39.89,\"dewPoint\":15.64,\"time\":1454828400,\"windSpeed\":4.38,\"humidity\":0.37,\"windBearing\":30,\"precipProbability\":0},{\"summary\":\"Clear\",\"icon\":\"clear-night\",\"cloudCover\":0,\"visibility\":10,\"apparentTemperature\":41.17,\"precipIntensity\":0,\"temperature\":41.17,\"dewPoint\":9.48,\"time\":1454832000,\"windSpeed\":1.4,\"humidity\":0.27,\"windBearing\":275,\"precipProbability\":0},{\"summary\":\"Clear\",\"icon\":\"clear-night\",\"cloudCover\":0,\"visibility\":10,\"apparentTemperature\":32.72,\"precipIntensity\":0,\"temperature\":39.38,\"dewPoint\":12.9,\"time\":1454835600,\"windSpeed\":10.36,\"humidity\":0.33,\"windBearing\":279,\"precipProbability\":0},{\"summary\":\"Clear\",\"icon\":\"clear-night\",\"cloudCover\":0,\"visibility\":10,\"apparentTemperature\":31.91,\"precipIntensity\":0,\"temperature\":37.47,\"dewPoint\":9.78,\"time\":1454839200,\"windSpeed\":7.29,\"humidity\":0.31,\"windBearing\":343,\"precipProbability\":0},{\"summary\":\"Clear\",\"icon\":\"clear-night\",\"cloudCover\":0.06,\"visibility\":10,\"apparentTemperature\":29.58,\"precipIntensity\":0,\"temperature\":36.04,\"dewPoint\":12.31,\"time\":1454842800,\"windSpeed\":8.33,\"humidity\":0.37,\"windBearing\":323,\"precipProbability\":0},{\"summary\":\"Clear\",\"icon\":\"clear-night\",\"cloudCover\":0,\"visibility\":10,\"apparentTemperature\":31.95,\"precipIntensity\":0,\"temperature\":37.84,\"dewPoint\":10.29,\"time\":1454846400,\"windSpeed\":8.02,\"humidity\":0.31,\"windBearing\":274,\"precipProbability\":0},{\"summary\":\"Clear\",\"icon\":\"clear-night\",\"cloudCover\":0,\"visibility\":10,\"apparentTemperature\":34.83,\"precipIntensity\":0,\"temperature\":34.83,\"dewPoint\":13.62,\"time\":1454850000,\"windSpeed\":2.93,\"humidity\":0.41,\"windBearing\":293,\"precipProbability\":0},{\"summary\":\"Clear\",\"icon\":\"clear-night\",\"cloudCover\":0,\"visibility\":10,\"apparentTemperature\":24.44,\"precipIntensity\":0,\"temperature\":28.83,\"dewPoint\":13.29,\"time\":1454853600,\"windSpeed\":3.98,\"humidity\":0.52,\"windBearing\":175,\"precipProbability\":0},{\"summary\":\"Clear\",\"icon\":\"clear-day\",\"cloudCover\":0,\"visibility\":10,\"apparentTemperature\":32.78,\"precipIntensity\":0,\"temperature\":32.78,\"dewPoint\":14.64,\"time\":1454857200,\"windSpeed\":1.64,\"humidity\":0.47,\"windBearing\":127,\"precipProbability\":0},{\"summary\":\"Clear\",\"icon\":\"clear-day\",\"cloudCover\":0,\"visibility\":10,\"apparentTemperature\":21.59,\"precipIntensity\":0,\"temperature\":31.13,\"dewPoint\":19.12,\"time\":1454860800,\"windSpeed\":12.22,\"humidity\":0.61,\"windBearing\":90,\"precipProbability\":0},{\"summary\":\"Clear\",\"icon\":\"clear-day\",\"cloudCover\":0,\"visibility\":10,\"apparentTemperature\":28.26,\"precipIntensity\":0,\"temperature\":33.44,\"dewPoint\":20.37,\"time\":1454864400,\"windSpeed\":5.6,\"humidity\":0.58,\"windBearing\":84,\"precipProbability\":0},{\"summary\":\"Clear\",\"icon\":\"clear-day\",\"cloudCover\":0.1,\"visibility\":10,\"apparentTemperature\":29.38,\"precipIntensity\":0,\"temperature\":34.31,\"dewPoint\":21.25,\"time\":1454868000,\"windSpeed\":5.49,\"humidity\":0.58,\"windBearing\":123,\"precipProbability\":0},{\"summary\":\"Clear\",\"icon\":\"clear-day\",\"cloudCover\":0,\"visibility\":10,\"apparentTemperature\":27.81,\"precipIntensity\":0,\"temperature\":34.98,\"dewPoint\":22.59,\"time\":1454871600,\"windSpeed\":9.21,\"humidity\":0.6,\"windBearing\":122,\"precipProbability\":0},{\"summary\":\"Clear\",\"icon\":\"clear-day\",\"cloudCover\":0.06,\"visibility\":10,\"apparentTemperature\":28.45,\"precipIntensity\":0,\"temperature\":34.93,\"dewPoint\":23.08,\"time\":1454875200,\"windSpeed\":7.94,\"humidity\":0.62,\"windBearing\":156,\"precipProbability\":0},{\"summary\":\"Clear\",\"icon\":\"clear-day\",\"cloudCover\":0,\"visibility\":10,\"apparentTemperature\":30.02,\"precipIntensity\":0,\"temperature\":35.13,\"dewPoint\":23.44,\"time\":1454878800,\"windSpeed\":5.93,\"humidity\":0.62,\"windBearing\":160,\"precipProbability\":0},{\"summary\":\"Clear\",\"icon\":\"clear-day\",\"cloudCover\":0,\"visibility\":10,\"apparentTemperature\":26.4,\"precipIntensity\":0,\"temperature\":34.26,\"dewPoint\":23.41,\"time\":1454882400,\"windSpeed\":10.27,\"humidity\":0.64,\"windBearing\":124,\"precipProbability\":0},{\"summary\":\"Clear\",\"icon\":\"clear-day\",\"cloudCover\":0,\"visibility\":10,\"apparentTemperature\":22.53,\"precipIntensity\":0,\"temperature\":31.03,\"dewPoint\":22.24,\"time\":1454886000,\"windSpeed\":9.97,\"humidity\":0.69,\"windBearing\":98,\"precipProbability\":0},{\"summary\":\"Clear\",\"icon\":\"clear-day\",\"cloudCover\":0.09,\"visibility\":10,\"apparentTemperature\":22.31,\"precipIntensity\":0,\"temperature\":28.77,\"dewPoint\":20.74,\"time\":1454889600,\"windSpeed\":6.09,\"humidity\":0.71,\"windBearing\":101,\"precipProbability\":0},{\"summary\":\"Clear\",\"icon\":\"clear-night\",\"cloudCover\":0.09,\"visibility\":10,\"apparentTemperature\":23.28,\"precipIntensity\":0,\"temperature\":27.52,\"dewPoint\":20.19,\"time\":1454893200,\"windSpeed\":3.69,\"humidity\":0.74,\"windBearing\":121,\"precipProbability\":0},{\"summary\":\"Clear\",\"icon\":\"clear-night\",\"cloudCover\":0,\"visibility\":10,\"apparentTemperature\":25.77,\"precipIntensity\":0,\"temperature\":25.77,\"dewPoint\":19.18,\"time\":1454896800,\"windSpeed\":1.64,\"humidity\":0.76,\"windBearing\":144,\"precipProbability\":0},{\"summary\":\"Clear\",\"icon\":\"clear-night\",\"cloudCover\":0,\"visibility\":10,\"apparentTemperature\":23.21,\"precipIntensity\":0,\"temperature\":23.21,\"dewPoint\":18.02,\"time\":1454900400,\"windSpeed\":2.25,\"humidity\":0.8,\"windBearing\":276,\"precipProbability\":0},{\"summary\":\"Clear\",\"icon\":\"clear-night\",\"cloudCover\":0.1,\"visibility\":10,\"apparentTemperature\":15.82,\"precipIntensity\":0,\"temperature\":20.6,\"dewPoint\":15.61,\"time\":1454904000,\"windSpeed\":3.37,\"humidity\":0.81,\"windBearing\":301,\"precipProbability\":0},{\"summary\":\"Clear\",\"icon\":\"clear-night\",\"cloudCover\":0.13,\"visibility\":10,\"apparentTemperature\":20.72,\"precipIntensity\":0,\"temperature\":20.72,\"dewPoint\":16.09,\"time\":1454907600,\"windSpeed\":1.19,\"humidity\":0.82,\"windBearing\":277,\"precipProbability\":0},{\"summary\":\"Clear\",\"icon\":\"clear-night\",\"cloudCover\":0.15,\"visibility\":10,\"apparentTemperature\":20.61,\"precipIntensity\":0,\"temperature\":20.61,\"dewPoint\":15,\"time\":1454911200,\"windSpeed\":2.74,\"humidity\":0.79,\"windBearing\":253,\"precipProbability\":0}]},\"offset\":-7,\"daily\":{\"data\":[{\"summary\":\"Clear throughout the day.\",\"sunsetTime\":1454891154,\"temperatureMinTime\":1454904000,\"icon\":\"clear-day\",\"temperatureMaxTime\":1454832000,\"cloudCover\":0.03,\"visibility\":10,\"apparentTemperatureMinTime\":1454904000,\"temperatureMin\":20.6,\"precipIntensity\":0,\"dewPoint\":17.18,\"sunriseTime\":1454853937,\"precipIntensityMax\":0,\"windSpeed\":1.36,\"time\":1454828400,\"humidity\":0.57,\"windBearing\":116,\"apparentTemperatureMaxTime\":1454832000,\"apparentTemperatureMax\":41.17,\"moonPhase\":0.97,\"precipProbability\":0,\"apparentTemperatureMin\":15.82,\"temperatureMax\":41.17}]}}";

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
			helper.doGet();
//			helper.setTestJson(testJson);
			// Print for curiosity
			System.out.println(helper.getJsonResponse().toString());

			// Get the conditions
			JSONObject jCurrent = helper.getJSONObject("currently");
			JSONObject jsonRespCurrent = new JSONObject();
			jsonRespCurrent.put("icon", jCurrent.get("icon"));
			jsonRespCurrent.put("summary", jCurrent.get("summary"));
			jsonRespCurrent.put("precipProbability", jCurrent.getDouble("precipProbability"));
			jsonRespCurrent.put("temperature", jCurrent.getInt("temperature"));
			jsonRespCurrent.put("humidity", jCurrent.getDouble("humidity"));
			jsonRespCurrent.put("wind", jCurrent.getDouble("humidity"));
			jsonRespCurrent.put("cloudCover", jCurrent.get("cloudCover"));

			// Add the currently response object
			jsonResp.put("currently", jsonRespCurrent);

			// Get conditions for the next 8 hours
			JSONArray jHourly = helper.getJSONObject("hourly").getJSONArray("data");
			JSONArray jsonRespHourly = new JSONArray();
			for (int i = 0; i < 8; i++) {
				JSONObject hObj = (JSONObject) jHourly.get(i);
				JSONObject jsonRespHour = new JSONObject();
				jsonRespHour.put("time", hObj.getLong("time"));
				jsonRespHour.put("temperature", hObj.getDouble("temperature"));
				jsonRespHour.put("precipProbability", hObj.getDouble("precipProbability"));
				jsonRespHourly.put(i, jsonRespHour);
			}
			// Add the hourly response object
			jsonResp.put("hourly", jsonRespHourly);

			// Get conditions for the next week
			JSONArray jDaily = helper.getJSONObject("daily").getJSONArray("data");
			JSONArray jsonRespDaily = new JSONArray();
			for (int i = 0; i < 7; i++) {
				JSONObject dObj = (JSONObject) jDaily.get(i);
				JSONObject jsonRespDay = new JSONObject();
				jsonRespDay.put("icon", dObj.getString("icon"));
				jsonRespDay.put("temperatureMin", dObj.getDouble("temperatureMin"));
				jsonRespDay.put("temperatureMax", dObj.getDouble("temperatureMax"));
				jsonRespDay.put("precipProbability", dObj.getDouble("precipProbability"));
				jsonRespDaily.put(i, jsonRespDay);
			}
			jsonResp.put("daily", jsonRespDaily);

//			// Build historic conditions
//			long time = System.currentTimeMillis();
//			double avgTemp = 0.0;
//			double avgHumidity = 0.0;
//			double avgCloudCover = 0.0;
//			Map<String, Integer> weather = new HashMap<String, Integer>();
//			for (int i = 0; i < YEARS_IN_HISTORY; i++) {
//				time -= YEAR_IN_MILLIS;
//				helper = new JSONHelper("https://api.forecast.io/forecast/def176538956da1b9adcf3bc55749d0f/" + sLat
//						+ "," + sLng + "," + Utils.darkSkyTimeFormat(time));
//				helper.doGet();
//				System.out.println("HISTORY: " + helper.getJsonResponse().toString());
//				JSONObject json = helper.getJSONObject("currently");
//				avgTemp += json.getDouble("temperature");
//				avgHumidity += json.getDouble("humidity");
//				avgCloudCover += json.getDouble("cloudCover");
//				String icon = json.getString("icon");
//				if (weather.containsKey(icon)) {
//					weather.put(icon, weather.get(icon) + 1);
//				} else {
//					weather.put(icon, Integer.valueOf(1));
//				}
//			}
//			avgTemp /= YEARS_IN_HISTORY;
//			avgHumidity /= YEARS_IN_HISTORY;
//			avgCloudCover /= YEARS_IN_HISTORY;
//
//			JSONArray jsonIconArr = new JSONArray();
//			for (String key : weather.keySet()) {
//				JSONObject iconObj = new JSONObject();
//				iconObj.put("icon", key);
//				iconObj.put("count", weather.get(key));
//				jsonIconArr.put(iconObj);
//			}
//
//			JSONObject jsonRespHistory = new JSONObject();
//			jsonRespHistory.put("avgTemp", avgTemp);
//			jsonRespHistory.put("avgHumidity", avgHumidity);
//			jsonRespHistory.put("avgCloudCover", avgCloudCover);
//			jsonRespHistory.put("iconCounts", jsonIconArr);
//
//			jsonResp.put("history", jsonRespHistory);

			// If there was an email attached, save the search history
			resp.getWriter().write(jsonResp.toString());
			resp.setStatus(HttpServletResponse.SC_OK);
		} else {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}

}
