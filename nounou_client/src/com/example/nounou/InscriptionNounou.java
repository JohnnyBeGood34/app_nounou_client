package com.example.nounou;

import com.example.nounou.data.Nounou;
import com.example.nounou.data.NounouBdd;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class InscriptionNounou extends Activity {

	Button an,val;
	EditText id,nom,prenom,dateDeNaissance,civilite,adresse,email,tarifHoraire,descriptionPrestation,telephone,disponibilite,password;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inscription_nounou);
		an = (Button) findViewById(R.id.buttonAn);
		val = (Button) findViewById(R.id.buttonVal);
		
		id = (EditText)findViewById(R.id.edId);
		nom = (EditText)findViewById(R.id.edNom);
		prenom = (EditText)findViewById(R.id.edPrenom);
		dateDeNaissance = (EditText)findViewById(R.id.edDate);
		civilite = (EditText)findViewById(R.id.edCivilite);
		adresse = (EditText)findViewById(R.id.edAdresse);
		email = (EditText)findViewById(R.id.edEmail);
		tarifHoraire = (EditText)findViewById(R.id.edTarif);
		descriptionPrestation = (EditText)findViewById(R.id.edDescription);
		telephone = (EditText)findViewById(R.id.edTel);
		disponibilite = (EditText)findViewById(R.id.edDispo);
		password = (EditText)findViewById(R.id.edMdp);
		
		final NounouBdd db=new NounouBdd(this);
		db.open();
		
		
		
		an.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		Intent intent=new Intent(InscriptionNounou.this,ListDesNounous.class);
    			startActivity(intent);
        	}
		});
		val.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		
        		Nounou nounous= new Nounou();
        		nounous.setIdNounou(id.getText().toString());
        		nounous.setNom(nom.getText().toString());
        		nounous.setPrenom(prenom.getText().toString());
        		nounous.setDateDeNaissance(dateDeNaissance.getText().toString());
        		nounous.setCivilite(civilite.getText().toString());
        		nounous.setAdresse(adresse.getText().toString());
        		nounous.setEmail(email.getText().toString());
        		nounous.setTarifHoraire(tarifHoraire.getText().toString());
        		nounous.setDescriptionPrestation(descriptionPrestation.getText().toString());
        		nounous.setTelephone(telephone.getText().toString());
        		nounous.setDisponibilite(disponibilite.getText().toString());
        		nounous.setCheminPhoto("aucun");
        		nounous.setPassword(password.getText().toString());
                db.insertNounou(nounous);
        		
        		Intent intent=new Intent(InscriptionNounou.this,ListDesNounous.class);
    			startActivity(intent);
        	}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inscription_nounou, menu);
		return true;
	}

}
