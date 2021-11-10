package de.hofuniversity.minf.stundenplaner.rest;

import de.hofuniversity.minf.stundenplaner.service.boundary.UserService;
import de.hofuniversity.minf.stundenplaner.service.to.UserTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserTO>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping
    public ResponseEntity<UserTO> createUser(@RequestBody UserTO userTO) {
        return ResponseEntity.status(201).body(userService.createUser(userTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserTO> updateUser(@PathVariable("id") Long id, @RequestBody UserTO userTO, @RequestParam(value = "checkRoles", defaultValue = "true") Boolean checkRoles) {
        return ResponseEntity.ok(userService.updateUser(id, userTO, checkRoles));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserTO> deleteUser(@PathVariable("id") Long id) {
        UserTO deleted = userService.removeUser(id);
        return ResponseEntity.ok(deleted);
    }
}
