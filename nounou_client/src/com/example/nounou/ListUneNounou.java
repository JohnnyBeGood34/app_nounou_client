package com.example.nounou;


import java.util.HashMap;

import com.example.nounou.data.ApiNounou;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ListUneNounou extends Activity {
	TextView nom,prenom,date,tarif,dispo,tel,des,email;
	Button ret;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_list_une_nounou);
			
			//final NounouBdd db=new NounouBdd(this);
			//db.open();
			Bundle extra = getIntent().getExtras();
	        String  Variable = extra.getString("id");
	        
	        
						
			ret = (Button)findViewById(R.id.buttonRetour);
			HashMap<String, TextView> arrayTextView = new HashMap<String, TextView>();
			
			nom=(TextView) findViewById(R.id.tvNom);
			prenom=(TextView)findViewById(R.id.tvPrenom);
			date=(TextView)findViewById(R.id.tvDateNaissance);
			tarif=(TextView)findViewById(R.id.tvTarif);
			dispo=(TextView)findViewById(R.id.tvHoraire);
			tel=(TextView)findViewById(R.id.tvTel);
			des=(TextView)findViewById(R.id.tvDescription);
			email=(TextView)findViewById(R.id.tvEmail);
			ImageView photoView = (ImageView) findViewById(R.id.imageView1);
			
			arrayTextView.put("nom", nom);
			arrayTextView.put("prenom", prenom);
			arrayTextView.put("date", date);
			arrayTextView.put("tarif", tarif);
			arrayTextView.put("dispo", dispo);
			arrayTextView.put("tel", tel);
			arrayTextView.put("des", des);
			arrayTextView.put("email", email);
			
			ApiNounou.getUneNounou("http://172.20.116.10:3000/api/nounou/"+Variable,this,arrayTextView,photoView);
			
			/*
			nom.setText(String.valueOf(nounou.getNom()));
			prenom.setText(String.valueOf(nounou.getPrenom()));
			date.setText(String.valueOf(nounou.getDateDeNaissance()));
			tarif.setText(String.valueOf(nounou.getTarifHoraire()));
			dispo.setText(String.valueOf(nounou.getDisponibilite()));
			tel.setText(String.valueOf(nounou.getTelephone()));
			des.setText(String.valueOf(nounou.getDescriptionPrestation()));
			email.setText(String.valueOf(nounou.getEmail()));*/
			
			
			ret.setOnClickListener(new OnClickListener() {
	        	@Override
	        	public void onClick(View v) {
	        		Intent intent=new Intent(ListUneNounou.this,ListDesNounous.class);
	    			startActivity(intent);
	        	}
	        });
			
		}


	}
