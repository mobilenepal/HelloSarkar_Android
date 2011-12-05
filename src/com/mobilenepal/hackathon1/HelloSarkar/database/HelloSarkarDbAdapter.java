package com.mobilenepal.hackathon1.HelloSarkar.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.mobilenepal.hackathon1.HelloSarkar.Complain;

public class HelloSarkarDbAdapter {
			// Database fields
			public static final String KEY_LOCAL_ID = "_id";
			public static final String KEY_SERVER_ID = "serverId";
			public static final String KEY_NAME = "name";
			public static final String KEY_COMPLAIN_TYPE = "complainType";
			public static final String KEY_DISTRICT = "district";
			public static final String KEY_ADDRESS = "address";
			public static final String KEY_COMPLAIN = "complain";
			public static final String KEY_DATE = "date";
			public static final String KEY_PHONE = "phone";
			public static final String KEY_GPS = "gps";
            public static final String KEY_MOBILE_INFO = "mobileInfo";
            
            //USED IN QUERY TO LIST SELECT COLUMNS
            public static final String[] KEY_COLUMNS=new String[]{KEY_LOCAL_ID , KEY_SERVER_ID ,KEY_NAME,KEY_COMPLAIN_TYPE,KEY_DISTRICT, KEY_ADDRESS,KEY_COMPLAIN,KEY_DATE,KEY_PHONE,KEY_GPS,KEY_MOBILE_INFO};
			
            private static final String DB_TABLE = "MyComplain";
			
			private Context context;
			private SQLiteDatabase db;
			private HelloSarkarDatabaseHelper dbHelper;

			public HelloSarkarDbAdapter(Context context){
				this.context = context;
			}
			public HelloSarkarDbAdapter open() throws SQLException {
				dbHelper = new HelloSarkarDatabaseHelper(context);
				db = dbHelper.getWritableDatabase();
				return this;
			}

			public void close() {
				dbHelper.close();
			}
			
			/**
			 * Create a new complain If the complain is successfully created return the new
			 * rowId for that note, otherwise return a -1 to indicate failure.
			 */

			public long createComplain(Complain complain) {
			ContentValues values = createContentValues(complain);

				return db.insert(DB_TABLE, null, values);
			}
			
			/**
			 * Update the complains
			 */

			public boolean updateComplain(Complain complain) {
				ContentValues values = createContentValues(complain);

				return db.update(DB_TABLE, values, KEY_LOCAL_ID  + "=" + complain.getLocalId(), null) > 0;
			}
			
			/**
			 * Deletes complains
			 */
			public boolean deleteComplain(long localId) {
				return db.delete(DB_TABLE, KEY_LOCAL_ID  + "=" + localId, null) > 0;
			}
			
			
			/**
				 * Return a Cursor over the list of all complains in the database
				 * 
				 * @return Cursor over all notes
				 */

				public Cursor fetchAllComplains() {
					return db.query(DB_TABLE, KEY_COLUMNS, null, null, null, null, null);
				}
				
				/**
				 * Return a Cursor positioned at the defined complain
				 */

				public Cursor fetchComplain(long localId) throws SQLException {
					Cursor mCursor = db.query(true, DB_TABLE, KEY_COLUMNS, KEY_LOCAL_ID + "="+localId, null, null, null, null, null);
					if (mCursor != null) {
						mCursor.moveToFirst();
					}
					return mCursor;
				}
			
			private ContentValues createContentValues(Complain complain) {
				ContentValues values = new ContentValues();
				values.put(KEY_SERVER_ID, complain.getServerId());
				values.put(KEY_NAME, complain.getName());
				values.put(KEY_COMPLAIN_TYPE, complain.getComplainType());
				values.put(KEY_DISTRICT, complain.getDistrict());
				values.put(KEY_ADDRESS, complain.getAddress());
				values.put(KEY_COMPLAIN, complain.getComplain());
				values.put(KEY_DATE, complain.getDate());
				values.put(KEY_PHONE, complain.getPhone());
				values.put(KEY_GPS, complain.getGps());
				values.put(KEY_MOBILE_INFO, complain.getMobileInfo());
				return values;
			}			

}
