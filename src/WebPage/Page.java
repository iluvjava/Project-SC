package WebPage;
import java.io.IOException;
import java.util.Map;

import org.jsoup.Connection.Request;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;


/**
 * This is class is just a html page that can 
 * be instantiated. 
 * A dummy class. 
 * @author victo
 *
 */
public class Page extends HtmlPage{

	public Page(String link) {
		super(link);
		
	}

	public static void main(String[] args) throws IOException {
	
		Page p = new Page("https://www.fimfiction.net/");
		p.loadPage();
		System.out.println(p.getResponse().statusCode());
		System.out.println(p.getResponse().statusMessage());
		System.out.println("************HTML****************");
		System.out.println(p.getDoc().html());
		System.out.println("************COOKIE***************");
		System.out.println(p.getCookie().toString());
		
		
	}

	
	

}
