package WebPage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 
 * 
 * Use the interfacce and abstract class to do things \
 * on a web page with domain name www.fimfiction.net/
 * @author Autistic Lycan
 *
 */
public class FimFiction extends HtmlPage{
	
	private List<StoryCard> G_listofsotrycard;

	public static void main(String[] args) throws IOException 
	{
		WebPage wb=(WebPage) instantiate("https://www.fimfiction.net");
				((FimFiction)wb).getStories();
		System.out.println(wb.getCookie());
		
	}

	
	
	private FimFiction(String link) 
	{
		super(link);
		this.G_listofsotrycard = new ArrayList<StoryCard>();
		
	}
	
	/**
	 * load the page and Return all the stroies on the main page. 
	 * 
	 * class= story-card
	 * @return
	 * @throws IOException 
	 */
	public List<StoryCard> getStories() throws IOException
	{
		this.loadPage();
	
		Elements eles = this.getDoc().getElementsByClass("story-card");
		
		
		for(Element n : eles)
		{
			
			this.G_listofsotrycard.add(new StoryCard(n));

		}
		System.out.println("________________story links______________");
		for(StoryCard sc : this.G_listofsotrycard)
		{
			System.out.println(sc.getStoryLink());
		}

		return this.G_listofsotrycard;	
	}



	/**
	 * Factory method, it will check whether it is 
	 * a domain name under fimfiction. 
	 * @param link
	 * @return
	 */
	public static FimFiction instantiate(String link)
	{
		if(!link.contains("www.fimfiction.net"))
			{
			System.out.println("domain name mismatched. ");
			return null;
			}
		return new FimFiction(link);
	}

	
	
	/**
	 * A class for the element in the web page. 
	 * @author autistic lycan
	 *
	 */
	private static class StoryCard
	{
		//the element of story class. 
		private Element G_storycard;
		public StoryCard(Element e)
		{
			this.G_storycard = e; 
		}
		
		/**
		 * return the link to the story
		 */
		public String getStoryLink()
		{
			String result = this.G_storycard.select(".story_link").attr("abs:href");
			return result;
		}
		
		public String toString()
		{
			return this.G_storycard.html();
		}
		
	}
	
	
	
}
