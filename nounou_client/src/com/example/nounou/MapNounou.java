package com.example.nounou;

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
import android.os.Bundle;

public class MapNounou extends Activity {
	private GoogleMap mMap;
	Double _latUtilisateur, _lonUtilisateur,_latNounou,_lonNounou;
	String _NomNounou, _Utilisateur;
	LatLng _latlonUtilisateur,_latlonNounou;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_nounou);
        
        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE); 
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        

        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        
		if(location != null){
		        	
			_latUtilisateur = location.getLatitude();
	        _lonUtilisateur = location.getLongitude();
	        _latlonUtilisateur = new LatLng(_latUtilisateur, _lonUtilisateur);
	        _Utilisateur = "Vous êtes ici";
	      //Place un marqueur sur la carte pour la localisation de l'utilisateur
	        mMap.addMarker(new MarkerOptions()
		        .position(_latlonUtilisateur)
		        //met la couleur du marker en bleu pour l'utilisateur
		        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
		        .title(_Utilisateur));
	        
		}
        
        
        
        _latNounou = 43.6200;
        _lonNounou = 3.87672;
        _latlonNounou = new LatLng(_latNounou, _lonNounou);
        _NomNounou = "Nom de la nounou";
        

        
        //Place un marqueur sur la carte pour la localisation de la nounou
        mMap.addMarker(new MarkerOptions()
	        .position(_latlonNounou)
	        .title(_NomNounou));
      
        //Permet de zoomer sur le marker de la nounou, le 12 permet de définir la profondeur du zoom
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(_latlonNounou,12));

    }
}
