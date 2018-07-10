package WebPage;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestEverything {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() throws IOException 
	{
		//testingDA("https://rainbow-highway.deviantart.com/art/The-Jewey-674978328");
		
//		testEqualsofHtmlPage();
		
//		testinggernalwebpages();
		
		testDeviantArtFavorite();
	}
	/**
	 * testing da basic functionality: 
	 * 1. loading the page
	 * 2. getting download link.
	 * @param dalink
	 * @throws IOException 
	 */
	public static void testingDA(String dalink) throws IOException
	{
		println("\n------------------Testing da--------------");
		WebPage wp =new DeviantArt(dalink);
		//wp.loadPage();
		println(wp);
		println("Trying to get image link: ");
		
		println(((DeviantArt)wp).getDAMainImag());
	}

	public static void println(Object o)
	{
		System.out.println(o);
	}
	
	/**
	 * Test the equal method of html page; 
	 * @throws IOException 
	 */
	public static void testEqualsofHtmlPage() throws IOException
	{
		HtmlPage google = new Page("https://www.google.com");
		HtmlPage google2 = new Page("https://www.google.com");
		google.loadPage(); 
		google2.loadPage();
		println("google has previously loaded: "+ google.hasPreviouslyLoaded());
		println("Google 2 has previously loaded: "+ google2.hasPreviouslyLoaded());
		println("The response url for google is: "+ google.getResponse().url().toString());
		println("The respose url for google2 is: "+ google2.getResponse().url().toString());
		assertTrue(google.equals(google2));
		
		println("\n\n------testing webpages that involves redirect properties--------");
		HtmlPage page1 = new Page("http://fav.me/dbwejdv");
		page1.setRedirect(true);
		println("Page1 url before loading: "+null);
		page1.loadPage();
		println("Page1 url after loading: "+page1.getResponse().url().toString());
		
		page1 = new Page("http://www.baidu.com/link?url=fKdhPBINM-IAD87S2qeRCgBxVnMZOSR7ReOG7wL4_MGhQP6pDigNOhhN0UIp1R2c&wd=&eqid=9bf7ccdc00004f49000000065a35ddcf");
		println("This is the url before redirct: "+ page1.getThispageLink());
		println("THis is the page after redirect: "+ page1.setRedirect(true).loadPage().getResponse().url().toString());
	}
	
	
	
	public static void testinggernalwebpages() throws IOException
	{
		SomeVeryGeneralWebPage p = new SomeVeryGeneralWebPage("https://www.w3schools.com/tags/tag_a.asp");
	}
	
	public static void testDeviantArtFavorite() throws IOException
	{
		println("----Testing if the given link is a DA favourite link.---");
		String url = "https://www.deviantart.com/sarmateppou/favourites/";
		println(url);
		println(DeviantArtFavorite.isDomain(url));
		
	}
	
	
	
}
