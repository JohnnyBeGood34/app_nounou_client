package com.example.nounou;


import Manager.ConnexionManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if(ConnexionManager.testConnexion(cm))
		{
			startActivity(new Intent(MainActivity.this, ListDesNounous.class));
		}
		else
		{
			Toast.makeText(this,"Vous devez être connecté à internet",Toast.LENGTH_LONG).show();
		}
		
	}



}
