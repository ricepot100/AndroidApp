package phonecommunicationmanage.servicemanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmClockReceiver extends BroadcastReceiver {
	
	private static final String TAG = "AlarmClockReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onReceiver: action: " + intent.getAction());
		Intent intentServiceMananger = new Intent(PhoneCommunicationServices.SERVICE_MANAGER_SERVICE_INTENT_ACTION);
		context.getApplicationContext().startService(intentServiceMananger);
	}

}
