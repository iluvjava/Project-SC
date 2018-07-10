package Selenium;

import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebElement;

public class TestEverything {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() 
	{
		
		WebDriverBridge yep = new WebDriverBridge();
		yep.get("www.google.com");
		System.out.println("Try to get the height of the page. ");
		System.out.println(yep.ExecuteJavaScript("return document.body.scrollHeight").getClass());
		yep.get("https://www.deviantart.com/rainbow-highway/favourites/69983545/Featured");
		
		
		println("Trying to select all the element that has a DA image in it. ");
		
		{
			List<String> allthumblinks= new LinkedList<>();
			while(yep.scrollDown())
			{
				println("Try to extract all the links there.");
				
				for(WebElement e : yep.getElementsByName("torpedo-thumb-link"))
				{
					println(e.getAttribute("href"));
					allthumblinks.add(e.getAttribute("href"));
				}
			}
			println("---All links collected while scrolling down: "); 
			for(String s : allthumblinks)
			{
				println(s);
			}
			
		}
	}
	
	
	public static void println()
	{
		System.out.println();
	}
	
	public static void println(Object o)
	{
		System.out.println(o);
	}
	
	

}
