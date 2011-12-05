package com.mobilenepal.hackathon1.HelloSarkar;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

public class MyLocationListener implements LocationListener {
   public static Location location;
    public void onLocationChanged(Location loc) {		
		location=loc;
		Log.i("location changed", " latitude:"+loc.getLatitude()+" longitude:"+loc.getLongitude());
	
	}

	public void onProviderDisabled(String provider) {
		Log.i("Provider disabled", " Provider disabled");
		// TODO Auto-generated method stub
		
	}

	public void onProviderEnabled(String provider) {
		Log.i("Provider enabled", "Provider enabled ");
		// TODO Auto-generated method stub
		
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		Log.i("status changed", " status:"+status);
		// TODO Auto-generated method stub
		
	}

}
