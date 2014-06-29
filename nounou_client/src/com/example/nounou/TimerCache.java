package com.example.nounou;

import java.util.TimerTask;

import android.content.Context;

/*
 * Classe h�ritant de TimerTask permettant d'�x�cuter 
 * une tache � intervalles r�guliers
 * */

public class TimerCache extends TimerTask{
    
	private Context context;
	
	public TimerCache(Context context){
		this.context=context;
	}
	
	
	@Override
	public void run() {
		
		VolleySingleton.getInstance(context).clearCache();
	}

}
