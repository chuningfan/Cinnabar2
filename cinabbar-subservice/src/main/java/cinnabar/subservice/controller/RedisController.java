package cinnabar.subservice.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cinnabar.subservice.entity.CinnabarUser;
import cinnabar.subservice.service.Subservice;

@RestController
@RequestMapping("/redis")
public class RedisController {
	
	@Autowired
	private Subservice subservice;
	
	@GetMapping("/save")
	public void putUserToRedis() {
		CinnabarUser user = new CinnabarUser();
		user.setId(1L);
		user.setAge(12);
		user.setTestName("name gogogo");
		user.setBirthday(new Date());
		subservice.saveUserToRedis(user);
	}
	
	@GetMapping("/get/{key}")
	public CinnabarUser get(@PathVariable Long key) {
		return subservice.getUserFromRedis(key);
	}
	
}
