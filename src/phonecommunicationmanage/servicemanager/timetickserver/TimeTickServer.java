package phonecommunicationmanage.servicemanager.timetickserver;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class TimeTickServer extends Service {

	private static String TAG = "TimeTickServer";
	
	BroadcastReceiver m_timeTickRecekver = null;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreate");
		
		m_timeTickRecekver = new TimeTickReceiver(); 
		IntentFilter filter = new IntentFilter(Intent.ACTION_TIME_TICK);			
		registerReceiver(m_timeTickRecekver,filter);
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "onStartCommand");	
		return super.onStartCommand(intent, flags, startId);
	}
	
	
	public void onDestroy() {
		/**
		if (null != m_timeTickRecekver) {
			unregisterReceiver(m_timeTickRecekver);
			m_timeTickRecekver = null;
		}*/
	}
	
	

}
