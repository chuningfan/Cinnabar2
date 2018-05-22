package cinnabar.core.constant;

public enum ClientType {
	
	BROWSER("browser"), IOS("ios"), ANDROID("android");
	
	private String clientType;
	
	private ClientType(String clientType) {
		this.setClientType(clientType);
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}
	
}
