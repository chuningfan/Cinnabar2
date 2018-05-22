package cinnabar.subservice.dao.impl;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import cinnabar.core.component.redis.RedisHelper;
import cinnabar.subservice.dao.CustomDao;
import cinnabar.subservice.entity.CinnabarUser;

/**
 * 自定义dao 实现
 * @author Vic.Chu
 *
 */
@Repository("customDao")
public class CustomDaoImpl implements CustomDao {

	// DI for persistence context
	@PersistenceContext
	private EntityManager entityManager;
	
	/*
	 * ID 对于redisTemplate 必须用resource注解。
	 * 原因： If you add a @Bean of your own of any of the auto-configured types it will replace the default (except in the case of RedisTemplate the exclusion is based on the bean name ‘redisTemplate’ not its type).
	 * 大意： 如果你为项目增加一个auto-config 的基于类型的bean， 那么这个bean就会替换掉默认的（如果有这个bean）bean， 但是redistemplate是基于名称的，并非基于类型的。
	 */
	@Resource
	private RedisHelper<String, CinnabarUser> redisHelper;
	
	@Override
	public CinnabarUser getUserByHql(String hql, Long id) {
		Query query = entityManager.createQuery(hql);
		query.setParameter("id", id);
		CinnabarUser u = (CinnabarUser) query.getSingleResult();
		return u;
	}

	@Override
	public CinnabarUser getUserBySql(String sql, Long id) {
		Query query = entityManager.createNativeQuery(sql)
		.setParameter("id", id);
		Object[] objs = (Object[]) query.getSingleResult();
		if (objs ==null || objs.length == 0) {
			return null;
		}
		CinnabarUser u = new CinnabarUser();
        u.setId(Long.valueOf(objs[0].toString()));
        u.setTestName(objs[1].toString());
        u.setBirthday((Date) objs[2]);
        u.setAge((int) objs[3]);
		return u;
	}

	/**
	 * 将user序列化后存入redis
	 */
	@Override
	public void saveUserToRedis(CinnabarUser user) {
		redisHelper.opsForValue().set(user.getId().toString(), user, 30, TimeUnit.SECONDS);
	}

	/**
	 * 根据key获得序列化的对象字符串，然后转换成对象返回。
	 */
	@Override
	public CinnabarUser getUserFromRedis(Long key) {
//		String source = stringRedisTemplate.opsForValue().get(key + "");
//		ObjectMapper objectMapper = new ObjectMapper();
//		CinnabarUser user = null;
//		if (source == null || source.trim().length() == 0) {
//			return user;
//		}
//		try {
//			user = objectMapper.readValue(source, CinnabarUser.class);
//			System.out.println("GET user " + user.getTestName());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		Object source = redisHelper.opsForValue().get(key.toString());
		CinnabarUser user = null;
		try {
			user = redisHelper.getObject(source, CinnabarUser.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
        return user;
	}

}
