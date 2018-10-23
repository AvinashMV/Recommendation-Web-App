# RecommendationSystem

This is a web application implemented with Apache Lucene, Java Spring boot at backend, and AJAX JavaScript at the front end.

Java programming wiki book site (https://en.wikibooks.org/wiki/Java_Programming) is crawled / scraped and indexed through Apache Lucene. Given data set is matched/searched against Java wiki book. Top 10 results are displayed in the web page.

Implementation:
At the backend, to scrape the web pages, JSOUP is used.
Spring boot and Apache Lucene is used to index and run at port 8080 as a REST service.
In a separate port front end server will run and it fetches the data from port 8080 and display it in a web page.

Requirements:
1.	Lucene jars - lucene-core-7.5.0.jar, lucene-analyzers-common-7.5.0.jar, lucene-queryparser-7.5.0.jar
2.	Spring boot.
3.	Google chrome and Extension Allow-Control -Allow-Origin.
4.	Atom editor and live server package.

To Run:
Start java spring boot application. This will run the application at http://127.0.0.1:8080. Open the web page index.html from atom editor and run at port 3000. That is http://127.0.0.1:3000/ .

In case of case of an error,
1. Make sure chrome extension Allow-control-allow-origin is installed.
2. Click on CORS extension and add http://127.0.0.1:8080/home to URLS intercepted part and click refresh. In case of error, right click->inspect, click on error and refresh.
3. Add Lucene jars to java spring boot application library. 
