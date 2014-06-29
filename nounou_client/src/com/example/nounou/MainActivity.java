package com.example.nounou;

import Manager.ConnectivityChangeReceiver;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			// Demande a l'utilisateur si il veut activer son gps
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("GPS manager");
			builder.setMessage("Voulez vous activer la fonction GPS de votre telephone?");
			builder.setPositiveButton("Oui",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// Lancement des settings pour activer le GPS
							Intent i = new Intent(
									Settings.ACTION_LOCATION_SOURCE_SETTINGS);
							startActivity(i);
						}
					});
			builder.setNegativeButton("Non",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
			builder.create().show();
		}
		
		LocationListener locationListener = null;
		// Mise � l'�coute des coordonn�es GPS du client
		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			locationListener = new MyLocationListener(this);
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
		}
		
		// Check de la connection internet
		registerReceiver(new ConnectivityChangeReceiver(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

	}

}
