package phonecommunicationmanage;

import phonecommunicationmanage.servicemanager.PhoneCommunicationServices;
import android.app.AlarmManager;
import android.app.PendingIntent;
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
		/**
		Intent intentTimeTickService = new Intent(PhoneCommunicationServices.TIME_TICK_SERVICE_INTENT_ACTION);
		context.getApplicationContext().startService(intentTimeTickService);
		*/
		Intent intentAlarmClock = new Intent(PhoneCommunicationServices.ALARM_CLOCK_RECEIVER_INTENT_ACTION);
		PendingIntent pendingIntentAlarmClock = PendingIntent.getBroadcast(context.getApplicationContext(), 0, intentAlarmClock, PendingIntent.FLAG_UPDATE_CURRENT);
		
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		am.cancel(pendingIntentAlarmClock);
		am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 10 * 1000, pendingIntentAlarmClock);
	}
}
