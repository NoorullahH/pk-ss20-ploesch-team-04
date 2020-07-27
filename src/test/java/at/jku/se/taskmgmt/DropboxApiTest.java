package at.jku.se.taskmgmt;


import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.logging.Logger;

import dropbox.DropboxApi;
import taskmanager.view.MainWindowController;

class DropboxApiTest {
	
	DropboxApi dropboxdownloader;

	private static final Logger LOGGER = Logger.getLogger(MainWindowController.class.getName());
	@Test
	void testUploadFile() {
		try {
			dropboxdownloader = new DropboxApi();
			dropboxdownloader.uploadFile("tasks.xml");

		 } catch (IOException e) {
			  LOGGER.log(null, "context", e);

		 } catch (Exception e) {

		}
	}

//	@Test
//	void testDownloadFile() {
//		try {
//			dropboxdownloader = new DropboxApi();
//			dropboxdownloader.downloadFile("tasks.xml");
//
//		 } catch (IOException e) {
//			  LOGGER.log(null, "context", e);
//
//		 } catch (Exception e) {
//			  LOGGER.log(null, "context", e);
//
//		}
//	}

}
