package Selenium;

/**
 * Prepare the selenium stuff for whatever by setting up firefox driver in system
 * properties. 
 * @author victo
 *
 */
public class Prepare 
{
	public static void pre()
	{
		System.setProperty("webdriver.gecko.driver","C:\\SeleniumGecko\\geckodriver.exe");
	}
	

}
