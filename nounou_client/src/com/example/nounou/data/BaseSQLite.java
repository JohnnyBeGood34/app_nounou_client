package com.example.nounou.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseSQLite extends SQLiteOpenHelper {
    
    //requete de création
    private static final String CREATE_DATABASE_REQUEST = "CREATE TABLE " + NounouBdd.TABLE_NAME + " ("
    + NounouBdd.COLUMN_ID_NOUNOU + " TEXT NOT NULL PRIMARY KEY, " + NounouBdd.COLUMN_NOM + " TEXT NOT NULL, "
    + NounouBdd.COLUMN_PRENOM + " TEXT NOT NULL," + NounouBdd.COLUMN_DATE_NAISSANCE+ " TEXT NOT NULL," 
    + NounouBdd.COLUMN_CIVILITE + " TEXT NOT NULL," + NounouBdd.COLUMN_ADRESSE + " TEXT NOT NULL," + NounouBdd.COLUMN_EMAIL + " TEXT NOT NULL,"
    + NounouBdd.COLUMN_TARIF_HORAIRE + " TEXT NOT NULL," + NounouBdd.COLUMN_DESCRIPTION_PRESTATION + " TEXT NOT NULL,"
    + NounouBdd.COLUMN_TELEPHONE + " TEXT NOT NULL," + NounouBdd.COLUMN_DISPONIBILITE + " TEXT NOT NULL,"
    + NounouBdd.COLUMN_CHEMIN_PHOTO + " TEXT NOT NULL," + NounouBdd.COLUMN_PASSWORD + " TEXT NOT NULL );";

    public BaseSQLite(Context context, String name, CursorFactory factory, int version) {
          super(context, name, factory, version);
    }

    public void onCreate(SQLiteDatabase db) {
          //on créé la table de la requete de création
          db.execSQL(CREATE_DATABASE_REQUEST);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
          //ici on supprime et recréer la base de données.
          db.execSQL("DROP TABLE " + NounouBdd.TABLE_NAME + ";");
          onCreate(db);
    }

}
