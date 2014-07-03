package com.example.nounou;

import org.json.JSONException;

import com.example.nounou.data.ApiNounou;
import com.example.nounou.data.Nounou;

import Manager.VerifChampsManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
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
		setTitle("Inscription, veuillez renseigner tous les champs");
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
				boolean testNom = verifChamps.nom(nom.getText().toString());
				boolean testPrenom = verifChamps.prenom(prenom.getText().toString());
				boolean testDateDeNaissance = verifChamps.dateDeNaissance(dateDeNaissance.getText().toString());
				boolean testCivilite = verifChamps.civilite(civilite.getText().toString());
				boolean testAdresse = verifChamps.adresse(adresse.getText().toString());
				boolean testVille = verifChamps.ville(ville.getText().toString());
				boolean testEmail = verifChamps.email(email.getText().toString());
				boolean testTarif = verifChamps.tarif(tarifHoraire.getText().toString());
				boolean testDescription = verifChamps.description(descriptionPrestation.getText().toString());
				boolean testTel = verifChamps.tel(telephone.getText().toString());
				boolean testDisponibilite = verifChamps.dispo(disponibilite.getText().toString());
				boolean testmdp = verifChamps.mdp(password.getText().toString());
				

				if(testNom == false|| testPrenom == false|| testDateDeNaissance== false || testCivilite == false|| testAdresse== false || testVille== false
						|| testEmail== false || testTarif== false	|| testDescription== false || testTel== false || testDisponibilite == false	|| testmdp == false ){
						if(testNom == false){
							nom.setError(Html.fromHtml("<font color='red'>Nom non valide, pas chiffre</font>"));
						}
						if(testPrenom == false){
							prenom.setError(Html.fromHtml("<font color='red'>Prenom non valide, pas chiffre</font>"));
						}
						if(testDateDeNaissance == false){
							dateDeNaissance.setError(Html.fromHtml("<font color='red'>Format Date non valide</font>"));
						}
						if(testCivilite == false){
							civilite.setError(Html.fromHtml("<font color='red'>Civilité non valide, pas chiffre</font>"));
						}
						if(testAdresse == false){
							adresse.setError(Html.fromHtml("<font color='red'>Adresse non valide</font>"));
						}
						if(testVille == false){
							ville.setError(Html.fromHtml("<font color='red'>Ville non valide, pas chiffre</font>"));
						}
						if(testEmail == false){
							email.setError(Html.fromHtml("<font color='red'>Email non valide</font>"));
						}
						if(testTarif == false){
							tarifHoraire.setError(Html.fromHtml("<font color='red'>Le tarif n'accepte que des chiffres</font>"));
						}
						if(testDescription == false){
							descriptionPrestation.setError(Html.fromHtml("<font color='red'>Valeur incorrect</font>"));
						}
						if(testTel == false){
							telephone.setError(Html.fromHtml("<font color='red'>Télephone non valide</font>"));
						}
						if(testDisponibilite == false){
							disponibilite.setError(Html.fromHtml("<font color='red'>Valeur erronée</font>"));
						}
						if(testmdp == false){
							password.setError(Html.fromHtml("<font color='red'>Le mdp doit contenir 6 à 20 caractères dont au moins 1 minuscule, 1 majuscule et 1 chiffre</font>"));
						}
				}
				
				else{
					ConnectivityManager cm =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
					try {
						ApiNounou.createNounou(InscriptionNounou.this,nounou,cm);
						Intent intent=new Intent(InscriptionNounou.this,ListDesNounous.class);
		        		intent.putExtra("id",email.getText().toString());
		    			startActivity(intent);
						} catch (JSONException e) {
							
							e.printStackTrace();
						}
				}
		       
            }
        	});
        }

}
