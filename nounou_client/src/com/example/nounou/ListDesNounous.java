package com.example.nounou;

import Adapteur.NounouAdapter;
import Manager.SessionManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class ListDesNounous extends Activity{
	EditText rec;
	Button connexion,inscription;
	TextView dist;
	private ListView mainListView ;
	private NounouAdapter _nounouManager;
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
	    
  
	    // Create ArrayAdapter 
	    _nounouManager = new NounouAdapter(this);
	    
	    
        
        mainListView.setAdapter( _nounouManager );
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

        inscription.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		
    			if(session.isUserLoggedIn()==true){
    				Intent intent=new Intent(ListDesNounous.this,Utilisateur.class);
    				String u = session.getLogin();
    				intent.putExtra("id",u);
	    			startActivity(intent);
        		}
        		else{
        			Intent intent=new Intent(ListDesNounous.this,InscriptionNounou.class);
        			startActivity(intent);
        		}
        	}
        });    
        
        mainListView.setOnItemClickListener(new OnItemClickListener(){
        	

        	@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
        		
                Intent intent= new Intent(getApplicationContext(),ListUneNounou.class);
                intent.putExtra("id", _nounouManager.getNounouAtIndex(arg2).getEmail());
                startActivity(intent);
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
                // TODO Auto-generated method stub
            }
 
            @Override
			public void onStopTrackingTouch(SeekBar seekBar) {
            	dist.setText(String.valueOf(progressChanged+"Km"));
            }
        });
        
	}
}
