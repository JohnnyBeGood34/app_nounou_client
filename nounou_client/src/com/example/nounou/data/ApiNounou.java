package com.example.nounou.data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Adapteur.NounouAdapter;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.nounou.ListUneNounou;
import com.example.nounou.R;
import com.example.nounou.VolleySingleton;

public class ApiNounou {
	private static NounouAdapter _nounouManager;
	static ProgressDialog dialog = null;
	public static void getAllNounousApi(String url, final Context contexte,
			final ListView listView) {
		dialog = ProgressDialog.show(contexte, "", "Chargement...");
		RequestQueue _volleyQueue = VolleySingleton.getInstance(contexte).getRequestQueue();
		_volleyQueue = Volley.newRequestQueue(contexte);

		JsonObjectRequest jsObjRequest = new JsonObjectRequest(
				Request.Method.GET, url, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						// parse le JSON et remplis un arrayList d'objet Nounou
						try {
							ArrayList<Nounou> arrayListNounou = new ArrayList();
							JSONArray jsonArrayNounou = response
									.getJSONArray("allNounous");
							for (int i = 0; i < jsonArrayNounou.length(); i++) {
								Nounou newNouNou = new Nounou();
								String cheminPhotoNounou = "";
								if (jsonArrayNounou.getJSONObject(i).getString(
										"cheminPhoto") != "") {
									cheminPhotoNounou = jsonArrayNounou
											.getJSONObject(i).getString(
													"cheminPhoto");
								}
								newNouNou.setIdNounou(jsonArrayNounou
										.getJSONObject(i).getString("_id"));
								newNouNou.setNom(jsonArrayNounou.getJSONObject(
										i).getString("nom"));
								newNouNou.setPrenom(jsonArrayNounou
										.getJSONObject(i).getString("prenom"));
								newNouNou.setDateDeNaissance(jsonArrayNounou
										.getJSONObject(i).getString(
												"dateDeNaissance"));
								newNouNou
										.setCivilite(jsonArrayNounou
												.getJSONObject(i).getString(
														"civilite"));
								newNouNou.setAdresse(jsonArrayNounou
										.getJSONObject(i).getString("adresse"));
								newNouNou.setEmail(jsonArrayNounou
										.getJSONObject(i).getString("email"));
								newNouNou.setTarifHoraire(jsonArrayNounou
										.getJSONObject(i).getString(
												"tarifHoraire"));
								newNouNou
										.setDescriptionPrestation(jsonArrayNounou
												.getJSONObject(i)
												.getString(
														"descriptionPrestation"));
								newNouNou.setTelephone(jsonArrayNounou
										.getJSONObject(i)
										.getString("telephone"));
								newNouNou.setDisponibilite(jsonArrayNounou
										.getJSONObject(i).getString(
												"disponibilite"));
								newNouNou.setCheminPhoto(cheminPhotoNounou);
								newNouNou
										.setPassword(jsonArrayNounou
												.getJSONObject(i).getString(
														"password"));

								arrayListNounou.add(newNouNou);
							}

							// Create ArrayAdapter
							_nounouManager = new NounouAdapter(contexte,
									arrayListNounou);
							listView.setAdapter(_nounouManager);
							listView.setOnItemClickListener(new OnItemClickListener() {
								@Override
								public void onItemClick(AdapterView<?> arg0,
										View arg1, int arg2, long arg3) {

									Intent intent = new Intent(contexte,
											ListUneNounou.class);
									intent.putExtra("id", _nounouManager
											.getNounouAtIndex(arg2)
											.getIdNounou());
									contexte.startActivity(intent);
								}

							});
							dialog.hide();
						} catch (JSONException e) {
							e.printStackTrace();
						}

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						Log.i("ERROR---------", error.toString());
					}
				});
		/*if (_volleyQueue.getCache().get(url) != null) {
			// response exists
			String cachedResponse = new String(
					_volleyQueue.getCache().get(url).data);
			Log.i("CACHE-----------------------", "From Cache: "+ cachedResponse);
		} else {
			// no response
			Log.i("CACHE RESPONSE----------------", "NO CACHE RESPONSE");
			_volleyQueue.add(jsObjRequest);
		}*/
		_volleyQueue.add(jsObjRequest);
	}

	public static void getUneNounou(final String url, final Context contexte,
			final HashMap hashMap, final ImageView imageView) {
		RequestQueue _volleyQueue = VolleySingleton.getInstance(contexte).getRequestQueue();
		_volleyQueue = Volley.newRequestQueue(contexte);
		JsonObjectRequest jsObjRequest = new JsonObjectRequest(
				Request.Method.GET, url, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {

						try {
							// Log.i("nounou-----------------------------",response.getString("nounou").toString());
							Nounou nounou = new Nounou(response
									.getString("_id"), response
									.getString("nom"), response
									.getString("prenom"), response
									.getString("dateDeNaissance"), response
									.getString("civilite"), response
									.getString("adresse"), response
									.getString("email"), response
									.getString("tarifHoraire"), response
									.getString("descriptionPrestation"),
									response.getString("telephone"), response
											.getString("disponibilite"),
									response.getString("cheminPhoto"), response
											.getString("password"));

							TextView nom = (TextView) hashMap.get("nom");
							nom.setText(String.valueOf(nounou.getNom()));

							TextView prenom = (TextView) hashMap.get("prenom");
							prenom.setText(String.valueOf(nounou.getPrenom()));

							TextView date = (TextView) hashMap.get("date");
							/*
							 * Determinatation de l'age de la nounou pour
							 * affichage
							 */
							long age;
							Date dateNaissanceNounou = new SimpleDateFormat(
									"dd/MM/yyyy", Locale.FRANCE).parse(nounou
									.getDateDeNaissance());
							;
							Date now = new Date();
							age = now.getTime() - dateNaissanceNounou.getTime();
							age = age / 31556952 / 100 / 10;

							date.setText(String.valueOf(age) + " ans");

							TextView tarif = (TextView) hashMap.get("tarif");
							tarif.setText(String.valueOf(nounou
									.getTarifHoraire()));

							TextView dispo = (TextView) hashMap.get("dispo");
							dispo.setText(String.valueOf(nounou
									.getDisponibilite()));

							TextView tel = (TextView) hashMap.get("tel");
							tel.setText(String.valueOf(nounou.getTelephone()));

							TextView des = (TextView) hashMap.get("des");
							des.setText(String.valueOf(nounou
									.getDescriptionPrestation()));

							TextView email = (TextView) hashMap.get("email");
							email.setText(String.valueOf(nounou.getEmail()));

							String parseUrl = parseUrl(url);

							String urlVeritable = "http://" + parseUrl
									+ nounou.getCheminPhoto();
							// Ajout de l'image via volley
							getImageFromUrl(urlVeritable, imageView, contexte);

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						Log.i("ERROR---------", error.toString());
					}
				});
		_volleyQueue.add(jsObjRequest);
	}

	public static void getImageFromUrl(String url, final ImageView imageView, Context contexte) {
		RequestQueue _volleyQueue = VolleySingleton.getInstance(contexte).getRequestQueue();
		_volleyQueue = Volley.newRequestQueue(contexte);
		ImageRequest imgRequest = new ImageRequest(url,
				new Response.Listener<Bitmap>() {
					@Override
					public void onResponse(Bitmap response) {
						imageView.setImageBitmap(response);
					}
				}, 0, 0, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.i("ERROR IMG--------------", error.toString());
					}
				});
		_volleyQueue.add(imgRequest);
	}

	public static String parseUrl(String url) {
		String[] arrayurl = url.split("/");
		// Log.i("url--------------------",arrayurl[2]);
		return arrayurl[2];
	}

}
