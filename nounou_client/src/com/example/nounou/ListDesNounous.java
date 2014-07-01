package com.example.nounou;

import com.example.nounou.data.ApiNounou;

import Manager.SessionManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ServiceCast")
public class ListDesNounous extends Activity {
	
	String URL = UrlServer.getServerUrl();
	Button connexion, inscription;
	TextView distance_text;
	private ListView mainListView;
	private SeekBar control_distance = null;
	SessionManager session;

	String longitude = "";
	String latitude = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_des_nounous);
		setTitle("Liste des nounous proches de chez vous");
		connexion = (Button) findViewById(R.id.buttonConnexion);
		inscription = (Button) findViewById(R.id.buttonInscription);
		distance_text = (TextView) findViewById(R.id.tvdistance);
		control_distance = (SeekBar) findViewById(R.id.volume_bar);

		// User Session Manager
		session = new SessionManager(this);
		if (session.isUserLoggedIn() == true) {
			connexion.setText("Déconnexion");
			inscription.setText("Mon Compte");
		} else {
			inscription.setText("Inscription");
			connexion.setText("Connexion");
		}

		// Find the ListView resource.
		mainListView = (ListView) findViewById(R.id.mainListView);

		/*
		 * Localisation
		 */

		final LocationManager locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
		// Si le GPS n'est activé pas activé
		if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			ApiNounou.getAllNounousApi(URL + "/api/nounous",
					ListDesNounous.this, mainListView);
		} else {
			Log.i("GPS", "activé");
			// Si le GPS est activé on récupere la derniere latitude et
			// longitude connue
			Location location = locationManager
					.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if (location != null) {
				latitude = String.valueOf(location.getLatitude());
				longitude = String.valueOf(location.getLongitude());
				ApiNounou.getAllNounousApi(URL + "/api/nounous/latitude/"
						+ latitude + "/longitude/" + longitude, this,
						mainListView);
			} else {
				ApiNounou.getAllNounousApi(URL + "/api/nounous", this,
						mainListView);
			}
		}

		connexion.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (session.isUserLoggedIn() == true) {
					session.logoutUser();
					Toast.makeText(getApplicationContext(),
							"Vous êtes déconnecté", Toast.LENGTH_LONG).show();
				} else {
					Intent intent = new Intent(ListDesNounous.this,
							PageConnexion.class);
					startActivity(intent);
				}
			}
		});

		/*
		 * Permet de visualiser son compte si connecté sinon de s'inscrire
		 */
		inscription.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (session.isUserLoggedIn() == true) {
					Intent intent = new Intent(ListDesNounous.this,
							Utilisateur.class);
					String idNounou = session.getLogin();
					intent.putExtra("id", idNounou);
					startActivity(intent);
				} else {
					Intent intent = new Intent(ListDesNounous.this,
							InscriptionNounou.class);
					startActivity(intent);
				}
			}
		});

		control_distance
				.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
					int progressChanged = 0;

					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						progressChanged = progress;

					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
					}

					/* Au changement des kilomètres choisis pour la recherche */
					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						distance_text.setText(String.valueOf(progressChanged
								+ "Km"));

						/*
						 * On actualise la liste des Nounous se trouvant dans le
						 * périmètre choisi
						 */
						/*
						 * TODO Remplacer la latitude/longitude par les
						 * véritbles coordonnées
						 */
						if (locationManager
								.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
							if (progressChanged != 0) {
								ApiNounou.getAllNounousApi(
										URL
												+ "/api/nounous/latitude/"
												+ latitude
												+ "/longitude/"
												+ longitude
												+ "/kilometres/"
												+ String.valueOf(progressChanged),
										ListDesNounous.this, mainListView);
							} else {
								ApiNounou.getAllNounousApi(URL
										+ "/api/nounous/latitude/" + latitude
										+ "/longitude/" + longitude,
										ListDesNounous.this, mainListView);
							}
						}
					}
				});

	}
	/*
	 * @Override public boolean onCreateOptionsMenu(Menu menu){
	 * Log.i("MENU","create menu"); MenuInflater inflater = getMenuInflater();
	 * inflater.inflate(R.menu.menu, menu); return
	 * super.onCreateOptionsMenu(menu); }
	 */
}
