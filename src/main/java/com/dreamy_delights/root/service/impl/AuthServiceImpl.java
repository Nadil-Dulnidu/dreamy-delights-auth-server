package com.dreamy_delights.root.service.impl;

import com.dreamy_delights.root.dto.AuthRequest;
import com.dreamy_delights.root.dto.Role;
import com.dreamy_delights.root.dto.User;
import com.dreamy_delights.root.entity.UserEntity;
import com.dreamy_delights.root.exception.*;
import com.dreamy_delights.root.mapper.UserDTOEntityMapper;
import com.dreamy_delights.root.repository.UserRepository;
import com.dreamy_delights.root.service.AuthService;
import com.dreamy_delights.root.service.RoleService;
import com.dreamy_delights.root.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User registerUser(User user) {
        log.info("Starting user registration process.");
        if (Objects.isNull(user)) {
            log.warn("Attempted to register with null user data.");
            throw new IllegalArgumentException("User data must not be null.");
        }
        log.debug("Checking if username '{}' already exists.", user.getUsername());
        if (userRepository.findByUsername(user.getUsername()) != null) {
            log.warn("Failed to create user: Username '{}' already exists.", user.getUsername());
            throw new UserAlreadyExistsException("User with username " + user.getUsername() + " already exists.");
        }
        log.debug("Checking if email '{}' already exists.", user.getEmail());
        if (userRepository.findByEmail(user.getEmail()) != null) {
            log.warn("Failed to create user: Email '{}' already exists.", user.getEmail());
            throw new EmailAlreadyExistsException("Email already exists.");
        }
        final String password = user.getPassword();
        log.debug("Validating password strength.");
        if (password.length() < 8) {
            log.warn("Password validation failed: too short.");
            throw new InvalidUserInputException("Password must be at least 8 characters long.");
        }
        if (!password.matches(".*[A-Z].*")) {
            log.warn("Password validation failed: missing uppercase letter.");
            throw new InvalidUserInputException("Password must contain at least one uppercase letter.");
        }
        if (!password.matches(".*[a-z].*")) {
            log.warn("Password validation failed: missing lowercase letter.");
            throw new InvalidUserInputException("Password must contain at least one lowercase letter.");
        }
        if (!password.matches(".*\\d.*")) {
            log.warn("Password validation failed: missing number.");
            throw new InvalidUserInputException("Password must contain at least one number.");
        }
        if (!password.matches(".*[^a-zA-Z0-9].*")) {
            log.warn("Password validation failed: missing special character.");
            throw new InvalidUserInputException("Password must contain at least one special character.");
        }
        log.debug("Mapping user DTO to entity.");
        final UserEntity userEntity = UserDTOEntityMapper.map(user);
        log.info("Saving user '{}' to database.", user.getUsername());
        final UserEntity savedUserEntity = userRepository.save(Objects.requireNonNull(userEntity));
        log.info("User '{}' successfully registered.", user.getUsername());
        return UserDTOEntityMapper.map(savedUserEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, String> loginUser(AuthRequest authRequest) {
        log.info("Login attempt received.");
        if (Objects.isNull(authRequest)) {
            log.warn("Login failed: AuthRequest is null.");
            throw new IllegalArgumentException("Auth data must not be null.");
        }
        log.debug("Looking up user by username: {}", authRequest.getUsername());
        final UserEntity userEntity = userRepository.findByUsername(authRequest.getUsername());
        if (userEntity == null) {
            log.warn("Login failed: No user found with username '{}'.", authRequest.getUsername());
            throw new InvalidCredentialsException("Invalid username or password.");
        }
        log.debug("Checking credentials for user '{}'.", authRequest.getUsername());
        if (Objects.equals(userEntity.getUsername(), authRequest.getUsername())
                && Objects.equals(userEntity.getPassword(), authRequest.getPassword())) {
            log.debug("Retrieving role for user '{}'.", userEntity.getUsername());
            final Role role = roleService.getRoleById(userEntity.getRoleId());
            if (Objects.isNull(role)) {
                log.warn("Login failed: Role not found for user '{}'.", userEntity.getUsername());
                throw new RoleNotFoundException("Role not found.");
            }
            log.info("Generating tokens for user '{}'.", userEntity.getUsername());
            final String accessToken = jwtUtil.generateToken(authRequest.getUsername(), role.getName());
            final String refreshToken = jwtUtil.generateRefreshToken(authRequest.getUsername());
            final Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", accessToken);
            tokens.put("refreshToken", refreshToken);
            log.info("Login successful for user '{}'.", authRequest.getUsername());
            return tokens;
        } else {
            log.warn("Login failed: Invalid credentials for user '{}'.", authRequest.getUsername());
            throw new InvalidCredentialsException("Invalid username or password.");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, String> refreshToken(String refreshToken) {
        log.info("Refresh token request received.");
        if (Objects.isNull(refreshToken)) {
            log.error("Refresh failed: refresh token is null.");
            throw new IllegalArgumentException("Refresh token must not be null.");
        }
        log.debug("Validating refresh token.");
        if (!jwtUtil.isTokenExpired(refreshToken)) {
            final String username = jwtUtil.extractUsername(refreshToken);
            log.debug("Extracted username '{}' from refresh token.", username);
            final UserEntity userEntity = userRepository.findByUsername(username);
            if (Objects.isNull(userEntity)) {
                log.warn("Refresh failed: No user found for username '{}'.", username);
                throw new InvalidTokenException("Invalid refresh token.");
            }
            final Role role = roleService.getRoleById(userEntity.getRoleId());
            if (Objects.isNull(role)) {
                log.error("Refresh failed: Role not found for user '{}'.", username);
                throw new RoleNotFoundException("Role not found.");
            }
            final String newAccessToken = jwtUtil.generateToken(username, role.getName());
            log.info("New access token generated for user '{}'.", username);
            final Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", newAccessToken);
            return tokens;
        } else {
            log.warn("Refresh failed: refresh token is expired.");
            throw new TokenExpiredException("Token is expired.");
        }
    }

}
