package Scraping;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import Gui.GuiModel;
import WebPage.DeviantArt;

/**
 * Let's together define some methods for the scrapable behaviours.
 * 
 * <ul>
 * <li>There is a string collection that contains all the visited url
 * it can be used as a reference to how many webs has been visisted
 * but it should not be absolute reference. 
 * </ul>
 * @author victo
 *
 */
public interface Scrapable 
{
	
	// an optional thing to help you. 
	// prevents from visting the same web pages repeatedly.
	Collection<String> G_alreadyVistedURL =new HashSet<String>();
	
	
	
	
	/**
	 * Preparing for getting the similiar web sites. 
	 * 1.
	 * 2. collect next web page, it can be more, 
	 * 	or it can be one; or null
	 * 
	 * <ul> 
	 * <li>The scope is: analyzing the websites,
	 * <li>This method is for preparation
	 * <li>You can add the urls to the public field of this interface. So
	 * you implemented class will know the string representation of the websites that 
	 * has visited already. 
	 * <li>
	 * </ul>
	 * 
	 * @throws IOException
	 */
	 Scrapable prepare();
	
	
	/**
	 * Create and return the next collection of web pages.
	 * @return
	 * null if there is non. 
	 * @throws IOException
	 */
	public Collection<Scrapable> getNextWebPages()throws IOException;
	
	
	/**
	 * Return a string: file name<br>
	 * map to an input stream: file content. <br>
	 * Please Don't return null. 
	 * @throws IOException
	 */
	public void doTheScraping(DownLoader dl);

	/**
	 * This is a method will return the url of the object
	 * it will be used for creating a file. <br>
	 * Yes, the source should be the file staight away; it is used for making the name of the file, 
	 * if the return is the tiltle of the web is also ok to do, as long as the 
	 * retun values is unique to each of the web pages. 
	 * <br>
	 * <b>The url should have the prost fix of the file, this is important.</b>
	 * @return
	 */
	public String getSourceContentUrl();
	
	
	/**
	 * This function will be called if the user wants to skip the craping process. 
	 * You should put the following code:
	 * return Scraper.Pause; 
	 */
	public boolean pauseAndSkip();
	
	
	
	/**
	 * This is a method that take in a scrapable object and try to 
	 * get a name for the file using the url return by the object, 
	 * <b>
	 * You should implement it in such a way that the reuturn web site url 
	 * is the same compare to the content downloaded. 
	 * </b>
	 * <p>
	 * This method will tries to read a post fix of the file from the url you have returned. 
	 * @param
	 * @return
	 */
	@Deprecated
	public static String getFilenameFromScrapable(Scrapable scr)
	{
		String arg = scr.getSourceContentUrl();
		
		int temp = arg.lastIndexOf('/');
		
		if(scr instanceof DeviantArt)
		{
			
		}
		
		return arg.substring(temp+1, arg.length());
	}
	
	
	
	
	public static void println(Object o )
	{
		GuiModel.println(o);
		System.out.println(o);
	}
	
	
	
	
}
