package url.data;

import java.util.List;

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
		List<UrlServerAttente> urlsEnAttente = urlServerDao.getAllurl();
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
				 * Récupération du label pour savoir quel methodes volley
				 * appeler
				 */
				String label = url.getLabel();

				if (label.equals("update")) {
					this.update(url);
				} else if (label.equals("insert")) {
					this.insert(url);
				} else if (label.equals("delete")) {
					this.delete(url);
				}
			}
		}
	}

	/**
	 * Fonction permettant de faire un update avec volley Ici je pars du
	 * princepe que l'url est deja formé pour être envoyé avec
	 * l'authentification etc
	 * 
	 * @param url l'objet url de la bdd
	 * @throws JSONException
	 */
	private void update(UrlServerAttente url) throws JSONException {
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
						
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						// TODO Auto-generated method stub

					}
				});
	}

	/**
	 * Fonction permettant de faire un delete avec volley
	 * 
	 * @param url l'objet url de la bdd
	 */
	private void delete(UrlServerAttente url) {

	}

	/**
	 * Fonction permettant de faire un insert avec volley
	 * 
	 * @param url l'objet url de la bdd
	 */
	private void insert(UrlServerAttente url) {

	}
}
