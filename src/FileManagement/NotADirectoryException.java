package FileManagement;

import java.io.IOException;
/**
 * The give directory file is already an existing file. 
 * @author victo
 *
 */

public class NotADirectoryException extends IOException
{

	public NotADirectoryException(String string) 
	{
		super(string);
	}
	
	

}
