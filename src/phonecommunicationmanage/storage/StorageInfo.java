package phonecommunicationmanage.storage;

import java.io.File;

import android.os.Environment;
import android.util.Log;

public class StorageInfo {
	private static final String TAG = "StorageInfo";
	
	private String m_RootStorageAbsoluteDirectory = null;
	
	protected String m_RootPackageStorageAbsoluteDirectory = null;
	protected String m_RootPackageStorageDirectoryName = "CommnicationManage";
	
	public StorageInfo() {
		if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
			File f_extStorage = Environment.getExternalStorageDirectory();	
			m_RootStorageAbsoluteDirectory = f_extStorage.getAbsolutePath();
		} else {
			m_RootStorageAbsoluteDirectory = Environment.getDataDirectory().getAbsolutePath();
		}
		
		File f_rootPackageStorage = new File(m_RootStorageAbsoluteDirectory + "/" + m_RootPackageStorageDirectoryName);
		if (!f_rootPackageStorage.exists()) {
				if (true == f_rootPackageStorage.mkdirs()) {
					m_RootPackageStorageAbsoluteDirectory = f_rootPackageStorage.getAbsolutePath();
				}
		} else
			m_RootPackageStorageAbsoluteDirectory = f_rootPackageStorage.getAbsolutePath();	
		
		Log.d(TAG, "m_RootPackageStorageAbsoluteDirectory: " + m_RootPackageStorageAbsoluteDirectory);
		
	}
}
