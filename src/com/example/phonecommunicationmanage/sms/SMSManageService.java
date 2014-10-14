package com.example.phonecommunicationmanage.sms;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class SMSManageService extends Service {
	
	//private SMSCommingReceiver m_smsReceiver = null;
	
	private SMSDBInBoxContentObserver m_smsDBInBoxObserver = null;
	private SMSSendBoxContentObserver m_smsDBSendBoxObserver = null;
	
	private static String TAG = "SMSManageService";

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onBind");
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreate");
		/**
		m_smsReceiver = new SMSCommingReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.provider.Telephony.SMS_RECEIVED");
		registerReceiver(m_smsReceiver, filter);
		*/
		//ContextDBHelper.getColumnName(this, Uri.parse(SMSDB_URI.SMS_URI_ALL));
		//ContextDBHelper.listColumnNameAndType(this, Uri.parse(SMSDB_URI.SMS_URI_ALL));
		//ContextDBHelper.listColumnNameAndType(this, ContactsContract.Contacts.CONTENT_URI);
		//ContextDBHelper.listColumnNameAndType(this, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
		m_smsDBInBoxObserver = new SMSDBInBoxContentObserver(this, new Handler());
		m_smsDBInBoxObserver.registerToContext();
		
		m_smsDBSendBoxObserver = new SMSSendBoxContentObserver(this, new Handler());
		m_smsDBSendBoxObserver.registerToContext();
		
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "onStartCommand");	
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		Log.d(TAG, "onDestroy");
		//unregisterReceiver(m_smsReceiver);
		m_smsDBInBoxObserver.unregisterFromContext();
		m_smsDBSendBoxObserver.unregisterFromContext();
		super.onDestroy();
	}

}

class SMSCommingReceiver extends BroadcastReceiver  {
	private static String TAG = "fanfan: In SMSCommingReceiver";
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.d(TAG, "action: " + intent.getAction());
	}
	
}
