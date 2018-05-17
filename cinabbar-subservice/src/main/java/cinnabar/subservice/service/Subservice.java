package cinnabar.subservice.service;

import cinnabar.subservice.dto.User;

public interface Subservice {

	void test(String name);
	
	User saveUser(User user);
	
}
