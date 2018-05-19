package cinnabar.core.context;

import java.util.Date;

import cinnabar.core.organization.UserRole;

public class Context {

	private Long userId;
	
	private String ipAddress;
	
	private UserRole role;
	
	private Date loggedTime;
	
	private Object redisId;

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

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public Date getLoggedTime() {
		return loggedTime;
	}

	public void setLoggedTime(Date loggedTime) {
		this.loggedTime = loggedTime;
	}

	public Object getRedisId() {
		return redisId;
	}

	public void setRedisId(Object redisId) {
		this.redisId = redisId;
	}
	
}
