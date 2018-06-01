package cinnabar.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cinnabar.user.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
