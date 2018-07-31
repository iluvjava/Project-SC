package Untilities.sys;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class ClipBoard {
	
	
	/**
	 * Here we test this class. 
	 * @param args
	 */
	public static void main (String[] args)
	{
		System.out.println(getClipBoard());
	}
	
	
	/**
	 * ---Fully Tested---<br>
	 * @return
	 * A string representation of what is inside the clipboard of the system. 
	 * null if there is something wrong of the null could be returned by the java
	 * native method. 
	 */
	public static String getClipBoard(){
	    try {
	        return (String)Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
	    } catch (HeadlessException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();            
	    } catch (UnsupportedFlavorException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();            
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    return null;
	}
	
	

}
