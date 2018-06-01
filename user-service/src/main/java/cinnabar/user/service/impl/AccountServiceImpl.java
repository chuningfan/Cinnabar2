package cinnabar.user.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import cinnabar.user.entity.Account;
import cinnabar.user.entity.Role;
import cinnabar.user.entity.User;
import cinnabar.user.exception.UsersServiceException;
import cinnabar.user.repository.AccountRepository;
import cinnabar.user.repository.UserRepository;
import cinnabar.user.service.AccountService;

@Service("accountService")
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Account saveOrUpdateAccount(Account account) {
		Account entity = accountRepository.save(account);
		return entity;
	}

	@Override
	public void deleteAccountById(Long id) throws UsersServiceException {
		accountRepository.deleteById(id);
	}

	@Override
	public Account getAccountById(Long id) {
		return accountRepository.getOne(id);
	}

	@Override
	public Account getAccountByLoginNameAndPassword(String loginName, String password) {
		Account acc = new Account();
		acc.setLoginEmail(loginName);
		acc.setLoginName(loginName);
		acc.setLoginPhone(loginName);
		acc.setLoginPassword(password);
		ExampleMatcher matcher = ExampleMatcher.matching()
	            .withMatcher("loginName", match -> match.exact())
	            .withMatcher("loginEmail", match -> match.exact())
	            .withMatcher("loginPhone", match -> match.exact())
	            .withMatcher("loginPassword", match -> match.exact());
		Example<Account> example = Example.of(acc, matcher);
		Optional<Account> o = accountRepository.findOne(example);
		if (o == null) {
			return null;
		}
		return o.get();
	}

	@Override
	public Account createAccount(Account account, User user) {
		Account acc = accountRepository.save(account);
		userRepository.save(user);
		return acc;
	}

}
