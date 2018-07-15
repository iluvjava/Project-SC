package WebPage;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
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

	protected static Set<String> targetFilePostFix = new TreeSet<>();
	
	
	static
	{
		 addFilePostFix("pdf","epub","txt","html","jpg","png","jpeg","gif","mp4","mp3","mkv","wav","ogg");
	}
	
	
	public static void addFilePostFix(String... args)
	{
		for(String s: args)
			targetFilePostFix.add(s);
	}
	
	
	
	Collection<String> URLSInterestedIn = new TreeSet<String>();
	// intermediate sink for storing. 
	
	
	
	
	/**
	 * This constructor should only be used internally. 
	 * @param link
	 * @throws IOException
	 */
	protected SomeVeryGeneralWebPage(String link) throws IOException 
	{
		
		super(link);
		this.loadPage();
		println("Som very general page <"+this.getTitle()+" >is created. ");
	}
	
	
	

	
	/**
	 * --Casually Tested---<br>
	 * Under testing
	 * Extract href attributes in elements.
	 */
	protected void extractURL()
	{
		GuiModel.setStreamPrintMode(false);
		
		Document doc = this.getDoc();
		
		Elements eles = doc.select("a[href]"); // anchor with attribute href
		
		List<String> extracted = new LinkedList<>();
		
		for(Element e : eles)
		{
			
			println(e.absUrl("href"));
			extracted.add(e.absUrl("href"));
		}
		
		
		println("Filtering out the links using file postfix...... ");
		//Filter out the useful refered links;
		
		Outer:
		for(Iterator<String> itr = extracted.iterator();itr.hasNext();)
		{
			String link = itr.next();
			if(Scrapable.G_alreadyVistedURL.contains(link))
			{
				itr.remove();continue;
			}
			
			Scrapable.G_alreadyVistedURL.add(link);
			for(String postfix : targetFilePostFix)
			{
				if(link.matches(".*\\."+postfix))continue Outer;
			}
			itr.remove();
		}
		
	
		
		for(String s : extracted)println(s);
		
		this.URLSInterestedIn = extracted;
		
		GuiModel.setStreamPrintMode(true);
		
	}
	
	
	@Override
	public Scrapable prepare() 
	{
		
		this.extractURL();
		return null;
	}

	
	@Override
	public Collection<Scrapable> getNextWebPages() throws IOException 
	{
		
		// This class is not supporting this particular funcationality. 
		return null;
	}

	

	@Override
	public String getSourceContentUrl() 
	{
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
		for(String s : this.URLSInterestedIn)
		{
			
			String filename = s.substring(s.lastIndexOf("/")+1, s.length());
			if(dl.fileAlreadyExist(filename))
			{
				println("File: "+filename+" already exists, I won't download. ");
				continue; 
			}
			try 
			{
				URL url = new URL(s);
				
				InputStream IPstream = new BufferedInputStream(url.openStream());
				
				ByteArrayOutputStream bis = new ByteArrayOutputStream();
				
				int len=0;
				for(byte[] temp = new byte[10000];(len = IPstream.read(temp))!=-1;)
				{
					bis.write(temp,0,len);
				}
				IPstream.close();
				
				InputStream result = new ByteArrayInputStream(bis.toByteArray());
				bis.close();
				
				
				dl.forwardFile(filename,result);
			}
			catch ( Exception e) 
			{
				println("File : "+s+" Openstream failed.");
				e.printStackTrace();
				
			}
			
		}
		
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
		try
		{
			return new SomeVeryGeneralWebPage(URL);
		}
		catch (Exception e) 
		{
			println("Failed to create an General web page for the following URL: ");
			println(URL);
		}
		return null; 
	}

}
