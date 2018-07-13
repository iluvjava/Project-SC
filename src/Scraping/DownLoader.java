package Scraping;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.concurrent.ConcurrentLinkedQueue;

import Gui.GuiModel;

/**
 * This is a new added class, that will be passed as param to scrapables from the scraper, so that 
 *  the receiver; scrapble, know where to put all the files they have gotten from the internet. 
 * @author victo
 *
 */
public class DownLoader 
{
	
	 protected ConcurrentLinkedQueue<FileNameAndStream> waitingfordonwload = new ConcurrentLinkedQueue<>();
	
	 protected final File download_directory ; 
	 
	 protected final Thread runningDownloading; 
	
	 public DownLoader(File directory_file)
	{
		assert directory_file!=null:"Something ridiculous has happended....";
		this.download_directory = directory_file;
		
		Thread t = new Thread
				(
						new Runnable()
						{
							public void run()
							{
								while(true)
								{
									if(!DownLoader.this.waitingfordonwload.isEmpty())
									{
										DownLoader.println("----->Fetching a new Dowload mission...");
										DownLoader.this.waitingfordonwload.poll().dumpTheFile();
									}
									
									try 
									{
										Thread.sleep(500);
									} catch (InterruptedException e)
									{
										e.printStackTrace();
									}
								}
							}
						}
						
						
				);
		t.start();
		this.runningDownloading = t;
	}
	 
	 
	 protected void finalize()
	 {
		 try 
		{
			this.runningDownloading.join();
		} catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		 
	 }
	
	
	 /**
	  * Forward the names and file to the method, if null, method will ignore them, you 
	  * can forward multiple times. 
	  * @param filename
	  * @param ips
	  */
	public void forwardFile(String filename, InputStream ips)
	{
		if(filename ==null||ips ==null) throw new IllegalArgumentException();
		FileNameAndStream fips = new FileNameAndStream(this.download_directory);
		fips.forwardFile(filename, ips);
		this.waitingfordonwload.add(fips);
	}
	
	
	/**
	 * Package two of the most important information for downloading.
	 * @author victo
	 *
	 */
	protected static class FileNameAndStream
	{
		public final File dir;
		private String filename;
		private  InputStream inputstr; 
		
		/**
		 * This constructor will be called by the scraper, and then it will pass a directory to it. 
		 * 
		 */
		public FileNameAndStream(File dir)
		{
			this.dir = dir;
			assert dir.isDirectory()&&dir.exists() :"Internal error. ";
		}
		
		
		/**
		 * 
		 * @param filename
		 * :<br> The name of the file, it must be valid! 
		 * @param ips
		 * :<br> An input stream of the file, just do whatever you can, this method will tries 
		 * to handle weird errors. 
		 */
		public void forwardFile(String filename, InputStream ips)
		{
			assert filename!=null && ips!=null;
			this.filename = filename; this.inputstr = ips; 
		}
		
		
		/**
		 * Dump the file to the location at the hard disk. 
		 * @return
		 * A boolean that represents the satus of the action .
		 */
		public boolean dumpTheFile()
		{
			try
			{
				InputStream inputstream = this.inputstr;
				String filename = this.filename;
				
				File file = new File(dir.getAbsolutePath()+"\\"+filename);
				
				if(file.exists())
				{
					println("File:"+ file.getAbsolutePath()+" already existed. ");
					return true;
				}
				
				println("We are trying to create a file for a scrapable under dir:");
				println(file.getAbsolutePath());
				
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
				BufferedInputStream is = new BufferedInputStream(inputstream);
				int temp;
				int loopcount=0;
				while((temp = is.read())!=-1)
				{
					bos.write(temp);
					loopcount++;
				}
				println("Buffered InputStream byte count: "+ loopcount);
				is.close();
				bos.close();
				return true;
			}
			catch(Exception e)
			{
				GuiModel.println(e);
				e.printStackTrace();
			}
			return false; 
		}
		
	}
	
	
	public static void println(Object o)
	{
		GuiModel.println(o);
	}

}

