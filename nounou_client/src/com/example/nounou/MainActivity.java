package com.example.nounou;

import java.util.Timer;

import com.example.nounou.data.ApiNounou;


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
		// Mise à l'écoute des coordonnées GPS du client
		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			locationListener = new MyLocationListener(this);
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
		}
		
		// Check de la connection internet
		registerReceiver(new ConnectivityChangeReceiver(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        
		
		/*
		 * Clear du cache de Volley à intervalle régulier
		 * */
		
		TimerCache clearTask=new TimerCache(this);
		Timer timer=new Timer();
		/*
		 * 1st Param : tache à effectuer
		 * 2nd Param : temps en millisecondes à partir duquel commencer la tache
		 * 3rd Param : intervalle en millisecondes
		 * */
		timer.schedule(clearTask, 10*60*1000,10*60*1000);//ici 10 => 10 minutes
		
		
	}

}
