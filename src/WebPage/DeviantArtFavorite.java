package WebPage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.WebElement;

import Scraping.Scrapable;
import Scraping.Scraper;
import Selenium.WebDriverBridge;

/**
 * The web could be empty if the user make their favorites private. 
 * @author victo
 *
 */
public class DeviantArtFavorite implements Scrapable
{
	
	public String link;
	public WebDriverBridge driverAPI;
	public Set<String> all_thumb_link;
	
	/**
	 * 
	 * @param url
	 */
	public DeviantArtFavorite(String url)
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
	public Scrapable createSynapse() 
	{
		Scrapable.G_alreadyVistedURL.addAll(all_thumb_link);
		return this;
	}

	@Override
	public Collection<Scrapable> getNextWebPages() throws IOException 
	{
		return null;
	}

	@Override
	public Map<String,InputStream> doTheScraping() throws IOException 
	{
		System.out.println("Colsing web Driver.");
		
		this.driverAPI.close();
		
		
		
		Map<String,InputStream> result = new HashMap<>();
		for(String s : this.all_thumb_link)
		{
			//if(this.pauseAndSkip())break;
			try
			{
				DeviantArt d = new DeviantArt(s);
				result.putAll(d.doTheScraping());
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
		
		return result;
	}

	@Override
	public String getSourceContentUrl() 
	{
		
		return this.driverAPI.getSourceURL();
	}
	
	
	protected void getAllThumbsLinks()
	{
		Set<String> allthumblinks= new HashSet<>();
		try{
			do
			{
				System.out.println("Scrolling down.");
				for(WebElement e : this.driverAPI.getElementsByName("torpedo-thumb-link"))
				{
					String temp = e.getAttribute("href");
					
					System.out.println(temp);
					
					if(Scrapable.G_alreadyVistedURL.contains(temp))continue;
					allthumblinks.add(temp);
				}
			}
			while(this.driverAPI.scrollDown()&&!this.pauseAndSkip());
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		
		this.all_thumb_link = allthumblinks;
		System.out.println("All the Thumb links created: ");
		System.out.println(allthumblinks);
		
	}


	@Override
	public synchronized boolean pauseAndSkip() {
		return Scraper.Pause;
		
	}

}