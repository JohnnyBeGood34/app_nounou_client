package com.example.nounou;

import java.util.Timer;

import Manager.ConnectivityChangeReceiver;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.IntentFilter;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		/*LocationListener locationListener = null;
		// Mise à l'écoute des coordonnées GPS du client
		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			locationListener = new MyLocationListener(this);
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
		}*/
		
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
