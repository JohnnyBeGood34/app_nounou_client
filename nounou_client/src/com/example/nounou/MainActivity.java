package com.example.nounou;

import java.util.Timer;

import Manager.ConnectivityChangeReceiver;
import Manager.ConnexionManager;
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
		

		final LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
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
							// Check de la connection internet
							registerReceiver(new ConnectivityChangeReceiver(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
						}
					});
			builder.create().show();
		}
		
		
		
        
		
		/*
		 * Clear du cache de Volley � intervalle r�gulier si il y a une connexion
		 * */
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if(ConnexionManager.testConnexion(connectivityManager)){
			TimerCache clearTask=new TimerCache(this);
			Timer timer=new Timer();
			/*
			 * 1st Param : tache � effectuer
			 * 2nd Param : temps en millisecondes � partir duquel commencer la tache
			 * 3rd Param : intervalle en millisecondes
			 * */
			timer.schedule(clearTask, 10*60*1000,10*60*1000);//ici 10 => 10 minutes
		}
		/*
		UrlServerAttenteDao urlAttente = new UrlServerAttenteDao(this);
		urlAttente.open();
		List<UrlServerAttente> listeTest = urlAttente.getAllurl();
		for(UrlServerAttente url : listeTest){
			Log.i("INFO URL ATTENTE",url.getCallurl());
		}
		urlAttente.deleteAll();
		for(UrlServerAttente url : listeTest){
			Log.i("INFO URL ATTENTE AFTER DELETE",url.getCallurl());
		}
		urlAttente.close();*/
	}

	@Override
	public void onResume(){
	    super.onResume();

		final LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		LocationListener locationListener = null;
		// Mise � l'�coute des coordonn�es GPS du client
		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

			// Check de la connection internet
			registerReceiver(new ConnectivityChangeReceiver(), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
			locationListener = new MyLocationListener(MainActivity.this);
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
		}
	}
}
