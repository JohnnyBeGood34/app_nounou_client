package com.example.nounou;


import Manager.ConnectivityChangeReceiver;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Positionnement gps du client
		LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

		if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
		    //Demande a l'utilisateur si il veut activer son gps
		    AlertDialog.Builder builder = new AlertDialog.Builder(this);
		    builder.setTitle("Location Manager");
		    builder.setMessage("Voulez vous activer la fonction GPS de votre telephone?");
		    builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
		        @Override
		        public void onClick(DialogInterface dialog, int which) {
		            //Lancement des settings pour activer le GPS
		            Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		            startActivity(i);
		        }
		    });
		    builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
		        @Override
		        public void onClick(DialogInterface dialog, int which) {
		        }
		    });
		    builder.create().show();
		}
		//Check de la connection internet
		registerReceiver(
			      new ConnectivityChangeReceiver(), 
			      new IntentFilter(
			            ConnectivityManager.CONNECTIVITY_ACTION));
		
		//Mise à l'écoute des coordonnées GPS du client
		LocationListener locationListener = new MyLocationListener(this);
		locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, locationListener);
		
	}



}
