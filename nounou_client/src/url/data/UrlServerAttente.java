package url.data;

import android.text.format.Time;

public class UrlServerAttente {
	private String CALL_URL;
	private String TIMESTAMP;
	
	UrlServerAttente(String url,String timestamp){
		this.CALL_URL = url;
		this.TIMESTAMP = timestamp;
	}
	
	public String getCallurl(){
		return this.CALL_URL;
	}
	
	public String getTimestamp(){
		return this.TIMESTAMP;
	}
	
	public void setTimestamp(String timestamp){
		this.TIMESTAMP = timestamp;
	}
	
	public void setCallurl(String callurl){
		this.CALL_URL = callurl;
	}
}
