<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ page import="com.googlecode.objectify.ObjectifyService"%>

<%@ page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Weather</title>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet" href="assets/css/main.css" />

<script src="https://code.jquery.com/jquery-1.10.2.js"></script>
<script src="assets/js/jquery.min.js"></script>
<script src="assets/js/jquery.dropotron.min.js"></script>
<script src="assets/js/skel.min.js"></script>
<script src="assets/js/skel-viewport.min.js"></script>
<script src="assets/js/util.js"></script>
<!-- [if lte IE 8]><script src="assets/js/ie/respond.min.js"></script><![endif] -->
<script src="assets/js/main.js"></script>
<script type="text/javascript" src="assets/js/weather.js"></script>
<!-- Put chart functions in the head because the Google Example did it this way -->
<script type="text/javascript"
	src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">
	//Make sure to load the charts package
	google.charts.load('current', {
		'packages' : [ 'corechart' ]
	});

	/*
	 *	Update the charts/ pretty UI
	 * */
	function updateCharts(data) {

		// Draw chart for hourly weather
		var hourly = data.hourly;
		hChartDataArray = [ [ 'Time', 'Temperature' ] ];

		for (var i = 0; i < hourly.length; i++) {
			hChartDataArray.push([ formatTime(hourly[i].time),
					hourly[i].temperature ]);
		}

		var hChartData = google.visualization.arrayToDataTable(hChartDataArray);

		var hOptions = {
			title : 'Hourly Temperature',
			legend : {
				position : 'none'
			},
			hAxis : {
				title : 'Time of day'
			},
			vAxis : {
				title : 'Temperature (ºf)'
			}
		};

		var hChart = new google.visualization.LineChart(document
				.getElementById('chart-hourly'));

		hChart.draw(hChartData, hOptions);

		// Draw charts for historic weather break down

		var history = data.history;
		// Draw data for icons chart
		var iChartDataArray = [ [ "Weather", "Days in Past 10 Years" ] ];
		for (var j = 0; j < history.iconCounts.length; j++) {
			iChartDataArray.push([
					getWeatherIconTitle(history.iconCounts[j].icon),
					history.iconCounts[j].count ]);
		}

		var iChartData = google.visualization.arrayToDataTable(iChartDataArray);

		var iOptions = {
			title : 'Today over past 10 years'
		};

		var iChart = new google.visualization.PieChart(document
				.getElementById('chart-weathertypes'));

		iChart.draw(iChartData, iOptions);

	}
</script>
<!-- Map Loader -->
<!-- Verified it was ok to embedded this key locally because I restricted this webapp host to using it on the console -->
<script
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCSMkM5Ynfei9sAiDgRTlx8UmSNid423QE&libraries=places&callback=initMap"
	async defer></script>
</head>
<body>

	<!-- Hold onto user if they are logged in -->
	<%
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
	%>
	<div id="page-wrapper">

		<!-- Header -->
		<header id="header">
		<div class="logo container">
			<div>
				<h1 id="logo">Groovy Weather</h1>
				<p>by SkyCast inc.</p>
			</div>
		</div>
		</header>

		<!-- Main -->
		<div id="main-wrapper">

			<div id="main" class="container">
				<div class="row">
					<div class="12u">
						<div class="content">
							<!-- Content -->
							<header>
							<h2>Search Weather, Anywhere</h2>
							</header>
							<!-- Put current weather history here -->
							<!-- Location picker/ map here -->
							<input id="pac-input" class="controls" type="text"
								placeholder="Search Locations">
							<div id="map"></div>
						</div>
					</div>
				</div>

				<!-- Loader container -->
				<div class="row 200%">
					<div id="loader" style="display: none;"></div>
				</div>

				<!-- Div encompassing all weather content to easily display/hide -->
				<div id="div-weather-content" style="display: none;">
					<!-- Section of page for displaying current data-->
					<div id="div-current" class="row 200%">
						<div class="12u">
							<!-- Display the location -->
							<h2 class="major">
								<span id="location"></span>
							</h2>
							<!-- Left justified items -->
							<div style="display: inline-block;">
								<div style="min-width: 300px; float: left;">
									<h3 id="summary">
										</h4>
										<div style="display: inline-block;">
											<img id="icon-weather-now" alt="" src="" />
											<h3 id="temp">
												</h4>
										</div>
								</div>
								<!-- Right justified items -->
								<div style="float: left;">
									<h3 id="precip">
										</h4>
										<h3 id="humidity">
											</h4>
											<h3 id="wind">
												</h4>
								</div>
							</div>
							<div id="chart-hourly"></div>
						</div>
					</div>


					<!-- Section of page for displaying upcoming weeks data -->
					<div id="div-week" class="row 200%">
						<div class="12u">
							<h2 class="major">
								<span>This Week</span>
							</h2>
							<!-- Upcoming week weather icons -->
							<div id="div-days-container" class="inline-items-container"
								style="width: 100%; overflow-x: auto; white-space: nowrap;">

							</div>
						</div>
					</div>

					<!-- Section of page for displaying historical data -->
					<div id="div-history" class="row 200%">
						<div class="12u">

							<h2 class="major">
								<span>Historical Averages</span>
							</h2>

							<div class="inline-items-container">
								<!-- Historically average temperature -->
								<div class="historyitem">
									<h2 id="average-temperature"></h2>
									<h3>Temperature</h3>
								</div>

								<!-- Historically average humidity -->
								<div class="historyitem">
									<h2 id="average-humidity"></h2>
									<h3>Humidity</h3>
								</div>

								<!-- Historic Cloud cover -->
								<div class="historyitem">
									<h2 id="average-cloudcover"></h2>
									<h3>Cloud Cover</h3>
								</div>
							</div>
							<!-- Historically speaking weather types -->
							<div style="float: center; text-align: center;">
								<div id="chart-weathertypes"></div>
								<h3>Weather Types</h3>
							</div>
						</div>
					</div>
				</div>




				<!-- Display search history or give users the option to track -->
				<div id="div-searches" class="row 200%">
					<%
						if (user != null) {
							pageContext.setAttribute("user", user);
					%>
					<p>
						Hello, ${fn:escapeXml(user.nickname)}! While you're signed in,
						we'll keep track of your search history. (You can always <a
							href="<%=userService.createLogoutURL(request.getRequestURI())%>">sign
							out</a>). To see your search history, <a href="/history">Click
							Here</a>.
					</p>
					<%
						} else {
					%>
					<p>
						Hello! <a
							href="<%=userService.createLoginURL(request.getRequestURI())%>">Sign
							in</a> to track your search history.
					</p>
					<%
						}
					%>

				</div>

			</div>
		</div>

	</div>




</body>
</html>