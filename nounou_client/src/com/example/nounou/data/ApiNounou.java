package com.example.nounou.data;


import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Adapteur.NounouAdapter;
import Manager.SessionManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.nounou.ListDesNounous;
import com.example.nounou.ListUneNounou;
import com.example.nounou.PageConnexion;
import com.example.nounou.R;
import com.example.nounou.UrlServer;
import com.example.nounou.VolleySingleton;

public class ApiNounou {
	
	
	private static NounouAdapter _nounouManager;
	static ProgressDialog dialog = null;
	
	
	public static void getAllNounousApi(String url, final Context contexte,final ListView listView) {

		dialog = ProgressDialog.show(contexte, "", "Chargement...");
		RequestQueue _volleyQueue = VolleySingleton.getInstance(contexte).getRequestQueue();
		_volleyQueue = Volley.newRequestQueue(contexte);
		
		/*Si on a un cache pour cette url*/
		if (_volleyQueue.getCache().get(url) != null) {
			
			try {
				//On récupère la liste depuis le cache
				JSONObject cacheContent = new JSONObject(new String(_volleyQueue.getCache().get(url).data));
				ajoutListeNounou(cacheContent, contexte, listView);
				Log.i("Api Nounou","Get url from cache ");
			} catch (JSONException e) {				
				Log.i("ERROR JSON EXCEPTION---------", e.toString());
			}
			
		/* Sinon on va chercher en BD */
		} else {
			
			JsonObjectRequest jsObjRequest = new JsonObjectRequest(
					
					Request.Method.GET, url, null,
					new Response.Listener<JSONObject>() {
						@Override
						public void onResponse(JSONObject response) {
							
							ajoutListeNounou(response, contexte, listView);
						}
					},
					new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {							
							Log.i("ERROR---------", error.toString());
						}
					});
			
			_volleyQueue.add(jsObjRequest);
		}
	}

	/*
	 * Parse le JSON obtenu par le cache ou la requete Et remplis la liste des
	 * nounous (CustomAdapter)
	 */
	public static void ajoutListeNounou(JSONObject response,final Context contexte, ListView listView)
	{
		// parse le JSON et remplis un arrayList d'objet
		// Nounou
		try {
			
			ArrayList<Nounou> arrayListNounou = new ArrayList<Nounou>();
			JSONArray jsonArrayNounou = response.getJSONArray("allNounous");
			
			for (int i = 0; i < jsonArrayNounou.length(); i++) {
				Nounou newNouNou = new Nounou();
				String cheminPhotoNounou = "";
				
				if (jsonArrayNounou.getJSONObject(i).getString("cheminPhoto") != "") {
					cheminPhotoNounou = jsonArrayNounou.getJSONObject(i)
							.getString("cheminPhoto");
				}
				
				newNouNou.setIdNounou(jsonArrayNounou.getJSONObject(i)
						.getString("_id"));
				
				newNouNou.setNom(jsonArrayNounou.getJSONObject(i).getString(
						"nom"));
				
				newNouNou.setPrenom(jsonArrayNounou.getJSONObject(i).getString(
						"prenom"));
				
				newNouNou.setDateDeNaissance(jsonArrayNounou.getJSONObject(i)
						.getString("dateDeNaissance"));
				
				newNouNou.setCivilite(jsonArrayNounou.getJSONObject(i)
						.getString("civilite"));
				
				newNouNou.setAdresse(jsonArrayNounou.getJSONObject(i)
						.getString("adresse"));
				
				newNouNou.setEmail(jsonArrayNounou.getJSONObject(i).getString(
						"email"));
				
				newNouNou.setTarifHoraire(jsonArrayNounou.getJSONObject(i)
						.getString("tarifHoraire"));
				
				newNouNou.setDescriptionPrestation(jsonArrayNounou
						.getJSONObject(i).getString("descriptionPrestation"));
						
				newNouNou.setTelephone(jsonArrayNounou.getJSONObject(i)
						.getString("telephone"));
				
				newNouNou.setDisponibilite(jsonArrayNounou.getJSONObject(i)
						.getString("disponibilite"));
				
				newNouNou.setCheminPhoto(cheminPhotoNounou);
				
				newNouNou.setPassword(jsonArrayNounou.getJSONObject(i)
						.getString("password"));
				
				newNouNou.setDistance(jsonArrayNounou.getJSONObject(i)
						.getString("distance"));
				

				arrayListNounou.add(newNouNou);
			}

			// Create NounouAdapter, pour la liste
			_nounouManager = new NounouAdapter(contexte, arrayListNounou);
			listView.setAdapter(_nounouManager);
			
			listView.setOnItemClickListener(new OnItemClickListener() {
				
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int index, long arg3) {

					Intent intent = new Intent(contexte, ListUneNounou.class);
					intent.putExtra("id", _nounouManager.getNounouAtIndex(index)
							.getIdNounou());
					contexte.startActivity(intent);
				}

			});
			
			dialog.hide();
			
		} catch (JSONException e) {					
			Log.i("Api Nounou","Probleme enregistrement newNounou");
		}
	}
	
    /*
     * Permet d'obtenir le détail d'une Nounou parmi la liste
     * */
	public static void getUneNounou(final String url, final Context contexte,final HashMap hashMap, final ImageView imageView) {
		
		RequestQueue _volleyQueue = VolleySingleton.getInstance(contexte).getRequestQueue();
		_volleyQueue = Volley.newRequestQueue(contexte);
		
		if (_volleyQueue.getCache().get(url) != null) {
			
			try 
			{
				//Si il y a un cache on récupère le JSON depuis le cache
				JSONObject cacheContent = new JSONObject(new String(_volleyQueue.getCache().get(url).data));
				afficherProfilNounou(contexte,cacheContent,imageView,url,hashMap);
			}
			catch(JSONException e)
			{
				Log.i("ERROR---------", e.toString());
			}
		}
		else{
		JsonObjectRequest jsObjRequest = new JsonObjectRequest(
				
				Request.Method.GET, url, null,
				
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						afficherProfilNounou(contexte,response,imageView,url,hashMap);
					}
				}, 
				new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						
						Log.i("ERROR---------", error.toString());
					}
				});
		
		_volleyQueue.add(jsObjRequest);
		}
		
	}

	public static void afficherProfilNounou(Context contexte,JSONObject response,ImageView imageView,String url,HashMap hashMap)
	{
		
		try {
						 
			Nounou nounou = new Nounou(response
					.getString("_id"), response
					.getString("nom"), response
					.getString("prenom"), response
					.getString("dateDeNaissance"), response//Attention à bien avoir une date renvoyée par la réponse
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
			 * Determine l'age de la nounou pour
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
			/* Ajout de l'image via volley*/
			
			getImageFromUrl(urlVeritable, imageView, contexte);

		} catch (JSONException e) {			
			e.printStackTrace();
		} catch (ParseException e) {			
			e.printStackTrace();
		}
	}
	
	
	
	/*
	 * Méthode utilisée dans l'activité PageConnexion pour s'authentifier
	 * */
	public static void identification(final String email,final String password,final Context activityConnection){
		
		RequestQueue _volleyQueue = VolleySingleton.getInstance(activityConnection).getRequestQueue();
		_volleyQueue = Volley.newRequestQueue(activityConnection);
		final String result="";
		
		/* On construit un Objet pour les paramètres à envoyer en POST */
		JSONObject params=new JSONObject();
		try {
			params.put("email", email);
			params.put("password", password);
		} catch (JSONException e) {			
			e.printStackTrace();
		}
				
		JsonObjectRequest jsObjRequest = new JsonObjectRequest(
				
				Request.Method.POST, UrlServer.getServerUrl()+"/api/connexionNounou",params,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						try {							
							/* On récupère le code HTTP reçu et on traite selon */
							int codeHTTP=response.getInt("code");
							/* Si le mot de passe ne correspond pas ou si l'email ne correspond pas à aucune Nounou */
							if(codeHTTP == 401 || codeHTTP == 404) Toast.makeText(activityConnection,"Email ou mot de passe incorrect.",Toast.LENGTH_LONG).show();
							
							/*Si les mots de passe correspondent*/
							else {
								
								/*On récupère l'id de la Nounou renvoyé par le serveur */
								String idNounou=response.getString("message");
							    Intent listeNounous=new Intent(activityConnection,ListDesNounous.class);
								SessionManager sm = new SessionManager(activityConnection);
						 		sm.createUserLoginSession(idNounou,email);
						 		Toast.makeText(activityConnection,email+
							                ", vous êtes Connecté!",
							                Toast.LENGTH_LONG).show();
						 		activityConnection.startActivity(listeNounous);
							}
							
						} catch (JSONException e) {							
							e.printStackTrace();
						}						
					}
					
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {						
						Log.i("ERROR---------", error.toString());
					}
				});
		
		_volleyQueue.add(jsObjRequest);
				
	}
	
	
 /*
  * Methode pour remplir les champs du profil de l'utilisateur dans l'activité Utilisateur
  */
	public static void getProfil(String idNounou,final Context activityUtilisateur,final HashMap listEditText,final ImageView imageProfil){
		
		RequestQueue _volleyQueue = VolleySingleton.getInstance(activityUtilisateur).getRequestQueue();
		_volleyQueue = Volley.newRequestQueue(activityUtilisateur);
				
		
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
				
				Request.Method.GET, UrlServer.getServerUrl()+"/api/nounou/"+idNounou,null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
											   
						try {
							afficherProfil(activityUtilisateur,response,listEditText,imageProfil);
						} catch (JSONException e) {							
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {						
						Log.i("ERROR---------", error.toString());
					}
				});
		_volleyQueue.add(jsObjRequest);
	}
	
	/* Affiche le compte de l'utilisateur avec toutes ses informations */
	public static void afficherProfil(Context activityUtilisateur,JSONObject response,HashMap listEditText,ImageView photoProfil) throws JSONException {
		
		/*Edit TExt de l'activity Utilisateur */
		EditText nom=(EditText)listEditText.get("nom");
		EditText prenom=(EditText)listEditText.get("prenom");
		EditText email=(EditText)listEditText.get("email");
		EditText dateNaissance=(EditText)listEditText.get("dateDeNaissance");
		EditText civilite=(EditText)listEditText.get("civilite");
		EditText password=(EditText)listEditText.get("password");
		EditText adresse=(EditText)listEditText.get("adresse");
		EditText telephone=(EditText)listEditText.get("telephone");
		EditText disponibilite=(EditText)listEditText.get("disponibilite");
		EditText description=(EditText)listEditText.get("description");
		EditText tarifHoraire=(EditText)listEditText.get("tarifHoraire");
		
		
		/*On pré-remplit les champs du profil avec la réponse du serveur */
		try {
			nom.setText(response.getString("nom"));
			prenom.setText(response.getString("prenom"));
			email.setText(response.getString("email"));
			dateNaissance.setText(response.getString("dateDeNaissance"));
			password.setText(response.getString("password"));
			civilite.setText(response.getString("civilite"));
			adresse.setText(response.getString("adresse"));
			telephone.setText(response.getString("telephone"));
			description.setText(response.getString("descriptionPrestation"));
			tarifHoraire.setText(response.getString("tarifHoraire"));
			disponibilite.setText(response.getString("disponibilite"));
		} catch (JSONException e) {			
			e.printStackTrace();
		}
		
		/* Affichage de la photo à partir de l'URL */
		String urlPhoto = UrlServer.getServerUrl()
				+ response.getString("cheminPhoto");
		
		getImageFromUrl(urlPhoto,photoProfil,activityUtilisateur);
	}
	
	
	@SuppressWarnings("deprecation")
	public static void updateProfil(final String idNounou,final Context activityUtilisateur,final Nounou nounou,final String cheminPhoto) throws JSONException{
		
		RequestQueue _volleyQueue = VolleySingleton.getInstance(activityUtilisateur).getRequestQueue();
		_volleyQueue = Volley.newRequestQueue(activityUtilisateur);
		
		Date date = new Date();
        long timestampClient = new Timestamp(date.getTime()).getTime();
               
                      
		JSONObject paramsBody=new JSONObject();

		try {
			paramsBody.put("nom",nounou.getNom());
			paramsBody.put("prenom",nounou.getPrenom());
			paramsBody.put("dateDeNaissance",nounou.getDateDeNaissance());
			paramsBody.put("civilite",nounou.getCivilite());
			paramsBody.put("adresse",nounou.getAdresse());
			paramsBody.put("email",nounou.getEmail());
			paramsBody.put("tarifHoraire",nounou.getTarifHoraire());
			paramsBody.put("descriptionPrestation",nounou.getDescriptionPrestation());
			paramsBody.put("telephone",nounou.getTelephone());
			paramsBody.put("disponibilite",nounou.getDisponibilite());
			paramsBody.put("cheminPhoto","chemin");
			paramsBody.put("password",nounou.getPassword());			
		} catch (JSONException e) {			
			e.printStackTrace();
		} 
		
		/*On contruit l'URL pour la signature avec les params du JSON envoyé en params*/
		String urlForSignature ="";
		Iterator<String> clésBody =paramsBody.keys();
		
       while(clésBody.hasNext()){
    	   String clé=clésBody.next();
    	 urlForSignature+=clé+"="+paramsBody.getString(clé)+"&";
    	
       }
       
       /* On enlève le dernier "&" à la fin */
       urlForSignature=urlForSignature.substring(0,urlForSignature.lastIndexOf("&"));
       
       /*On crypte l'URL pour faire la signature du client */
        String signatureClient="";
		try {
			 signatureClient = Auth.Hmac.createHmacForServer(urlForSignature, timestampClient);
		} catch (InvalidKeyException e1) {			
			e1.printStackTrace();
		} catch (SignatureException e1) {			
			e1.printStackTrace();
		} catch (NoSuchAlgorithmException e1) {			
			e1.printStackTrace();
		}
      
        
        String paramsUrl="?time="+timestampClient+"&login=abcd4ABCD"+"&signature="+signatureClient;
             
				
		 JsonObjectRequest jsObjRequest = new JsonObjectRequest(
					
					Request.Method.PUT, UrlServer.getServerUrl()+"/api/nounou/"+idNounou+paramsUrl,paramsBody,
					new Response.Listener<JSONObject>() {

						@Override
						public void onResponse(JSONObject response) {
						    
							try {
								if(response.getInt("code")==200)
									Toast.makeText(activityUtilisateur, "Mise à jour du profil réussie !",Toast.LENGTH_LONG).show();								
								else Toast.makeText(activityUtilisateur, "Erreur dans la mise à jour !",Toast.LENGTH_LONG).show();
							} catch (JSONException e) {								
								e.printStackTrace();
							}
						}
					},
					new Response.ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {							
							Log.i("Api", error.toString());
							Toast.makeText(activityUtilisateur, "Erreur dans la mise à jour !",Toast.LENGTH_LONG).show();
						}
					});
			_volleyQueue.add(jsObjRequest);
			
		    /* Mise à jour de la photo de profil réalisée séparemment
		     * A mettre dans un Thread séparé de l'UI
		     * Volley de gérant pas les paramètres de type fichier,
		     * et n'ayant pas besoin de cache dans ce cas
		     * on passe par une requete POST classique. 
		     * 
		     * */	
			Thread thread=new Thread(){		    

			@Override
			public void run() {
				
				Log.i("Api ","Chemin photo : "+cheminPhoto);
				HttpClient client =new DefaultHttpClient();
				HttpPost httpPost=new HttpPost(UrlServer.getServerUrl()+"/api/image/id/"+idNounou);
				File photo=new File(cheminPhoto);
				MultipartEntity entity=new MultipartEntity();

				entity.addPart("image",new FileBody(photo));
				
				httpPost.setEntity(entity);
				try {
					HttpResponse response=client.execute(httpPost);
					
				} catch (ClientProtocolException e) {
					
					Log.i("Api ","reponse :"+e.toString());
				} catch (IOException e) {
					
					Log.i("Api ","reponse :"+e.toString());
				}
				
			}
			
			};
			thread.start();
	}
	
	/*
	 * Méthode utilisée pour l'utilisation de l'API
	 * pour la  création d'une nounou
	 * */
	public static void createNounou(final Context activityInscription,final Nounou nounou) throws JSONException{
		
		RequestQueue _volleyQueue = VolleySingleton.getInstance(activityInscription).getRequestQueue();
		_volleyQueue = Volley.newRequestQueue(activityInscription);
		
		Date date = new Date();
		long timestampClient = new Timestamp(date.getTime()).getTime();
		
		JSONObject paramsBody =new JSONObject();
		paramsBody.put("nom",nounou.getNom());
		paramsBody.put("prenom",nounou.getPrenom());
		paramsBody.put("dateDeNaissance",nounou.getDateDeNaissance());
		paramsBody.put("civilite",nounou.getCivilite());
		paramsBody.put("adresse",nounou.getAdresse());
		paramsBody.put("email",nounou.getEmail());
		paramsBody.put("tarifHoraire",nounou.getTarifHoraire());
		paramsBody.put("descriptionPrestation",nounou.getDescriptionPrestation());
		paramsBody.put("telephone",nounou.getTelephone());
		paramsBody.put("disponibilite",nounou.getDisponibilite());
		paramsBody.put("cheminPhoto","chemin");
		paramsBody.put("password",nounou.getPassword());
		
		/*On contruit l'URL pour la signature avec les params du JSON envoyé en params*/
		String urlForSignature ="";
		Iterator<String> clésBody =paramsBody.keys();
		
       while(clésBody.hasNext()){
    	   String clé=clésBody.next();
    	 urlForSignature+=clé+"="+paramsBody.getString(clé)+"&";
    	
       }
       
       /* On enlève le dernier "&" à la fin */
       urlForSignature=urlForSignature.substring(0,urlForSignature.lastIndexOf("&"));
       
       /*On crypte l'URL pour faire la signature du client */
        String signatureClient="";
		try {
			 signatureClient = Auth.Hmac.createHmacForServer(urlForSignature, timestampClient);
		} catch (InvalidKeyException e1) {			
			e1.printStackTrace();
		} catch (SignatureException e1) {			
			e1.printStackTrace();
		} catch (NoSuchAlgorithmException e1) {			
			e1.printStackTrace();
		}
      
        
        String paramsUrl="?time="+timestampClient+"&login=abcd4ABCD"+"&signature="+signatureClient;
             
		
		 JsonObjectRequest jsObjRequest = new JsonObjectRequest(
					
					Request.Method.POST, UrlServer.getServerUrl()+"/api/nounous"+paramsUrl,paramsBody,
					new Response.Listener<JSONObject>() {

						@Override
						public void onResponse(JSONObject response) {
						    
							try {
								
								if(response.getInt("code") == 200){
									
									Toast.makeText(activityInscription, "Création du profil réussie !",Toast.LENGTH_LONG).show();								
									
									Intent intent=new Intent(activityInscription,ListDesNounous.class);
    								String id = response.getString("message");
    								String email = nounou.getEmail();
    								SessionManager sm = new SessionManager(activityInscription);
    								sm.createUserLoginSession(id, email);
    								Toast.makeText(activityInscription,email+
    										", vous êtes Connecté!",
    										Toast.LENGTH_LONG).show();
    								
    								activityInscription.startActivity(intent);
								}
									
								else Toast.makeText(activityInscription, "Erreur dans la création  !",Toast.LENGTH_LONG).show();
								
							} catch (JSONException e) {								
								e.printStackTrace();
							}
						}
					},
					new Response.ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {							
							Log.i("Api", error.toString());
							Toast.makeText(activityInscription, "Erreur dans la création ou vous n'êtes pas connecté !",Toast.LENGTH_LONG).show();
						}
					});
			_volleyQueue.add(jsObjRequest);
		
	}
	
	
	/*
	 * Methode utilisée dans l'activité Utilisateur afin de supprimer le profil de la personne connectée
	 * */
	public static void deleteProfil(final Context activityUtilisateur,final String idNounou) {
		
		RequestQueue requestQueue =VolleySingleton.getInstance(activityUtilisateur).getRequestQueue();
		requestQueue = Volley.newRequestQueue(activityUtilisateur);
		
		Date date = new Date();
		long timestampClient = new Timestamp(date.getTime()).getTime();
		
		JSONObject paramsBody =new JSONObject();
		try {
			paramsBody.put("idNounou",idNounou);
		} catch (JSONException e2) {			
			e2.printStackTrace();
		}

		/*On contruit l'URL pour la signature avec les params du JSON envoyé en params*/
		String urlForSignature ="idNounou="+idNounou;
		            
       
       /*On crypte l'URL pour faire la signature du client */
        String signatureClient="";
		try {
			 signatureClient = Auth.Hmac.createHmacForServer(urlForSignature, timestampClient);
		} catch (InvalidKeyException e1) {			
			e1.printStackTrace();
		} catch (SignatureException e1) {			
			e1.printStackTrace();
		} catch (NoSuchAlgorithmException e1) {			
			e1.printStackTrace();
		}
      
        
        String paramsUrl="?time="+timestampClient+"&login=abcd4ABCD"+"&signature="+signatureClient;
		
		 JsonObjectRequest jsObjRequest = new JsonObjectRequest(
					
					Request.Method.DELETE, UrlServer.getServerUrl()+"/api/nounou/id/"+idNounou+paramsUrl,null,
					new Response.Listener<JSONObject>() {

						@Override
						public void onResponse(JSONObject response) {
							
						    Log.i("Api",response.toString());
							try {
								
								if(response.getInt("code") == 200){
									
								SessionManager	session=new SessionManager(activityUtilisateur);
								session.logoutUser();
								Toast.makeText(activityUtilisateur, "Suppression du profil réussie !",Toast.LENGTH_LONG).show();																	
								Intent intent=new Intent(activityUtilisateur,ListDesNounous.class);
 								
								Toast.makeText(activityUtilisateur,
				    	                "Votre compte a été supprimé",
				    	                Toast.LENGTH_LONG).show();
 								
 								activityUtilisateur.startActivity(intent);
								}
									
								else Toast.makeText(activityUtilisateur, "Erreur dans la suppression de votre profil  !",Toast.LENGTH_LONG).show();
								
							} catch (JSONException e) {								
								e.printStackTrace();
							}
						}
					},
					new Response.ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {
							
							Log.i("Api", error.toString());
							Toast.makeText(activityUtilisateur, "Erreur dans la suppression de votre profil !",Toast.LENGTH_LONG).show();
						}
					});
		 
		 requestQueue.add(jsObjRequest);
		 
		
	}
	
	
	/*
	 * Permet de telecharger une image du serveur en utilisant le Singleton
	 * volley 
	 */
	public static void getImageFromUrl(String url, final ImageView imageView,Context contexte) {
		
		VolleySingleton volleyInstance = VolleySingleton.getInstance(contexte);
		ImageLoader imageLoader = volleyInstance.getImageLoader();
		
		imageLoader.get(url, new ImageListener() {
			
			@Override
			public void onErrorResponse(VolleyError error) {
				imageView.setImageResource(R.drawable.ic_launcher); 
			}
			
			@Override
			public void onResponse(ImageContainer response, boolean arg1) {
				
				Log.i("Api","Get image url reponse :"+response.getRequestUrl());
				if (response.getBitmap() != null) {
					imageView.setImageBitmap(response.getBitmap());
				}
			}
		});
	}
	
	public static String parseUrl(String url) {
		String[] arrayurl = url.split("/");
		return arrayurl[2];
	}

}
