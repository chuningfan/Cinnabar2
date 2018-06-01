package cinnabar.user.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import cinnabar.core.db.BaseEntity;

@Entity
@Table(name="users")
public class User extends BaseEntity {
	
	private Long accountId;
	
	private String familyName;
	
	private String givenName;
	
	private Date birthday;
	
	private String email;
	
	private String phone;
	
	private String photoUrl;
	
	private Long marketingId;
	
	private String IDCardNumber;
	
	private Set<Role> roles;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity=Account.class)
    @JoinColumn(name = "account_id")
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public Long getMarketingId() {
		return marketingId;
	}

	public void setMarketingId(Long marketingId) {
		this.marketingId = marketingId;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER, targetEntity=Role.class)
	@JoinTable(name = "user_roles",
	joinColumns = {@JoinColumn(name = "account_id"), @JoinColumn(name = "marketing_id")},
	inverseJoinColumns = @JoinColumn(name = "role_id"))
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getIDCardNumber() {
		return IDCardNumber;
	}

	public void setIDCardNumber(String iDCardNumber) {
		IDCardNumber = iDCardNumber;
	}
	
}
