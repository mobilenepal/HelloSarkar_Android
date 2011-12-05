package com.mobilenepal.hackathon1.HelloSarkar.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HelloSarkarDatabaseHelper extends SQLiteOpenHelper{
	
	private static final String DATABASE_NAME = "HelloSarkar";

	private static final int DATABASE_VERSION = 1;

	public HelloSarkarDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		HelloSarkarTable.onCreate(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		HelloSarkarTable.onUpgrade(db, oldVersion, newVersion);
	}

}
