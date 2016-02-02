package ir.assignments.three;

import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.regex.Pattern;

import com.sun.javafx.collections.MappingChange.Map;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import edu.uci.ics.crawler4j.url.WebURL;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Iterator;
import java.util.Scanner;

public class Crawler extends WebCrawler {
    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g|png|tiff?|mid|mp2|mp3|mp4"
			+ "|wav|avi|mov|mpeg|ram|m4v|pdf|ps|rm|smil|wmv|swf"
			+ "|webm|tar|wma|zip?|rar|gz|xz|bz|lz|7z|dmg|xls|xlsx))$");
	public final static Pattern TRAPS = Pattern.compile("^http://(archive|calendar)\\.ics\\.uci\\.edu/.*");
	public final static Pattern DOMAIN = Pattern.compile("^http://.*\\.ics\\.uci\\.edu/.*");
    static ArrayList<String> urlList = new ArrayList<String>();
    
    static String crawlStorageFolder = "/data/crawl/root";
	String textFilesPath = "/data/crawl/root/urlTextFiles";
	static int crawlCount = 0;
	static String longestPageName = "";
	static int longestPageLength = 0;
	static HashMap<String,Integer> freqMap = new HashMap<String,Integer>();
	static TreeSet<Frequency> freqSet = null;
	ArrayList<String> stopList = new ArrayList<String>();
	
	/**
	 * This method is for testing purposes only. It does not need to be used
	 * to answer any of the questions in the assignment. However, it must
	 * function as specified so that your crawler can be verified programatically.
	 * 
	 * This methods performs a crawl starting at the specified seed URL. Returns a
	 * collection containing all URLs visited during the crawl.
	 */
	public static Collection<String> crawl(String seedURL) {
		// TODO implement me
        int numberOfCrawlers = 1;

        CrawlConfig config = new CrawlConfig();
        //config.setCrawlStorageFolder(crawlStorageFolder);
        config.setUserAgentString("UCI Inf141-CS121 crawler 50765033");
        config.setPolitenessDelay(600);

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
            //controller.addSeed("http://www.ics.uci.edu/~lopes/");
            //controller.addSeed("http://www.ics.uci.edu/~welling/");
            controller.addSeed("http://www.ics.uci.edu/");

            /*
             * Start the crawl. This is a blocking operation, meaning that your code
             * will reach the line after this only when crawling is finished.
             */
            controller.start(Crawler.class, numberOfCrawlers);
            
        }
        catch(Exception e)
        {
        	
        };

        
		
		return urlList;
	}
	
	public void onStart() {
		File file = new File(textFilesPath);
		file.mkdirs();
		System.out.println("onStart");
		
		File stopFile = new File("stopwords.txt");
		stopList = Utilities.tokenizeFile(stopFile);
	}
	
	public void onBeforeExit()
	{
		createAnswerFile();
        freqSet = getSortedFrequencies(freqMap);
        createCommonWordFile(freqSet);   
	}
	
	@Override
	public void visit(Page page) {
	    String url = page.getWebURL().getURL();
	    urlList.add(url);
	    
	    System.out.println("\nURL: " + url);
	    System.out.println("ID: " + url.hashCode());
	   
	
	    if (page.getParseData() instanceof HtmlParseData) {
	        HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
	        String text = htmlParseData.getText();
	        String html = htmlParseData.getHtml();
	        Set<WebURL> links = htmlParseData.getOutgoingUrls();
	
	        System.out.println("Text length: " + text.length());
	        System.out.println("Html length: " + html.length());
	        System.out.println("Number of outgoing links: " + links.size());
	        
	        //Create text file and store contents of page in it
	        File file = createUrlFile(url,text, 0);
	        
	        ArrayList<String> tokenList = Utilities.tokenizeFile(file);
	        
	        if(tokenList.size() > longestPageLength)
	        {
	        	longestPageName = url;
	        	longestPageLength = tokenList.size();
	        }
	        
	        //I chose to store the token frequencies in a map first because checking for existing words, adding new ones
	        //	or incrementing are all O(1) operations
	        for(String token : tokenList) {
	        	if(!stopList.contains(token)) {
	        		Integer count = freqMap.get(token);
		        	if(count == null) {
		        		freqMap.put(token, 1);
		        	}
		        	else {
		        		freqMap.put(token, ++count);
		        	}
	        	}
	        }
	        
			//TESTING freqMap --------------------------------------------------------
	        //createAnswerFile();
	        //freqSet = getSortedFrequencies(freqMap);
	        //createCommonWordFile(freqSet);   
	        //-------------------------------------------------------------------------
	       System.out.println("Map size: " + freqMap.size());
	       System.out.println("crawlCount: " + ++crawlCount);
	    }
	}
	
	
	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        return !FILTERS.matcher(href).matches()
