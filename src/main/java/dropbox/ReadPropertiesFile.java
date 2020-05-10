package dropbox;

import java.io.*;
import java.util.*;
/**
 * @author Dino
 *
 */
public class ReadPropertiesFile{

   /**
 * @param fileName
 * @return
 * @throws IOException
 */
public static Properties get(String fileName) throws IOException {
      FileInputStream fis = null;
      Properties prop = null;
      try {
         fis = new FileInputStream(fileName);
         prop = new Properties();
         prop.load(fis);
      } catch(FileNotFoundException fnfe) {
         fnfe.printStackTrace();
      } catch(IOException ioe) {
         ioe.printStackTrace();
      } finally {
         fis.close();
      }
      return prop;
   }
}
