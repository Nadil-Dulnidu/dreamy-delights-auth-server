package com.dreamy_delights.root.controller;

import com.dreamy_delights.root.common.Constants;
import com.dreamy_delights.root.dto.Admin;
import com.dreamy_delights.root.dto.AuthRequest;
import com.dreamy_delights.root.dto.RegularUser;
import com.dreamy_delights.root.dto.User;
import com.dreamy_delights.root.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/register", produces = Constants.APPLICATION_JSON, consumes = Constants.APPLICATION_JSON)
    public ResponseEntity<?> registerUser(@Valid @RequestBody Map<String, Object> payload) {
        ObjectMapper mapper = new ObjectMapper();
        Integer role = (Integer) payload.get("user_role");
        if (role == null) return ResponseEntity.badRequest().body("Role cannot be null");
        User registeredUser;
        switch (role) {
            case 1:
                Admin admin = mapper.convertValue(payload, Admin.class);
                registeredUser = userService.registerUser(admin);
                break;
            case 2:
                RegularUser user = mapper.convertValue(payload, RegularUser.class);
                registeredUser = userService.registerUser(user);
                break;
            default:
                return ResponseEntity.badRequest().body("Invalid Role");
        }
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping(value = "/login", produces = Constants.APPLICATION_JSON, consumes = Constants.APPLICATION_JSON)
    public ResponseEntity<?> loginUser(@Valid @RequestBody final AuthRequest authRequest) {
        final Map<String,String> tokens =  userService.loginUser(authRequest);
        if(tokens.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        return ResponseEntity.ok(tokens);
    }

    @PostMapping(value = "/refresh-token", produces = Constants.APPLICATION_JSON, consumes = Constants.APPLICATION_JSON)
    public ResponseEntity<?> refreshToken(@Valid @RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        if (refreshToken == null) return ResponseEntity.badRequest().body("RefreshToken is null");
        Map<String,String> tokens =  userService.refreshToken(refreshToken);
        if(tokens.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired refresh token.");
        }
        return ResponseEntity.ok(tokens);
    }

}
