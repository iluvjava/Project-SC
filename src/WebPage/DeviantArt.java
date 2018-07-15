package WebPage;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import Gui.GuiModel;
import Scraping.DownLoader;
import Scraping.Scrapable;
import Scraping.Scraper;

/**
 * Restructure my old code.
 * 
 * <p>
 * Refine the behaviour defined by the interface, include exception. 
 * @author autistic lycan
 * @param <T>
 */
public final class DeviantArt<T> extends HtmlPage implements Scrapable{

	private Collection<String> G_nextPages = new TreeSet<String>(); // A string representaion of all the pages that are on this page. 
	String G_theimage; // the image link of this page. 
	private Collection<Scrapable> G_moreAD ;// all the others pages that is one this pages
	byte[] theimgbytearray;// the image in byte array. 
	
	
	
	/**
	 * <p>
	 * This class will do the following things currently
	 * <ul>
	 * <li>1. It will load a page that has the deviant art as its 
	 * domain name
	 * <li>2. it will get the image of this page and loaded it 
	 * in the RAM as byte array, it will be returned by 
	 * a method in the scrapable.
	 * </ul>
	 * 
	 * 
	 *<p>there are many exceptions in this class need to be handled
	 *
	 * @param link
	 * 			The deviant art page
	 * @throws IOException 
	 * @throws IllegalArgumentException
	 *
	 * 		
	 * IllegalArumentException if the link isn't under the domain name
	 * of deviant art. 
	 */
	public DeviantArt(String link,String referedlink) throws IOException 
	{
		super(link);
		if(!DeviantArt.isInDadomain(link))throw new IllegalArgumentException();
		if(referedlink!=null)this.setReferedLink(referedlink);
		this.ignorecontentType(false);
		this.setRedirect(true); //recentely added. 
		this.loadPage();
		this.G_theimage = this.getDAMainImag();
		
	}
	
	public DeviantArt(String link) throws IOException
	{
		this(link, null);
	}
	
	

	/**
	 * United tested
	 * 
	 * This method search for reachable links in the description of the 
	 * deviantart page. 
	 * It search for: 
	 * 1. links: fav.me
	 * 2. Links with the same artist name on the page. 
	 * @return
	 * 
	 * A list of links. 
	 */
	private List<String> getLinksinDescription()
	{
		
		ArrayList<String> astrlist = new ArrayList<String>();
		
		Elements ele = this.getDoc().select(".dev-description")
				.select(".text-ctrl")
				.select(".text.block");
			
			
			for(Element e :ele.select("a[href]"))
			{
				astrlist.add(e.attr("abs:href"));
			}
		
			String artistname = this.getArtistName().toLowerCase();
			
			
			Iterator<String> itr =astrlist.iterator();
			
		while(itr.hasNext())
		{
			
			String temp = itr.next();
			if(temp.contains("fav.me"))
			{
				// don't remove
			}
			else if(!temp.contains("deviantart")||!temp.contains(artistname))
			{
				itr.remove();
			}
		}
		return astrlist;
	}
	
	
	private String getArtistName()
	{
		Elements eles = this.getDoc().select(".dev-title-container h1 small")
				.select("span.username-with-symbol");
			
		String result = eles.text();
		
		if(result.length()>0)
			return result;
		else
		{return "";}
	}
	
	/** 
	 * This method retrieve links on the right column of the page, 
	 * the first nine links.
	 * and links in the comments sections.
	 * 
	 * @return
	 * A set of links on the right side column.
	 */
	private Set<String> getMoreDA()
	{
		
		List<String> morelinks = this.Sub_getMoreDA();
		
		//add some new things to the collection from the super class! 
		
		List<String> slist = this.getLinksinDescription();
		
		Set<String> linkset = new TreeSet<String>(slist);
		
		linkset.addAll(morelinks); 
		linkset.addAll(slist);
		
//		println("getMoreDA method return: ");
//		println(linkset);
		
		return linkset;
		
	}
	
	
	/**
	 * Throughly tested.
	 * @return
	 */
	private List<String> Sub_getMoreDA()
	{
		Document arg = this.getDoc();
		ArrayList<String > slist = new ArrayList<String>();
		Elements eles = arg.select("div.tinythumb");
		int c = 0;
		for(Element e : eles)
		{
			if(c++==9)break;
			slist.add(e.parent().attr("href"));
		}
		
		
		return slist;
	}

	public String getHighQualityDownloadImage()throws MalformedURLException, IOException
	{
		String downloadlink = getDownloadlink(this.getDoc());
		if(downloadlink == null)
		{
			println("High quality image link is not there. ");
			return null;
		}
		
		WebPage wp =new Page(downloadlink);
		
		wp.setReferedLink(this.getThispageLink());
		wp.setRedirectCookie(this.getCookie());
		wp.ignorecontentType(true);
		wp.setRedirect(true);
		wp.loadPage();
		
		println(this.getTitle()+"---Download status---"+ wp.getResponse().statusCode());
		
		//if(wp.getResponse().statusCode()!=200)return null;
		
		return wp.getThispageLink();
	}

	/**
	 * Tries to get the high quality image links. 
	 * if not there, it will return image on the page. 
	 * @return
	 * String that is the link to the page. 
	 * @throws IOException
	 */
	public String getDAMainImag() throws IOException
	{
		String highquality = this.getHighQualityDownloadImage();
		 if(highquality!=null)
		 {
			 return new URL(highquality).toString();
		 }
		 else
		 {
			Elements eles = this.getDoc().getAllElements();
			eles = eles.select(".dev-content-full");
			for(Element e : eles)
			{
		
				println("Getting mainimage "+e.baseUri());
				println("main Img: "+e.attr("abs:src"));
				return new URL(e.attr("abs:src")).toString();
			}
		 }
		return null;
	}

