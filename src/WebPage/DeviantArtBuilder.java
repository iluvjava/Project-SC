package WebPage;

import java.io.IOException;

import Scraping.Scrapable;


/**
 * This is class is designed for buidling instances of scrapable deviant art page. 
 * @author victo
 *
 */
public class DeviantArtBuilder
{
	
	/**
	 * 
	 * @return
	 * A scrapable instance for deviant art web links as a scrapable. <br>
	 * null if the given url is not a deviant art link. 
	 */
	public static Scrapable getInstance(final String DA_url)
	{
		if(!isInDadomain(DA_url))return null; 
		if(isFavoritePage(DA_url))return new DeviantArtFavorite(DA_url);
		try {
			return new DeviantArt(DA_url);
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		return null;
	}
	
	private static boolean isInDadomain(String link)
	{
		boolean result =false;
		result|= link.matches("https://.*deviantart.*");
		result |= link.matches("http://fav.me/.+");
		return result;
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
	private static boolean isFavoritePage(String arg)
	{
		return arg.matches("https://www.deviantart.com/.*/favourites/.*"); 
	}

}
