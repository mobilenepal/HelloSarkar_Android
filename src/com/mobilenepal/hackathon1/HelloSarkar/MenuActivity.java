package com.mobilenepal.hackathon1.HelloSarkar;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import com.mobilenepal.hackathon1.HelloSarkar.database.HelloSarkarDbAdapter;

public class MenuActivity extends Activity{
	
	HelloSarkarDbAdapter sarkarAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
	}
	
	public void  makeComplainClick(View view) {
		Intent makeComplain = new Intent(MenuActivity.this, HelloSarkarActivity.class);
		startActivity(makeComplain);
	}
	
	public void  listComplainClick(View view) {
		sarkarAdapter=new HelloSarkarDbAdapter(this);
		sarkarAdapter.open();
		Cursor cursor=sarkarAdapter.fetchAllComplains();		
		
		ArrayList<String> results = new ArrayList<String>();		
		if (cursor != null ) {
    		if  (cursor.moveToFirst()) {
    			do {
    				Complain complain=new Complain(cursor,this.getApplicationContext());  
    				String detail="";
    				if(complain.getServerId()==null){
    					detail="\nStatus: Not Posted";
    				}else{
    					detail="\nStatus: Posted";
    				}
    				try {
						detail+="\nComplain Type: "+complain.getMyXmlCode(2, complain.getComplainType(),2);
					} catch (Exception e) {
						detail+="\nComplain Type: ";
					}
    				if(complain.getComplain().length()>100){
    					detail+="\nComplain: "+complain.getComplain().substring(0, 100);
    				}else{
    					detail+="\nComplain: "+complain.getComplain();
    				}
    				results.add(detail+"\ndate: " + complain.getDate());
    			}while (cursor.moveToNext());
    		} 
    	}
		sarkarAdapter.close();
    	
		Bundle bundle=new Bundle();
		bundle.putStringArrayList("complainList", results);
		Intent i=new Intent(this,ComplainList.class);
		i.putExtras(bundle);
		startActivity(i);   

}
}
