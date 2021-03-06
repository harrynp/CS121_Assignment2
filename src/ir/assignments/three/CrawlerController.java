package ir.assignments.three;

import java.util.concurrent.TimeUnit;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import ir.assignments.three.*;

public class CrawlerController {
	
	public static void main(String[] args) {
		String crawlStorageFolder = "/data/crawl/root";
        int numberOfCrawlers = 8;

        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorageFolder);
//        config.setUserAgentString("UCI Inf141-CS121 crawler 50765033 79422112");
      config.setUserAgentString("test");
        config.setPolitenessDelay(1200);
        config.setResumableCrawling(true);
        config.setMaxDepthOfCrawling(-1);
        //Test only 5 pages
//        config.setMaxPagesToFetch(5);

        /*
         * Instantiate the controller for this crawl.
         */
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        
        try{
        	CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
        	/*
             * For each crawl, you need to add some seed urls. These are the first
             * URLs that are fetched and then the crawler starts following links
             * which are found in these pages
             */
//            controller.addSeed("http://www.ics.uci.edu/~lopes/");
//            controller.addSeed("http://www.ics.uci.edu/~welling/");
            controller.addSeed("http://www.ics.uci.edu/");

            /*
             * Start the crawl. This is a blocking operation, meaning that your code
             * will reach the line after this only when crawling is finished.
             */
            long startTime = System.currentTimeMillis();
            controller.start(Crawler.class, numberOfCrawlers);
            long endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;
            
            System.out.println(String.format("1. %d hours, %d minutes, %d seconds", 
            		TimeUnit.MILLISECONDS.toHours(totalTime),
            		TimeUnit.MILLISECONDS.toMinutes(totalTime) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(totalTime)),
            		TimeUnit.MILLISECONDS.toSeconds(totalTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(totalTime))));
            
        }
        catch(Exception e)
        {
			e.printStackTrace();
        };

        
	}
}