package Untilities.sys;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * THis 
 * @author victo
 *
 */
public class ErrorLog
{
	
	
	

	public static void main(String[] args)
	{
		creatReportTitle(new ErrorLog(), "Testing the class itself. Line 21");
	}
	
	final static RandomAccessFile errorlog ;
	
	static
	{
		errorlog = getRaf();
		
		if(errorlog !=null)
		try {
			errorlog.seek(errorlog.length());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	static RandomAccessFile getRaf()
	{
		RandomAccessFile raf = null;
		File f = new File("Errorlog.txt");
		
		try 
			{
				if(!f.exists())
				f.createNewFile();
				raf =  new RandomAccessFile(f,"rw");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return 	raf;
	}
	
	public static void print(Object o)
	{
		if(errorlog ==null)return;
		
		byte[] cr = o.toString().getBytes();
		try {
			errorlog.write(cr, 0, cr.length);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void println()
	{
		print(System.lineSeparator());
	}
	public static void println(Object o)
	{
		print(o);
		println();
	}
	/**
	 * By giving an instance of the objects involved in the error, a title will 
	 * be created in the error log.<br>
	 * The object class and an appended message will be displayed together. 
	 * @param reportingclass
	 * @param message
	 */
	public static void creatReportTitle(Object reportingclass, String message)
	{
		println("#######################"+new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date())+"#########################");
		println("----------------"+reportingclass.getClass()+"--------------------");
		println(message);
	}

}
