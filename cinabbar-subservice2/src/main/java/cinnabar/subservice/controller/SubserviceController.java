package cinnabar.subservice.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

import cinnabar.subservice.dto.Hobby;
import cinnabar.subservice.dto.User;
import cinnabar.subservice.service.Subservice;

@RestController
@RequestMapping("/subservice2")
public class SubserviceController {

	@Autowired
	private Subservice subservice;
	
	@GetMapping("/test")
	public User test() {
		User user = new User();
		user.setId(1L);
		user.setName("test");
		user.setBirthday(new Date());
		Hobby h1 = new Hobby();
		h1.setName("football");
		Hobby h2 = new Hobby();
		h2.setName("basketball");
		List<Hobby> hobbies = Lists.newArrayList(h1, h2);
		user.setHobbies(hobbies);
		return subservice.test(user);
	}
	
}
