package ir.assignments.three;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import ir.assignments.three.*;

public class CrawlTest {
	
	public static void main(String[] args) {

        /*
         * Start the crawl. This is a blocking operation, meaning that your code
         * will reach the line after this only when crawling is finished.
         */
		Collection<String> urlList = Crawler.crawl(args[0]);
		for (String url : urlList){
			System.out.println(url);
		}
		System.out.println("Total URLS: " + urlList.size());
	}
}