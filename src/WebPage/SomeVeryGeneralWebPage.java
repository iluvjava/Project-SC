package WebPage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import Gui.GuiModel;
import Scraping.DownLoader;
import Scraping.Scrapable;
import Scraping.Scraper;


/**
 * 
 * <a href="https://www.w3schools.com/tags/tag_a.asp">
 * Learn about html
 * </a>
 * <br>
 * This web page will not recurse like deviant art, its functionalities on that is disabled. <br>
 * 
 * <p><b>This class do the following:</b></p><br>
 * 
 * 
 * 
 * @author victo
 */
public class SomeVeryGeneralWebPage extends HtmlPage implements Scrapable {

	Collection<String> URLSInterestedIn = new TreeSet<String>();
	// intermediate sink for storing. 
	
	Collection<String> SimiliarWebPages = new TreeSet<String>();
	
	
	public SomeVeryGeneralWebPage(String link) throws IOException 
	{
		
		super(link);
		this.loadPage();
		println("");
	}
	
	
	

	
	/**
	 * Under testing
	 * Extract href attributes in elements.
	 */
	private void extractURL()
	{
		Document doc = this.getDoc();
		
		Elements eles = doc.select("a[href]"); // anchor with attibutes href
		
		for(Element e : eles)
		{
			println(e.absUrl("href"));
		}
		
		
	}
	
	
	@Override
	public Scrapable createSynapse() {
		return null;
	}

	@Override
	public Collection<Scrapable> getNextWebPages() throws IOException {
		return null;
	}

	

	@Override
	public String getSourceContentUrl() {
		return null;
	}
	
	
	@Override
	public boolean pauseAndSkip() 
	{
		return Scraper.Pause;
		
	}


	@Override
	public void doTheScraping(DownLoader dl) 
	{
		
		
	}
	
	
	public static void print(Object o)
	{
		System.out.println(o);
		GuiModel.print(o);
	}
	
	public static void println(Object o)
	{
		System.out.println(o);
		GuiModel.println(o);
	}
	
	
	/**
	 * Return an instance of this class. 
	 * @param URL
	 * @return
	 * null if we can not create such an instance. 
	 */
	public static Scrapable getInstance(String URL)
	{
		return null; 
	}

}
