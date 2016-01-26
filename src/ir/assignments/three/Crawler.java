package ir.assignments.three;

import java.util.Collection;
import java.util.Set;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class Crawler extends WebCrawler {
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
		
		
		
		return null;
	}
	
	@Override
	public void visit(Page page) {
	    String url = page.getWebURL().getURL();
	    System.out.println("\nURL: " + url);
	    System.out.println("ID: " + url.toString().hashCode());
	
	    if (page.getParseData() instanceof HtmlParseData) {
	        HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
	        String text = htmlParseData.getText();
	        String html = htmlParseData.getHtml();
	        Set<WebURL> links = htmlParseData.getOutgoingUrls();
	
	        System.out.println("Text length: " + text.length());
	        System.out.println("Html length: " + html.length());
	        System.out.println("Number of outgoing links: " + links.size());
	    }
	}
	
	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
	    String href = url.getURL().toLowerCase();
	    // Only accept the url if it is in the "www.ics.uci.edu" domain and protocol is "http".
	    return href.startsWith("http://www.ics.uci.edu/");
	    
	}
	
}
