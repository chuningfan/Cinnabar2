package cinnabar.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import cinnabar.core.constant.UserStatus;
import cinnabar.core.db.BaseEntity;
import cinnabar.user.constant.PasswordStrength;

@Entity
@Table(name="users")
public class Account extends BaseEntity {
	
	private String loginName;
	
	private String loginPhone;
	
	private String loginEmail;
	
	private String loginPassword;
	
	private UserStatus userStatus;
	
	private PasswordStrength passwordStrength;

	@Column(name="login_name")
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Column(name="login_phone")
	public String getLoginPhone() {
		return loginPhone;
	}

	public void setLoginPhone(String loginPhone) {
		this.loginPhone = loginPhone;
	}

	@Column(name="login_email")
	public String getLoginEmail() {
		return loginEmail;
	}

	public void setLoginEmail(String loginEmail) {
		this.loginEmail = loginEmail;
	}

	@Column(name="login_password")
	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	@Column(name="user_status")
	@Enumerated(EnumType.STRING)
	public UserStatus getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}

	@Column(name="password_strength")
	@Enumerated(EnumType.STRING)
	public PasswordStrength getPasswordStrength() {
		return passwordStrength;
	}

	public void setPasswordStrength(PasswordStrength passwordStrength) {
		this.passwordStrength = passwordStrength;
	}
	
}
