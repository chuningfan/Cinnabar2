package cinnabar.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cinnabar.core.db.BaseEntity;

@Entity
@Table(name="roles")
public class Role extends BaseEntity {
	
	private String roleName;
	
	@Column(name="role_name")
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
