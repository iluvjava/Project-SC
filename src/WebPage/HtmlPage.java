package WebPage;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Request;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * use the interface to create a web page. 
 * Can not be instiated, all methods from interface 
 * is implemented using Jsoup. 
 * <br>
 * Adding some of the basic features of
 * a html parser should have.
 * <br>
 * <ul>
 * <li>Getting the titile of the webs</li>
 * 
 * </ul>
 * @author autistic lycan
 *
 */
public abstract class HtmlPage implements WebPage
{
	
	// we can create a page with a referred http
	public String G_referedLink;
	
	// the cookies for connecting to this page. 
	protected Map<String,String> G_cookiesforupload;
	
	protected boolean G_ignorecontenttype = true;
	
	protected boolean G_redirect = false; 
	
	
	//**********************************
	
	protected Document G_ThispageContent;	
	
	protected Response G_response;
	
	protected String G_ThisPageURL;
	
	protected Connection G_thispageconnection;
	
	protected Map<String,String> G_cookies;
	
	protected int G_timeout = 10000;
	
	protected Request G_thispagerequest;
	
	//**********************************************
	
	private boolean G_haveloaded = false; 
	
	
	
	
	/**
	 * A web page can be created with a many ways, 
	 * so we won't really do 
	 * any thing except setting up things in the 
	 * constractor 
	 */
	public HtmlPage(String link)
	{
		this.G_ThisPageURL = link; 
	}
	
	/**
	 * Load the page according to the options we have
	 * set up. 
	 * 
	 */
	public WebPage loadPage() throws IOException
	{
		Connection con = Jsoup.connect(this.G_ThisPageURL);
		
		this.G_thispageconnection = con; 
		
		con.userAgent(WebPage.GetUserAgent());
		con.timeout(G_timeout);
		con.ignoreContentType(G_ignorecontenttype);
		con.followRedirects(G_redirect);
		
		if(this.G_cookiesforupload!=null)
		{
			con.cookies(this.G_cookiesforupload);
		}
		if(this.G_referedLink!=null)
		{
			con.referrer(G_referedLink);
		}
		
		
		
		this.G_response = con.execute();
		this.G_thispagerequest = con.request();
		this.G_ThispageContent = this.G_response.parse();
		
		this.G_cookies = this.G_response.cookies();
		
		this.G_haveloaded = true; 
		
		
		return this;
	}

	public Document getDoc() {
		
		return this.G_ThispageContent;
	}

	@Override
	public Map<String, String> getCookie() {
		return this.G_cookies;
	}

	@Override
	public Response getResponse() {
		return this.G_response;
	}

	@Override
	public Request getRequest() {
		return this.G_thispagerequest;
	}
	
	public Connection getConnection()
	{
		return this.G_thispageconnection; 
	}
	
	public String getThispageLink()
	{
		
		if(this.G_response!=null)return this.G_response.url().toString();
		return this.G_ThisPageURL;
		
	}

	public WebPage setRedirect(boolean arg)
	{
		this.G_redirect = arg;
		return this;
	}
	
	
	/**
	 * Set the cookies for this page, so the
	 * page will upload cookies while created. 
	 * @return
	 */
	public WebPage setRedirectCookie(Map<String,String> cookies)
	{
		this.G_cookiesforupload = cookies; 
		return this; 
	}

	/**
	 * set the field of the class. 
	 * when the url is load, it will
	 * resquest it with this refered link;
	 * @param referedlink
	 * @return
	 */
	public WebPage setReferedLink(String referedlink)
	{
		if(referedlink == null)return this;
		this.G_referedLink = referedlink;
		return this;
	}

	public WebPage setTimeout(int milisec)
	{
		this.G_timeout = milisec; 
		return this; 
	}

	/**
	 * Let jsoup ignore the content type. 
	 * @param arg
	 * @return
	 */
	public WebPage ignorecontentType(boolean arg)
	{
		this.G_ignorecontenttype = arg;
		return this;
	}

	public boolean hasPreviouslyLoaded()
	{
		return this.G_haveloaded;
	}

	public String toString()
	{
		String s = "\n----------" + this.getClass()+"-----------\n";
		s+="This is the url of the page: \""+this.G_ThisPageURL+"\"\n";
		if(this.G_haveloaded)s+="This page has called and loaded.\n";
		if(this.G_ignorecontenttype)s+="Ignore Content Type enable.\n";
		if(this.G_cookies!=null&&!this.G_cookies.isEmpty())s+="This is the page cookies: "+ this.G_cookies.toString()+"\n";
		if(this.G_cookiesforupload!=null&&!this.G_cookiesforupload.isEmpty())
			s+= "This is the cookies for upload: "+ this.G_cookiesforupload.toString()+"\n";
		if(this.G_referedLink!=null)s+="This is the refered link(for upload): "+ this.G_referedLink;
		if(this.G_haveloaded)s+="This is the respose code: "+ this.G_response.statusCode()+"\n";
		return s;
	}
	
	
	/**
	 * Under unit Testing. .... 
	 * This method is partially working. it applies to some of the website that got 
	 * redirected. 
	 * 
	 * 
	 * Conditions for two equaled web pages: 
	 * 1. If the web pages are loaded, we compare the weblink after redirect.
	 * 2. If any one of the web pages are not loaded yet, we compare the string that 
	 * used to initaited the web page. 
	 * @return
	 */
	public boolean equals(Object o)
	{
		if(!(o instanceof HtmlPage))
		{
			return false; 
		}
		HtmlPage p = (HtmlPage) o ; 
		if(this.hasPreviouslyLoaded() && p.hasPreviouslyLoaded())
		{
			return p.G_response.url().toString().equals(this.G_response.url().toString());
		}
		return this.G_ThisPageURL.equals(p.G_ThisPageURL);
	}
	
	
	public List<Element> getElementsByAttri(String attribute)
	{
		return this.getDoc().getElementsByAttribute(attribute);
	}
	
	
}
