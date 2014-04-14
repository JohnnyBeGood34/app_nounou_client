package com.example.nounou;


import com.example.nounou.data.BaseSQLite;
import com.example.nounou.data.Nounou;
import com.example.nounou.data.NounouBdd;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

<<<<<<< HEAD
		startActivity(new Intent(MainActivity.this, ListNounou.class));
		
		NounouBdd db = new NounouBdd(this);
		String idNounou="b";
		String nom="b";
		String prenom="b";
		String dateDeNaissance="b";
		String civilite="b";
		String adresse="b";
		String email="b";
		String tarifHoraire="b";
		String descriptionPrestation="b";
		String telephone="b";
		String disponibilite="b";
		String cheminPhoto="b";
		String password="b";
		Nounou nounou = new Nounou(idNounou,nom,prenom,dateDeNaissance, civilite,adresse, email, tarifHoraire, descriptionPrestation, telephone, disponibilite, cheminPhoto, password);
		Nounou nounou2 = new Nounou("2","c","c","c","c","c","c","c","c","c","c","c","c");
		Nounou nounou3 = new Nounou("3","bgu","bgu","bgu","bgu","bgu","bgu","bgu","bgu","bgu","bgu","bgu","bgu");
		db.open();
		db.insertNounou(nounou);
		db.insertNounou(nounou2);
		db.insertNounou(nounou3);
		db.close(); 
=======
		startActivity(new Intent(MainActivity.this, ListDesNounous.class));
>>>>>>> 49a6840f31a7ec5816f1d244c03aa3774ae91567

		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
