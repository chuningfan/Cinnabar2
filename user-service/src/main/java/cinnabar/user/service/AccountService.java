package cinnabar.user.service;

import cinnabar.user.entity.Account;
import cinnabar.user.entity.User;
import cinnabar.user.exception.UsersServiceException;

public interface AccountService {
	
	Account saveOrUpdateAccount(Account account);
	
	void deleteAccountById(Long id) throws UsersServiceException;
	
	Account getAccountById(Long id);
	
	Account getAccountByLoginNameAndPassword(String loginName, String password);
	
	Account createAccount(Account account, User user);
	
}
