package Gui;

public enum ToolTips 
{
	
	TextFiledTips("Type In URL to the textfield."),
	SwingSpinnerTips("Type in an integer between 1-500, this is the number of "
			+ "Things you wnat to scrape from the internet."),
	StartBottonTips("Press it to start.");
	
	String ToolTips;
	ToolTips(String s)
	{
		this.ToolTips = s;
	}
	
	

}
