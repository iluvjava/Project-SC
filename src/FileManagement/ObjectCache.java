package FileManagement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * <b>
 * This class is dedicated to the saving of 
 * objects onto the hardisk. 
 * </b>
 * <b>List of Funtionalities(Planed)</b>
 * <ul>
 * <li>1. Store the object given the object and info about directory;
 * <li>2. Retrive the object when wanted. 
 * <li>3. The parameter five as a file object should not ended with a "/", normally
 * it should be just a file that pointed to a directory.
 * <li>Be able to throw customized exceptions at will. 
 * <li>Be able to move objects around for fun. 
 * <li>Provide many static methods so that if the info about the files are not used many times, 
 * we have the option to choose the static method. 
 * </ul>
 * <p>
 * Generic implemetation is optional. 
 * @author victo
 * 
 *
 */
public class ObjectCache<T> 
{
	public T G_targetedobject;
	
	public final String G_dir;
	
	private File G_filedir; // this can change.
	
	public final String G_filename;
	
	private File G_storedfile;
	
	
	/**
	 * 
	 * @param athing
	 * @param dirc
	 * It is a directory,
	 * @param filename
	 * The name of the file you want to save.  
	 * @throws FileRepeatedException 
	 */
	public ObjectCache(T athing,File dirc, String filename) throws NotADirectoryException
	{
		if(athing ==null&&dirc==null)throw new AssertionError();
		this.G_dir = dirc.getAbsolutePath();
		this.G_targetedobject = athing;
		this.G_filename = filename;
		this.G_filedir = dirc;
		if(filename == null||filename.isEmpty())throw new IllegalArgumentException();
		
		if(!ObjectCache.establishDirectory(this.G_dir))throw new IllegalArgumentException();
		
		this.G_storedfile = new File(this.G_dir+'/'+this.G_filename);
	}
	
	@SuppressWarnings("unchecked")
	
	/**<b>
	 * If the file exists and the field of the targeted object is null
	 * the field will got replaced by the filed read from the disk;
	 * </b>
	 * <p>
	 * The object in the class field should be replaced manually. 
	 * 
	 */
	public T readObject()
	{
		Object result =ObjectCache.readObject(this.G_storedfile); 
		
		//println("readObject() result"+ result);
		
		if(result!=null&&this.G_targetedobject==null)this.G_targetedobject = (T) result;
		return result==null?null:(T)result;
	}
	
	
	/**
	 * Unit tested.
	 * Write the object in the class field to the directory specified in 
	 * this class object; 
	 * @return
	 * false if the object doesn't get written to the disk;
	 */
	public boolean writeObject()
	{
		return ObjectCache.writeObejct(G_storedfile, G_targetedobject);
	}
	
	
	/**
	 * 
	 * @return
	 * if the specified object in the object field is already in the disk. 
	 */
	public boolean isThere()
	{
		return this.G_storedfile.exists();
	}
	
	
	
	

	/**
	 * United tested.
	 * @param filelreadfrom
	 * @return
	 * null if objective did achieve.
	 */
	public static Object readObject(File filelreadfrom)
	{
		if(!filelreadfrom.exists())return null;
		try 
		{
			//println("readObject() is executing....");
			ObjectInputStream objinput = new ObjectInputStream(new FileInputStream(filelreadfrom));
			Object result = objinput.readObject();
			objinput.close();
			return result;
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * Simple write the object to the specified file, as the file.
	 * @param thefile
	 * @param arg
	 * @return
	 */
	public static boolean writeObejct(File thefile, Object arg)
	{
		try 
		{
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(thefile));
			oos.writeObject(arg);
			oos.close();
			return true;
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Create a certain direcotry if there isn't or it is already there. 
	 * @throws FileRepeatedException 
	 * We don't risk to destroy unwanted file. This is not virus.
	 */
	public static boolean establishDirectory(String dir) throws NotADirectoryException
	{
		File f = new File(dir);
		if(f.isFile())throw new NotADirectoryException("Direcotory points to an existing file. ");
		if(f.isDirectory())
		{
			if(f.exists());else if(!f.mkdir())return false;
		}
		return true;
	}
	
	public static void println(Object o)
	{
		System.out.println(o);
	}
	
	
	public File getStoreFile()
	{
		return this.G_storedfile;
	}
	
	
	public String toString()
	{
		String s  = "\n\n\n-----------------------"+ this.getClass()+"-------------------------\n";
		s+= "This directory is: "+this.G_dir+'\n';
		s+= "File name: "+this.G_filename+'\n';
		s+= "This is the stored the directory: "+this.G_storedfile+'\n';
		s+= this.G_targetedobject==null?
				"There is no object linked to it\n":"This object is cached with an object \n";
		s+= this.isThere()?"The specified file is there\n":"The object is not there.\n";
		return s;
	}

}
