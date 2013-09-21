package org.peer.client;

import java.io.FileOutputStream;
import java.io.IOException;

public class ClientHelper {
	public static void writeToDisk(byte [] fileData, String rfcTitle){
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(rfcTitle);
			fileOutputStream.write(fileData);
			fileOutputStream.close();
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
}
