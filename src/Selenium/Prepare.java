package Selenium;

import java.io.File;
import java.io.IOException;

/**
 * Prepare the selenium stuff for whatever by setting up firefox driver in system
 * properties. 
 * @author victo
 *
 */
public class Prepare 
{
	
	public static void main(String[] args) throws IOException
	{
		System.out.println("We are checking the system properties to ensure that the geckodiver is there. ");
		
		
		pre();
		
		System.out.println(System.getProperty("webdriver.gecko.driver"));
		
		Runtime.getRuntime().exec(System.getProperty("webdriver.gecko.driver"));
	}
	
	// Ensure the Geko driver is presented. 
	
	final static String seleniumdriverdirectory;
	static 
	{
		File f = new File("");
		
		System.out.println("Source File Abs path: "+ f.getAbsolutePath());
		seleniumdriverdirectory=  f.getAbsolutePath()+"\\Dependencies\\geckodriver.exe";
		
		
	}
	public static void pre()
	{
		System.setProperty("webdriver.gecko.driver",seleniumdriverdirectory);
	}
	

}
