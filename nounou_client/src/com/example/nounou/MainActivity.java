package com.example.nounou;


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
		registerReceiver(
			      new ConnectivityChangeReceiver(), 
			      new IntentFilter(
			            ConnectivityManager.CONNECTIVITY_ACTION));
		
	}



}
