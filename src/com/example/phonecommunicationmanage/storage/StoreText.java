package com.example.phonecommunicationmanage.storage;

import java.io.FileWriter;
import java.io.IOException;

import android.util.Log;

public class StoreText extends StorageInfo {
	
	private String TAG = "StoreText";
	
	FileWriter m_storeTextFile = null;
	String m_strTextFileName = null;
	
	public StoreText(String strTextFileName) {
		super();
		m_strTextFileName = strTextFileName;
	}
	
	public void writeToText(String text) {
		Log.d(TAG, "writeToText: " + ((m_strTextFileName != null) ? "Ok" : "Wrong"));
		if (m_strTextFileName != null) {
			try {
				String absolutePath = m_RootPackageStorageAbsoluteDirectory + "/" + m_strTextFileName;
				m_storeTextFile = new FileWriter(absolutePath, true);
				m_storeTextFile.write(text);
				m_storeTextFile.flush();
				m_storeTextFile.close();
				m_storeTextFile = null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				m_storeTextFile = null;
				e.printStackTrace();
			}
		}
	}
}
