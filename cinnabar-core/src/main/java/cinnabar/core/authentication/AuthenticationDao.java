package cinnabar.core.authentication;

import org.springframework.stereotype.Repository;

import cinnabar.core.authentication.dto.UserValidateDto;

@Repository("authenticationDao")
public class AuthenticationDao {
	
	public UserValidateDto validate(String userName, String password) {
		return null;
	};
	
}
