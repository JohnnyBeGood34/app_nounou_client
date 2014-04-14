package com.example.nounou;



import java.util.List;

import com.example.nounou.data.Nounou;
import com.example.nounou.data.NounouBdd;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PageConnexion extends Activity {
	Button ret,val;
	EditText et,pass;
	ProgressDialog dialog = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_page_connexion);
		ret=(Button)findViewById(R.id.buttonRetourConnexion);
		val=(Button)findViewById(R.id.ButtonValider);
		et = (EditText)findViewById(R.id.username);
        pass= (EditText)findViewById(R.id.password);
		
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
	void verificationLogin(String ets,String pass){
		final NounouBdd db=new NounouBdd(this);
		db.open();

    	List<Nounou> nounous =db.getNounouWithLogin(ets);
    	
    	if(nounous.isEmpty())
    	{
    		Toast.makeText(PageConnexion.this,"Veuillez entrer un login correct!", Toast.LENGTH_SHORT).show();
    	}
    	else
    	{
    		if(nounous.get(0).getPassword().equals(pass))
    		{     
                Intent intent=new Intent(PageConnexion.this,Utilisateur.class);
                String etLogin = et.getText().toString();
        		intent.putExtra("id",etLogin);
    			startActivity(intent);
    		}
    		else
    		{
    			showAlert(); 
    		}
    	}
    }
	public void showAlert(){
    	PageConnexion.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(PageConnexion.this);
                builder.setTitle("Erreur d'Email");
                builder.setMessage("Utilisateur non trouvé.") 
                       .setCancelable(false)
                       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int id) {
                           }
                       });                    
                AlertDialog alert = builder.create();
                alert.show();              
            }
        });
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.page_connexion, menu);
		return true;
	}

}
