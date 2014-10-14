package com.example.phonecommunicationmanage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class TimeTickeReceiver extends BroadcastReceiver {
	
	private static final String TAG = "TimeTickeReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onReceiver");
		String action_sms_manage_service ="com.example.phonecommunicationmanage.sms.SMSManageService";
		String action_phone_manage_service = "com.example.phonecommunicationmanage.phone.PhoneManageService";
		Intent intentStartSMSService = new Intent();
		Intent intentStartPhoneService = new Intent();
		intentStartSMSService.setAction(action_sms_manage_service);
		context.startService(intentStartSMSService);
		
		intentStartPhoneService.setAction(action_phone_manage_service);
		context.startService(intentStartPhoneService);
	}

}
