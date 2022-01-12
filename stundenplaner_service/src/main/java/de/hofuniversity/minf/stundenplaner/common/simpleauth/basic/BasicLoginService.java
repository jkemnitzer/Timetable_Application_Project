package de.hofuniversity.minf.stundenplaner.common.simpleauth.basic;

import de.hofuniversity.minf.stundenplaner.common.exception.SimpleAuthException;
import de.hofuniversity.minf.stundenplaner.common.simpleauth.SimpleAuthService;
import de.hofuniversity.minf.stundenplaner.persistence.user.UserRepository;
import de.hofuniversity.minf.stundenplaner.persistence.user.data.UserDO;
import de.hofuniversity.minf.stundenplaner.service.boundary.UserService;
import de.hofuniversity.minf.stundenplaner.service.to.RoleTO;
import de.hofuniversity.minf.stundenplaner.service.to.UserTO;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class BasicLoginService implements SimpleAuthService {

    private final UserRepository userRepository;
    private final BasicLoginRepository tokenRepository;
    private final UserService userService;

    @Autowired
    public BasicLoginService(UserRepository userRepository, BasicLoginRepository tokenRepository,
                             UserService userService) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.userService = userService;
    }

    @Override
    public TokenTO login(String username, String password) throws SimpleAuthException {
        Optional<UserDO> optional = userRepository.findByUsernameOrEmail(username, username);
        if (optional.isPresent()) {
            UserDO userDO = optional.get();
            tokenRepository.deleteByUserDO(userDO);
            TokenDO tokenDO = new TokenDO(
                    null, generateToken(userDO.getUsername()), LocalDateTime.now().plusMinutes(5), userDO);
            tokenRepository.save(tokenDO);
            return TokenTO.fromDO(tokenDO);
        } else {
            throw new SimpleAuthException(SimpleAuthException.AuthErrorType.LOGIN_FAIL);
        }
    }

    @Override
    public void register(String username, String email, String password) throws SimpleAuthException {
        Set<RoleTO> roleSet = new HashSet<>();
        Optional<UserDO> optional = userRepository.findByUsernameOrEmail(username, username);
        if (optional.isPresent()) {
            throw new SimpleAuthException(SimpleAuthException.AuthErrorType.USER_ALREADY_EXIST);
        } else {
            userService.createUser(
                    new UserTO(null, username, "NotSet", "NotSet", "NotSet", email, "ACTIVE", roleSet));
        }

    }

    @Override
    public UserDO validateToken(String token) throws SimpleAuthException {
        Optional<TokenDO> optional = tokenRepository.findByToken(token);
        if (optional.isPresent() && optional.get().getValidTo().isAfter(LocalDateTime.now())) {
            optional.get().setValidTo(LocalDateTime.now().plusMinutes(5));
            return optional.get().getUserDO();
        } else {
            throw new SimpleAuthException(SimpleAuthException.AuthErrorType.INVALID_TOKEN);
        }
    }

    @Override
    public void revokeToken(String token) {
        Optional<TokenDO> optional = tokenRepository.findByToken(token);
        optional.ifPresent(tokenRepository::delete);
    }

    private String generateToken(String username) throws SimpleAuthException {
        try {
            byte[] hashContent = ArrayUtils.addAll(currentTimeBytes(), username.getBytes());
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(hashContent);
            return new HexBinaryAdapter().marshal(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new SimpleAuthException(SimpleAuthException.AuthErrorType.LOGIN_FAIL);
        }
    }

    public byte[] currentTimeBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(System.currentTimeMillis());
        return buffer.array();
    }
}
