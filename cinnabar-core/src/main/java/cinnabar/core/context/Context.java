package cinnabar.core.context;

public class Context {

	private Long userId;
	
	private String ipAddress;
	
	private String userRole;
	
	private String loggedTime;
	
	private String redisId;
	
	private String clientType;
	
	private String rememberMe;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public String getLoggedTime() {
		return loggedTime;
	}

	public void setLoggedTime(String loggedTime) {
		this.loggedTime = loggedTime;
	}

	public void setRedisId(String redisId) {
		this.redisId = redisId;
	}

	public String getRedisId() {
		return redisId;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public String getRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(String rememberMe) {
		this.rememberMe = rememberMe;
	}

	@Override
	public String toString() {
		return "Context [userId=" + userId + ", ipAddress=" + ipAddress + ", userRole=" + userRole + ", loggedTime="
				+ loggedTime + ", redisId=" + redisId + ", clientType=" + clientType + ", rememberMe=" + rememberMe
				+ "]";
	}
	
}
