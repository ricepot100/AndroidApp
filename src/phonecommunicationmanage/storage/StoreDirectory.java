package phonecommunicationmanage.storage;

import java.io.File;

import android.util.Log;

public class StoreDirectory extends StorageInfo {
	
	String m_directoryPath = null;
	String m_directoryPathAbsolute = null;
	
	private final String TAG = "StoreDirectory";
	
	public StoreDirectory(String directoryPath) {
		super();
		m_directoryPath = m_RootPackageStorageAbsoluteDirectory + "/" + directoryPath;
		File directoryPathFile = new File(m_directoryPath);
		if (!directoryPathFile.exists()) {
			directoryPathFile.mkdirs();
		}
		m_directoryPathAbsolute = directoryPathFile.getAbsolutePath();
		Log.d(TAG, "m_directoryPathAbsolute:" + m_directoryPathAbsolute);
	}
	
	public String mkDirs(String directoryPaht) {
		String absolutePath = m_directoryPathAbsolute + "/" + directoryPaht;
		Log.d(TAG, "mkDirs absolutePath:" + absolutePath);
		File directoryPathFile = new File(absolutePath);
		if (directoryPathFile.exists() && directoryPathFile.isDirectory()) {
			return directoryPathFile.getAbsolutePath();
		} else if (directoryPathFile.exists() && !directoryPathFile.isDirectory()){
			Log.d(TAG, "directoryPathFile is not a directory");
			return null;
		} else {
			directoryPathFile.mkdirs();
			return directoryPathFile.getAbsolutePath();
		}
	}
	
	
	public String getStoreDirectoryAbsolutePath() {
		return m_directoryPathAbsolute;
	}

}
