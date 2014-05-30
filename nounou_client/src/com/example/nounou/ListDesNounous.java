package com.example.nounou;

import com.example.nounou.data.ApiNounou;

import Manager.SessionManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class ListDesNounous extends Activity{
	String URL = UrlServer.getServerUrl();
	EditText rec;
	Button connexion,inscription;
	TextView dist;
	private ListView mainListView ;
	//private NounouAdapter _nounouManager;
	private SeekBar volumeControl = null;
	SessionManager session;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_des_nounous);
		connexion=(Button)findViewById(R.id.buttonConnexion);
		inscription=(Button)findViewById(R.id.buttonInscription);
		dist=(TextView)findViewById(R.id.tvdistance);
		
		volumeControl = (SeekBar) findViewById(R.id.volume_bar);
		
		
		
		 // User Session Manager
        session = new SessionManager(this);
        if(session.isUserLoggedIn()==true){
        	connexion.setText("Déconnexion");
        	inscription.setText("Mon Compte");
        }
        else{
        	inscription.setText("Inscription");
        	connexion.setText("Connexion");
        }
        
		// Find the ListView resource.   
		mainListView = (ListView) findViewById( R.id.mainListView ); 
		/*
		 * TODO
		 * A remplacer dans l'URL les coordonnées réelles 
		 * */
		ApiNounou.getAllNounousApi(URL+"/api/nounous/latitude/44/longitude/4/kilometres/2000",this,mainListView);
		
        connexion.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		if(session.isUserLoggedIn()==true){
        			session.logoutUser();
        			Toast.makeText(getApplicationContext(),
        	                "Vous êtes déconnecté",
        	                Toast.LENGTH_LONG).show();
        		}
        		else{
	        		Intent intent=new Intent(ListDesNounous.this,PageConnexion.class);
	    			startActivity(intent);
        		}
        	}
        });
        
        /*Permet de visualiser son compte si connecté sinon de s'inscrire*/
        inscription.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		
    			if(session.isUserLoggedIn()==true){
    				Intent intent=new Intent(ListDesNounous.this,Utilisateur.class);
    				String idNounou = session.getLogin();
    				intent.putExtra("id",idNounou);
	    			startActivity(intent);
        		}
        		else{
        			Intent intent=new Intent(ListDesNounous.this,InscriptionNounou.class);
        			startActivity(intent);
        		}
        	}
        });    
        
        volumeControl.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            int progressChanged = 0;
 
            @Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                progressChanged = progress;
               
            }
 
            @Override
			public void onStartTrackingTouch(SeekBar seekBar) {               
            }
            
            /*Au changement des kilomètres choisis pour la recherche*/
            @Override
			public void onStopTrackingTouch(SeekBar seekBar) {
            	dist.setText(String.valueOf(progressChanged+"Km"));
            	/*On actualise la liste des Nounous se trouvant dans le périmètre choisi*/
            	/*TODO
            	 * Remplacer la latitude/longitude par les véritbles coordonnées
            	 * */
            	ApiNounou.getAllNounousApi(URL+"/api/nounous/latitude/44/longitude/4/kilometres/"+String.valueOf(progressChanged),ListDesNounous.this,mainListView);
            }
        });
        
	}
}
