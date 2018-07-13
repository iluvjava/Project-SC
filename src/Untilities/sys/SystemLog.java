package Untilities.sys;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * This is a place where we keep all the log inside a file in the src package. 
 * @author victo
 *
 */
public class SystemLog extends OutputStream
{
	
	public static void main(String[] args)
	{
		
	}
	
	public final static File outputFile; 
	public static final RandomAccessFile raf;
	static
	{
		outputFile = new File("SystemLog.txt");
		if(!outputFile.exists())
			try {
				outputFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		raf = getRAF();
		println("SystemLog Initialized.");
		println("----"+new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date())+"----");
	}
	
	private static RandomAccessFile getRAF()
	{
		
		try 
		{
			 return new RandomAccessFile(outputFile, "rw");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	

	public void write(int arg0) throws IOException 
	{
		raf.seek(raf.length());
		raf.write(arg0);
	}
	
	private static void writechar(int arg0)
	{
	
		try 
		{
			raf.seek(raf.length());
			raf.write(arg0);
		} catch (IOException e) {
		System.out.print(arg0);
		}
	}
	
	public static void println()
	{
		for(char c :System.lineSeparator().toCharArray())writechar(c);
	}
	
	public static void println(Object o)
	{
		for(char c : o.toString().toCharArray())writechar(c);
		println();
	}
	public static void print(Object o)
	{
		for(char c : o.toString().toCharArray())writechar(c);
	}

	
	

}
