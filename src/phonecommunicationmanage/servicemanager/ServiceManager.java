package phonecommunicationmanage.servicemanager;

import java.util.List;
import java.util.Vector;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;

public class ServiceManager extends Service {

	private static final String TAG = "ServiceManager";
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	static Vector<String> m_vec_servicesNameDefault = new Vector<String>();
	static Vector<Intent> m_vec_intentServices = new Vector<Intent>();
	
	private void startPhoneCommunicationServices() {
		
		Intent sms_intent = 
				new Intent(PhoneCommunicationServices.startSeparateService(this.getApplicationContext(), 
				PhoneCommunicationServices.SMS_SERVICE_INTENT_ACTION));
		m_vec_intentServices.add(sms_intent);
		
		Intent phone_intent = 
				new Intent(PhoneCommunicationServices.startSeparateService(this.getApplicationContext(), 
				PhoneCommunicationServices.PHONE_SERVICE_INTENT_ACTION));
		m_vec_intentServices.add(phone_intent);
	}
	
	private void stopPhoneCommunicationServices() {
		if (m_vec_intentServices.size() > 0) {
			for (int index=0; index<m_vec_intentServices.size(); index++) {
				PhoneCommunicationServices.stopSeparateService(this, m_vec_intentServices.get(index));
			}
			
			m_vec_intentServices.removeAllElements();
		}
	}

	
	private boolean isAllPhoneCommunicationServicesRunning() {
		ActivityManager manager = (ActivityManager)this.getApplication().getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningServiceInfo> list_service = manager.getRunningServices(Integer.MAX_VALUE);
	
		boolean allRunning = false;
		
		for (int index1=0; index1<PhoneCommunicationServices.s_vec_ServicesClassName.length; index1++) {
			for (int index2=0; index2<list_service.size(); index2++) {
				String fetch_service_name = list_service.get(index2).service.getClassName();
				if (true == fetch_service_name.equals(PhoneCommunicationServices.s_vec_ServicesClassName[index1])) {
					Log.d(TAG, "find the service: " + fetch_service_name);
					allRunning = true;
					break;
				}
				allRunning = false;
			}
			if (false == allRunning) {Log.d(TAG, "Can not find the service: " + PhoneCommunicationServices.s_vec_ServicesClassName[index1]);
				break;
			}
		}
		list_service.clear();
		
		return allRunning;
	}
	
	WakeLock m_wakelock = null;
	@Override
	public void onCreate(){
		Log.d(TAG, "onCreate");
		super.onCreate();
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		m_wakelock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, TAG);
		m_wakelock.acquire();
		Intent intentAlarmClock = new Intent(PhoneCommunicationServices.ALARM_CLOCK_RECEIVER_INTENT_ACTION);
		PendingIntent pendingIntentAlarmClock = PendingIntent.getBroadcast(this.getApplicationContext().getApplicationContext(), 0, intentAlarmClock, PendingIntent.FLAG_UPDATE_CURRENT);
		
		AlarmManager am = (AlarmManager) this.getApplication().getSystemService(Context.ALARM_SERVICE);
		am.cancel(pendingIntentAlarmClock);
		am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 10 * 1000, pendingIntentAlarmClock);
		stopPhoneCommunicationServices();
		startPhoneCommunicationServices();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "onStartCommand");
		
		if (false == isAllPhoneCommunicationServicesRunning()) {
			Log.d(TAG, "onStartCommand: Need restart the services");
			stopPhoneCommunicationServices();
			startPhoneCommunicationServices();
		}
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		Log.d(TAG, "onDestroy");
		stopPhoneCommunicationServices();
		m_wakelock.release();
		super.onDestroy();
	}
}
