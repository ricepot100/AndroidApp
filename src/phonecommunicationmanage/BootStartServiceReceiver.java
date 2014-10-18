package phonecommunicationmanage;

import phonecommunicationmanage.servicemanager.PhoneCommunicationServices;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootStartServiceReceiver extends BroadcastReceiver {

	private final String TAG = "BootProcessAction";
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onReceive");
		Intent intentServiceMananger = new Intent(PhoneCommunicationServices.SERVICE_MANAGER_SERVICE_INTENT_ACTION);
		context.getApplicationContext().startService(intentServiceMananger);
	}
}
