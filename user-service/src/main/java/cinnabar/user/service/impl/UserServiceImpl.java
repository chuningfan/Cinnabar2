package cinnabar.user.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import cinnabar.user.entity.User;
import cinnabar.user.repository.UserRepository;
import cinnabar.user.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User saveOrUpdateUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public void deleteUserById(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public User getUserById(Long id) {
		return userRepository.getOne(id);
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User getUserInfo(Long accountId, Long marketingId) {
		User user = new User();
		user.setAccountId(accountId);
		user.setMarketingId(marketingId);
		ExampleMatcher matcher = ExampleMatcher.matching()
				.withMatcher("accountId", match -> match.exact())
				.withMatcher("marketingId", match -> match.exact());
		Example<User> example = Example.of(user, matcher);
		Optional<User> o = userRepository.findOne(example);
		if (o == null) {
			return null;
		}
		return o.get();
	}

}
