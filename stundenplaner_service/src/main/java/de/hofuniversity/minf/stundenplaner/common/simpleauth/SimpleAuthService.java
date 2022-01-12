package de.hofuniversity.minf.stundenplaner.common.simpleauth;

import de.hofuniversity.minf.stundenplaner.common.exception.SimpleAuthException;
import de.hofuniversity.minf.stundenplaner.common.simpleauth.basic.TokenTO;
import de.hofuniversity.minf.stundenplaner.persistence.user.data.UserDO;

public interface SimpleAuthService {

    TokenTO login(String username, String password) throws SimpleAuthException;

    void register(String username, String email, String password) throws SimpleAuthException;

    UserDO validateToken(String token) throws SimpleAuthException;

    void revokeToken(String token);

}