	@Override
	/**
	 * UnderTested
	 * 
	 * @return
	 * The instance of this object. 
	 * 
	 */
	public Scrapable prepare() 
	{
		GuiModel.setStreamPrintMode(false);
		println("Creating synapses: ");
		
		for(String s : this.getMoreDA())
		{
			// only tries to visited the unvisited pages. 
			if(!Scrapable.G_alreadyVistedURL.contains(s))this.G_nextPages.add(s);
			
			Scrapable.G_alreadyVistedURL.add(s); //add anyway because it is a set. 
			
			println(s);
		}
		//println("All next stage pages are added to the field");
		//println(this.G_nextPages);
		GuiModel.setStreamPrintMode(true);
		return this;
	}

	@Override
	/**
	 * Unit Tested
	 * This is a method that will return a buffered out put stream that 
	 * can be prepared for downloading the content; 
	 * @Throw MalformedURLexception.
	 */
	public void doTheScraping(DownLoader dl) 
	{
		if(this.theimgbytearray!=null)
		{
			Map<String, InputStream> result = new HashMap<>();
			result.put
			(
					Scrapable.getFilenameFromScrapable(this),
					new ByteArrayInputStream(this.theimgbytearray)
			);
			
			for(Entry<String, InputStream> en:result.entrySet())
			{
				if(dl.fileAlreadyExist(en.getKey()))
					{
						println("The file is already there, I refuse to download. ->"+en.getKey());
						continue;
					}
				dl.forwardFile(en.getKey(), en.getValue());
			}
			return ;
		}
		
		String sourcelink = this.G_theimage;
		
		try
		{
		
			String filename = Scrapable.getFilenameFromScrapable(this);
			
			if(dl.fileAlreadyExist(filename))
			{
				println("The file is already there, I refuse to download. ->"+filename);
				return;
			}
			
			URL url = new URL(sourcelink);
			
			InputStream stream = new BufferedInputStream(url.openStream());
			
			ByteArrayOutputStream bis = new ByteArrayOutputStream();
			
			// reading from URL and forward result.
			
				int len =0;
				for(byte[] arr = new byte[2048*8];(len = stream.read(arr))!=-1;)
				{
					bis.write(arr,0,len);
				}
				stream.close();
				
				InputStream fileinputstream = new ByteArrayInputStream(bis.toByteArray());
				
				this.theimgbytearray = bis.toByteArray();
				
				bis.close();
			
				dl.forwardFile(filename, fileinputstream);
		
		}catch (Exception e) {
			e.printStackTrace();
			
		}
		
	}

	public String toString()
	{
		String s=super.toString();
		s+=this.G_theimage!=null?"This DA is associated with image link: "+this.G_theimage+'\n'
				:
				"this DA is not associated with any image.";
		return s; 
	}

	@Override
	/**
	 * 
	 * Unit Tested 
	 * this function will call create synapeses and also tries to 
	 * load the next web pages in the field of this class. 
	 * 
	 */
	public Collection<Scrapable> getNextWebPages() throws IOException 
	{
		if(this.G_moreAD!=null&&!this.G_moreAD.isEmpty())return this.G_moreAD;
		
		if(this.G_moreAD==null)
			this.G_moreAD = new HashSet<Scrapable>();
		
		this.prepare();
		this.reachoutToNextWebs_helper();
		return this.G_moreAD;
	}

	private void reachoutToNextWebs_helper() 
	{
			for(String s : this.G_nextPages)
			{
				Scrapable temp = reachoutToNextWebs_helper(s,this.getThispageLink(), 3);
				if(temp!=null)
					if(!this.G_moreAD.contains(temp))
						this.G_moreAD.add(temp);
			}
	}
	
	
	/**
	 * 
	 * @param link
	 * @param referedlink
	 * @param number_of_retry
	 * @return
	 * null if the creating the deviant art objects 
	 * have failed several times, equals to number of retry; 
	 */
	private static DeviantArt reachoutToNextWebs_helper(String link,String referedlink, int number_of_retry)
	{
		try
		{
			return new DeviantArt(link,referedlink);
		}
		catch(IOException e)
		{
			if(number_of_retry <=0)return null;
			return reachoutToNextWebs_helper(link,referedlink, number_of_retry-1);
		}

	}

	
	
	/**100% working
	 * A public method is deviat art that tells whether the web link contains 
	 * the domain name of deviant art. 
	 * @param link
	 * @return
	 */
	@Deprecated
	public static boolean isInDadomain(String link)
	{
		boolean result =false;
		result|= link.matches("https://.*deviantart.*");
		result |= link.matches("http://fav.me/.+");
		return result;
	}

	/**
	 * Get the download link on the page. 
	 * @param doc
	 * @return
	 * null if there is not download buttons on the da page. 
	 */
	public static String getDownloadlink(Document doc)
	{
		String downloadlink =null; 
		Elements eles = doc.select("a.dev-page-download");
		//Elements eles = doc.select(".dev-page-button dev-page-button-with-text dev-page-download ");
		if(eles.size()>0)
		{
			println("There is a download botton on this deviant art page. ");
			 downloadlink = eles.get(0).attr("href");
			 println("---> "+ downloadlink);
		}
		return downloadlink;
	}

	public static void println(Object o )
	{
		
		GuiModel.println(o);
		System.out.println(o);
	}

	@Override
	/**
	 * Unit tested. 
	 */
	public String getSourceContentUrl() {
		
		return this.G_theimage;
		
	}

	@Override
	public boolean pauseAndSkip() {
		return Scraper.Pause;
		
	}
	
	
	
	
}
