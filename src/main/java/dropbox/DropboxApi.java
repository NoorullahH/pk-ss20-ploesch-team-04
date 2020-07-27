package dropbox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.UploadErrorException;
import com.dropbox.core.v2.files.WriteMode;

import taskmanager.view.MainWindowController;

/**
 * @author Dino
 *
 */
public class DropboxApi {
	private String accessToken;
	private String dropboxFoldername = "/tasks.xml";
	private DbxClientV2 dropboxClient;
	private static final Logger LOGGER = Logger.getLogger(MainWindowController.class.getName());

	/**
	 * @throws IOException
	 */
	public DropboxApi() throws IOException {
		Properties prop = ReadPropertiesFile.get("app.properties");
		this.accessToken = prop.getProperty("ACCESS_TOKEN");
		DbxRequestConfig pathConfig = DbxRequestConfig.newBuilder("/taskmgmt_g4").build();
		dropboxClient = new DbxClientV2(pathConfig, this.accessToken);

	}

	/**
	 * @param path2
	 * @throws UploadErrorException
	 * @throws DbxException
	 * @throws IOException
	 */
	public void uploadFile(String path2) throws Exception {
		try {
			dropboxClient.files().uploadBuilder(this.dropboxFoldername).withMode(WriteMode.OVERWRITE)
					.uploadAndFinish(new FileInputStream(path2));
		} catch (DbxException | IOException ex) {
			  LOGGER.log(null, "context", ex);

 		}
	}

	/**
	 * @param path
	 * @throws Exception
	 * @throws FileNotFoundException
	 */
	public void downloadFile(String path) throws Exception {
		try {

			dropboxClient.files().downloadBuilder(this.dropboxFoldername).download(new FileOutputStream(path));
		} catch (DbxException | IOException ex) {
			  LOGGER.log(null, "context", ex);

		}

	}

}
