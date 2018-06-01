package cinnabar.user.service;

import java.util.List;

import cinnabar.user.entity.User;

public interface UserService {
	
	User saveOrUpdateUser(User users);
	
	void deleteUserById(Long id);
	
	User getUserById(Long id);
	
	List<User> getAllUsers();
	
	User getUserInfo(Long accountId, Long marketingId);
	
}
