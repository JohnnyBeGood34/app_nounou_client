package com.example.nounou;





import com.example.nounou.data.ApiNounou;

import Manager.ConnexionManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class PageConnexion extends Activity {
	Button ret,val;
	EditText et,pass;
	ProgressDialog dialog = null;
	
	ConnectivityManager connectManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_page_connexion);
		ret=(Button)findViewById(R.id.buttonRetourConnexion);
		val=(Button)findViewById(R.id.ButtonValider);
		et = (EditText)findViewById(R.id.username);
        pass= (EditText)findViewById(R.id.password);
		
        connectManager=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        
		ret.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		Intent intent=new Intent(PageConnexion.this,ListDesNounous.class);
    			startActivity(intent);
        	}
        });
		val.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                verificationLogin(et.getText().toString(),pass.getText().toString()); 
            }
        });
	}
	
	void verificationLogin(String email,String pass){
		
		boolean hasConnection=false;
		
		if(ConnexionManager.testConnexion(connectManager)){
			hasConnection=true;
		}
					
	     ApiNounou.identification(email, pass,this,hasConnection);
						
    }
	public void showAlert(){
    	PageConnexion.this.runOnUiThread(new Runnable() {
            @Override
			public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(PageConnexion.this);
                builder.setTitle("Erreur d'Email");
                builder.setMessage("Utilisateur non trouvé.") 
                       .setCancelable(false)
                       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                           @Override
						public void onClick(DialogInterface dialog, int id) {
                           }
                       });                    
                AlertDialog alert = builder.create();
                alert.show();              
            }
        });
    }

}
