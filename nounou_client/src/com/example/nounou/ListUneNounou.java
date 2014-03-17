package com.example.nounou;


import java.util.ArrayList;
import java.util.List;

import com.example.nounou.data.Nounou;
import com.example.nounou.data.NounouBdd;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class ListUneNounou extends Activity {
	Button ret; 
	private ArrayAdapter<String> listAdapter2 ;
	private ListView mainListView2 ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_une_nounou);
		ret = (Button) findViewById(R.id.buttonRet);
		
		
		final NounouBdd db=new NounouBdd(this);
		db.open();
		// Find the ListView resource.   
	    mainListView2 = (ListView) findViewById( R.id.mainListViewEmp); 
	    
	    ArrayList<String> visit = new ArrayList<String>();   
	    // Create ArrayAdapter 
	    listAdapter2 = new ArrayAdapter<String>(this, R.layout.simplerow2, visit);
	    List<Nounou> nounous =db.getAllNounou();
	    Bundle extra = getIntent().getExtras();
        String  Variable = extra.getString("id");
        
        for(Nounou vi : nounous){
        	//Log.i("test nom",vi.getNom());
        	//Log.i("test var",String.valueOf(Variable));
        	if((vi.getIdNounou()+"   -   "+vi.getNom()).equals(Variable)){
        		
        		//Toast.makeText(ListeUnEmploye.this,"dans le if", Toast.LENGTH_SHORT).show();
        		
        		listAdapter2.add(String.valueOf(vi.getIdNounou()));
        		listAdapter2.add(String.valueOf(vi.getNom()));
        		listAdapter2.add(String.valueOf(vi.getPrenom()));
        		listAdapter2.add(String.valueOf(vi.getDateDeNaissance()));
        		listAdapter2.add(String.valueOf(vi.getCivilite()));
        		listAdapter2.add(String.valueOf(vi.getAdresse()));
        		listAdapter2.add(String.valueOf(vi.getEmail()));
        		listAdapter2.add(String.valueOf(vi.getTarifHoraire()));
        		listAdapter2.add(String.valueOf(vi.getDescriptionPrestation()));
        		listAdapter2.add(String.valueOf(vi.getTelephone()));
        		listAdapter2.add(String.valueOf(vi.getDisponibilite()));
        		listAdapter2.add(String.valueOf(vi.getCheminPhoto()));
        		listAdapter2.add(String.valueOf(vi.getPassword()));
        		
        	}
        	else{
        		//Toast.makeText(ListeUnEmploye.this,"dans le else", Toast.LENGTH_SHORT).show();
        	}
        	if((vi.getPrenom()).equals(Variable)){
        		
        		//Toast.makeText(ListeUnEmploye.this,"dans le if", Toast.LENGTH_SHORT).show();
        		
        		listAdapter2.add(String.valueOf(vi.getIdNounou()));
        		listAdapter2.add(String.valueOf(vi.getNom()));
        		listAdapter2.add(String.valueOf(vi.getPrenom()));
        		listAdapter2.add(String.valueOf(vi.getDateDeNaissance()));
        		listAdapter2.add(String.valueOf(vi.getCivilite()));
        		listAdapter2.add(String.valueOf(vi.getAdresse()));
        		listAdapter2.add(String.valueOf(vi.getEmail()));
        		listAdapter2.add(String.valueOf(vi.getTarifHoraire()));
        		listAdapter2.add(String.valueOf(vi.getDescriptionPrestation()));
        		listAdapter2.add(String.valueOf(vi.getTelephone()));
        		listAdapter2.add(String.valueOf(vi.getDisponibilite()));
        		listAdapter2.add(String.valueOf(vi.getCheminPhoto()));
        		listAdapter2.add(String.valueOf(vi.getPassword()));
        	}
        	else{
        		//Toast.makeText(ListeUnEmploye.this,"dans le else", Toast.LENGTH_SHORT).show();
        	}
        }
        mainListView2.setAdapter( listAdapter2 );
        ret.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		Intent intent=new Intent(ListUneNounou.this,ListNounou.class);
    			startActivity(intent);
        	}
        });
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_une_nounou, menu);
		return true;
	}

}
