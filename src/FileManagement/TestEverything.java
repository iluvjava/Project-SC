package FileManagement;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestEverything {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() throws NotADirectoryException 
	{
		testStoringObjects();
		
	}

	
	public void testStoringObjects() throws NotADirectoryException
	{
		{
		String dir ="E:/ObjectTest/";
		int[] athing = {4,3,2,1,345,66,445,0,-12};
		ObjectCache objc = new ObjectCache(athing,new File(dir),"athing");
		
		println(objc);
		
		println("Here is where the file store: "+ objc.getStoreFile());
		
		println(objc);
		
		Object something = objc.readObject();
		
		println(something);
		
		println("Now we are trying to store a map object onto the hard disk. ");
		
		}
		
		Map<String, String> amap = new TreeMap<String, String>();
		amap.put("RainbowDash", "Blue pegasus");
		amap.put("Twilight", "A lots of purple");
		amap.put("Pinkie", "A mystery");
		
		ObjectCache<Map> something = new ObjectCache<Map>(amap, new File("E:/ObjectTest/"), "amap");
		println("Storing it");
		something.writeObject();
		println("Retriving it...");
		Object somethingelse = something.readObject();
		println(somethingelse);
		
	}
	
	public static void println(Object o )
	{
		System.out.println(o);
	}
}
