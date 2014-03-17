package com.example.nounou;

import java.util.ArrayList;
import java.util.List;

import com.example.nounou.data.Nounou;
import com.example.nounou.data.NounouBdd;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ListNounou extends Activity {
	EditText rec;
	Button connexion;
	private ListView mainListView ;
	private ArrayAdapter<String> listAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_nounou);
		rec = (EditText)findViewById(R.id.inputRecherche);
		connexion=(Button)findViewById(R.id.buttonConnexion);
		
		
		
		final NounouBdd db=new NounouBdd(this);
		db.open();

		// Find the ListView resource.   
		mainListView = (ListView) findViewById( R.id.mainListView ); 
	    

	    ArrayList<String> visit = new ArrayList<String>();   
	    // Create ArrayAdapter 
	    listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, visit);
	    List<Nounou> nounous =db.getAllNounou();
        
	    for(Nounou vi : nounous){
        	listAdapter.add(String.valueOf(vi.getIdNounou()+"   -   "+vi.getNom()));
        }
        mainListView.setAdapter( listAdapter );
        
        rec.addTextChangedListener(new TextWatcher() {
        	
            
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
            	ListNounou.this.listAdapter.getFilter().filter(cs);   
            }
             
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                    int arg3) {
                // TODO Auto-generated method stub
                 
            }
             
            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub                          
            }
        });
        connexion.setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		Intent intent=new Intent(ListNounou.this,PageConnexion.class);
    			startActivity(intent);
        	}
        });
        mainListView.setOnItemClickListener(new OnItemClickListener(){
        	

        	@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// ListView Clicked item index
                //int itemPosition     = arg2;
                // ListView Clicked item value
                String  itemValue    = (String) mainListView.getItemAtPosition(arg2);
                // Show Alert 
                /*Toast.makeText(getApplicationContext(),
                  "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                  .show();*/
                
                Intent intent= new Intent(getApplicationContext(),ListUneNounou.class);
                intent.putExtra("id", itemValue);
                //Log.i("id",String.valueOf(itemPosition));
                startActivity(intent);
			}
        	
        });
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_nounou, menu);
		return true;
	}
}
