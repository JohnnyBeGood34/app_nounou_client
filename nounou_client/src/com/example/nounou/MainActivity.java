package com.example.nounou;


import Manager.ConnectivityChangeReceiver;
import Manager.ConnexionManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		registerReceiver(
			      new ConnectivityChangeReceiver(), 
			      new IntentFilter(
			            ConnectivityManager.CONNECTIVITY_ACTION));
		
	}



}
