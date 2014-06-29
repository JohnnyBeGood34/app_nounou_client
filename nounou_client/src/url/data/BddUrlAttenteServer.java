package url.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class BddUrlAttenteServer extends SQLiteOpenHelper {
	/*Requete de creation de la table URL*/
	private static final String CREATION_BASE_REQUEST = "CREATE TABLE "+UrlServerAttenteDao.TABLE_NAME+" ("+UrlServerAttenteDao.CALL_URL+" TEXT NOT NULL PRIMARY KEY, "+UrlServerAttenteDao.TIMESTAMP+" TEXT NOT NULL );";
	public BddUrlAttenteServer(Context context, String name,CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATION_BASE_REQUEST);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE "+UrlServerAttenteDao.TABLE_NAME+";");
		onCreate(db);
	}

}
