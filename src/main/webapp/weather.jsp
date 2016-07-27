<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ page import="java.util.List"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Weather</title>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet" href="assets/css/main.css" />
<style>
html, body {
	height: 100%;
	margin: 0;
	padding: 0;
}

#map {
	height: 100%;
	min-height: 300px;
	width: 100%
}

.controls {
	margin-top: 10px;
	border: 1px solid transparent;
	border-radius: 2px 0 0 2px;
	box-sizing: border-box;
	-moz-box-sizing: border-box;
	height: 32px;
	outline: none;
	box-shadow: 0 2px 6px rgba(0, 0, 0, 0.3);
}

#pac-input {
	background-color: #fff;
	font-family: Roboto;
	font-size: 15px;
	font-weight: 300;
	margin-left: 12px;
	padding: 0 11px 0 13px;
	text-overflow: ellipsis;
	width: 300px;
}

#pac-input:focus {
	border-color: #4d90fe;
}

.pac-container {
	font-family: Roboto;
}

#type-selector {
	color: #fff;
	background-color: #4d90fe;
	padding: 5px 11px 0px 11px;
}

#type-selector label {
	font-family: Roboto;
	font-size: 13px;
	font-weight: 300;
}

#target {
	width: 345px;
}
</style>
<script src="https://code.jquery.com/jquery-1.10.2.js"></script>

