package com.mobilenepal.hackathon1.HelloSarkar.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class HelloSarkarTable {
	// Database creation SQL statement
			private static final String DATABASE_CREATE = "create table MyComplain (_id integer primary key autoincrement, "
					+ "serverId text  null,name TEXT not null, complainType TEXT not null,district TEXT not null," 
					+ "address TEXT not null,complain TEXT not null,date TEXT not null,phone TEXT not null,gps TEXT ,mobileInfo TEXT not null);";
			
			public static void onCreate(SQLiteDatabase database) {
				database.execSQL(DATABASE_CREATE);
				
			}

			public static void onUpgrade(SQLiteDatabase database, int oldVersion,int newVersion) {
				Log.w(HelloSarkarTable.class.getName(), "Upgrading database from version "
						+ oldVersion + " to " + newVersion
						+ ", which will destroy all old data");
				database.execSQL("DROP TABLE IF EXISTS MyComplain");
				onCreate(database);
			}
}
