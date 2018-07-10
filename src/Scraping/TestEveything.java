package Scraping;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import WebPage.DeviantArt;

public class TestEveything {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() throws IOException 
	{
		//testScrapingProperties("https://rated-r-ponystar.deviantart.com/art/AtN-The-Hooves-Twins-Part-10-719127958");
		//testScrapingProperties_part("https://blackkaries.deviantart.com/art/Twilight-Sparkle-271672604");
		theemaintest("https://ramiras.deviantart.com/art/Two-Sides-of-Melody2-715076940");
		
	}
	
	/**
	 * testing scraping
	 * @throws IOException 
	 */
	public static void testScrapingProperties(String link) throws IOException
	{
		println("testScrapingProperties");
		DeviantArt sp = new DeviantArt(link);
		println(sp);
		println("The web is loaded and we are going to create synapses;");
		println("------------here are all the links we got: ");
		sp.createSynapse();
		println("\n\n\n-------------------We are going to instantiate all the objects-----------\n\n");
		Collection<Scrapable> allwebs = ((Scrapable)sp).getNextWebPages();
		for(Object o : allwebs)println(allwebs);
	}
	
	public static void testScrapingProperties_part(String link) throws IOException
	{
		println("\n\n-----------------testing wether we can get legal file name from url;");
		Scrapable scr = new DeviantArt(link);
		println(scr);
		String s = Scrapable.getFilenameFromScrapable(scr);
		println("This is the file name we got for the scrapable. ");
		println(s);
		
		File f = new File("E:/");
		
		Scrapable.createFileFromScrapable(f, scr);
	}
	
	
	/**
	 * This is a test that is going to test the functionality of the 
	 * scraper class the actual process of dealing 
	 * with the scraping like a recursive data structure. 
	 * @throws IOException 
	 * 
	 */
	public static void theemaintest(String thelink) throws IOException
	{
		println("Testing the totality of the functionalities of the scraper class.");
		Scrapable page = new DeviantArt(thelink);
		File f = new File("E:/TestingScrapable");
		f.mkdirs();
		Scraper scr = new Scraper(page,f,10);
		
		println(scr);
		
		println("Testing the scraping properties of this object. ");
		scr.execute();
		println(scr);
	}
	

	public static void println(Object o)
	{
		System.out.println(o);
	}
}
