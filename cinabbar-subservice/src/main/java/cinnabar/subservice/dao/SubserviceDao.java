package cinnabar.subservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cinnabar.subservice.entity.CinnabarUser;

/**
 * 使用JPA（JPA只是一个标准，spring实现了这个标准）， 只需要将接口继承自JpaRepository<Entity, ID>
 * 就可以使用很多已实现的方法，如果有自己的方法，那么正常编写，再加一个实现类即可。
 * @author Vic.Chu
 *
 */
@Repository
public interface SubserviceDao extends JpaRepository<CinnabarUser, Long> {
	
}
