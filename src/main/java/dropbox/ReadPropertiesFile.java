package dropbox;

import java.io.*;
import java.util.*;

import properties.PropertiesReader;
/**
 * @author Dino
 *
 */
public class ReadPropertiesFile implements PropertiesReader{

	private ReadPropertiesFile() {
		throw new IllegalStateException("Utility class");
	}
	
	  /**
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static Properties get(String fileName)  throws IOException {
	      FileInputStream fis = null;
	      Properties prop = null;
	      fis = new FileInputStream(fileName);
	      prop = new Properties();
	      prop.load(fis);
	      fis.close();
	      return prop;
	   }
}
