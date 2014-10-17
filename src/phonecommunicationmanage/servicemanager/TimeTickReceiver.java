package phonecommunicationmanage.servicemanager;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class TimeTickReceiver extends BroadcastReceiver {
	
	private static final String TAG = "TimeTickeReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onReceiver");
		Intent intentServiceMananger = new Intent(PhoneCommunicationServices.SERVICE_MANAGER_SERVICE_INTENT_ACTION);
		context.getApplicationContext().startService(intentServiceMananger);
	}

}
