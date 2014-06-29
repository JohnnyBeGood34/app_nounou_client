package url.data;

public class UrlServerAttente {
	private String CALL_URL;
	private String TIMESTAMP;
	private String LABEL;
	
	UrlServerAttente(String url,String timestamp,String label){
		this.CALL_URL = url;
		this.TIMESTAMP = timestamp;
		this.LABEL = label;
	}
	
	public String getCallurl(){
		return this.CALL_URL;
	}
	
	public String getTimestamp(){
		return this.TIMESTAMP;
	}
	
	public String getLabel(){
		return this.LABEL;
	}
	public void setTimestamp(String timestamp){
		this.TIMESTAMP = timestamp;
	}
	
	public void setCallurl(String callurl){
		this.CALL_URL = callurl;
	}
	
	public void setLabel(String label){
		this.LABEL = label;
	}
}
