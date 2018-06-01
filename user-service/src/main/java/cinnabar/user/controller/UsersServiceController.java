package cinnabar.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import cinnabar.user.entity.Account;
import cinnabar.user.entity.User;
import cinnabar.user.service.AccountService;
import cinnabar.user.service.RoleService;
import cinnabar.user.service.UserService;


@Controller
@RequestMapping("/usersService")
public class UsersServiceController {
	
	@Autowired
	private UserService usersService;
	
	@Autowired
	private AccountService accountsService;

	@Autowired
	private RoleService rolesService;
	
	@GetMapping("/getUserInfo/{accountId}/{marketingId}")
	public User getUserInfo(@PathVariable Long accountId, @PathVariable Long marketingId) {
		return usersService.getUserInfo(accountId, marketingId);
	}

	@PostMapping("/createAccount")
	public Account saveAccount(@RequestBody Account account) {
		return accountsService.saveOrUpdateAccount(account);
	}
	
}
