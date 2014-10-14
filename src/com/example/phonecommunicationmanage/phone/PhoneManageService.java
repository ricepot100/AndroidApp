package com.example.phonecommunicationmanage.phone;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;




public class PhoneManageService extends Service {
	
	private final static String TAG = "PhoneManageService";
	
	private PhoneStatusReceiver m_phoneStatusReceiver = null;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreate");
		m_phoneStatusReceiver = new PhoneStatusReceiver(this);
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.intent.action.PHONE_STATE");
		filter.addAction(Intent.ACTION_NEW_OUTGOING_CALL);
		registerReceiver(m_phoneStatusReceiver, filter);
		
	}
	
	
	@Override
	public void onDestroy() {
		Log.d(TAG, "onDestroy");
		unregisterReceiver(m_phoneStatusReceiver);
		super.onDestroy();
	}

}
