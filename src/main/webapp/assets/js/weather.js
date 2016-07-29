/*
 * Javascript functions used on weather.jsp
 * There are a lot of ID references here, it is specifically meant for weather.jsp
 * 
 */

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

	// Init the controls for searching places
	map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);
	// Bias the SearchBox results towards current map's viewport.
	map.addListener('bounds_changed', function() {
		searchBox.setBounds(map.getBounds());
	});

	// Listen for the event fired when the user selects a prediction and
	// retrieve
	// more details for that place.
	searchBox.addListener('places_changed', function() {
		var places = searchBox.getPlaces();
		var bounds = new google.maps.LatLngBounds();

		// Verify there is was a place selected
		if (places.length == 0) {
			return;
		}

		// Clear out the old markers.
		markers.forEach(function(marker) {
			marker.setMap(null);
		});
		markers = [];

		// For each place, get the icon, name and location.
		places.forEach(function(place) {
			if (!place.geometry) {
				return;
			}

			// update the location
			updateLocation(map, markers, place.geometry.location.lat(),
					place.geometry.location.lng(), place.name);

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
					position.coords.longitude, "Your Location");
		},
				function() {
					// Update location to denver
					updateLocation(map, markers, 39.7392, -104.9903,
							"Denver, Colorado");
				});
	} else {
		// Update location to denver
		updateLocation(map, markers, 39.7392, -104.9903, "Denver, Colorado");
	}

}

/*
 * React to the location being changed
 */
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
	fetchWeather(latitude, longitude, title);
	document.getElementById('location').innerText = "Current Weather at "
			+ title;
}

/*
 * Make requests to weather API
 */
function fetchWeather(latitude, longitude, title) {
	// Get the current weather conditions and update visuals
	var conditions = "/conditions";
	var data = {
		'lat' : latitude,
		'lng' : longitude,
		'place' : title
	};

	// Display loading dialog
	document.getElementById("loader").style.display = "block";
	// display content
	document.getElementById("div-weather-content").style.display = "none";

	// Fetch weather conditions json
	$.get(conditions, data, function(resp) {
		var json = JSON.parse(resp);
		updateWeather(json);

		// Hide loader
		document.getElementById("loader").style.display = "none";
		// display content
		document.getElementById("div-weather-content").style.display = "block";

		updateCharts(json);

	});
	// if there is an error, display error
	// Get historical weather conditions (Run a loop getting historical
	// conditions 25 times a year apart)
}

/*
 * Parse json request to weather app and update weather info
 */
function updateWeather(data) {

	// Defined these here for readability
	var precipProb = Number(data.currently.precipProbability * 100).toFixed(1);
	var humidity = Number(data.currently.humidity * 100).toFixed(1);

	// Update all the current forcast items
	document.getElementById('summary').innerHTML = data.currently.summary;

	$('#icon-weather-now').attr("src", getWeatherIconSrc(data.currently.icon));
	document.getElementById('temp').innerHTML = data.currently.temperature
			+ "ºf";

	document.getElementById('precip').innerHTML = precipProb
			+ "% chance of precipitation";
	document.getElementById('humidity').innerHTML = humidity + "% humidity";

	document.getElementById('wind').innerHTML = data.currently.wind
			+ "mph wind";

	// Draw the forcast for the upcoming week
	drawWeekForcast(data.daily);

	// Update historic items
	document.getElementById('average-temperature').innerHTML = Number(
			data.history.avgTemp).toFixed(1)
			+ "ºf";
	document.getElementById('average-humidity').innerHTML = Number(
			data.history.avgHumidity * 100).toFixed(1)
			+ "%";
	document.getElementById('average-cloudcover').innerHTML = Number(
			data.history.avgCloudCover * 100).toFixed(1)
			+ "%";

}

/*
 * Dynamically draw the weekly forcast
 */
