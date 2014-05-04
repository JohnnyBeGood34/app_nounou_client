package com.example.nounou;

import com.example.nounou.data.Nounou;
import com.example.nounou.data.NounouBdd;

import Manager.SessionManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InscriptionNounou extends Activity {
	//Variable permettant d'obtenir la reponse de l'acivity gallery
	private static int RESULT_LOAD_IMAGE = 1;
	Button boutonAnnuler,boutonvalider,boutonUploadPhoto;
	EditText id,nom,prenom,dateDeNaissance,civilite,adresse,email,tarifHoraire,descriptionPrestation,telephone,disponibilite,password;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inscription_nounou);
		//Buttons
		boutonAnnuler = (Button) findViewById(R.id.buttonAn);
		boutonvalider = (Button) findViewById(R.id.buttonVal);
		//boutonUploadPhoto = (Button) findViewById(R.id.buttonUploadPhoto);
		//id = (EditText)findViewById(5);
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
		/*
		 * Action sur le click du bouton d'upload
		 
		boutonUploadPhoto.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		//On lance l'intent de la gellerie android (photos)
        		Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        		startActivityForResult(i, RESULT_LOAD_IMAGE);
        	}
		});*/
		
		boutonAnnuler.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		Intent intent=new Intent(InscriptionNounou.this,ListDesNounous.class);
    			startActivity(intent);
        	}
		});
		boutonvalider.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		
        		Nounou nounous= new Nounou();
        		//nounous.setIdNounou(id.getText().toString());
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
        		String etLogin = email.getText().toString();
                String mdpLogin = password.getText().toString();
         		SessionManager sm = new SessionManager(getApplicationContext());
         		sm.createUserLoginSession(etLogin, mdpLogin);
         		Toast.makeText(getApplicationContext(),etLogin+
     	                ", vous êtes Connecté!",
     	                Toast.LENGTH_LONG).show();
    			startActivity(intent);
        	}
		});
	}

}
