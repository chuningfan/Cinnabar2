package cinnabar.subservice.service;

import cinnabar.subservice.dto.User;
import cinnabar.subservice.entity.CinnabarUser;

public interface Subservice {

	User test(User user);
	
	CinnabarUser saveCinnabarUser(CinnabarUser user);
	
}
