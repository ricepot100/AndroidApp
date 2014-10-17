package phonecommunicationmanage.database;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.format.DateUtils;
import android.util.Log;

public class ContextDBHelper {
	private final static String TAG = "ContextDBHelper";
	
	private final static String ColumnType[] = {"NULL", "INTEGER", "FLOAT", "STRING", "BLOB"};
	
	public final static String[] getColumnName(Context context, Uri uri) {
		String column_name[] = null;
		Cursor cr = context.getContentResolver().query(uri, null, null, null, null);
		int column_count = cr.getColumnCount();
		if (column_count >  0) {
			column_name = new String[column_count];
			for (int index = 0; index < column_count; index++) {
				column_name[index] = cr.getColumnName(index);
			}
		}
		if (null != cr) cr.close();
		return column_name;
	}
	
	public final static void listColumnNameAndType(Context context, Uri uri) {
		Cursor cr = context.getContentResolver().query(uri, null, null, null, null);
		int column_count = cr.getColumnCount();
		if (column_count > 0 && cr.moveToFirst()) {
			for (int index = 0; index < column_count; index++) {
				Log.d(TAG, "ColumnIndex: " + index + "; ColumnName: " + cr.getColumnName(index) + "; ColumnType: " + ColumnType[cr.getType(index)]);
			}
		}
		if (null != cr) cr.close();
		
	}
	
	public final static String formatTimeStampString(Context context, long when) {
		int format_flags = DateUtils.FORMAT_ABBREV_ALL | 
				DateUtils.FORMAT_SHOW_YEAR | 
				DateUtils.FORMAT_SHOW_DATE | 
				DateUtils.FORMAT_SHOW_TIME;
		
		return DateUtils.formatDateTime(context, when, format_flags);
	}

	public final static String findContactFromSmsPerson(Context context, String person) {
		if (null == person) return null;
		
		String str_contactPerson = null;
		String str_projection[] = {
				ContactsContract.Contacts._ID,
				ContactsContract.Contacts.HAS_PHONE_NUMBER,
				ContactsContract.Contacts.DISPLAY_NAME,
		};
		Cursor cr = context.getContentResolver().query(
				ContactsContract.Contacts.CONTENT_URI, 
				str_projection, 
				ContactsContract.Contacts._ID + " = "  + "'" + person + "'", 
				null, 
				null);
		if (null!=cr && 0<cr.getCount()) {
			cr.moveToFirst();
			str_contactPerson = cr.getString(cr.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
			cr.close();
		}
		return str_contactPerson;
	}

	public final static String findContactFromSmsAddress(Context context, String address) {
		if (null == address) return null;
		
		String str_contactPerson = null;
		String str_projection[] = {
			ContactsContract.PhoneLookup.DISPLAY_NAME,
			ContactsContract.CommonDataKinds.Phone.NUMBER,
		};
		
		Cursor cr = context.getContentResolver().query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI, 
				str_projection,  ContactsContract.CommonDataKinds.Phone.NUMBER + " = '" + address + "'",
				null, 
				null);
		if (null!=cr && 0<cr.getCount()) {
			cr.moveToFirst();
			str_contactPerson = cr.getString(cr.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
			cr.close();
		}
		return str_contactPerson;
	}

}

