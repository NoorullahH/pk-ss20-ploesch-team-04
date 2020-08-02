package at.jku.se.taskmgmt;


import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.logging.Logger;

import dropbox.DropboxApi;
import taskmanager.view.MainWindowController;

// Die Klasse testDownloadFile() ist unter Anführungszeichen gesetzt, weil beim Hochladen auf Sonar Probleme verursacht.
// Da wir die kostenlose Version von Dropbox für Entwickler verwenden, müssen wir jedesmal Access-token erneuern. Die Testklasse 
//würde funktionieren, wenn wir unseren Access-token erneuern und dann auf Sonar hochladen. 
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
//
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
