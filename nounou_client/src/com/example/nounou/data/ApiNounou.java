package com.example.nounou.data;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Adapteur.NounouAdapter;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class ApiNounou {
	static RequestQueue _volleyQueue; // File d'attente volley
	public static void getAllNounousApi(String url,Context c)
	{
		_volleyQueue = Volley.newRequestQueue(c);
		//final NounouAdapter adapter = new NounouAdapter(c);
		
		JsonObjectRequest jsObjRequest = new JsonObjectRequest(
				Request.Method.GET, url, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						//parse le JSON et remplis un arrayList d'objet Nounou
						try {
							ArrayList<Nounou> arrayListNounou = new ArrayList();
							JSONArray jsonArrayNounou = response.getJSONArray("allNounous");
							for(int i = 0 ; i < jsonArrayNounou.length() ; i++){
								Nounou newNouNou = new Nounou();
								String cheminPhotoNounou = "";
								if(jsonArrayNounou.getJSONObject(i).getString("cheminPhoto") != "")
								{
									cheminPhotoNounou = jsonArrayNounou.getJSONObject(i).getString("cheminPhoto");
								}
								newNouNou.setIdNounou(jsonArrayNounou.getJSONObject(i).getString("_id"));
								newNouNou.setNom(jsonArrayNounou.getJSONObject(i).getString("nom"));
								newNouNou.setPrenom(jsonArrayNounou.getJSONObject(i).getString("prenom"));
								newNouNou.setDateDeNaissance(jsonArrayNounou.getJSONObject(i).getString("dateDeNaissance"));
								newNouNou.setCivilite(jsonArrayNounou.getJSONObject(i).getString("civilite"));
								newNouNou.setAdresse(jsonArrayNounou.getJSONObject(i).getString("adresse"));
								newNouNou.setEmail(jsonArrayNounou.getJSONObject(i).getString("email"));
								newNouNou.setTarifHoraire(jsonArrayNounou.getJSONObject(i).getString("tarifHoraire"));
								newNouNou.setDescriptionPrestation(jsonArrayNounou.getJSONObject(i).getString("descriptionPrestation"));
								newNouNou.setTelephone(jsonArrayNounou.getJSONObject(i).getString("telephone"));
								newNouNou.setDisponibilite(jsonArrayNounou.getJSONObject(i).getString("disponibilite"));
								newNouNou.setCheminPhoto(cheminPhotoNounou);
								newNouNou.setPassword(jsonArrayNounou.getJSONObject(i).getString("password"));
								
								arrayListNounou.add(newNouNou);
							}
							//adapter.notifyAll();
							
						} catch (JSONException e) {
							e.printStackTrace();
						}
						
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						Log.i("ERROR---------",error.toString());
					}
				});
		_volleyQueue.add(jsObjRequest);
	}

}
