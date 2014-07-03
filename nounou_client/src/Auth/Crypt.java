package Auth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Crypt {
	
	public static String toMD5(String passToCrypt){
		
		StringBuffer buffer=new StringBuffer();
		
		try {
			MessageDigest message =MessageDigest.getInstance("MD5");
			message.update(passToCrypt.getBytes());
			
			byte byteData[]=message.digest();
			
			
			
			for(int i=0;i< byteData.length;i++){
				
				buffer.append(Integer.toString((byteData[i] & 0xff)+0x100,16).substring(1));
			}
			
			
		} catch (NoSuchAlgorithmException e) {
			
			e.printStackTrace();
		}
		return buffer.toString();
	}

}
