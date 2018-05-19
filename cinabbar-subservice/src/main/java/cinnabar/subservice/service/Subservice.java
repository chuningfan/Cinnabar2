package cinnabar.subservice.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import cinnabar.subservice.dto.User;
import cinnabar.subservice.entity.CinnabarUser;

public interface Subservice {

	void test(String name);
	
	User saveUser(User user);
	
	CinnabarUser saveCinnabarUser(CinnabarUser user);
	
	CinnabarUser getCinnabarUser(Long id);
	
	List<CinnabarUser> getPagedUsers(Pageable p);
	
	CinnabarUser getUserByHql(Long id);
	
	CinnabarUser getUserBySql(Long id);
	
	void saveUserToRedis(CinnabarUser user);
	
	CinnabarUser getUserFromRedis(Long key);
}
