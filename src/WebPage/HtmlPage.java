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
	public String referedLink;
	
	// the cookies for connecting to this page. 
	protected Map<String,String> cookiesforupload;
	
	protected boolean ignorecontenttype = true;
	
	protected boolean redirect = false; 
	
	
	//**********************************
	
	protected Document thispagecontent;	
	
	protected Response response;
	
	protected String thispageURL;
	
	protected Connection thispageconnection;
	
	protected Map<String,String> cookies;
	
	protected int timeout = 10000;
	
	protected Request thispagerequest;
	
	//**********************************************
	
	private boolean haveloaded = false; 
	
	
	
	
	/**
	 * A web page can be created with a many ways, 
	 * so we won't really do 
	 * any thing except setting up things in the 
	 * constractor 
	 */
	public HtmlPage(String link)
	{
		this.thispageURL = link; 
	}
	
	/**
	 * Load the page according to the options we have
	 * set up. 
	 * 
	 */
	public WebPage loadPage() throws IOException
	{
		Connection con = Jsoup.connect(this.thispageURL);
		
		this.thispageconnection = con; 
		
		con.userAgent(WebPage.GetUserAgent());
		con.timeout(timeout);
		con.ignoreContentType(ignorecontenttype);
		con.followRedirects(redirect);
		
		if(this.cookiesforupload!=null)
		{
			con.cookies(this.cookiesforupload);
		}
		if(this.referedLink!=null)
		{
			con.referrer(referedLink);
		}
		
		
		
		this.response = con.execute();
		
		this.thispagerequest = con.request();
		
		this.thispagecontent = this.response.parse();
		
		this.cookies = this.response.cookies();
		
		this.haveloaded = true; 
		
		
		return this;
	}

	public Document getDoc() {
		
		return this.thispagecontent;
	}

	@Override
	public Map<String, String> getCookie() {
		return this.cookies;
	}

	@Override
	public Response getResponse() {
		return this.response;
	}

	@Override
	public Request getRequest() {
		return this.thispagerequest;
	}
	
	public Connection getConnection()
	{
		return this.thispageconnection; 
	}
	
	public String getThispageLink()
	{
		
		if(this.response!=null)return this.response.url().toString();
		return this.thispageURL;
		
	}

	public WebPage setRedirect(boolean arg)
	{
		this.redirect = arg;
		return this;
	}
	
	
	/**
	 * Set the cookies for this page, so the
	 * page will upload cookies while created. 
	 * @return
	 */
	public WebPage setRedirectCookie(Map<String,String> cookies)
	{
		this.cookiesforupload = cookies; 
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
		this.referedLink = referedlink;
		return this;
	}

	public WebPage setTimeout(int milisec)
	{
		this.timeout = Math.abs(milisec); 
		return this; 
	}

	/**
	 * Let jsoup ignore the content type. 
	 * @param arg
	 * @return
	 */
	public WebPage ignorecontentType(boolean arg)
	{
		this.ignorecontenttype = arg;
		return this;
	}

	public boolean hasPreviouslyLoaded()
	{
		return this.haveloaded;
	}

	public String toString()
	{
		String s = "\n----------" + this.getClass()+"-----------\n";
		s+="This is the url of the page: \""+this.thispageURL+"\"\n";
		if(this.haveloaded)s+="This page has called and loaded.\n";
		if(this.ignorecontenttype)s+="Ignore Content Type enable.\n";
		if(this.cookies!=null&&!this.cookies.isEmpty())s+="This is the page cookies: "+ this.cookies.toString()+"\n";
		if(this.cookiesforupload!=null&&!this.cookiesforupload.isEmpty())
			s+= "This is the cookies for upload: "+ this.cookiesforupload.toString()+"\n";
		if(this.referedLink!=null)s+="This is the refered link(for upload): "+ this.referedLink;
		if(this.haveloaded)s+="This is the respose code: "+ this.response.statusCode()+"\n";
		return s;
	}
	
	
	/**
	 * Under unit Testing. .... 
	 * This method is partially working. it applies to some of the website that got 
	 * redirected. 
	 * 
	 * 
	 * Conditions for two equaled web pages: <br>
	 * 1. If the web pages are loaded, we compare the web link after redirect.<br>
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
			return p.response.url().toString().equals(this.response.url().toString());
		}
		return this.thispageURL.equals(p.thispageURL);
	}
	
	
	public List<Element> getElementsByAttri(String attribute)
	{
		return this.getDoc().getElementsByAttribute(attribute);
	}
	
	
	
	/**
	 *--- Method carfully Tested --- <br>
	 * 
	 * if this page has been loaded, the method will return the tile of the page, if not 
	 * it will return a the url of this page. 
	 * @return
	 */
	public String getTitle()
	{
		if(this.getDoc()!=null)return this.getDoc().getElementsByTag("title").text();
		return "Title null, webURL: "+this.thispageURL;
	}
	
	
}
