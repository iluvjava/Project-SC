	package Selenium;

import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;


/**
 * Bridge the api to a new, simpler api of Selenium webdriver. 
 * @author victo
 *
 */
public class WebDriverBridge
{
	
	
	
	
		private static WebDriver d;
		
		public WebDriverBridge()
		{
			TheDriver.SeleniumFirefox.open();
			d = TheDriver.SeleniumFirefox.getDriver();
		}
		
		
		/**
		 * 
		 * @param URL 
		 * <br>
		 *  A complete URL. 
		 * @throws InvalidArgumentException
		 */
		public synchronized void get(String URL)throws InvalidArgumentException
		{
			try{
			if(URL.matches("www..*"))URL="http://"+URL;
			d.get(URL);
			}catch (Exception e) {
			}
		}
		
		public synchronized WebDriver getDriver()
		{
			return d;
		}
		
		public synchronized Document getHtml()
		{ 
			try{
		
			Document doc =  Jsoup.parse(d.getPageSource());
			return doc;
			}catch (Exception e) {
				return null;
			}
		}
		
		/**
		 * 
		 * @param id
		 * @return
		 * One element with given Id
		 */
		public synchronized WebElement getElementById(String id)
		{
			try{
			return d.findElement(By.id(id));
			}
			catch (Exception e) {
			return null;
			}
		}
		/**
		 * 
		 * @param id
		 * @return
		 * All Elements with Given ID. 
		 */
		public synchronized List<WebElement> getElementsById(String id)
		{
			try{
			return d.findElements(By.id(id));
			}catch (Exception e) {
				return null; 
			}
		}
		
		
		/**
		 * It will return class with the given name. div class="name"
		 * @param name
		 * @return
		 */
		public synchronized WebElement getElementByName(String name)
		{
			try{
			return d.findElement(By.className(name));
		}catch (Exception e) {
			return null;
		}
		}
		
		/**
		 * 
		 * @param name
		 * @return
		 * All classes with given name.
		 */
		public synchronized List<WebElement>getElementsByName(String name)
		{
			try{
			return d.findElements(By.className(name));
			}catch (Exception e) {
				return null;
			}
		}
		
		
		public synchronized Object getCookies()
		{
			try{
			Set<Cookie> c = d.manage().getCookies();
			return c;
			}catch (Exception e) {
				return null;
			}
			
		}
		
		
		/**
		 * <code>
		 *"<"a" href=
			"http://www.google.com/search?q=cheese" >search for cheese<"/a">
			<br>
			WebElement cheese = driver.findElement(By.partialLinkText(
			"cheese" ));
			</code>
		 * @return
		 * 
		 */
		public synchronized WebElement getElementByLinkText(String text)
		{
			try{
			return d.findElement(By.partialLinkText(text));
			}catch (Exception e) {
				return null;
			}
			
		}
		
		
		public synchronized String getSourceURL()
		{
			try{
			return d.getCurrentUrl();
			}catch (Exception e) {
				return null;
			}
		}
		
		
		
		public synchronized Object ExecuteJavaScript(String command)
		{
			try{
			JavascriptExecutor js = (JavascriptExecutor) d;
			return js.executeScript(command);
			}catch (Exception e) {
			return null;
			}
		}
		
		
		/**
		 * Keep scrolling down, if the content changed, it will still keep scrolling down.<br>
		 * If the web load too slow, it will stop scrolling down. 
		 */
		public synchronized void KeepScrollingToBotton()
		{
			try{
			for(
					Long h = (Long) this.ExecuteJavaScript("return document.body.scrollHeight"), newh = 0L;
					!newh.equals(h)
					;)
			{
				this.ExecuteJavaScript("window.scrollTo(0, document.body.scrollHeight)");
				sleep();
				h = newh;
				newh = (Long) this.ExecuteJavaScript("return document.body.scrollHeight");
			}
			}catch (Exception e) {
				return;
			}
		}
		
		
		
		/**
		 * This method will try to scroll down the web page, if it loads slowly, it 
		 * will not work well. 
		 * @return
		 * A boolean to indicate if new content has been loaded at the bottom. True if you can still 
		 * scroll down, false if you are at the absolute bottom. 
		 */
		public synchronized boolean scrollDown()
		{
			try{
			Long h = (Long) this.ExecuteJavaScript("return document.body.scrollHeight");
			this.ExecuteJavaScript("window.scrollTo(0, document.body.scrollHeight)");
			sleep();
			Long  newh = (Long) this.ExecuteJavaScript("return document.body.scrollHeight");
			return newh-h!=0;
			}catch (Exception e) {
				return false; 
			}
		}
		
		
		private void sleep()
		{
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		public synchronized void close()
		{
			try{
			TheDriver.SeleniumFirefox.close();
			}catch(Exception e){}
		}
		
		
		/**
		 * ---Not Tested---<br>
		 * This method should enter a text to a text field given the id of the element,
		 *  and them it will submit the text. 
		 * @return
		 * false if operation unsuccessful. 
		 */
		public synchronized boolean enterTextTo(String text, String id)
		{
			try
			{
				WebElement we = this.getElementById(id);
				we.sendKeys(text);
				return true; 
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			return false; 
		}
		
		
		
		
}


enum TheDriver
{
	
	SeleniumFirefox;
	
	WebDriver d;
	
	TheDriver()
	{
		Prepare.pre();
		this.d  = new FirefoxDriver();
	}
	
	public void open()
	{
		if(this.d!=null)return;
		this.d = new FirefoxDriver();
	}
	
	public void close()
	{
		if(d!=null)d.quit();
		this.d = null; 
	}
	
	public WebDriver getDriver()
	{
		return this.d;
	}
	
	
	
}
