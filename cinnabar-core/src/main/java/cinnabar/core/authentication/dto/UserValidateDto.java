package cinnabar.core.authentication.dto;

import cinnabar.core.constant.UserStatus;
import cinnabar.core.organization.UserRole;

public class UserValidateDto {
	
	private Long id;
	
	private String userName;
	
	private UserStatus userStatus;
	
	private String redisId;
	
	private UserRole userRole;
	

	public UserValidateDto(String redisId) {
		this.redisId = redisId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public UserStatus getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}

	public String getRedisId() {
		return redisId;
	}

	public void setRedisId(String redisId) {
		this.redisId = redisId;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}
	
}
