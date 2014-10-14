package com.example.phonecommunicationmanage;

import com.example.phonecommunicationmanage.timetickserver.TimeTickReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

public class BootStartServiceReceiver extends BroadcastReceiver {

	private final String TAG = "BootProcessAction";
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onReceive");
		String action_sms_manage_service ="com.example.phonecommunicationmanage.sms.SMSManageService";
		String action_phone_manage_service = "com.example.phonecommunicationmanage.phone.PhoneManageService";
		Intent intentStartSMSService = new Intent();
		Intent intentStartPhoneService = new Intent();
		intentStartSMSService.setAction(action_sms_manage_service);
		context.startService(intentStartSMSService);
		
		intentStartPhoneService.setAction(action_phone_manage_service);
		context.startService(intentStartPhoneService);
		
		IntentFilter filter = new IntentFilter(Intent.ACTION_TIME_TICK);
		BroadcastReceiver timeTickRecekver = new TimeTickReceiver();
		context.getApplicationContext().registerReceiver(timeTickRecekver,filter);
	}

}
