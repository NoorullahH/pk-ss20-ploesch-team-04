package dropbox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Properties;
import com.dropbox.core.DbxAuthInfo;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuth;
import com.dropbox.core.NetworkIOException;
import com.dropbox.core.RetryException;
import com.dropbox.core.json.JsonReader;
import com.dropbox.core.util.IOUtil.ProgressListener;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.DbxPathV2;
import com.dropbox.core.v2.files.CommitInfo;
import com.dropbox.core.v2.files.DeleteErrorException;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.UploadErrorException;
import com.dropbox.core.v2.files.UploadSessionCursor;
import com.dropbox.core.v2.files.UploadSessionFinishErrorException;
import com.dropbox.core.v2.files.UploadSessionLookupErrorException;
import com.dropbox.core.v2.files.WriteMode;

/**
 * @author Dino
 *
 */
public class DropboxApi {
private String key;
private String access_token;
private String dropboxFoldername = "/tasks.xml";
private DbxClientV2 dropboxClient;

/**
 * @throws IOException
 */
public DropboxApi() throws IOException{
	 Properties prop = ReadPropertiesFile.get("app.properties");
     this.key= prop.getProperty("APP_KEY");
     this.access_token=  prop.getProperty("ACCESS_TOKEN");
	 DbxRequestConfig pathConfig = DbxRequestConfig.newBuilder("/taskmgmt_g4").build();
	 dropboxClient = new DbxClientV2(pathConfig, this.access_token);

}

/**
 * @param path2
 * @throws UploadErrorException
 * @throws DbxException
 * @throws IOException
 */
public void uploadFile(String path2) throws UploadErrorException, DbxException, IOException {
	dropboxClient.files().uploadBuilder(this.dropboxFoldername).withMode(WriteMode.OVERWRITE).uploadAndFinish(new FileInputStream(path2));
}

/**
 * @param path
 * @throws DeleteErrorException
 * @throws DbxException
 */
public void deleteFile(String path) throws DeleteErrorException, DbxException {
	dropboxClient.files().delete(path);
}
/**
 * @param path
 * @throws FileNotFoundException
 * @throws DbxException
 * @throws IOException
 */
public void downloadFile( String path) throws FileNotFoundException, DbxException, IOException {
	dropboxClient.files().downloadBuilder(this.dropboxFoldername).download(new FileOutputStream(path));

}

}
