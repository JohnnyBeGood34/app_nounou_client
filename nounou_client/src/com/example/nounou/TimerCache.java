package com.example.nounou;

import java.util.TimerTask;

import android.content.Context;

/*
 * Classe héritant de TimerTask permettant d'éxécuter 
 * une tache à intervalles réguliers
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
