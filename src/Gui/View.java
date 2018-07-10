package Gui;

import java.util.EventListener;

import javax.swing.JFrame;
import javax.swing.JTextPane;

/**
 * We need a framework for the functions of view. 
 * @author victo
 *
 */
interface View 
{
	
	
	
	/**
	 * Add a listener to all the components on the gui. 
	 * @param listener
	 * @return
	 */
	View addListener( Object listener);
	
	JFrame getFrame();
	/**
	 * return a JTestPane if possible.
	 * @return
	 * null, this object is absence from this frame. 
	 */
	

}
