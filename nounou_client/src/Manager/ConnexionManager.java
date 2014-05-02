package Manager;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnexionManager {

	public static boolean testConnexion(ConnectivityManager cm)
	{
		boolean result = false;
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if(netInfo != null && netInfo.isConnectedOrConnecting())
		{
			result = true;;
		}
		return result;
	}

}