//               && href.startsWith("http://www.ics.uci.edu/")
               && !TRAPS.matcher(href).matches()
               && DOMAIN.matcher(href).matches();
	}
	
	//recursive creates a file with a unique file name if a collision occurs. Returns pathname
	File createUrlFile(String url, String text, int count) {
		 String fileName = String.valueOf(url.hashCode() + "_" + count + ".txt");
	        File textFile = new File(textFilesPath + '/' + fileName);
	        try {
				if(textFile.createNewFile())
				{
					PrintWriter writer = new PrintWriter(textFile);
					writer.println(url);
					writer.println(text);
					
					writer.close();
					
					return textFile;
				}
				else
				{
					return createUrlFile(url, text, ++count);
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        return null;
	}
	
	private static TreeSet<Frequency> getSortedFrequencies(HashMap<String,Integer> freqMap) {
        TreeSet<Frequency> freqSet = new TreeSet<Frequency>(frequencySetComparator);
        
        if(freqMap == null){
            return freqSet;
        }
        
        Set<String> keySet = freqMap.keySet();
        
        for(String word : keySet) {
        	Frequency freq = new Frequency(word, freqMap.get(word));
        	freqSet.add(freq);
        }
        
		return freqSet;
	}
	
	private static Comparator<Frequency> frequencySetComparator = new Comparator<Frequency>(){
		public int compare(Frequency freq1, Frequency freq2){
			String textCount1 = freq1.getText().toUpperCase();
			String textCount2 = freq2.getText().toUpperCase();
			
			//If the words are the same we consider them equal in value regardless of frequency. 
			//	This will make it so no frequencies with duplicate words are added to the set.
			if(textCount1.compareTo(textCount2) == 0){
				return 0;
			}
			
			Integer freqCount1 = freq1.getFrequency();
			Integer freqCount2 = freq2.getFrequency();
			
			if(freqCount1.compareTo(freqCount2) == 0){
				return textCount1.compareTo(textCount2);
			
			}
			else {
				return freqCount1.compareTo(freqCount2) * (-1);
			}
		
		}  
	};
	
	public static void createAnswerFile() {
		System.out.println("LongestPage: " + longestPageName);
		System.out.println("	word cound: " + longestPageLength);
		System.out.println("visited pages: " + urlList.size());
		File answerFile = new File(crawlStorageFolder + "/Answers.txt");

		try {
			PrintWriter writer = new PrintWriter(answerFile);
			writer.println("Question 1: How much time did it take to crawl the entire domain?");
			writer.println("\t  Not finished");
			writer.println("Question 2: How many unique pages did you find in the entire domain?");
			writer.println("\t" + urlList.size());
			writer.println("Question 4: What is the longest page in terms of number of words? ");
			writer.println("\t" + longestPageName);
			
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void createCommonWordFile(TreeSet<Frequency> freqSet){
		File commonFile = new File(crawlStorageFolder + "/CommonWords.txt");
		try {
			PrintWriter writer = new PrintWriter(commonFile);
			
			int wordCount = 0;
			for(Frequency freq : freqSet) {
				writer.println(freq);
				
				wordCount++;
				if(wordCount >= 500){
					break;
				}
			}
			
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
