package com.mobilenepal.hackathon1.HelloSarkar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.mobilenepal.hackathon1.HelloSarkar.database.HelloSarkarDbAdapter;

public class HelloSarkarActivity extends Activity {
    /** Called when the activity is first created. */
	
	private TextView tvName, tvAdd, tvComplain, tvDistrict,todaysDate;
	private EditText etName, etAdd, etComplain;
	private Spinner spType, spDistrict;
		
	//DatePicker dpTime;
	Button bSubmit,postButton;
	private LocationManager locManager;
	private String bestProvider;
	private Location location;
	private HelloSarkarDbAdapter sarkarAdapter;
	private LocationListener locationListener;
	MyToast errortoast=new MyToast(0,this);
	MyToast successtoast=new MyToast(1,this);
	String mDate;

	int districtsList;
	
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
      
        setContentView(R.layout.main);        
        initialize();
        if(HaveNetworkConnection()){
			Log.d("INTERNET_CONNECITON", "CONNNECTION SUCCESSFUL");
        } else {
        	
        	errortoast.setMessage("Please enable wifi");
        	errortoast.displayToast();
        	Log.d("INTERNET_CONNECITON", "CONNNECTION Failed");
        }

        locManager = (LocationManager) HelloSarkarActivity.this.getSystemService(Context.LOCATION_SERVICE);
//        List<String> providers=locManager.getAllProviders();
//    	int i=0;
//    	for(String provider:providers){
//    		Log.i("\nprovider"+i++, provider);
//    	}
    	Criteria criteria=new Criteria();
    	criteria.setAccuracy(2);
    //	criteria.setSpeedAccuracy(2);
    	criteria.setPowerRequirement(2);
    //	criteria.setHorizontalAccuracy(2);
    //	bestProvider=locManager.getBestProvider(criteria, false);
    	locationListener=new MyLocationListener();    
    	location=locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if(location == null){
        	locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }
    }
    
protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	//	saveState();
//		outState.putSerializable(HelloSarkarDbAdapter.KEY_ROWID, mRowId);
	}


	private void initialize() {
		tvName = (TextView) findViewById(R.id.tvName);
		tvAdd = (TextView) findViewById(R.id.tvAdd);
		tvComplain = (TextView) findViewById(R.id.tvComplain);
		tvDistrict = (TextView) findViewById(R.id.tvDistrict);
	//	dpTime = (DatePicker) findViewById(R.id.dpTime);
		
		etName = (EditText) findViewById(R.id.etName);
		etAdd = (EditText) findViewById(R.id.etAdd);
		etComplain = (EditText) findViewById(R.id.etComplain);
		
		spType = (Spinner) findViewById(R.id.spType);
		spDistrict = (Spinner) findViewById(R.id.spDistrict);
		
	//	bSubmit =(Button) findViewById(R.id.bSubmit);
		bSubmit =(Button) findViewById(R.id.postButton);

//		dpTime = (DatePicker) findViewById(R.id.dpTime);
		todaysDate=(TextView)findViewById(R.id.currentdate);
		Date currentDate = new Date(System.currentTimeMillis());
		String today= (currentDate.getYear()+1900)+"-"+(currentDate.getMonth()+1)+"-"+Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		todaysDate.setText(today);
		mDate=today;
		
		Button pickDate= (Button)findViewById(R.id.pkdate);
		pickDate.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		    	
				Intent pick = new Intent(HelloSarkarActivity.this, PickADate.class);
				HelloSarkarActivity.this.startActivityForResult(pick, 1);
		    }
		});
		
		//ArrayAdapter: list District
		ArrayAdapter<CharSequence> adapterDistrict = ArrayAdapter.createFromResource(
				  this, R.array.district_name, android.R.layout.simple_spinner_item );
		adapterDistrict.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);	
		spDistrict.setAdapter(adapterDistrict);
		
		//ArrayAdapter: list Complaint Type
				ArrayAdapter<CharSequence> adapterComplaint = ArrayAdapter.createFromResource(
						  this, R.array.complaint_type, android.R.layout.simple_spinner_item );
				adapterComplaint.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);	
				spType.setAdapter(adapterComplaint);			
		
	}			 


	public void postComplainSubmit(View view){
		//validation form fields;
		Complain complain=new Complain(this.getApplicationContext());
		ArrayList<String> errors=new ArrayList<String>();
		if(TextUtils.isEmpty(etName.getText().toString())){
			errors.add("Please enter Name of Person");			
		}else{
			complain.setName(etName.getText().toString());
		}
		if(TextUtils.isEmpty(etAdd.getText())){
			errors.add("Please enter complain details");			
		}else{
			complain.setAddress(etAdd.getText().toString());
		}
		if(TextUtils.isEmpty(etComplain.getText().toString())){
			errors.add("Please enter complaint");
		}else{
			complain.setComplain(etComplain.getText().toString());
		}		
	
		
		if(errors.isEmpty()){	
			location=locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			if(location==null){
				location=MyLocationListener.location;
			}
			complain.setDistrict(spDistrict.getSelectedItem().toString());
			String[] districts = getResources().getStringArray(R.array.district_name);
			complain.setComplainType(spType.getSelectedItem().toString());
			String[] complainType = getResources().getStringArray(R.array.complaint_type);
			complain.setDate(mDate);
		
			if(location != null){
				complain.setGps(location.getLatitude()+","+location.getLongitude());
				
				 /*******
				  * display toast only for developer test
				  * ******/
			//	 successtoast.setMessage(location.getLatitude()+","+location.getLongitude());
		    //     successtoast.displayToast();  
			}else{
				complain.setGps("unavailable");				
			}
			 complain.setPostParameters(complain,this.getApplicationContext());
			
           	try {
				String response = CustomHttpClient.executeHttpPost("http://apps.mobilenepal.net/hellosarkar/public/complain/receive", complain.getPostParameters());
				complain.setServerId(response);
				sarkarAdapter=new HelloSarkarDbAdapter(this);
				sarkarAdapter.open();
				long result=sarkarAdapter.createComplain(complain);
				sarkarAdapter.close();
			 	if(result != -1){
					successtoast.setMessage("complain successfully posted and saved");
				    successtoast.displayToast();
				}
				Intent i=new Intent(Alert.context,MenuActivity.class);
				Alert.context.startActivity(i);
				
				} catch (Exception e) {
					
				Alert alert=new Alert(this,"Do you want localy save your complain","No Internet Connection",complain);
				alert.displayDialog();		
			}
			
		}else{
			String error="";
			for(int i=0;i<errors.size();i++)
				error+=errors.get(i)+"\n";
			errortoast.setMessage( error);
			errortoast.displayToast();
			
		}
		
	}

	/** Register for the updates when Activity is in foreground */
	@Override
	protected void onResume() {
		super.onResume();
		
	}

	/** Stop the updates when Activity is paused */
	@Override
	protected void onPause() {
		super.onPause();
	//	populateFields();
		locManager.removeUpdates(locationListener);
	}
	
	//for Picker date function
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
            	try {
                    String value = data.getStringExtra("value");
                    if (value != null && value.length() > 0) {
                    	mDate= value;
                    	todaysDate.setText(mDate);
                    }
            	}catch (Exception e) {
                }
            	break;
        }
	}

	private boolean HaveNetworkConnection()
	{
		boolean HaveConnectedWifi = false;
		boolean HaveConnectedMobile = false;

		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] netInfo = cm.getAllNetworkInfo();
		for (NetworkInfo ni : netInfo)
		{
			if (ni.getTypeName().equalsIgnoreCase("WIFI"))
				if (ni.isConnected())
					HaveConnectedWifi = true;
			if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
				if (ni.isConnected())
					HaveConnectedMobile = true;
		}
		return HaveConnectedWifi || HaveConnectedMobile;
	}
		
}