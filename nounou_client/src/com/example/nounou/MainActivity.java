package com.example.nounou;

import java.util.List;

import com.example.nounou.data.Nounou;
import com.example.nounou.data.NounouBdd;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		

		final NounouBdd nnbdd = new NounouBdd(this);
		nnbdd.open();
		Nounou nn = new Nounou("b","b", "b", "b", "b", "b", "b", "b", "b", "b", "b", "b", "b");
		nnbdd.insertNounou(nn);
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
