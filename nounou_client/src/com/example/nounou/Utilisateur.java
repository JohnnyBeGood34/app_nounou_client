package com.example.nounou;


import com.example.nounou.data.Nounou;
import com.example.nounou.data.NounouBdd;

import Manager.SessionManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Utilisateur extends Activity {
	Button an,sup,val;
	EditText nom,prenom,dateDeNaissance,civilite,adresse,email,tarifHoraire,descriptionPrestation,telephone,disponibilite,password;
	SessionManager session;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_utilisateur);
		an = (Button) findViewById(R.id.buttonAn);
		val = (Button) findViewById(R.id.buttonVal);
		sup = (Button) findViewById(R.id.buttonSup);
		
		
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
		
		/*IL faut récupérer l'ID de la sessions !!!!!!!!!*/
		Bundle extra = getIntent().getExtras();
        final String  Variable = extra.getString("id");
        Nounou nounous=db.getNounouConnexion(Variable);
        


        nom.setText(String.valueOf(nounous.getNom()));
        prenom.setText(String.valueOf(nounous.getPrenom()));
        dateDeNaissance.setText(String.valueOf(nounous.getDateDeNaissance()));
        civilite.setText(String.valueOf(nounous.getCivilite()));
        adresse.setText(String.valueOf(nounous.getAdresse()));
        email.setText(String.valueOf(nounous.getEmail()));
        tarifHoraire.setText(String.valueOf(nounous.getTarifHoraire()));
        descriptionPrestation.setText(String.valueOf(nounous.getDescriptionPrestation()));
        telephone.setText(String.valueOf(nounous.getTelephone()));
        disponibilite.setText(String.valueOf(nounous.getDisponibilite()));
        password.setText(String.valueOf(nounous.getPassword()));
		
		
		
		an.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		Intent intent=new Intent(Utilisateur.this,ListDesNounous.class);
    			startActivity(intent);
        	}
		});
		val.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		
        		Nounou nounous= new Nounou();
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
        		nounous.setPassword(password.getText().toString());
                		
				//Log.i("visiteur",nounous.toString());
                db.updateNounou(nounous);
        		
        		Intent intent=new Intent(Utilisateur.this,ListDesNounous.class);
        		intent.putExtra("id",email.getText().toString());
    			startActivity(intent);
        	}
		});
		sup.setOnClickListener(new OnClickListener() {
			@Override
        	public void onClick(View v) {
				db.removeNounou(Variable);
        		Intent intent=new Intent(Utilisateur.this,ListDesNounous.class);
    			startActivity(intent);
        	}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.utilisateur, menu);
		return true;
	}
}
