package com.example.nounou;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.nounou.data.Nounou;

public class SingletonCache {
	/** Constructeur priv� pour le singleton*/
	private SingletonCache() {}

	/** Instance unique non pr�initialis�e */
	private static SingletonCache INSTANCE = null;
	private static HashMap<String,Nounou> mapObjects = new HashMap<String,Nounou>();
	
	/** Point d'acc�s pour l'instance unique du singleton */
	public static SingletonCache getInstance() {
		if (INSTANCE == null) {
			/**Synchronized permet de partager l'instance entre les threads*/
			synchronized (SingletonCache.class) {
				if (INSTANCE == null) {
					INSTANCE = new SingletonCache();
				}
			}
		}
		return INSTANCE;
	}
	
	//Ajout d'un objet en cle valeur, la cle correspond à l'id, cela permettra de requeter le HashMap
	public void addNounou(Nounou nounou)
	{
		mapObjects.put(String.valueOf(nounou.getIdNounou()), nounou);
	}
}
