package cinnabar.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cinnabar.user.entity.Role;
import cinnabar.user.repository.RoleRepository;
import cinnabar.user.service.RoleService;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public Role saveOrUpdateRole(Role role) {
		return roleRepository.save(role);
	}

	@Override
	public void deleteRoleById(Long id) {
		roleRepository.deleteById(id);
	}

}
