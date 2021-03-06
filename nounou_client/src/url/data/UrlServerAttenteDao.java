package url.data;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UrlServerAttenteDao {
	private static final String DB_NAME = "infos_app.db";
	private static final int VERSION = 1;
	
	public static final String TABLE_NAME = "urlAttente";
	
	public static final String CALL_URL = "call_url";
	public static final String TIMESTAMP = "timestamp_request";
	public static final String LABEL = "label";
	public static final String PARAM_BODY = "param_body";
	
	public static final int NUM_COLUMN_CALL_URL = 0;
	public static final int NUM_COLUMN_TIMESTAMP = 1;
	public static final int NUM_COLUMN_LABEL = 2;
	public static final int NUM_COLUMN_PARAM = 3;	
	
	private SQLiteDatabase managerDb;
	private BddUrlAttenteServer managerbaseSqlite;

	/* Cr�� la base si elle n'existe pas */
	public UrlServerAttenteDao(Context contexte) {
		this.managerbaseSqlite = new BddUrlAttenteServer(contexte, DB_NAME, null,
				VERSION);
	}

	/* Ouvre la base en �criture */
	public void open() {
		this.managerDb = managerbaseSqlite.getWritableDatabase();
	}

	/* Fermeture de la base de donn�es */
	public void close() {
		managerDb.close();
	}

	/* Insertion d'une url en attente en bdd */
	public long insertUrl(UrlServerAttente url) {
		ContentValues values = new ContentValues();
		values.put(CALL_URL, url.getCallurl());
		values.put(TIMESTAMP, url.getTimestamp());
		values.put(LABEL, url.getLabel());
		values.put(PARAM_BODY, url.getParamsBody());
		return managerDb.insert(TABLE_NAME, null, values);
	}

	/* Mise � jour */
	public int updateUrl(UrlServerAttente url) {
		ContentValues values = new ContentValues();
		values.put(CALL_URL, url.getCallurl());
		values.put(TIMESTAMP, url.getTimestamp());
		values.put(LABEL, url.getLabel());
		values.put(PARAM_BODY, url.getParamsBody());
		return managerDb.update(TABLE_NAME, values,
				CALL_URL + " = '" + url.getCallurl() + "'", null);
	}
	
	/*Suppression*/
	public int removeUrl(String url){
		return managerDb.delete(TABLE_NAME, CALL_URL + " = '"+url+"'", null);
	}
	
	/*Retourne la liste de toutes les urls de la table*/
	public List<UrlServerAttente> getAllurl(){
		List<UrlServerAttente> listeUrl = new ArrayList<UrlServerAttente>();
		Cursor c = managerDb.query(TABLE_NAME, new String[]{CALL_URL,TIMESTAMP,LABEL,PARAM_BODY},null,null,null,null,null);
		c.moveToFirst();
		int sizeCursor = c.getCount();
		for (int i=0;i<sizeCursor;i++){
			UrlServerAttente URL = new UrlServerAttente(c.getString(NUM_COLUMN_CALL_URL),c.getString(NUM_COLUMN_TIMESTAMP),c.getString(NUM_COLUMN_LABEL),c.getString(NUM_COLUMN_PARAM));
			listeUrl.add(URL);
			c.moveToNext();
		}
		c.close();
		return listeUrl;
	}
	
	public UrlServerAttente getUrl(String url){
		Cursor curseur = managerDb.query(TABLE_NAME,new String[]{CALL_URL,TIMESTAMP,LABEL,PARAM_BODY},CALL_URL + " LIKE \""+url+"\" ",null,null,null,null);
		UrlServerAttente URL = new UrlServerAttente(curseur.getString(NUM_COLUMN_CALL_URL),curseur.getString(NUM_COLUMN_TIMESTAMP),curseur.getString(NUM_COLUMN_LABEL),curseur.getString(NUM_COLUMN_PARAM));
		curseur.close();
		return URL;
	}
	
	public int checkUrlAttente(){
		Cursor mCount= managerDb.rawQuery("select count(*) from "+TABLE_NAME, null);
		mCount.moveToFirst();
		int count= mCount.getInt(0);
		mCount.close();
		return count;
	}
	
	public void deleteAll(){
		managerDb.delete(TABLE_NAME, null, null);
	}
}
