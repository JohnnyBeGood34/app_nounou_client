package com.example.nounou;

import com.example.nounou.data.ApiNounou;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;

public class MapNounou extends Activity {
	private GoogleMap mapNounou;
	Double _latUtilisateur, _lonUtilisateur,_latNounou,_lonNounou;
	String _NomNounou, _Utilisateur;
	LatLng _latlonUtilisateur,_latlonNounou;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_nounou);
        Bundle extraIdNounou = getIntent().getExtras();
        String idNounou = extraIdNounou.getString("idNounou");

        //Récupération du location manager
        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        
        //Fragment map
        mapNounou = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        
        String routelLatLng = UrlServer.getServerUrl()+"/api/getLatLngNounou/"+idNounou;
        
        ConnectivityManager connectivityManager =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        
        ApiNounou.getLatLngNounou(routelLatLng, this, mapNounou, location,connectivityManager);
        /*
        //Si le client à une connexion GPS
		if(location != null){
		     //On récupère ses coordonnée et on l'ajoute en marqueur   	
			_latUtilisateur = location.getLatitude();
	        _lonUtilisateur = location.getLongitude();
	        _latlonUtilisateur = new LatLng(_latUtilisateur, _lonUtilisateur);
	        _Utilisateur = "Vous êtes ici";
	      //Place un marqueur sur la carte pour la localisation de l'utilisateur
	        mapNounou.addMarker(new MarkerOptions()
		        .position(_latlonUtilisateur)
		        //met la couleur du marker en bleu pour l'utilisateur
		        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
		        .title(_Utilisateur));
	        
		}*/
        
        
        /*
        _latNounou = 43.6200;
        _lonNounou = 3.87672;
        _latlonNounou = new LatLng(_latNounou, _lonNounou);
        _NomNounou = "Nom de la nounou";
        

        
        //Place un marqueur sur la carte pour la localisation de la nounou
        mapNounou.addMarker(new MarkerOptions()
	        .position(_latlonNounou)
	        .title(_NomNounou));
      
        //Permet de zoomer sur le marker de la nounou, le 12 permet de définir la profondeur du zoom
        mapNounou.animateCamera(CameraUpdateFactory.newLatLngZoom(_latlonNounou,12));*/

    }
}
