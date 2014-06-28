package com.example.nounou;


import java.util.HashMap;

import org.json.JSONException;

import com.example.nounou.data.ApiNounou;
import com.example.nounou.data.Nounou;

import Manager.SessionManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.MediaColumns;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class Utilisateur extends Activity {
	
	
	private static int RESULT_LOAD_IMAGE = 1;
	private String cheminImageProfil = "";
	Button annuler,supprimer,valider;
	EditText nom,prenom,dateDeNaissance,civilite,adresse,email,tarifHoraire,description,telephone,disponibilite,password;
	SessionManager session;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_utilisateur);
		
		annuler = (Button) findViewById(R.id.buttonAn);
		valider = (Button) findViewById(R.id.buttonVal);
		supprimer = (Button) findViewById(R.id.buttonSup);
		ImageView photoView = (ImageView) findViewById(R.id.imageViewProfil);
		
		
		photoView.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		//On lance l'intent de la gallerie android (photos)
        		Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        		startActivityForResult(i, RESULT_LOAD_IMAGE);
        	}
		});
		
		nom = (EditText)findViewById(R.id.edNom);
		prenom = (EditText)findViewById(R.id.edPrenom);
		dateDeNaissance = (EditText)findViewById(R.id.edDate);
		civilite = (EditText)findViewById(R.id.edCivilite);
		adresse = (EditText)findViewById(R.id.edAdresse);
		email = (EditText)findViewById(R.id.edEmail);
		tarifHoraire = (EditText)findViewById(R.id.edTarif);
		description = (EditText)findViewById(R.id.edDescription);
		telephone = (EditText)findViewById(R.id.edTel);
		disponibilite = (EditText)findViewById(R.id.edDispo);
		password = (EditText)findViewById(R.id.edMdp);
		
		HashMap<String, EditText> listEditText = new HashMap<String, EditText>();
		
		listEditText.put("nom",nom);
		listEditText.put("prenom",prenom);
		listEditText.put("dateDeNaissance",dateDeNaissance);
		listEditText.put("email",email);
		listEditText.put("tarifHoraire",tarifHoraire);
		listEditText.put("telephone",telephone);
		listEditText.put("civilite",civilite);
		listEditText.put("password",password);
		listEditText.put("description",description);
		listEditText.put("disponibilite",disponibilite);
		listEditText.put("adresse",adresse);
		
		
		/* On récupère l'id de la Nounou en session */	
		Bundle extra = getIntent().getExtras();
        final String  idNounou = extra.getString("id");
        
        /* Appel de l'API qui va remplir les champs du profil en fonction de l'ID de la Nounou */
        ApiNounou.getProfil(idNounou, this,listEditText,photoView);
        
               
		
		annuler.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		Intent intent=new Intent(Utilisateur.this,ListDesNounous.class);
    			startActivity(intent);
        	}
		});
		
		/* Validation du formulaire de mise a jour */		
		valider.setOnClickListener(new OnClickListener() {
			
        	@Override
        	public void onClick(View v) {
        		
        		Nounou nounou=new Nounou();        		
        		nounou.setNom(nom.getText().toString());
        		nounou.setPrenom(prenom.getText().toString());
        		nounou.setEmail(email.getText().toString());
        		nounou.setAdresse(adresse.getText().toString());
        		nounou.setPassword(password.getText().toString());
        		nounou.setCivilite(civilite.getText().toString());
        		nounou.setDateDeNaissance(dateDeNaissance.getText().toString());
        		nounou.setDescriptionPrestation(description.getText().toString());
        		nounou.setTarifHoraire(tarifHoraire.getText().toString());
        		nounou.setDisponibilite(disponibilite.getText().toString());
        		nounou.setTelephone(telephone.getText().toString());
        		
        		try {
					ApiNounou.updateProfil(idNounou,Utilisateur.this,nounou,cheminImageProfil);
				} catch (JSONException e) {					
					e.printStackTrace();
				}
        		        		
        		
        		Intent intent=new Intent(Utilisateur.this,ListDesNounous.class);
        		intent.putExtra("id",email.getText().toString());
    			startActivity(intent);
        	}
		});
		
		
		supprimer.setOnClickListener(new OnClickListener() {
			
			@Override
        	public void onClick(View v) {
				
				
				ApiNounou.deleteProfil(Utilisateur.this,idNounou);
								
        	}
		});
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 * On activity result de l'activity gellery
	 */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaColumns.DATA };
 
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
 
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            
            Log.i("PICTURE PATH++++++++++++++",picturePath);
            
            ImageView imageViewProfil = (ImageView) findViewById(R.id.imageViewProfil);
            imageViewProfil.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            cheminImageProfil = picturePath;
        }
     
     
    }

}
