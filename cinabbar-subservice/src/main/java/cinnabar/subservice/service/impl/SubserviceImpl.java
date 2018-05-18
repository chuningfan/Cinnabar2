package cinnabar.subservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cinnabar.subservice.dao.CustomDao;
import cinnabar.subservice.dao.SubserviceDao;
import cinnabar.subservice.dto.User;
import cinnabar.subservice.entity.CinnabarUser;
import cinnabar.subservice.service.Subservice;

@Service("subservice")
public class SubserviceImpl implements Subservice {

	//DI
	@Autowired
	private SubserviceDao subserviceDao;
	
	@Autowired
	private CustomDao customDao;
	
	@Override
	public void test(String name) {
		System.out.println("==============>" + name);
	}

	@Override
	public User saveUser(User user) {
		System.out.println(user.toString());
		return user;
	}

	//spring data jpa api
	@Override
	public CinnabarUser saveCinnabarUser(CinnabarUser user) {
		return subserviceDao.save(user);
	}

	
	//spring data jpa api
	@Override
	public CinnabarUser getCinnabarUser(Long id) {
		return subserviceDao.getOne(id);
	}

	@Override
	public List<CinnabarUser> getPagedUsers(Pageable p) {
		Page<CinnabarUser> page = subserviceDao.findAll(p);
		List<CinnabarUser> list= page.getContent();
		return list;
	}

	//custom hql 
	@Override
	public CinnabarUser getUserByHql(Long id) {
		String hql = "from CinnabarUser where id=:id";
		return customDao.getUserByHql(hql, id);
	}

	//custom sql 
	@Override
	public CinnabarUser getUserBySql(Long id) {
		String sql = "select id, test_name, birthday, age from platform.cinnabar_user where id=:id";
		return customDao.getUserBySql(sql, id);
	}

	@Override
	public void saveUserToRedis(CinnabarUser user) {
		customDao.saveUserToRedis(user);
	}

	@Override
	public CinnabarUser getUserFromRedis(Long key) {
		return customDao.getUserFromRedis(key);
	}

}
