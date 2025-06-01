package com.dreamy_delights.root.controller;

import com.dreamy_delights.root.common.Constants;
import com.dreamy_delights.root.dto.User;
import com.dreamy_delights.root.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAnyAuthority('" + Constants.ADMIN + "','" + Constants.USER + "')")
    @PutMapping()
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user) {
        User updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(updatedUser);
    }

    @PreAuthorize("hasAnyAuthority('" + Constants.ADMIN + "','" + Constants.USER + "')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getUser(@Valid @PathVariable Integer id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasAuthority('" + Constants.ADMIN + "')")
    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasAnyAuthority('" + Constants.ADMIN + "','" + Constants.USER + "')")
    @GetMapping(value = "/{username}")
    public ResponseEntity<User> getUserByUsername(@Valid @PathVariable String username) {
        User user = userService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasAnyAuthority('" + Constants.ADMIN + "','" + Constants.USER + "')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<User> deleteUser(@Valid @PathVariable Integer id) {
        User deletedUser = userService.deleteUserById(id);
        return ResponseEntity.ok(deletedUser);
    }
}
