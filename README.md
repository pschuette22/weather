# Weather Application built on Google App Engine

This is a web app based on Google App Engine JSP files built to be 

## Setup
This assumes your machine is running apache maven and Java 1.7 (Java 1.8 is fine, but app engine runs in 1.7 environment). Download source and navigate to directory.

	Install pom libraries
	$ mvn clean install

If you would like to deploy your own version to app engine, set up a project in Google App Engine console and update the following tags:
1. Update the `<application>` tag in `src/main/webapp/WEB-INF/appengine-web.xml`
   with your project name.
2. Update the `<version>` tag in `src/main/webapp/WEB-INF/appengine-web.xml`
   with your version name.
3. Update API keys (specifically in weather.jsp -> Google Places/Geolocation API key and ConditionsServlet -> weatherAPI key)

You may now deploy your own version

## Running locally
    $ mvn appengine:devserver

## Deploying
    $ mvn appengine:update


## Bluewolf Project Specifications

1. enter in any location and retrieve current and useful information about the weather in that area, as well as a future forecast.

Enter any location in the places search and appropriate weather info will be retrieved


2. Include charts for historic weather information about that area using any visualization library of your choice 

I chose to use Google charts. I use this library to display hourly temperature change and a pie chart depicting the break down of weather types displayed for the past 10 day/month pairs of the searched date.


3. allowing a user to track his or her search history

This is done using objectify. If the user is signed into their Google Account, their weather searches will be stored. They may navigate to the history page to view these past searches if they desire