package de.hofuniversity.minf.stundenplaner.persistence.user;

import de.hofuniversity.minf.stundenplaner.persistence.user.data.UserDO;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserDO, Long> {}
