package de.hofuniversity.minf.stundenplaner.common.simpleauth;

import de.hofuniversity.minf.stundenplaner.common.simpleauth.basic.TokenTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("authenticate")
public class SimpleAuthController {

    private final SimpleAuthService authService;

    @Autowired
    public SimpleAuthController(SimpleAuthService authService) {
        this.authService = authService;
    }

    @DeleteMapping
    public ResponseEntity<Void> revokeToken(@RequestHeader("Authorization") String token) {
        authService.revokeToken(token);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<TokenTO> loginUser(@RequestBody LoginTO login) {
        return ResponseEntity.ok(authService.login(login.username, login.password));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody RegisterTO register) {
        authService.register(register.username, register.email, register.password);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
