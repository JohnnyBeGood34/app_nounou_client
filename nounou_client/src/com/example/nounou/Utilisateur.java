package com.example.nounou;


import com.example.nounou.data.Nounou;
import com.example.nounou.data.NounouBdd;

import Manager.SessionManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import android.widget.Toast;

public class Utilisateur extends Activity {
	private static int RESULT_LOAD_IMAGE = 1;
	private String cheminImageProfil = "";
	Button an,sup,val;
	EditText nom,prenom,dateDeNaissance,civilite,adresse,email,tarifHoraire,descriptionPrestation,telephone,disponibilite,password;
	SessionManager session;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_utilisateur);
		/*
		 * IL FAUT CHANGER LA SYNTAXE 
		 */
		an = (Button) findViewById(R.id.buttonAn);
		val = (Button) findViewById(R.id.buttonVal);
		sup = (Button) findViewById(R.id.buttonSup);
		ImageView photoView = (ImageView) findViewById(R.id.imageViewProfil);
		photoView.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		//On lance l'intent de la gellerie android (photos)
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
		descriptionPrestation = (EditText)findViewById(R.id.edDescription);
		telephone = (EditText)findViewById(R.id.edTel);
		disponibilite = (EditText)findViewById(R.id.edDispo);
		password = (EditText)findViewById(R.id.edMdp);
		
		final NounouBdd db=new NounouBdd(this);
		db.open();
		
		/*IL faut r�cup�rer l'ID de la sessions !!!!!!!!!*/
		Bundle extra = getIntent().getExtras();
        final String  Variable = extra.getString("id");
        Nounou nounous=db.getNounouConnexion(Variable);
        
        session = new SessionManager(this); 

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
		
		if(!String.valueOf(nounous.getCheminPhoto()).equals(""))
		{
			photoView.setImageBitmap(BitmapFactory.decodeFile(nounous.getCheminPhoto()));
		}
		
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
                if(cheminImageProfil != "")
                {
                	nounous.setCheminPhoto(cheminImageProfil);
                }
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
				session.logoutUser();
				Toast.makeText(getApplicationContext(),
    	                "Votre compte a �t� supprim�",
    	                Toast.LENGTH_LONG).show();
				
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
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
 
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
