package com.example.phonecommunicationmanage.database;

import android.provider.ContactsContract;

/**
 * 
 * @author fanfan
 * Please see ContactsContract.Contacts.* (for example ContactsContract.Contacts._ID) to see the name of the 
 * Contract table information
 * ContactsContract.Contacts.CONTENT_URI
 */

public interface ContactDB_COLUMN_INFO {
	
	public final static int INDEX__ID = 10;
	public final static String NAME__ID = ContactsContract.Contacts._ID;
	
	public final static int INDEX_PHOTO_ID = 15;
	public final static String NAME_PHOTO_ID = ContactsContract.Contacts.PHOTO_ID;
	
	public final static int INDEX_HAS_PHONE_NUMBER = 20;
	public final static String NAME_HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;	
	
	public final static int INDEX_DISPLAY_NAME = 23;
	public final static String NAME_DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
}
