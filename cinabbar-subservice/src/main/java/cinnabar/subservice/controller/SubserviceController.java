package cinnabar.subservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cinnabar.subservice.dto.User;
import cinnabar.subservice.service.Subservice;

@RestController
@RequestMapping("/subservice")
public class SubserviceController {

	@Autowired
	private Subservice subservice;
	
	@GetMapping("/test/{name}")
	public String test(@PathVariable String name) {
		subservice.test(name);
		return "hello " + name;
	}
	
	@PostMapping("/saveUser")
	public User saveUser(@RequestBody User user) {
		System.out.println("Method [saveUser] was called!");
		return user;
	}
	
}
