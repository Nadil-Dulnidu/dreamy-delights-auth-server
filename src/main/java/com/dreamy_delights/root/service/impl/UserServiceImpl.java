package com.dreamy_delights.root.service.impl;

import com.dreamy_delights.root.dto.AuthRequest;
import com.dreamy_delights.root.dto.Role;
import com.dreamy_delights.root.dto.User;
import com.dreamy_delights.root.entity.UserEntity;
import com.dreamy_delights.root.exception.*;
import com.dreamy_delights.root.mapper.UserDTOEntityMapper;
import com.dreamy_delights.root.repository.UserRepository;
import com.dreamy_delights.root.service.RoleService;
import com.dreamy_delights.root.service.UserService;
import com.dreamy_delights.root.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User registerUser(User user) {
        if (Objects.isNull(user))
            throw new IllegalArgumentException("User data must not be null.");
        if(userRepository.findByUsername(user.getUsername()) != null)
            throw new UserAlreadyExistsException("User with username " + user.getUsername() + " already exists.");
        if(userRepository.findByEmail(user.getEmail()) != null)
            throw new EmailAlreadyExistsException("Email already exists.");
        final String password = user.getPassword();
        if (password.length() < 8)
            throw new InvalidPasswordException("Password must be at least 8 characters long.");
        if (!password.matches(".*[A-Z].*"))
            throw new InvalidPasswordException("Password must contain at least one uppercase letter.");
        if (!password.matches(".*[a-z].*"))
            throw new InvalidPasswordException("Password must contain at least one lowercase letter.");
        if (!password.matches(".*\\d.*"))
            throw new InvalidPasswordException("Password must contain at least one number.");
        if (!password.matches(".*[^a-zA-Z0-9].*"))
            throw new InvalidPasswordException("Password must contain at least one special character.");
        final UserEntity userEntity = UserDTOEntityMapper.map(user);
        final UserEntity savedUserEntity = userRepository.save(Objects.requireNonNull(userEntity));
        return UserDTOEntityMapper.map(savedUserEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String,String> loginUser(AuthRequest authRequest) {
        if (Objects.isNull(authRequest))
            throw new IllegalArgumentException("Auth data must not be null.");
        final UserEntity userEntity = userRepository.findByUsername(authRequest.getUsername());
        if (Objects.equals(userEntity.getUsername(),authRequest.getUsername())
                && Objects.equals(userEntity.getPassword(),authRequest.getPassword())) {
            final Role role = roleService.getRoleById(userEntity.getRoleId());
            if (Objects.isNull(role))
                throw new RoleNotFoundException("Role not found.");
            final String accessToken = jwtUtil.generateToken(authRequest.getUsername(), role.getName());
            final String refreshToken = jwtUtil.generateRefreshToken(authRequest.getUsername());
            final Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", accessToken);
            tokens.put("refreshToken", refreshToken);
            return tokens;
        }else throw new InvalidCredentialsException("Invalid username or password.");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User updateUser(User user) {
        if (Objects.isNull(user))
            throw new IllegalArgumentException("User data must not be null.");
        final UserEntity existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found."));
        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        existingUser.setMobile(user.getPhone());
        final UserEntity savedEntity = userRepository.save(existingUser);
        return UserDTOEntityMapper.map(savedEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User getUserById(Integer id) {
        if (Objects.isNull(id))
            throw new IllegalArgumentException("User id must not be null.");
        final UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found."));
        return UserDTOEntityMapper.map(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User getUserByUsername(String username) {
        if (Objects.isNull(username))
            throw new IllegalArgumentException("username must not be null.");
        final UserEntity user = userRepository.findByUsername(username);
        return UserDTOEntityMapper.map(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User deleteUserById(Integer id) {
        if (Objects.isNull(id))
            throw new IllegalArgumentException("User data must not be null.");
        final UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found."));
        userRepository.deleteById(id);
        return  UserDTOEntityMapper.map(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<User> getAllUsers() {
        final List<UserEntity> users = userRepository.findAll();
        return users.stream()
                .map(UserDTOEntityMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, String> refreshToken(String refreshToken) {
        if (Objects.isNull(refreshToken)) throw new IllegalArgumentException("Refresh token not be null.");
        if (!jwtUtil.isTokenExpired(refreshToken)) {
            final String username = jwtUtil.extractUsername(refreshToken);
            final UserEntity userEntity = userRepository.findByUsername(username);
            final Role role = roleService.getRoleById(userEntity.getRoleId());
            final String newAccessToken = jwtUtil.generateToken(username, role.getName());
            final Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", newAccessToken);
            return tokens;
        }else throw new TokenExpiredException("Token is expired.");
    }
}
