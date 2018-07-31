package Selenium;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


/**
 * <a href ="https://www.w3schools.com/jS/js_htmldom.asp">JavaScript: Document Object Model</a>
 * 
 * <br>
 * <a href= "https://www.w3schools.com/jS/js_window.asp">Java Script BOM</a>
 * @author victo
 *
 */
public class Lab 
{

	
	
	public static void main(String[] args) throws InterruptedException
	{
		idea();
	}
	
	
	public static void idea() throws InterruptedException
	{
		Selenium.Prepare.pre();
		
		WebDriver browser  = new ChromeDriver();
		browser.get("https://rainbow-highway.deviantart.com/favourites/69983545/Featured");
		JavascriptExecutor je = (JavascriptExecutor)browser;
		int i = 0;
		while(i<100)
		{
			Thread.sleep(500);
			je.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		}
		
	}
}
