package phonecommunicationmanage.servicemanager.smsservice;

import phonecommunicationmanage.database.*;
import phonecommunicationmanage.storage.StoreText;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;


public class SMSDBContentObserver extends ContentObserver {
	
	private final static String TAG = "SMSDBContentObserver";

	protected Handler m_handler = null;
	protected Context m_context = null;
	protected Uri m_uri = null;					// to query
	protected int m_max_id = -1;
	public SMSDBContentObserver(Context context, Handler handler, Uri uri) {
		// TODO Auto-generated constructor stub
		super(handler);
		m_context = context;
		m_handler = handler;
		m_uri = uri;
		
		Cursor cr = m_context.getContentResolver().query(m_uri,
				null,										// all columns 
				null,									 	//
				null, 
				SMSDB_COLUMN_INFO.NAME__ID + " asc");		// sorted by id
		if (null != cr) {
			if (true == cr.moveToLast()) {
				m_max_id = cr.getInt(SMSDB_COLUMN_INFO.INDEX__ID);
				cr.close();
			}
		}
	}
	
	protected void DoAddRowAction(Cursor cr){
		Log.d(TAG, "DoAddRowAction");
	}
	protected void DoDelRowAction(Cursor cr){
		Log.d(TAG, "DoDelRowAction");
	}
	
	public void registerToContext() {
		Log.d(TAG, "registerToContext");
		m_context.getContentResolver().registerContentObserver(Uri.parse(SMSDB_URI.SMS_URI_ALL), true, this);
	}
	
	public void unregisterFromContext() {
		Log.d(TAG, "unregisterFromContext");
		m_context.getContentResolver().unregisterContentObserver(this);
	}
	
	@Override
	public void onChange(boolean selfChange) {
		Log.d(TAG, "onChange");
		int current_max_id = -1;
		Cursor cr = m_context.getContentResolver().query(m_uri,
				null,										// all columns 
				null,									 	//
				null, 
				SMSDB_COLUMN_INFO.NAME__ID + " asc");		// sorted by id
		if (null != cr) {
			if (true == cr.moveToLast()) {
				current_max_id = cr.getInt(SMSDB_COLUMN_INFO.INDEX__ID);
			}
			
			if (current_max_id > m_max_id) {				// Inserted a new row
				DoAddRowAction(cr);
			}
			else {
				DoDelRowAction(cr);
			}
			
			cr.close();
		}
		m_max_id = current_max_id;
	}
}


class SMSDBInBoxContentObserver extends SMSDBContentObserver {
	
	private final static String TAG = "SMSDBInBoxContentObserver";
	private final static String SMS_LOG_FILE = "smsrecord.txt";
	
	private StoreText m_logFile = new StoreText(SMS_LOG_FILE);
	
	private final static Uri s_f_smsInboxUri = Uri.parse(SMSDB_URI.SMS_URI_INBOX);
	
	public SMSDBInBoxContentObserver(Context context, Handler handler) {
		super(context, handler, s_f_smsInboxUri);
	}
	
	@Override
	protected void DoAddRowAction(Cursor cr) {
		Log.d(TAG, "You have receive a sms");
		String str_receiveAddress = cr.getString(cr.getColumnIndex(SMSDB_COLUMN_INFO.NAME_ADDRESS)); 
		String str_person = cr.getString(cr.getColumnIndex(SMSDB_COLUMN_INFO.NAME_PERSON)); 
		String str_receiveDate = ContextDBHelper.formatTimeStampString(m_context, 
													cr.getLong(cr.getColumnIndex(SMSDB_COLUMN_INFO.NAME_DATE)));
		String str_receiveBody = cr.getString(cr.getColumnIndex(SMSDB_COLUMN_INFO.NAME_BODY));
		String str_personName = "who";
		/**
		str_personName = ContextDBHelper.findContactFromSmsPerson(m_context, str_person);
		if (null == str_personName) {
			str_personName = ContextDBHelper.findContactFromSmsAddress(m_context, str_receiveAddress);
		}
		if (null == str_personName) str_personName = "unKnown";
		*/
		
		String title = "Received from " + str_receiveAddress + "(" + str_personName + ")" + "; at " + str_receiveDate + "\r\n";
		String body = str_receiveBody + "\r\n";
		m_logFile.writeToText(title + body);
		
		Log.d(TAG, title + body);
		
	}
	
	@Override
	protected void DoDelRowAction(Cursor cr) {
		//Log.d(TAG, "Delete a comed sms");
	}
}

class SMSSendBoxContentObserver extends SMSDBContentObserver {
	
	private final static String TAG = "SMSSendBoxContentObserver";
	private final static String SMS_LOG_FILE = "smsrecord.txt";
	
	private final static Uri s_f_smsOutboxUri = Uri.parse(SMSDB_URI.SMS_URI_SEND);
	
	private StoreText m_logFile = new StoreText(SMS_LOG_FILE);

	public SMSSendBoxContentObserver(Context context, Handler handler) {
		super(context, handler, s_f_smsOutboxUri);
	}
	
	@Override
	protected void DoAddRowAction(Cursor cr) {
		Log.d(TAG, "You have send a sms");
		String str_sendAddress = cr.getString(cr.getColumnIndex(SMSDB_COLUMN_INFO.NAME_ADDRESS));
		String str_person = cr.getString(cr.getColumnIndex(SMSDB_COLUMN_INFO.NAME_PERSON));
		String str_sendDate = ContextDBHelper.formatTimeStampString(m_context, cr.getLong(SMSDB_COLUMN_INFO.INDEX_DATE));
		String str_sendBody = cr.getString(cr.getColumnIndex(SMSDB_COLUMN_INFO.NAME_BODY));
		String str_personName = "who";
		/**
		String str_personName = ContextDBHelper.findContactFromSmsPerson(m_context, str_person);
		if (null == str_personName) {
			str_personName = ContextDBHelper.findContactFromSmsPerson(m_context, str_sendAddress);
		}
		if (null == str_personName) str_personName = "unKnown";
		*/
		
		String title = "Send to " + str_sendAddress + "(" + str_personName + ")" + "; at " + str_sendDate + "\r\n";
		String body = str_sendBody + "\r\n";
		m_logFile.writeToText(title + body);
		Log.d(TAG, title + body);
	}
	
	@Override
	protected void DoDelRowAction(Cursor cr) {
		//Log.d(TAG, "Delete a out old sms");
	}
	
}



