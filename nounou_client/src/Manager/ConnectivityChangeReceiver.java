package Manager;

import com.example.nounou.ListDesNounous;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class ConnectivityChangeReceiver extends BroadcastReceiver {
	static ProgressDialog dialog=null;
   @Override
   public void onReceive(Context context, Intent intent) {
	   dialog = ProgressDialog.show(context, "", "En attente d'une connexion...");
      if(debugIntent(intent, "Network State") == true){
    	  dialog.hide();
    	  Log.i("INFO CONNEXION-----------","CO");
    	  context.startActivity(new Intent(context, ListDesNounous.class));
      }
      else{
    	  Log.i("INFO CONNEXION-----------","PAS CO");
      }
   }
   
   private Boolean debugIntent(Intent intent, String tag) {
      Bundle extras = intent.getExtras();
      Boolean checkConnexion = true;
      if (extras != null) {
         for (String key: extras.keySet()) {
        	 if(key.equals("noConnectivity"))
        	 {
        		 checkConnexion = false;
        	 }
         }
      }

      return checkConnexion;
   }

}
