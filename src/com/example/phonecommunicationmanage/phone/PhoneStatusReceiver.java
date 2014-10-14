package com.example.phonecommunicationmanage.phone;

import java.io.File;
import java.io.IOException;

import com.example.phonecommunicationmanage.storage.StorageInfo;
import com.example.phonecommunicationmanage.storage.StoreDirectory;
import com.example.phonecommunicationmanage.storage.StoreText;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.format.Time;
import android.util.Log;

class CallingPhoneStarter {
	public static final int NOT_ANY_CALL = 1;
	public static final int Call_From_Other = 2;
	public static final int Call_From_Self = 3;
	
	private int m_whocall = NOT_ANY_CALL;
	public int getWhoCall() { return m_whocall; }
	public void setWhoCall(int whoCall) {
		m_whocall = whoCall;
	}
}

public class PhoneStatusReceiver extends BroadcastReceiver {
	
	private static final String TAG = "PhoneStatusReceiver";
	private static final String PHONE_LOG_NAME = "phonerecord.txt";
	
	private StoreText m_log = new StoreText(PHONE_LOG_NAME);
	private MyPhoneStateListener m_phoneListener= null;
	private Context m_context = null;
	private CallingPhoneStarter m_caller = new CallingPhoneStarter();
	
	private String m_goingoutNumber = null;  // only for Intent.ACTION_NEW_OUTGOING_CALL
	
	public CallingPhoneStarter WhoIsCaller() {
		return m_caller;
	}
	
	public String getGoingOutNumber() {
		return m_goingoutNumber;
	}
	
	public PhoneStatusReceiver(Context context) {
		super();
		m_context = context.getApplicationContext();
		TelephonyManager tm = (TelephonyManager)m_context.getSystemService(Service.TELEPHONY_SERVICE); 
		m_phoneListener= new MyPhoneStateListener(this);
		tm.listen(m_phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		m_log.writeToText(TAG + ":onReceive: Intent action: " + intent.getAction() + "\r\n");
		Log.d(TAG, "onReceive: Intent action: " + intent.getAction());
		if(intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {	// going a call
			m_caller.setWhoCall(CallingPhoneStarter.Call_From_Self);
			m_goingoutNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
			m_log.writeToText(TAG + ":Call out number: " + m_goingoutNumber + "\r\n");
			Log.d(TAG, "Call out number: " + m_goingoutNumber);
		}
		else {
			// There is not a special action for coming a call, but if not going, it should be a coming
			m_goingoutNumber = null;
			m_caller.setWhoCall(CallingPhoneStarter.Call_From_Other);
			Log.d(TAG, "Get comming call");
		}
	}
}

class MyPhoneStateListener extends PhoneStateListener {
	
	private static final String TAG = "MyPhoneStateListener";
	private static final String PHONE_LOG_NAME = "phonestatus.txt";
	private static final String PHONE_RECORD_DIRECTORY_NAME = "PhoneRecord";
	private PhoneStatusReceiver m_statusReceiver = null;
	private StoreText m_log = new StoreText(PHONE_LOG_NAME);
	private StoreDirectory m_storePhoneDirectory = null;
	private MediaRecorder m_recorder;
	
	public MyPhoneStateListener(PhoneStatusReceiver receiver) {
		super();
		m_statusReceiver = receiver;
		m_storePhoneDirectory = new StoreDirectory(PHONE_RECORD_DIRECTORY_NAME);
	}
	@Override
	public void onCallStateChanged(int state, String __incomingNumber) {
		
		super.onCallStateChanged(state, __incomingNumber);
		String incomingNumber = null;
		String str_promotion = null;
		if (CallingPhoneStarter.Call_From_Other == m_statusReceiver.WhoIsCaller().getWhoCall()) {
			str_promotion = "From";
			incomingNumber = __incomingNumber;
		} else if (CallingPhoneStarter.Call_From_Self == m_statusReceiver.WhoIsCaller().getWhoCall()) {
			str_promotion = "To";
			incomingNumber = m_statusReceiver.getGoingOutNumber();
		}
		m_log.writeToText(TAG + ":onCallStateChanged " + str_promotion + " incomingNumber: " + incomingNumber + 
				" ; The current state is: " + state + "\r\n");
		Log.d(TAG, ":onCallStateChanged " + str_promotion +  " incomingNumber: " + incomingNumber + 
				" ; The current state is: " + state);
		switch (state) {
			case TelephonyManager.CALL_STATE_IDLE: {
				Log.d(TAG, "Hung up the diag: " + incomingNumber);
				releasePhoneSound();
				Log.d(TAG, "Stop record the sound");
				m_log.writeToText(TAG + ":Hung up the diag: " + incomingNumber + "\r\n");
				m_statusReceiver.WhoIsCaller().setWhoCall(CallingPhoneStarter.NOT_ANY_CALL);
				break;
			}
			case TelephonyManager.CALL_STATE_OFFHOOK: {
				Log.d(TAG, "Answer the diag: " + incomingNumber);
				m_log.writeToText(TAG + ":Answer the diag: " + incomingNumber + "\r\n");
				if (null != incomingNumber)
					recordPhoneSound(incomingNumber);
				break;
			}
			case TelephonyManager.CALL_STATE_RINGING: {
				Log.d(TAG, "Calling the diag: " + incomingNumber);
				m_log.writeToText(TAG + ":Calling the diag: " + incomingNumber + "\r\n");
				break;
			}
		}
	}

	
	private void recordPhoneSound(String number)
	{		
		Time time = new Time();
		time.setToNow();
		int year = time.year;
		int month = time.month + 1;
		int day = time.monthDay;
		String directory_name = year + "-" + month + "-" + day;
		
		m_storePhoneDirectory = new StoreDirectory(PHONE_RECORD_DIRECTORY_NAME);
		
		String strDirectAbsolutePath = m_storePhoneDirectory.mkDirs(directory_name);
		
		m_recorder = new MediaRecorder();
		m_recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		m_recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		m_recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		
		int hour = time.hour;
		int minute = time.minute;
		int sec = time.second;
		String sound_file_name = hour + "-" + minute + "-" + sec + "-" + number + ".3gp";
		m_recorder.setOutputFile(strDirectAbsolutePath + "/" + sound_file_name);
		Log.d(TAG, "filenam: " + strDirectAbsolutePath + "/" + sound_file_name);
		
		try {
			m_recorder.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		m_recorder.start();
	}
	
	private void releasePhoneSound() {
		if (null != m_recorder) {
			m_recorder.stop();
			m_recorder.release();
			m_recorder = null;
		}
	}
}
