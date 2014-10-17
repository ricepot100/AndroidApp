package phonecommunicationmanage;

import java.io.File;
import java.io.IOException;
import phonecommunicationmanage.servicemanager.PhoneCommunicationServices;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.phonecommunicationmanage.R;


public class MainActivity extends Activity {
	private final String TAG = "MainActivity";

	private Button btn_start_service=null;
	private Button btn_stop_service=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		btn_start_service = (Button)findViewById(R.id.btn_start_service);
		btn_stop_service = (Button)findViewById(R.id.btn_stop_service);
		btn_start_service.setOnClickListener(new ButtonActionStartService());
		btn_stop_service.setOnClickListener(new ButtonActionStopService());
		
		File f_extStorage = Environment.getExternalStorageDirectory();
		String str_pathInfo = f_extStorage.getAbsolutePath(); Log.d(TAG, "external absolute path = " + str_pathInfo);
		try {
			str_pathInfo = f_extStorage.getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} Log.d(TAG, "external canonical path = " + str_pathInfo);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
	Intent m_intentServiceMananger = null;
	
    
    class ButtonActionStartService implements OnClickListener {

		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			m_intentServiceMananger = new Intent(PhoneCommunicationServices.SERVICE_MANAGER_SERVICE_INTENT_ACTION);
			startService(m_intentServiceMananger);
		}		
	}
	
	class ButtonActionStopService implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(null != m_intentServiceMananger) {
				stopService(m_intentServiceMananger);
				m_intentServiceMananger = null;
			}

		}	
	}	
}
