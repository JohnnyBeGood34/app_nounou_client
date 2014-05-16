package com.example.nounou;

import com.example.nounou.data.Nounou;
import com.example.nounou.data.NounouBdd;

import Manager.SessionManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
         //Cha�nes de caract�res pour les validations de saisie
         String mailAVerif = email.getText().toString();
         String dateAVerif = dateDeNaissance.getText().toString();
         String telAVerif = telephone.getText().toString();
         String tarifAVerif = tarifHoraire.getText().toString();
         //bool�ens pour les conditions if de validation du formulaire
         boolean verifEMail = db.verificationMail(mailAVerif);
         boolean verifDate = db.verificationDate(dateAVerif);
         boolean verifTel = db.verificationTelephone(telAVerif);
         boolean verifTarif = db.verificationTarif(tarifAVerif);
         //nounou renvoy�e par la requ�te (select * from nounou where email = "email saisi")
         Nounou nounou = db.getNounouConnexion(mailAVerif);
         //variables pour l'affichage des toasts
         int duration = Toast.LENGTH_SHORT;
         String messageMail1 = "Vous avez saisi un email d�j� utilis�.";
         String messageMail2 = "Veuillez saisir un email valide.";
         String messageDate = "Veuillez entrer une date valide au format DD/MM/AAAA.";
         String messageTarif = "Veuillez entrer un montant de tarif horaire en chiffre.";
         String messageTel = "Veuillez entrer un num�ro de t�l�phone valide";
         Context context = getApplicationContext();

         	if (verifEMail == true)
         	{
         		if (verifDate == true)
         		{
         			if (verifTel == true)
         			{
         				if(verifTarif == true)
         				{
         					if (nounou == null)
         					{
         						//attribue les saisies aux propri�t� de la nounou cr��e
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
         						//ajoute la nounou cr��e dans la base
         						db.insertNounou(nounous);
         						//Renvoie vers la liste des nounous
         						Intent intent = new Intent(InscriptionNounou.this,ListDesNounous.class);
         						startActivity(intent);
         					}
         					else
         					{
         						Toast.makeText(context, messageMail1, duration).show();
         					}
         				}
         				else
         				{
         					Toast.makeText(context, messageTarif, duration).show();
         				}
         			}
         			else
         			{
         				Toast.makeText(context, messageTel, duration).show();	
         			}
         		}
         		else
         		{
         			Toast.makeText(context, messageDate, duration).show();
         		}
         	}
         	else
         	{
         		Toast.makeText(context, messageMail2, duration).show();
         	}
         }
});
}
/*public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.inscription_nounou, menu);
	return true;
	}*/

}
