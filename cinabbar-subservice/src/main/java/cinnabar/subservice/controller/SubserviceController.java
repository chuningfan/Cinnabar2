package cinnabar.subservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cinnabar.subservice.dto.User;
import cinnabar.subservice.entity.CinnabarUser;
import cinnabar.subservice.service.Subservice;

/**
 * service 入口
 * @author Vic.Chu
 *
 */
@RestController //标记当前类为spring rest API 入口
@RequestMapping("/subservice") //class级别URL pattern
public class SubserviceController {

	@Autowired
	private Subservice subservice; // DI
	
	//REST GET method级别 pattern
	@GetMapping("/test/{name}")
	public String test(@PathVariable String name) {
		subservice.test(name);
		return "hello " + name;
	}
	
	//REST POST，以此类推， 还有@RestPut  @RestDelete, 我没有写例子。
	@PostMapping("/saveUser")
	public User saveUser(@RequestBody User user) {
		System.out.println("Method [saveUser] was called!");
		return user;
	}
	
	@PostMapping("/saveCinnabarUser")
	public CinnabarUser saveCinnabarUser(@RequestBody CinnabarUser user) {
		System.out.println("[saveCinnabarUser] was called!");
		CinnabarUser cUser = subservice.saveCinnabarUser(user);
		return cUser;
	}
	
	@GetMapping("/get/{userId}")
	public CinnabarUser getCinnabarUser(@PathVariable Long userId) {
		System.out.println("[getCinnabarUser] was called!");
		CinnabarUser cUser = subservice.getCinnabarUser(userId);
		return cUser;
	}
	
	@GetMapping("/getPagedUsers/{pageNum}")
	public List<CinnabarUser> getPagedUsers(@PathVariable int pageNum) {
		Pageable p = PageRequest.of(pageNum - 1, 4);
		List<CinnabarUser> users = subservice.getPagedUsers(p);
		return users;
	}
	
	@GetMapping("/hql/{userId}")
	public CinnabarUser getByHql(@PathVariable Long userId) {
		System.out.println("[getByHql] was called!");
		CinnabarUser cUser = subservice.getUserByHql(userId);
		return cUser;
	}
	
	@GetMapping("/sql/{userId}")
	public CinnabarUser getBySql(@PathVariable Long userId) {
		System.out.println("[getBySql] was called!");
		CinnabarUser cUser = subservice.getUserBySql(userId);
		return cUser;
	}
}
