package com.example.nounou;

import org.json.JSONException;

import com.example.nounou.data.ApiNounou;
import com.example.nounou.data.Nounou;

import Manager.VerifChampsManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class InscriptionNounou extends Activity {
/*public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.inscription_nounou, menu);
	return true;
	}*/
	//Variable permettant d'obtenir la reponse de l'acivity gallery
	private static int RESULT_LOAD_IMAGE = 1;
	Button boutonAnnuler,boutonvalider,boutonUploadPhoto;
	EditText id,nom,prenom,dateDeNaissance,civilite,adresse,ville,email,tarifHoraire,descriptionPrestation,telephone,disponibilite,password;
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
		ville= (EditText)findViewById(R.id.edVille);
		email = (EditText)findViewById(R.id.edEmail);
		tarifHoraire = (EditText)findViewById(R.id.edTarif);
		descriptionPrestation = (EditText)findViewById(R.id.edDescription);
		telephone = (EditText)findViewById(R.id.edTel);
		disponibilite = (EditText)findViewById(R.id.edDispo);
		password = (EditText)findViewById(R.id.edMdp);
		
		//final NounouBdd db=new NounouBdd(this);
		//db.open();
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
        		Log.i("Api","click valider");   		              
                
                Nounou nounou= new Nounou();
				
				nounou.setNom(nom.getText().toString());
				nounou.setPrenom(prenom.getText().toString());
				nounou.setDateDeNaissance(dateDeNaissance.getText().toString());
				nounou.setCivilite(civilite.getText().toString());
				nounou.setAdresse(adresse.getText().toString());
				nounou.setVille(ville.getText().toString());
				nounou.setEmail(email.getText().toString());
				nounou.setTarifHoraire(tarifHoraire.getText().toString());
				nounou.setDescriptionPrestation(descriptionPrestation.getText().toString());
				nounou.setTelephone(telephone.getText().toString());
				nounou.setDisponibilite(disponibilite.getText().toString());
				nounou.setCheminPhoto("aucun");
				nounou.setPassword(password.getText().toString());
				
				VerifChampsManager verifChamps = new VerifChampsManager();
				Boolean testNom = verifChamps.nom(nom.getText().toString());
				Boolean testPrenom = verifChamps.prenom(prenom.getText().toString());
				Boolean testDateDeNaissance = verifChamps.dateDeNaissance(dateDeNaissance.getText().toString());
				Boolean testCivilite = verifChamps.civilite(civilite.getText().toString());
				Boolean testAdresse = verifChamps.adresse(adresse.getText().toString());
				Boolean testVille = verifChamps.ville(ville.getText().toString());
				Boolean testEmail = verifChamps.email(email.getText().toString());
				Boolean testTarif = verifChamps.tarif(tarifHoraire.getText().toString());
				Boolean testDescription = verifChamps.description(descriptionPrestation.getText().toString());
				Boolean testTel = verifChamps.tel(telephone.getText().toString());
				Boolean testDisponibilite = verifChamps.dispo(disponibilite.getText().toString());
				Boolean testmdp = verifChamps.mdp(password.getText().toString());
				
				if( (testNom || testPrenom || testDateDeNaissance || testCivilite || testAdresse || testVille || testEmail || testTarif
						|| testDescription || testTel || testDisponibilite || testmdp) == false ){
					if(testNom == false){
						nom.setError(Html.fromHtml("<font color='red'>Valeur incorrect</font>"));
					}
					if(testPrenom == false){
						prenom.setError(Html.fromHtml("<font color='red'>Valeur incorrect</font>"));
					}
					if(testDateDeNaissance == false){
						dateDeNaissance.setError(Html.fromHtml("<font color='red'>Valeur incorrect</font>"));
					}
					if(testCivilite == false){
						civilite.setError(Html.fromHtml("<font color='red'>Valeur incorrect</font>"));
					}
					if(testAdresse == false){
						adresse.setError(Html.fromHtml("<font color='red'>Valeur incorrect</font>"));
					}
					if(testVille == false){
						ville.setError(Html.fromHtml("<font color='red'>Valeur incorrect</font>"));
					}
					if(testEmail == false){
						email.setError(Html.fromHtml("<font color='red'>Valeur incorrect</font>"));
					}
					if(testTarif == false){
						tarifHoraire.setError(Html.fromHtml("<font color='red'>Valeur incorrect</font>"));
					}
					if(testDescription == false){
						descriptionPrestation.setError(Html.fromHtml("<font color='red'>Valeur incorrect</font>"));
					}
					if(testTel == false){
						telephone.setError(Html.fromHtml("<font color='red'>Valeur incorrect</font>"));
					}
					if(testDisponibilite == false){
						disponibilite.setError(Html.fromHtml("<font color='red'>Valeur incorrect</font>"));
					}
					if(testmdp == false){
						password.setError(Html.fromHtml("<font color='red'>Valeur incorrect</font>"));
					}
				}
				else{
					try {
						ApiNounou.createNounou(InscriptionNounou.this,nounou);
						} catch (JSONException e) {
							
							e.printStackTrace();
						}
				}
		       
            }
        	});
        }

}
