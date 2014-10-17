package phonecommunicationmanage.servicemanager;

import java.util.Vector;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class ServiceManager extends Service {

	private static final String TAG = "ServiceManager";
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	Vector<Intent> m_vec_intentServices = new Vector<Intent>();
	
	private void startPhoneCommunicationServices() {
		
		Intent sms_intent = 
				new Intent(PhoneCommunicationServices.startSeparateService(this, 
				PhoneCommunicationServices.SMS_SERVICE_INTENT_ACTION));
		m_vec_intentServices.add(sms_intent);
		
		Intent phone_intent = 
				new Intent(PhoneCommunicationServices.startSeparateService(this, 
				PhoneCommunicationServices.PHONE_SERVICE_INTENT_ACTION));
		m_vec_intentServices.add(phone_intent);
		
		Intent timetick_intent = 
				new Intent(PhoneCommunicationServices.startSeparateService(this, 
				PhoneCommunicationServices.TIME_TICK_SERVICE_INTENT_ACTION));
		m_vec_intentServices.add(timetick_intent);
	}
	
	private void stopPhoneCommunicationServices() {
		if (m_vec_intentServices.size() > 0) {
			for (int index=0; index<m_vec_intentServices.size(); index++) {
				PhoneCommunicationServices.stopSeparateService(this, m_vec_intentServices.get(index));
			}
			
			m_vec_intentServices.removeAllElements();
		}
	}
	
	@Override
	public void onCreate(){
		Log.d(TAG, "onCreate");
		super.onCreate();
		startPhoneCommunicationServices();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "onStartCommand");	
		startPhoneCommunicationServices();
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		Log.d(TAG, "onDestroy");
		stopPhoneCommunicationServices();
		super.onDestroy();
	}
}
