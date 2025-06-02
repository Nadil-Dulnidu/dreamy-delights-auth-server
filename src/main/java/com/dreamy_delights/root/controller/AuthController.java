package com.dreamy_delights.root.controller;

import com.dreamy_delights.root.common.Constants;
import com.dreamy_delights.root.dto.*;
import com.dreamy_delights.root.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/auth")
@Validated
@Slf4j
@Tag(name = "Authentication Controller", description = "Handles user registration, login, and token refresh")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(
            summary = "Register a new user",
            description = "Registers a user based on the provided user role. Role 1 is Admin, Role 2 is Regular User."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Role.class))),
            @ApiResponse(responseCode = "400", description = "Invalid data"),
            @ApiResponse(responseCode = "409",description = "User already exists"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    @PostMapping(value = "/register", produces = Constants.APPLICATION_JSON, consumes = Constants.APPLICATION_JSON)
    public ResponseEntity<?> registerUser(
            @Parameter(description = "User object including user_role", required = true)
            @Valid @RequestBody Map<String, Object> payload) {
        ObjectMapper mapper = new ObjectMapper();
        Integer role = (Integer) payload.get("user_role");
        if (Objects.isNull(role)) role = 2;
        User registeredUser;
        switch (role) {
            case 1:
                Admin admin = mapper.convertValue(payload, Admin.class);
                registeredUser = authService.registerUser(admin);
                break;
            case 2:
                RegularUser user = mapper.convertValue(payload, RegularUser.class);
                registeredUser = authService.registerUser(user);
                break;
            default:
                return ResponseEntity.badRequest().body("Invalid Role");
        }
        return ResponseEntity.ok(registeredUser);
    }

    @Operation(
            summary = "Login user",
            description = "Authenticates the user and returns JWT access and refresh tokens"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User logged in successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid credentials"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = "/login", produces = Constants.APPLICATION_JSON, consumes = Constants.APPLICATION_JSON)
    public ResponseEntity<?> loginUser(
            @Parameter(description = "Login credentials", required = true)
            @Valid @RequestBody final AuthRequest authRequest) {
        final Map<String,String> tokens =  authService.loginUser(authRequest);
        return ResponseEntity.ok(tokens);
    }

    @Operation(
            summary = "Generate new JWT token using refresh token",
            description = "Generates a new access token using a valid refresh token"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token refreshed successfully"),
            @ApiResponse(responseCode = "400", description = "Refresh token is missing or invalid"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    @PostMapping(value = "/refresh", produces = Constants.APPLICATION_JSON, consumes = Constants.APPLICATION_JSON)
    public ResponseEntity<?> refreshToken(
            @Parameter(description = "Map containing the refreshToken key", required = true)
            @Valid @RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        if (refreshToken == null) return ResponseEntity.badRequest().body("RefreshToken is null");
        Map<String,String> tokens =  authService.refreshToken(refreshToken);
        return ResponseEntity.ok(tokens);
    }
}
