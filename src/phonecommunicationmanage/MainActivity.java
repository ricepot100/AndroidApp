package phonecommunicationmanage;

import java.io.File;
import java.io.IOException;

import phonecommunicationmanage.servicemanager.PhoneCommunicationServices;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.phonecommunicationmanage.R;


public class MainActivity extends Activity {
	private final String TAG = "MainActivity";

	private Button btn_start_service=null;
	private Button btn_stop_service=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		btn_start_service = (Button)findViewById(R.id.btn_start_service);
		btn_stop_service = (Button)findViewById(R.id.btn_stop_service);
		btn_start_service.setOnClickListener(new ButtonActionStartService(this));
		btn_stop_service.setOnClickListener(new ButtonActionStopService(this));
		
		File f_extStorage = Environment.getExternalStorageDirectory();
		String str_pathInfo = f_extStorage.getAbsolutePath(); Log.d(TAG, "external absolute path = " + str_pathInfo);
		try {
			str_pathInfo = f_extStorage.getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} Log.d(TAG, "external canonical path = " + str_pathInfo);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
	//Intent m_intentTimeTick = null;
	Intent m_intentAlarmClock = null;
	PendingIntent m_pendingIntentAlarmClock = null;
	
    
    class ButtonActionStartService implements OnClickListener {
    	
    	private Context m_context = null;

		public ButtonActionStartService(Context context) {
			m_context = context;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			/**		
			m_intentTimeTick = new Intent(PhoneCommunicationServices.TIME_TICK_SERVICE_INTENT_ACTION);
			startService(m_intentTimeTick);
			*/
			
			m_intentAlarmClock = new Intent(PhoneCommunicationServices.ALARM_CLOCK_RECEIVER_INTENT_ACTION);
			m_pendingIntentAlarmClock = PendingIntent.getBroadcast(m_context, 0, m_intentAlarmClock, PendingIntent.FLAG_UPDATE_CURRENT);
			
			AlarmManager am = (AlarmManager) m_context.getSystemService(Context.ALARM_SERVICE);
			am.cancel(m_pendingIntentAlarmClock);
			am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 10 * 1000, m_pendingIntentAlarmClock);
		}		
	}
	
	class ButtonActionStopService implements OnClickListener {
		
		private Context m_context = null;
		
		public ButtonActionStopService(Context context) {
			m_context = context;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			/**
			if(null != m_intentTimeTick) {
				stopService(m_intentTimeTick);
				m_intentTimeTick = null;
			}
			*/
			if (null !=  m_intentAlarmClock) {
				AlarmManager am = (AlarmManager) m_context.getSystemService(Context.ALARM_SERVICE);
				am.cancel(m_pendingIntentAlarmClock);
			}
			

		}	
	}	
}
