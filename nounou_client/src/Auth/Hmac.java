package Auth;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

public class Hmac {
	/*
	 * Pseudo, cle privée et mot de passe du client
	 * Le login et le mot de passe sont enregistré en base de données coté serveur
	 * La clé sert au cryptage SHA1 du HMAC
	 */
	private static String LOGIN = "abcd4ABCD";
    private static String CLE = "bonjourbonsoir";
    private static String PASSWORD = "azerty5AZERTY";
    
    public static String createHmacForServer(String urlParamters,long timestamp) throws SignatureException, NoSuchAlgorithmException, InvalidKeyException
    {
    	/*
    	 * Concatenation des éléments de l'URL, du password et du timestamp
    	 */
        String concat = "";
        String[] tabparam = urlParamters.split("&");
        for (String st : tabparam)
        {
            String[] post = st.split("=");
            concat = concat + post[1];
        }
        concat = concat + PASSWORD;
        System.out.println(concat);
        concat = concat+timestamp;
        String hmac = Signature.calculateRFC2104HMAC(concat, CLE);
        return hmac;
    }
}
