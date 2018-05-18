package cinnabar.subservice.dao;

import cinnabar.subservice.entity.CinnabarUser;

/**
 * 自定义dao
 * @author Vic.Chu
 *
 */
public interface CustomDao {
	
	CinnabarUser getUserByHql(String hql, Long id);
	
	CinnabarUser getUserBySql(String sql, Long id);
	
	void saveUserToRedis(CinnabarUser user);
	
	CinnabarUser getUserFromRedis(Long key);
	
}
