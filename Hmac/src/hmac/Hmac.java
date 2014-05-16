/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hmac;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.sql.Timestamp;

/**
 *
 * @author JOHNNY
 */
public class Hmac
{

    private static String PSEUDO = "abcd4ABCD";
    private static String CLE = "bonjourbonsoir";
    private static String PASSWORD = "azerty5AZERTY";
    private static String URLSERVEUR = "http://localhost:3000/api/nounous";

    public static void main(String[] args) throws Exception
    {
        java.util.Date date = new java.util.Date();
        long timestampClient = new Timestamp(date.getTime()).getTime();
        //sendRequestGet(timestampClient);
        sendRequestPost(timestampClient, "POST");
    }
    public static void sendRequestPost(long timestamp, String postGetPut) throws MalformedURLException, IOException, SignatureException, NoSuchAlgorithmException, InvalidKeyException
    {
        String urlParameters = "nom=JAVABIEN&prenom=JAVAPASBIEN&dateDeNaissance=16/04/1992&civilite=JAVA&adresse=une adresse de test java&email=jonathanaffre@gmail.com&tarifHoraire=500&descriptionPrestation=description description&telephone=0611675498&disponibilite=dispo&cheminPhoto=chemin chemin&password=azerrttytrut";
        String signatureClient = createHmacForServer(PASSWORD,urlParameters,timestamp);
        System.out.println(signatureClient);
        String request = URLSERVEUR + "?time=" + timestamp + "&login=" + PSEUDO+"&signature="+signatureClient;
        URL url = new URL(request);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setInstanceFollowRedirects(false);
        connection.setRequestMethod(postGetPut);
        connection.setUseCaches(false);
        try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream()))
        {
            wr.writeBytes(urlParameters);
            wr.flush();
        }
        String line;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream())))
        {
            StringBuilder builder = new StringBuilder();
            for (line = null; (line = reader.readLine()) != null;)
            {
                builder.append(line).append("\n");
            }
            System.out.println(builder.toString());
        }
        connection.disconnect();
    }

    public static String createHmacForServer(String password, String urlParamters,long timestamp) throws SignatureException, NoSuchAlgorithmException, InvalidKeyException
    {
        String concat = "";
        String[] tabparam = urlParamters.split("&");
        for (String st : tabparam)
        {
            String[] post = st.split("=");
            concat = concat + post[1];
        }
        concat = concat + password;
        System.out.println(concat);
        concat = concat+timestamp;
        String hmac = Signature.calculateRFC2104HMAC(concat, CLE);
        return hmac;
    }
}
