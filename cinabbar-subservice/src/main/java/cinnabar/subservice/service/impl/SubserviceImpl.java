package cinnabar.subservice.service.impl;

import org.springframework.stereotype.Service;

import cinnabar.subservice.dto.User;
import cinnabar.subservice.service.Subservice;

@Service("subservice")
public class SubserviceImpl implements Subservice {

	@Override
	public void test(String name) {
		System.out.println("==============>" + name);
	}

	@Override
	public User saveUser(User user) {
		System.out.println(user.toString());
		return user;
	}

}
