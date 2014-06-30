package url.data;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.nounou.VolleySingleton;

import android.content.Context;
import android.util.Log;

public class EnvoiUrlAttenteServeur {

	private Context contexte = null;
	private UrlServerAttenteDao urlServerDao = null;

	public EnvoiUrlAttenteServeur(Context contexte) {
		this.contexte = contexte;
		this.urlServerDao = new UrlServerAttenteDao(this.contexte);
	}

	/**
	 * Fonction permettant de savoir si il y a des url en attentes en bdd
	 */
	private boolean checkUrlAttenteBdd() {
		boolean urlAttente = false;
		// On r�cup�re la liste des urls en attente
		List<UrlServerAttente> urlsEnAttente = urlServerDao.getAllurl();
		// Si la liste n'est pas vide on renvoi true
		if (!urlsEnAttente.isEmpty()) {
			urlAttente = true;
		}
		return urlAttente;
	}

	public void envoiUrlServeur() throws JSONException {

		// Si il y a des urls en attente pour le serveur
		if (checkUrlAttenteBdd()) {
			List<UrlServerAttente> urlsEnAttente = urlServerDao.getAllurl();
			for (UrlServerAttente url : urlsEnAttente) {
				/**
				 * R�cup�ration du label pour savoir quel methodes volley
				 * appeler
				 */
				String label = url.getLabel();

				if (label.equals("update")) {
					this.update(url);
				} else if (label.equals("insert")) {
					this.insert(url);
				} else if (label.equals("delete")) {
					this.delete(url);
				} else if (label.equals("photo")) {
					this.envoiPhoto(url);
				}
			}
		}
	}

	/**
	 * Fonction permettant de faire un update avec volley Ici je pars du
	 * princepe que l'url est deja form� pour �tre envoy� avec
	 * l'authentification etc
	 * 
	 * @param url l'objet url de la bdd
	 * @throws JSONException
	 */
	private void update(final UrlServerAttente url) throws JSONException {
		// Get instance du volley singleton
		RequestQueue _volleyQueue = VolleySingleton.getInstance(this.contexte)
				.getRequestQueue();
		_volleyQueue = Volley.newRequestQueue(this.contexte);
		JSONObject jsonUpdate = new JSONObject(url.getParamsBody());
		JsonObjectRequest jsObjRequest = new JsonObjectRequest(

		Request.Method.PUT, url.getCallurl(), jsonUpdate,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						try {
							// Si l'update s'est bien pass�
							if (response.getInt("code") == 200) {
								// On supprime l'URL dans la bdd locale
								urlServerDao.removeUrl(url.getCallurl());
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.i("ERREUR MISE A JOUR DE LA BASE SUR AUTOUPDATE",
								error.toString());
					}
				});
		_volleyQueue.add(jsObjRequest);
	}

	/**
	 * Fonction permettant de faire un delete avec volley
	 * 
	 * @param url l'objet url de la bdd
	 * @throws JSONException 
	 */
	private void delete(final UrlServerAttente url) throws JSONException {
		// Get instance du volley singleton
		RequestQueue _volleyQueue = VolleySingleton.getInstance(this.contexte)
				.getRequestQueue();
		_volleyQueue = Volley.newRequestQueue(this.contexte);
		JSONObject jsonDelete = new JSONObject(url.getParamsBody());
		JsonObjectRequest jsObjRequest = new JsonObjectRequest(

				Request.Method.DELETE, url.getCallurl(), jsonDelete,
						new Response.Listener<JSONObject>() {
							@Override
							public void onResponse(JSONObject response) {
								try {
									//Si on recoit une response 200 OK
									if (response.getInt("code") == 200) {
										//On supprimer l'URL sur la bdd locale
										urlServerDao.removeUrl(url.getCallurl());
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
						}, new Response.ErrorListener() {
							@Override
							public void onErrorResponse(VolleyError error) {
								Log.i("ERREUR DURANT LE DELETE AUTOMATIQUE-----", error.toString());
							}
						});

		_volleyQueue.add(jsObjRequest);
	}

	/**
	 * Fonction permettant de faire un insert avec volley
	 * 
	 * @param url
	 *            l'objet url de la bdd
	 * @throws JSONException
	 */
	private void insert(final UrlServerAttente url) throws JSONException {
		// Get instance du volley singleton
		RequestQueue _volleyQueue = VolleySingleton.getInstance(this.contexte)
				.getRequestQueue();
		_volleyQueue = Volley.newRequestQueue(this.contexte);
		JSONObject jsonInsert = new JSONObject(url.getParamsBody());
		JsonObjectRequest jsObjRequest = new JsonObjectRequest(

		Request.Method.POST, url.getCallurl(), jsonInsert,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						try {
							// Si on re�oit une response 200 OK
							if (response.getInt("code") == 200) {
								// On supprime l'URL dans la bdd locale
								urlServerDao.removeUrl(url.getCallurl());
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Log.i("ERREUR DURANT L'INSERT AUTOMATIQUE-----",
								error.toString());
					}

				});
		_volleyQueue.add(jsObjRequest);
	}

	/**
	 * Fonction permettant de faire un insert ou mise � jours de la photo de
	 * profil C'est une fonction � part car on utilise pas Volley pour l'envoi
	 * de photos. On utilise une requete HTTP POST classique
	 * 
	 * @param url l'objet url de la bdd
	 */
	private void envoiPhoto(final UrlServerAttente url) {
		Thread thread = new Thread() {

			@Override
			public void run() {

				HttpClient client = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(url.getCallurl());
				File photo = new File(url.getParamsBody());
				MultipartEntity entity = new MultipartEntity();

				entity.addPart("image", new FileBody(photo));

				httpPost.setEntity(entity);
				try {
					HttpResponse response = client.execute(httpPost);
					// On supprime l'URL dans la bdd locale
					urlServerDao.removeUrl(url.getCallurl());
				} catch (ClientProtocolException e) {

					Log.i("Api ", "reponse :" + e.toString());
				} catch (IOException e) {

					Log.i("Api ", "reponse :" + e.toString());
				}

			}

		};
		thread.start();
	}
}