</head>
<body>
	<%
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (user != null) {
			pageContext.setAttribute("usr", user);
		}
	%>

	<div id="page-wrapper">

		<!-- Header -->
		<header id="header">
		<div class="logo container">
			<div>
				<h1>Groovy Weather</h1>
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
							<article class="box page-content"> <header>
							<h2>Search Weather, Anywhere</h2>
							</header> <!-- Put current weather history here --> <!-- Location picker/ map here -->
							<input id="pac-input" class="controls" type="text"
								placeholder="Search Locations">
							<div id="map"></div>
						</div>
					</div>
				</div>

				<h2 id="location">Location</h2>
				<h4 id="summary">Summary</h4>
				<img id="icon-weather" alt="/images/icons/unknown.png"
					src="/images/icons/unknown.png" />
				<h4 id="temp">Temperature</h4>
				<h4 id="precip">Precip</h4>
				<h4 id="humidity">Humidity</h4>
				<h4 id="wind">Wind</h4>
				<div id="chart-hourly"></div>

				<!-- Upcoming week weather icons -->
				<div class="row 200%" style="overflow-x: auto; white-space: nowrap;">
					<img id="icon-weather1" alt="/images/icons/unknown.png"
						src="/images/icons/unknown.png" /> <img id="icon-weather2"
						alt="/images/icons/unknown.png" src="/images/icons/unknown.png" />
					<img id="icon-weather3" alt="/images/icons/unknown.png"
						src="/images/icons/unknown.png" /> <img id="icon-weather4"
						alt="/images/icons/unknown.png" src="/images/icons/unknown.png" />
					<img id="icon-weather5" alt="/images/icons/unknown.png"
						src="/images/icons/unknown.png" /> <img id="icon-weather6"
						alt="/images/icons/unknown.png" src="/images/icons/unknown.png" />
					<img id="icon-weather7" alt="/images/icons/unknown.png"
						src="/images/icons/unknown.png" />

				</div>


				<div class="row 200%">
					<div class="12u">

						<!-- Features -->
						<section class="box features">
						<h2 class="major">
							<span>Historical Averages</span>
						</h2>
						<div>
							<div class="row">
								<div class="3u 12u(mobile)">

									<!-- Feature -->
									<section class="box feature"> <a href="#"
										class="image featured"><img src="images/pic01.jpg" alt="" /></a>
									<h3>
										<a href="#">A Subheading</a>
									</h3>
									<p>Phasellus quam turpis, feugiat sit amet ornare in, a
										hendrerit in lectus dolore. Praesent semper mod quis eget sed
										etiam eu ante risus.</p>
									</section>

								</div>
								<div class="3u 12u(mobile)">

									<!-- Feature -->
									<section class="box feature"> <a href="#"
										class="image featured"><img src="images/pic02.jpg" alt="" /></a>
									<h3>
										<a href="#">Another Subheading</a>
									</h3>
									<p>Phasellus quam turpis, feugiat sit amet ornare in, a
										hendrerit in lectus dolore. Praesent semper mod quis eget sed
										etiam eu ante risus.</p>
									</section>

								</div>
								<div class="3u 12u(mobile)">

									<!-- Feature -->
									<section class="box feature"> <a href="#"
										class="image featured"><img src="images/pic03.jpg" alt="" /></a>
									<h3>
										<a href="#">And Another</a>
									</h3>
									<p>Phasellus quam turpis, feugiat sit amet ornare in, a
										hendrerit in lectus dolore. Praesent semper mod quis eget sed
										etiam eu ante risus.</p>
									</section>

								</div>
								<div class="3u 12u(mobile)">

									<!-- Feature -->
									<section class="box feature"> <a href="#"
										class="image featured"><img src="images/pic04.jpg" alt="" /></a>
									<h3>
										<a href="#">And One More</a>
									</h3>
									<p>Phasellus quam turpis, feugiat sit amet ornare in, a
										hendrerit in lectus dolore. Praesent semper mod quis eget sed
										etiam eu ante risus.</p>
									</section>

								</div>
							</div>
						</div>
						</section>

					</div>
				</div>
			</div>
		</div>

	</div>

	<!-- Scripts -->
	<script src="assets/js/jquery.min.js"></script>
	<script src="assets/js/jquery.dropotron.min.js"></script>
	<script src="assets/js/skel.min.js"></script>
	<script src="assets/js/skel-viewport.min.js"></script>
	<script src="assets/js/util.js"></script>
	<!-- [if lte IE 8]><script src="assets/js/ie/respond.min.js"></script><![endif] -->
	<script src="assets/js/main.js"></script>

	<script>
		function initMap() {

			// Init the map element centered around Denver, Colorado
			var map = new google.maps.Map(document.getElementById('map'), {
				center : {
					lat : 39.7392,
					lng : -104.9903
				},
				zoom : 13,
				mapTypeId : google.maps.MapTypeId.ROADMAP
			});

			// Init the markers element
			var markers = [];

			// Create the search box and link it to the UI element.
			var input = document.getElementById('pac-input');
			var searchBox = new google.maps.places.SearchBox(input);
			map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);

			// Bias the SearchBox results towards current map's viewport.
			map.addListener('bounds_changed', function() {
				searchBox.setBounds(map.getBounds());
			});

			// Listen for the event fired when the user selects a prediction and retrieve
			// more details for that place.
			searchBox.addListener('places_changed', function() {
				var places = searchBox.getPlaces();

				if (places.length == 0) {
					return;
				}

				// Clear out the old markers.
				markers.forEach(function(marker) {
					marker.setMap(null);
				});
				markers = [];

				// For each place, get the icon, name and location.
				var bounds = new google.maps.LatLngBounds();
				places.forEach(function(place) {
					if (!place.geometry) {
						console.log("Returned place contains no geometry");
						return;
					}

					// update the location
					updateLocation(map, markers, place.geometry.location.lat(),
							place.geometry.location.lng(), place.title);

					if (place.geometry.viewport) {
						// Only geocodes have viewport.
						bounds.union(place.geometry.viewport);
					} else {
						bounds.extend(place.geometry.location);
					}
				});
				map.fitBounds(bounds);
			});

			// Try geolocation.
			if (navigator.geolocation) {
				navigator.geolocation.getCurrentPosition(function(position) {
					// Update the location to the current position
					updateLocation(map, markers, position.coords.latitude,
							position.coords.longitude, "Current Location");
				}, function() {
					// Update location to denver
					updateLocation(map, markers, 39.7392, -104.9903,
							"Denver, CO");
				});
			} else {
				// Update location to denver
				updateLocation(map, markers, 39.7392, -104.9903, "Denver, CO");
			}

		}

		/*
		 *	React to the location being changed
		 * */
		function updateLocation(map, markers, latitude, longitude, title) {

			// init the icon for 
			var icon = {
				url : "http://maps.google.com/mapfiles/ms/micons/red.png",
				size : new google.maps.Size(71, 71),
				origin : new google.maps.Point(0, 0),
				anchor : new google.maps.Point(17, 34),
				scaledSize : new google.maps.Size(25, 25)
			};

			// push location marker
			markers.push(new google.maps.Marker({
				map : map,
				icon : icon,
				title : title,
				position : {
					lat : latitude,
					lng : longitude
				}
			}));

			// re-center
			map.setCenter(new google.maps.LatLng(latitude, longitude));

			// Update the weather report
			fetchWeather(latitude, longitude)
		}

		/*
		 *	Make requests to weather API
		 * */
		function fetchWeather(latitude, longitude) {
			console.log("Update weather at " + latitude + ", " + longitude);
			// Get the current weather conditions and update visuals
			var conditions = "/conditions";
			var data = {
				'lat' : latitude,
				'lng' : longitude
			};
			$.get(conditions, data, function(resp) {
				console.log(resp);
				var json = JSON.parse(resp);
				updateWeather(json);
				updateCharts(json);
				// TODO: update history if logged in
			});
			// Get historical weather conditions (Run a loop getting historical conditions 25 times a year apart)
		}

		/*
		 *	Parse json request to weather app and update weather info
		 * */
		function updateWeather(data) {
			
			document.getElementById('summary').innerHTML = data.currently.summary;
			document['icon-weather'].src = iconSrc(data.currently.icon);

			document.getElementById('temp').innerHTML = data.currently.temperature
					+ "ºf";

			// Consider small charts?
			document.getElementById('precip').innerHTML = data.currently.precipProbability
					+ "% chance of precipitation";
			document.getElementById('humidity').innerHTML = data.currently.humidity
					+ "% humidity";
			document.getElementById('wind').innerHTML = data.currently.humidity
					+ "mph wind";

			// Set the upcoming week items
			var i;
			for (i = 0; i < 7; i++) {
				var dayItem = data.daily[i];
				var id = "weather-icon" + i;
				document[id].src = iconSrc(dayItem.icon);
				// TODO: set max/min temps
			}
		}

		/*
		 *	Get source of an image to properly display an icon as defined by Dark Sky API
		 * */
		function iconSrc(icon) {
			var src = "/images/icons/unknown.png";
			switch (icon) {
			case 'clear-day':
				src =  "images/icons/clear.png";
			case 'clear-night':
				src =  "images/icons/clearnight.png";
			case 'rain':
				src =  "images/icons/rain.png";
			case 'snow':
				src =  "images/icons/snow.png";
			case 'sleet':
				src =  "images/icons/sleet.png";
			case 'wind':
				src =  "images/icons/windy.png";
			case 'fog':
				src =  "images/icons/cloudy.png";
			case 'cloudy':
				src =  "images/icons/clear.png";
			case 'partly-cloudy-day':
				src =  "images/icons/partlycloudy.png";
			case 'partly-cloudy-night':
				src =  "images/icons/partlycloudynight.png";
			case 'hail':
				src =  "images/icons/frost.png";
			case 'thunderstorm':
				src =  "/images/icons/thunderstorms.png";
			default:
				// use the default
			}
			return src;
		}
	</script>

	<!-- Chart Functions -->
	<script type="text/javascript"
		src="https://www.gstatic.com/charts/loader.js"></script>
	<script type="text/javascript">
		// Load the Visualization API and the corechart package.
		google.charts.load('current', {
			'packages' : [ 'corechart' ]
		});
		// Set a callback to run when the Google Visualization API is loaded.
		// TODO: implement safety in case charts are not loaded in time for data
		// google.charts.setOnLoadCallback(drawChart);

		/*
		 *	Update the charts/ pretty UI
		 * */
		function updateCharts(data) {

			var hourly = data.hourly;

			var chartData = google.visualization.arrayToDataTable([
					[ 'Time', 'Temperature' ],
					[ hourly[0].time, hourly[0].temperature ],
					[ hourly[1].time, hourly[1].temperature ],
					[ hourly[2].time, hourly[2].temperature ],
					[ hourly[3].time, hourly[3].temperature ],
					[ hourly[4].time, hourly[4].temperature ],
					[ hourly[5].time, hourly[5].temperature ],
					[ hourly[6].time, hourly[6].temperature ] ]);

			var options = {
				title : 'Hourly Temperature',

				vAxis : {
					title : 'Temperature (ºf)'
				}
			};

			var chart = new google.visualization.LineChart(document
					.getElementById('chart-hourly'));

			chart.draw(data, options);

		}
	</script>
	<script
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCSMkM5Ynfei9sAiDgRTlx8UmSNid423QE&libraries=places&callback=initMap"
		async defer></script>
</body>
</html>