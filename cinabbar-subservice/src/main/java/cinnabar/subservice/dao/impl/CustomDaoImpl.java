package cinnabar.subservice.dao.impl;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

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

}
