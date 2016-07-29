<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ page import="com.googlecode.objectify.ObjectifyService"%>
<%@ page import="com.pschuette.weather.data.Search"%>

<%@ page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Weather | Search History</title>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet" href="assets/css/main.css" />

<script src="assets/js/jquery.min.js"></script>
<script src="assets/js/jquery.dropotron.min.js"></script>
<script src="assets/js/skel.min.js"></script>
<script src="assets/js/skel-viewport.min.js"></script>
<script src="assets/js/util.js"></script>
<!-- [if lte IE 8]><script src="assets/js/ie/respond.min.js"></script><![endif] -->
<script src="assets/js/main.js"></script>

</head>

<body>

	<div id="page-wrapper">

		<!-- Header -->
		<header id="header">
		<div class="logo container">
			<div>
				<a href="/">
					<h1 id="logo">Groovy Weather</h1>
					<p>by SkyCast inc.</p>
				</a>
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
							<h2>Weather Search History</h2>
							</header>

							<!-- Hold onto user if they are logged in -->
							<%
								UserService userService = UserServiceFactory.getUserService();
								User user = userService.getCurrentUser();
								String listInnerHTML = "";
								if (user != null) {
									pageContext.setAttribute("user", user);

									// Query for the searches and build the search history
									// Only display last 50
									List<Search> searches = ObjectifyService.ofy().load().type(Search.class)
											.filter("email =", user.getEmail()).order("-date").limit(50).list();
									pageContext.setAttribute("searches", searches);

									if (searches.isEmpty()) {
										System.out.println("Searches empty");
									} else {
										System.out.println("Search count: " + searches.size());
									}
							%>
							<p>
								Hello, ${fn:escapeXml(user.nickname)}! Here's the weather
								locations you've searched. (You can <a
									href="<%=userService.createLogoutURL(request.getRequestURI())%>">sign
									out</a>.)
							</p>

							<table style="width: 100%">
								<tr>
									<th>Date</th>
									<th>Place</th>
									<th>Latitude</th>
									<th>Longitude</th>
								</tr>
								<c:forEach items="${searches}" var="search">
									<tr>
										<td>${fn:escapeXml(search.date)}</td>
										<td>${fn:escapeXml(search.placeTitle)}</td>
										<td>${fn:escapeXml(search.latitude)}</td>
										<td>${fn:escapeXml(search.longitude)}</td>
									</tr>
								</c:forEach>

							</table>
							<%
								} else {
							%>
							<p>
								Hello! <a
									href="<%=userService.createLoginURL(request.getRequestURI())%>">Sign
									in</a> to display search history.
							</p>
							<%
								}
							%>
						</div>
					</div>
				</div>
			</div>



		</div>
</body>
</html>