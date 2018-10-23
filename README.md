# RecommendationSystem

This is a web application implemented with Apache Lucene, Java Spring boot at backend, and AJAX JavaScript at the front end.

Java programming wiki book site (https://en.wikibooks.org/wiki/Java_Programming) is crawled / scraped and indexed through Apache Lucene. Given data set is matched/searched against Java wiki book. Top 10 results are displayed in the web page.

Implementation:
At the backend, to scrape the web pages, JSOUP is used.
Spring boot and Apache Lucene is used to index and run at port 8080 as a REST service.
In a separate port front end server will run and it fetches the data from port 8080 and display it in a web page. 