function drawWeekForcast(data) {

	var html = "";
	var i;
	var day;
	var dayTitle;
	for (i = 0; i < data.length; i++) {
		var day = data[i];
		var dayTitle = formatDay(day.time);
		html += "<div class=\"dayofweek\">";
		html += "<h4 >" + dayTitle + "</h4>";
		html += "<img id=\"icon-weather\" alt=\"\" src=\""
				+ getWeatherIconSrc(day.icon) + "\" />";
		html += "<h3 >" + day.tempMax + "ºf</h3>";
		html += "<h3 >" + day.tempMin + "ºf</h3>";
		html += "</div>";
	}

	// set the inner html after being
	document.getElementById("div-days-container").innerHTML = html;

}

/*
 * Get source of an image to properly display an icon as defined by Dark Sky API
 */
function getWeatherIconSrc(icon) {

	var imgPath = "images/icons/unknown.png";
	switch (icon) {
	case 'clear-day':
		imgPath = "images/icons/clear.png";
		break;
	case 'clear-night':
		imgPath = "images/icons/clearnight.png";
		break;
	case 'rain':
		imgPath = "images/icons/rain.png";
		break;
	case 'snow':
		imgPath = "images/icons/snow.png";
		break;
	case 'sleet':
		imgPath = "images/icons/sleet.png";
		break;
	case 'wind':
		imgPath = "images/icons/windy.png";
		break;
	case 'fog':
		imgPath = "images/icons/cloudy.png";
		break;
	case 'cloudy':
		imgPath = "images/icons/clear.png";
		break;
	case 'partly-cloudy-day':
		imgPath = "images/icons/partlycloudy.png";
		break;
	case 'partly-cloudy-night':
		imgPath = "images/icons/partlycloudynight.png";
		break;
	case 'hail':
		imgPath = "images/icons/frost.png";
		break;
	case 'thunderstorm':
		imgPath = "images/icons/thunderstorms.png";
		break;
	}

	var imgSrc = window.location.protocol + "//" + window.location.host + "/"
			+ imgPath;
	// update image src
	return imgSrc;
}

/**
 * Return a title for a given icon that is acceptable to display (english)
 */
function getWeatherIconTitle(icon) {
	switch (icon) {
	case 'clear-day':
	case 'clear-night':
		return "Clear";
	case 'rain':
		return "Rain";
	case 'snow':
		return "Snow";
	case 'sleet':
		return "Sleet";
	case 'wind':
		return "Wind";
	case 'fog':
		return "Fogg";
	case 'cloudy':
		return "Cloudy";
	case 'partly-cloudy-day':
	case 'partly-cloudy-night':
		return "Partly Cloudy";
	case 'hail':
		return "Hail";
	case 'thunderstorm':
		return "Thunderstorm";
	default:
		return "Unknown";
	}
}

/**
 * Format a time variable
 */
function formatTime(time) {
	var date = new Date(time);
	var hour = date.getHours() + 1;
	var isAm = true;
	var min = "";
	var timeStr = "";

	// Check to see if this is after noon
	if (hour > 12) {
		hour -= 12;
		isAm = false;
	}
	// If noon or midnight, swap am/pm
	if (hour == 12) {
		isAm = !isAm;
	}
	// If it isn't an even hour, add minutes
	if (date.getMinutes() != 0) {
		min = ":" + date.getMinutes();
	}

	// Build the time strings and return
	timeStr += hour;
	timeStr += min;
	if (isAm) {
		timeStr += "am";
	} else {
		timeStr += "pm";
	}

	return timeStr;
}

/**
 * Format the time to return a human readable day
 */
function formatDay(time) {

	var date = new Date(time);
	switch (date.getDay()) {
	case 0:
		return "Sun";
	case 1:
		return "Mon";
	case 2:
		return "Tue";
	case 3:
		return "Wed";
	case 4:
		return "Thu";
	case 5:
		return "Fri";
	case 6:
		return "Sat";
	default:
		return "err";
	}
}



