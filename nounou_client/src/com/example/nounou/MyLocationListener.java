package com.example.nounou;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;

public class MyLocationListener implements LocationListener {
	Context contextApplication;
	
	public MyLocationListener(Context contexte)
	{
		contextApplication = contexte;
	}
	
	@Override
	public void onLocationChanged(Location localisationClient) {
		// TODO Auto-generated method stub
		double latitudeClient = localisationClient.getLatitude();
		double longitudeClient = localisationClient.getLongitude();
		
		String Text = "La current position du client est: " +"Latitude = " + latitudeClient +"Longitude = " + longitudeClient;
		Toast.makeText( contextApplication,Text,Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		Toast.makeText( contextApplication,"Gps Disabled",Toast.LENGTH_SHORT ).show();
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		Toast.makeText( contextApplication,"Gps Enabled",Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

}
