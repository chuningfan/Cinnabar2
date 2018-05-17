package cinnabar.subservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import cinnabar.subservice.dto.User;
import cinnabar.subservice.service.Subservice;

@Service("subservice")
public class SubserviceImpl implements Subservice {

	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	@HystrixCommand(fallbackMethod="feedback")
	public User test(User user) {
		ResponseEntity<User> resp = restTemplate.postForEntity("http://subservice/subservice/saveUser", user, User.class);
		return resp.getBody();
	}
	
	public User feedback(User user) {
		return null;
	}

}
