package de.hofuniversity.minf.stundenplaner.persistence.user;

import de.hofuniversity.minf.stundenplaner.persistence.user.data.UserDO;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserDO, Long> {

    Optional<UserDO> findByUsernameOrEmail(String username, String email);

}
