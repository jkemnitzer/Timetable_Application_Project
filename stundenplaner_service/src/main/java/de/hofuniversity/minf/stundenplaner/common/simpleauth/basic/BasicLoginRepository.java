package de.hofuniversity.minf.stundenplaner.common.simpleauth.basic;

import de.hofuniversity.minf.stundenplaner.persistence.user.data.UserDO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface BasicLoginRepository extends CrudRepository<TokenDO, Long> {

    @Transactional
    void deleteByUserDO(UserDO userDO);
    Optional<TokenDO> findByToken(String token);
}
