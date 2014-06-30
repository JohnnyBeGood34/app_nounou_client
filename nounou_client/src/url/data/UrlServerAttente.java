package url.data;

public class UrlServerAttente {
	private String CALL_URL;
	private String TIMESTAMP;
	/**
	 * Le label peut être update, insert, delete ou photo
	 */
	private String LABEL;
	
	private String PARAM_BODY;
	
	UrlServerAttente(String url,String timestamp,String label,String params){
		this.CALL_URL = url;
		this.TIMESTAMP = timestamp;
		this.LABEL = label;
		this.PARAM_BODY = params;
	}
	
	public String getCallurl(){
		return this.CALL_URL;
	}
	
	public String getParamsBody(){
		return this.PARAM_BODY;
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
	
	public void setParamsBody(String params){
		this.PARAM_BODY = params;
	}
	
	public void setLabel(String label){
		this.LABEL = label;
	}
}
