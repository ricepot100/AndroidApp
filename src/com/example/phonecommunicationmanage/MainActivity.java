package com.example.phonecommunicationmanage;

import java.io.File;
import java.io.IOException;

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
    
    class ButtonActionStartService implements OnClickListener {
		final Intent intentStartSMSService = new Intent();
		final Intent intentStartPhoneService = new Intent();
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			intentStartSMSService.setAction(f_action_sms_manage_service);
			startService(intentStartSMSService);
			
			intentStartPhoneService.setAction(f_action_phone_manage_service);
			startService(intentStartPhoneService);
			
			IntentFilter filter = new IntentFilter(Intent.ACTION_TIME_TICK);
			BroadcastReceiver timeTickRecekver = new TimeTickeReceiver();
			registerReceiver(timeTickRecekver,filter);
		}		
	}
	
	class ButtonActionStopService implements OnClickListener {
		final Intent intentStopSMSService = new Intent();
		final Intent intentStopPhoneService = new Intent();
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			intentStopSMSService.setAction(f_action_sms_manage_service);
			stopService(intentStopSMSService);
			intentStopPhoneService.setAction(f_action_phone_manage_service);
			stopService(intentStopPhoneService);
			
			BroadcastReceiver timeTickRecekver = new TimeTickeReceiver();
			unregisterReceiver(timeTickRecekver);
		}	
	}	
}
