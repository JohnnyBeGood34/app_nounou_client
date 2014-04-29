package com.example.nounou.data;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class NounouBdd {
	private static final int VERSION = 1;
	private static final String DB_NAME = "infos_app.db";
    public static final String TABLE_NAME = "table_nounou";
    public static final String COLUMN_ID_NOUNOU = "id_nounou";
    public static final int NUM_COLUMN_ID_NOUNOU = 0;
    public static final String COLUMN_NOM = "nom";
    public static final int NUM_COLUMN_NOM = 1;
    public static final String COLUMN_PRENOM = "prenom";
    public static final int NUM_COLUMN_PRENOM = 2;
    public static final String COLUMN_DATE_NAISSANCE = "date_naissance";
    public static final int NUM_COLUMN_DATE_NAISSANCE = 3;
    public static final String COLUMN_CIVILITE = "civilite";
    public static final int  NUM_COLUMN_CIVILITE = 4;
    public static final String COLUMN_ADRESSE = "adresse";
    public static final int NUM_COLUMN_ADRESSE = 5;
    public static final String COLUMN_EMAIL = "email";
    public static final int NUM_COLUMN_EMAIL = 6;
    public static final String COLUMN_TARIF_HORAIRE = "tarif_horaire";
    public static final int NUM_COLUMN_TARIF_HORAIRE = 7;
    public static final String COLUMN_DESCRIPTION_PRESTATION = "description_prestation";
    public static final int NUM_COLUMN_DESCRIPTION_PRESTATION = 8;
    public static final String COLUMN_TELEPHONE = "telephone";
    public static final int NUM_COLUMN_TELEPHONE = 9;
    public static final String COLUMN_DISPONIBILITE = "disponibilite";
    public static final int NUM_COLUMN_DISPONIBILITE = 10;
    public static final String COLUMN_CHEMIN_PHOTO = "chemin_photo";
    public static final int NUM_COLUMN_CHEMIN_PHOTO = 11;
    public static final String COLUMN_PASSWORD = "password";
    public static final int NUM_COLUMN_PASSWORD = 12;
    
    private SQLiteDatabase mDb;
    
    private BaseSQLite mBaseSQLite;
    
    public final static Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"
        );
    
    public final static Pattern TARIF_PATTERN = Pattern.compile(
    		".[0-9]{1,5}"
    		);
    
    public final static Pattern TELEPHONE_PATTERN = Pattern.compile(
    		"\\d\\d\\d\\d\\d\\d\\d\\d\\d\\d"
    		);
    
    public final static Pattern DATE_PATTERN = Pattern.compile(
    		"(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)"
    		);
    		
    
    
    /** créer la base de données si inexistante */
    public NounouBdd(Context context){
          //On créer la BDD et sa table
          mBaseSQLite = new BaseSQLite(context, DB_NAME, null, VERSION);
    }
    
    /** ouvre la base de données en écriture */
    public void open(){
          //on ouvre la BDD en écriture
          mDb = mBaseSQLite.getWritableDatabase();
    }

    /** ferme la base de données */
    public void close(){
          //on ferme l'accès à la BDD
          mDb.close();
    }
    
    
    /** insere une instance de Nounou */
    public long insertNounou(Nounou appInfo){
          ContentValues values = new ContentValues();
          values.put(COLUMN_ID_NOUNOU, appInfo.getIdNounou());
          values.put(COLUMN_NOM, appInfo.getNom());
          values.put(COLUMN_PRENOM, appInfo.getPrenom());
          values.put(COLUMN_DATE_NAISSANCE, appInfo.getDateDeNaissance());
          values.put(COLUMN_CIVILITE, appInfo.getCivilite());
          values.put(COLUMN_ADRESSE, appInfo.getAdresse());
          values.put(COLUMN_EMAIL, appInfo.getEmail());
          values.put(COLUMN_TARIF_HORAIRE, appInfo.getTarifHoraire());
          values.put(COLUMN_DESCRIPTION_PRESTATION, appInfo.getDescriptionPrestation());
          values.put(COLUMN_TELEPHONE, appInfo.getTelephone());
          values.put(COLUMN_DISPONIBILITE, appInfo.getDisponibilite());
          values.put(COLUMN_CHEMIN_PHOTO, appInfo.getCheminPhoto());
          values.put(COLUMN_PASSWORD, appInfo.getPassword());
          return mDb.insert(TABLE_NAME, null, values);
    }
    
    /** met à jour une instance de Nounou
     * @return: le nombre de lignes éditées*/
    public int updateNounou(Nounou appInfo){
    	ContentValues values = new ContentValues();
         values.put(COLUMN_NOM, appInfo.getNom());
         values.put(COLUMN_PRENOM, appInfo.getPrenom());
         values.put(COLUMN_DATE_NAISSANCE, appInfo.getDateDeNaissance());
         values.put(COLUMN_CIVILITE, appInfo.getCivilite());
         values.put(COLUMN_ADRESSE, appInfo.getAdresse());
         values.put(COLUMN_EMAIL, appInfo.getEmail());
         values.put(COLUMN_TARIF_HORAIRE, appInfo.getTarifHoraire());
         values.put(COLUMN_DESCRIPTION_PRESTATION, appInfo.getDescriptionPrestation());
         values.put(COLUMN_TELEPHONE, appInfo.getTelephone());
         values.put(COLUMN_DISPONIBILITE, appInfo.getDisponibilite());
         values.put(COLUMN_CHEMIN_PHOTO, appInfo.getCheminPhoto());
         values.put(COLUMN_PASSWORD, appInfo.getPassword());
     	return mDb.update(TABLE_NAME, values, COLUMN_EMAIL + " = '" +appInfo.getEmail()+"'", null);
    }
    /** supprimer une instance de Nounou */
    public int removeNounou(String idNounou){
          return mDb.delete(TABLE_NAME, COLUMN_EMAIL + " = '" +idNounou+"'", null);
    }

    /** retourne toutes les instances d'application information */
    public List<Nounou> getAllNounou() {
          Cursor c = mDb.query(TABLE_NAME, new String[] {COLUMN_ID_NOUNOU, COLUMN_NOM, COLUMN_PRENOM, COLUMN_DATE_NAISSANCE,
        		  COLUMN_CIVILITE,COLUMN_ADRESSE,COLUMN_EMAIL,COLUMN_TARIF_HORAIRE,COLUMN_DESCRIPTION_PRESTATION,
        		  COLUMN_TELEPHONE,COLUMN_DISPONIBILITE,COLUMN_CHEMIN_PHOTO,COLUMN_PASSWORD}, null, null, null, null, null);
          return cursorToNounouList(c);
    }
    /** retourne une instance de Nounou avec son id */
    public Nounou getNounouConnexion(String id){
          Cursor c = mDb.query(TABLE_NAME, new String[]{COLUMN_ID_NOUNOU, COLUMN_NOM, COLUMN_PRENOM, COLUMN_DATE_NAISSANCE,
        		  COLUMN_CIVILITE,COLUMN_ADRESSE,COLUMN_EMAIL,COLUMN_TARIF_HORAIRE,COLUMN_DESCRIPTION_PRESTATION,
        		  COLUMN_TELEPHONE,COLUMN_DISPONIBILITE,COLUMN_CHEMIN_PHOTO,COLUMN_PASSWORD}, COLUMN_EMAIL + " LIKE \"" + id +"\"", null, null, null, null);

          return cursorToNounou(c);
    }
    public List<Nounou> getNounouWithLogin(String login){
        Cursor c = mDb.query(TABLE_NAME, new String[]{COLUMN_ID_NOUNOU, COLUMN_NOM, COLUMN_PRENOM, COLUMN_DATE_NAISSANCE,
      		  COLUMN_CIVILITE,COLUMN_ADRESSE,COLUMN_EMAIL,COLUMN_TARIF_HORAIRE,COLUMN_DESCRIPTION_PRESTATION,
      		  COLUMN_TELEPHONE,COLUMN_DISPONIBILITE,COLUMN_CHEMIN_PHOTO,COLUMN_PASSWORD}, COLUMN_EMAIL + " LIKE \"" + login +"\"", null, null, null, null);
        return cursorToNounouList(c);
  }
    private Nounou cursorToNounou(Cursor c){
        if (c.getCount() == 0)
              return null;
        c.moveToFirst();
        Nounou appInfo = new Nounou();

        appInfo.setIdNounou(c.getString(NUM_COLUMN_ID_NOUNOU));
        appInfo.setNom(c.getString(NUM_COLUMN_NOM));
        appInfo.setPrenom(c.getString(NUM_COLUMN_PRENOM));
        appInfo.setDateDeNaissance(c.getString(NUM_COLUMN_DATE_NAISSANCE));
        appInfo.setCivilite(c.getString(NUM_COLUMN_CIVILITE));
        appInfo.setAdresse(c.getString(NUM_COLUMN_ADRESSE));
        appInfo.setEmail(c.getString(NUM_COLUMN_EMAIL));
        appInfo.setTarifHoraire(c.getString(NUM_COLUMN_TARIF_HORAIRE));
        appInfo.setDescriptionPrestation(c.getString(NUM_COLUMN_DESCRIPTION_PRESTATION));
        appInfo.setTelephone(c.getString(NUM_COLUMN_TELEPHONE));
        appInfo.setDisponibilite(c.getString(NUM_COLUMN_DISPONIBILITE));
        appInfo.setCheminPhoto(c.getString(NUM_COLUMN_CHEMIN_PHOTO));
        appInfo.setPassword(c.getString(NUM_COLUMN_PASSWORD));
        c.close();
        return appInfo;
    }
    private List<Nounou> cursorToNounouList(Cursor c){

        c.moveToFirst();
        List<Nounou> apps = new ArrayList<Nounou>();
        int nb = 0, size = c.getCount();
        while(nb<size) {
        	Nounou appInfo = new Nounou();
        	appInfo.setIdNounou(c.getString(NUM_COLUMN_ID_NOUNOU));
            appInfo.setNom(c.getString(NUM_COLUMN_NOM));
            appInfo.setPrenom(c.getString(NUM_COLUMN_PRENOM));
            appInfo.setDateDeNaissance(c.getString(NUM_COLUMN_DATE_NAISSANCE));
            appInfo.setCivilite(c.getString(NUM_COLUMN_CIVILITE));
            appInfo.setAdresse(c.getString(NUM_COLUMN_ADRESSE));
            appInfo.setEmail(c.getString(NUM_COLUMN_EMAIL));
            appInfo.setTarifHoraire(c.getString(NUM_COLUMN_TARIF_HORAIRE));
            appInfo.setDescriptionPrestation(c.getString(NUM_COLUMN_DESCRIPTION_PRESTATION));
            appInfo.setTelephone(c.getString(NUM_COLUMN_TELEPHONE));
            appInfo.setDisponibilite(c.getString(NUM_COLUMN_DISPONIBILITE));
            appInfo.setCheminPhoto(c.getString(NUM_COLUMN_CHEMIN_PHOTO));
            appInfo.setPassword(c.getString(NUM_COLUMN_PASSWORD));
            apps.add(appInfo);
            c.moveToNext();
            nb++;
        }

        c.close();
        return apps;
  }
    
    //Fonction qui vérifie que l'email est valide (renvoie true ou false)
    public boolean verificationMail(String email)
    {
    	return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }
    
    //Vérifie que le tarif horaire est bien un nombre
    public boolean verificationTarif (String tarifHoraire)
    {
    	return TARIF_PATTERN.matcher(tarifHoraire).matches();
    }
    
    //Vérifie que le numéro de téléphone ne contient que des chiffres
    public boolean verificationTelephone(String telephone)
    {
    	return TELEPHONE_PATTERN.matcher(telephone).matches();
    }
    
    //Vérifie le format et la validité d'une date
    public boolean verificationDate(String Date)
    {
    	return DATE_PATTERN.matcher(Date).matches();
    }
    
}
