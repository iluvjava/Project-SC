package Gui;

import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.EventListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.JTextPane;

import Scraping.Scrapable;
import Scraping.Scraper;
import Untilities.sys.SystemLog;
import WebPage.DeviantArt;
import WebPage.DeviantArtBuilder;
import WebPage.DeviantArtFavorite;

/**
 * This class will be the model behind the gui 
 * and control the program. 
 * This is the brain of this program, it should deal with many things. 
 * 
 * <ul>
 * <li>Print lines of text to the output window.
 * <li>Handle the event and produce correct output to the 
 * window when user interact with the view.
 * <li>It will print the content to the sys log. 
 * </ul>
 * 
 * <p>
 * The model contains all the neccessary information to create a SINGLE scrapable 
 * object and control the object through the methods is have. 
 * @author victo
 *
 */
public class GuiModel 
{
	
	ScraperGui G_GUI;
	public static volatile DisplayText G_displayedText; // the text panel essentially. 
	
	
	/********************things for data part**********************/
	File dir;
	String initiallink;
	private Scrapable scr;
	private volatile Scraper scraper;
	int target=0;
	
	public GuiModel(View arg)
	{
		if(arg instanceof View)this.G_GUI = (ScraperGui) arg;
		
		G_displayedText = new DisplayText(G_GUI.getTextArea());
	}
	
	
	public static void print(Object o)
	{
		if(GuiModel.G_displayedText!=null)G_displayedText.print(o);
	}
	public static void println(Object o)
	{
		if(GuiModel.G_displayedText!=null)GuiModel.G_displayedText.println(o);
	}
	
	
	/**
	 * Tested.
	 * <br>
	 * The class that controls the output of JTextPanel.
	 * @author victo
	 *
	 */
	public class DisplayText 
	{
		private TextArea JTpane;
		
		
		public DisplayText(TextArea panel)
		{
			this.JTpane=panel;
		}
		
		
		
		public synchronized void print(Object o)
		{
			this.JTpane.append(o.toString());
			this.JTpane.setCaretPosition(this.JTpane.getCaretPosition()+1);
			SystemLog.print(o);
		}
		
		public synchronized void println(Object o)
		{
			print(o.toString()+"\n");
			
		}
		
		public void setText(String s)
		{
			this.JTpane.setText(s);
		}
		
		
		
//		private void clear()
//		{
//			this.G_JTpane.setText(null);
//			
//		}

		
	}
	
	
	/**
	 * <p>
	 * this method will tries to create a scrapable object in the field.
	 * @return
	 * A boolean to indecate whether the process is successful. 
	 */
	public boolean createScrapableFromLink()
	{
		
		if(this.dir == null)
		{
			println("File path is not specified.");return false;
		}
		
		this.target = (int) this.G_GUI.getSpinner().getValue();
		this.initiallink = this.G_GUI.getTextField().getText();
		println("Setting target: "+this.target);

		println("Setting the URL: "+ this.initiallink);
     	println("This is some sort of a deviant art link. ");
		Scrapable scrapable = DeviantArtBuilder.getInstance(this.initiallink);
		
		
		
		if(scrapable == null)
		{
			println("Cannot Recognize url.");return false;
		}
		
		this.scr = scrapable;
		this.scraper = new Scraper(this.scr,this.dir,this.target);
		return true;
	}
		
		
		
		


	/**
	 * Import file to the field. <br>
	 * The method will check the filechooser from the GUI. 
	 */
	public void setDirectory() 
	{
		File f = this.G_GUI.getFileChooser().getSelectedFile();
		this.dir = f; 
		GuiModel.println("Setting Directory: "+ f);
		//this.G_GUI.getFileChooser().setCurrentDirectory(f);
	}


	public void removeDirectory() 
	{
		this.dir= null;
		println("Removing Directery to null.");
	}


	/**
	 * Under Testing.<br>
	 * <ol>
	 * <li>Disable the botton
	 * <li>CreateScrapableFromLink
	 * <li>execute
	 * <li>Enable the botton. 
	 * </ol>
	 */
	public void startExecute() 
	{
		this.G_GUI.getBtnStart().setEnabled(false);
		
		Thread t =
				new Thread
				(
						new Runnable()
				{
					public void run()
					{
						if(GuiModel.this.createScrapableFromLink())
						{
							//Start the scraping. 
							GuiModel.this.scraper.execute();
							
						}
						GuiModel.this.G_GUI.getBtnStart().setEnabled(true);
					}
				}
				);
		t.start();
//		try {
//			t.join();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		
//		this.G_GUI.getBtnStart().setEnabled(true);
	}
	
	
	public void closeFileChooser()
	{
		FileChooserDialog temp=this.G_GUI.getFileChooserDialog();
		if(temp!=null)temp.setVisible(false);
		
	}


	public synchronized void StopBottonPressed() 
	{
		switch(this.G_GUI.getBtnPause().getText())
		{
		case "Stop":
			this.G_GUI.getBtnPause().setText("Continue");
			Scraper.Pause=true;
			break;
		case "Continue":
			this.G_GUI.getBtnPause().setText("Stop");
			Scraper.Pause=false;
			break;
		}
		
	}
	

}



