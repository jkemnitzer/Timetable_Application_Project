package de.hofuniversity.minf.stundenplaner.common.simpleauth.basic;

import de.hofuniversity.minf.stundenplaner.persistence.user.data.UserDO;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BasicLoginRepository extends CrudRepository<TokenDO, Long> {

    void deleteByUserDO(UserDO userDO);
    Optional<TokenDO> findByToken(String token);
}
