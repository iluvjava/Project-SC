package WebPage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.WebElement;

import Gui.GuiModel;
import Scraping.DownLoader;
import Scraping.Scrapable;
import Scraping.Scraper;
import Selenium.WebDriverBridge;

/**
 * The web could be empty if the user make their favorites private. 
 * @author victo
 *
 */
public class DeviantArtFavoriteOrGallery implements Scrapable
{
	
	public String link;
	public WebDriverBridge driverAPI;
	protected Set<String> all_thumb_link;
	
	/**
	 * 
	 * @param url
	 */
	public DeviantArtFavoriteOrGallery(String url)
	{
		this.link = url;
		this.driverAPI =new WebDriverBridge();
		this.driverAPI.get(url);
		this.getAllThumbsLinks();
	}
	
	
	/**
	 * ---Casually Tested---<br>
	 * 
	 * Here is an example of a favorite page, it is public. <br>
	 * https://www.deviantart.com/rainbow-highway/favourites/69983545/Featured
	 * @param arg
	 * @return
	 * A boolean represents whether if the given parameter is a url of deviant art favorite page. 
	 */
	@Deprecated
	public static boolean isDomain(String arg)
	{
		return arg.matches("https://www.deviantart.com/.*/favourites/.*"); 
	}

	@Override
	public Scrapable prepare() 
	{
		
		return this;
	}

	@Override
	public Collection<Scrapable> getNextWebPages() throws IOException 
	{
		return null;
	}

	@Override
	public void doTheScraping(DownLoader dl)
	{
		System.out.println("Colsing web Driver.");
		
		this.driverAPI.close();
		
		
		for(String s : this.all_thumb_link)
		{
			// If the web has been visted from the last time; it will be skipped. 
			if(Scrapable.G_alreadyVistedURL.contains(s))continue;
			try
			{
				DeviantArt d = new DeviantArt(s);
				d.doTheScraping(dl);
				
				Scrapable.G_alreadyVistedURL.add(s); // only add if there is not error detected. 
			}
			catch(Error ee)
			{
				ee.printStackTrace();
				continue;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				continue;
			}
		}
		
	}

	@Override
	public String getSourceContentUrl() 
	{
		
		return this.driverAPI.getSourceURL();
	}
	
	
	protected void getAllThumbsLinks()
	{
		GuiModel.setStreamPrintMode(false);
		GuiModel.getProgressBar().setIndeterminate(true);
		
		Set<String> allthumblinks= new HashSet<>();
		try{
			do
			{
				System.out.println("Scrolling down.");
				for(WebElement e : this.driverAPI.getElementsByName("torpedo-thumb-link"))
				{
					String temp = e.getAttribute("href");
					System.out.println(temp);
					allthumblinks.add(temp);
				}
				GuiModel.println("ThumbsLinks: "+allthumblinks.size());
			}
			while(this.driverAPI.scrollDown());
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		GuiModel.setStreamPrintMode(true);
		
		this.all_thumb_link = allthumblinks;
		System.out.println("\nAll the Thumb links are stored: ");
		System.out.println(allthumblinks);
		GuiModel.println("Number of thumblinks: "+allthumblinks.size());
		GuiModel.getProgressBar().setIndeterminate(false);
		
	}

}
