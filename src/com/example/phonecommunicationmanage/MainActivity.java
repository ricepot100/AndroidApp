package com.example.phonecommunicationmanage;

import java.io.File;
import java.io.IOException;

import com.example.phonecommunicationmanage.timetickserver.TimeTickReceiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity {
	private final String TAG = "MainActivity";

	private Button btn_start_service=null;
	private Button btn_stop_service=null;
	private final String f_action_sms_manage_service ="com.example.phonecommunicationmanage.sms.SMSManageService";
	private final String f_action_phone_manage_service = "com.example.phonecommunicationmanage.phone.PhoneManageService";
	private final String f_action_timetick_server = "com.example.phonecommunicationmanage.timetickserver.TimeTickServer";
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
    
	Intent m_intentStartSMSService = null;
	Intent m_intentStartPhoneService = null;
	Intent m_intentTimeTickServer = null;
	
    
    class ButtonActionStartService implements OnClickListener {

		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (null != m_intentStartSMSService) {
				stopService(m_intentStartSMSService);
			}
			
			if (null != m_intentStartPhoneService) {
				stopService(m_intentStartPhoneService);
			}
			
			if (null != m_intentTimeTickServer) {
				stopService(m_intentTimeTickServer);
			}
			
			m_intentStartSMSService = new Intent();
			m_intentStartPhoneService = new Intent();
			m_intentTimeTickServer = new Intent();
			
			m_intentStartSMSService.setAction(f_action_sms_manage_service);
			startService(m_intentStartSMSService);
			
			m_intentStartPhoneService.setAction(f_action_phone_manage_service);
			startService(m_intentStartPhoneService);
			
			m_intentTimeTickServer.setAction(f_action_timetick_server);
			startService(m_intentTimeTickServer);
			
			
		}		
	}
	
	class ButtonActionStopService implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (null != m_intentStartSMSService) {
				stopService(m_intentStartSMSService);
				m_intentStartSMSService = null;
			}
			if (null != m_intentStartPhoneService) {
				stopService(m_intentStartPhoneService);
				m_intentStartPhoneService = null;
			}
			
			if (null != m_intentTimeTickServer) {
				stopService(m_intentTimeTickServer);
			}

		}	
	}	
}
