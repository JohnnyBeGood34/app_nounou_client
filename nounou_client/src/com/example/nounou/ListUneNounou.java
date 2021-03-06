package com.example.nounou;


import java.util.HashMap;

import com.example.nounou.data.ApiNounou;


import android.net.ConnectivityManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ListUneNounou extends Activity {
	TextView nom,prenom,date,tarif,dispo,tel,des,email;
	ImageView boutonRetour;
	ImageView boutonMap;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_list_une_nounou);

			setTitle("Detail nounou");
			//final NounouBdd db=new NounouBdd(this);
			//db.open();
			Bundle extra = getIntent().getExtras();
	        final String  idNounou = extra.getString("id");

			boutonRetour = (ImageView)findViewById(R.id.buttonRetour);
			boutonMap = (ImageView)findViewById(R.id.buttonMap);
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
			ConnectivityManager cm =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			ApiNounou.getUneNounou(UrlServer.getServerUrl()+"/api/nounou/"+idNounou,this,arrayTextView,photoView,cm);
			
			
			boutonRetour.setOnClickListener(new OnClickListener() {
	        	@Override
	        	public void onClick(View v) {
	        		Intent intent=new Intent(ListUneNounou.this,ListDesNounous.class);
	    			startActivity(intent);
	        	}
	        });
			
			boutonMap.setOnClickListener(new OnClickListener() {
	        	@Override
	        	public void onClick(View v) {
	        		Intent intent=new Intent(ListUneNounou.this,MapNounou.class);
	        		intent.putExtra("idNounou",idNounou);
	    			startActivity(intent);
	        	}
	        });
			
		}
	}
