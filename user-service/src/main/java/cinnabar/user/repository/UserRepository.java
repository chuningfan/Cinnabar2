package cinnabar.user.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cinnabar.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
