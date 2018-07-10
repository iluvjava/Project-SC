package WebPage;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.jsoup.Connection;
import org.jsoup.Connection.Request;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * This is a general webpage for JSoup. 
 * 
 * Some of the ways that we can use jsoup to 
 * open a web;
 * 
 * It contains lots of useful static methods. 
 * @author Autistic lycan
 *
 */
public interface WebPage 
{
	
	public Document getDoc();
	public Map<String,String> getCookie();
	public String toString();
	
	public Response getResponse();
	
	
	public WebPage loadPage()throws IOException;
	
	
	/**
	 * return a jsoup connection request. 
	 * @return
	 */
	public Request getRequest();
	
	/**
	 * This will let the jsoup to ignore the content type when 
	 * loading a page. 
	 * 
	 * @param arg
	 * @return
	 */
	public WebPage ignorecontentType(boolean arg);
	
	/**
	 * Let the Jsoup to set the redirect to a boolean.
	 * @param arg
	 * @return
	 */
	public WebPage setRedirect(boolean arg);
	
	/**
	 * Let the jsoup set to a certain redirect link;
	 * should be called before loading the page. 
	 * @param referedlink
	 * @return
	 */
	public WebPage setReferedLink(String referedlink);
	
	public WebPage setTimeout(int milisec);
	
	/**
	 * Gives coolkies to the jsoup, it will be used when the web page is loaded. 
	 * @param cookies
	 * @return
	 */
	public WebPage setRedirectCookie(Map<String,String> cookies);
	
	/**
	 * A boolean tells if the the webpage has been loaded. 
	 * @return
	 */
	public boolean hasPreviouslyLoaded();
	
	/**
	 * If redirect, it should show us the redirect
	 * url
	 * if not, it will return web page when it is constructed. 
	 * @return
	 */
	public String getThispageLink();
	
	
	/**
	 * A bridged method for the Jsoup api: getelementsbyattributes
	 * @param attribute
	 * @return
	 */
	 List<Element> getElementsByAttri(String attribute);
	
	
	/**
	 * 
	 * @return
	 * the jsoup connection of the page. 
	 */
	public Connection getConnection();
	
	static final String[] ALLUSERAGENT={"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36",
			//something weird
			"Mozilla",
			//Windows edge. 
			"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.135 Safari/537.36 Edge/12.246",
			// Ie 11				
			"Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; AS; rv:11.0) like Gecko"					};
	
	
	
	/**
	 * return a random string of user agent. 
	 * @return
	 */
	public static String GetUserAgent()
	{
		return WebPage.ALLUSERAGENT[new Random().nextInt(WebPage.ALLUSERAGENT.length)];
	}
	
	
	/**
	 * One of the primitive redirect methods. 
	 * 
	 * options: 
	 * ignore content: true
	 * redirect status : true
	 * referedlink: the input webpage
	 * cookies: get from the inputwebpage;
	 * @param wp
	 * @return
	 */
	public static WebPage Jumpto(WebPage wp)throws IOException
	{
		return null;
	}
	
	
	
	
	
}
