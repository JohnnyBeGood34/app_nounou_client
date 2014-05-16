package Manager;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnexionManager {

	public static boolean testConnexion(ConnectivityManager connectivityManager)
	{
		boolean result = false;
		NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
		if(netInfo != null && netInfo.isConnectedOrConnecting())
		{
			result = true;;
		}
		return result;
	}

}
