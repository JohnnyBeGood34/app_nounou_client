package com.example.nounou;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;

public class MyLocationListener implements LocationListener {
	
	Context _contextApplication;
	 public double latitudeClient;
	public double longitudeClient;
	
	public MyLocationListener(Context contexte)
	{
		_contextApplication = contexte;
	}
	
	@Override
	public void onLocationChanged(Location localisationClient) {
		
		latitudeClient = localisationClient.getLatitude();
		 longitudeClient = localisationClient.getLongitude();
		String Text = "La current position du client est: " +"Latitude = " + latitudeClient +"Longitude = " + longitudeClient;
		//Toast.makeText( _contextApplication,Text,Toast.LENGTH_SHORT).show();
		//Log.i("Location",Text);
	}

	@Override
	public void onProviderDisabled(String arg0) {
		
		Toast.makeText( _contextApplication,"Gps Disabled",Toast.LENGTH_SHORT ).show();
	}

	@Override
	public void onProviderEnabled(String provider) {
		
		Toast.makeText( _contextApplication,"Gps Enabled",Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		

	}

}
