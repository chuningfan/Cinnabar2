package cinnabar.user.service;

import cinnabar.user.entity.Role;

public interface RoleService {
	
	Role saveOrUpdateRole(Role role);
	
	void deleteRoleById(Long id);
	
}
