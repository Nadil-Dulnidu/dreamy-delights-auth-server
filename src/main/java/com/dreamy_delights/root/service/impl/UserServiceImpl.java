package com.dreamy_delights.root.service.impl;

import com.dreamy_delights.root.dto.User;
import com.dreamy_delights.root.entity.UserEntity;
import com.dreamy_delights.root.exception.*;
import com.dreamy_delights.root.mapper.UserDTOEntityMapper;
import com.dreamy_delights.root.repository.UserRepository;
import com.dreamy_delights.root.service.RoleService;
import com.dreamy_delights.root.service.UserService;
import com.dreamy_delights.root.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
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
    public User updateUser(User user) {
        log.info("Updating user with ID");
        if (Objects.isNull(user)) {
            log.error("User data is null.");
            throw new IllegalArgumentException("User data must not be null.");
        }
        final UserEntity existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> {
                    log.warn("User not found with ID: {}", user.getId());
                    return new UserNotFoundException("User not found.");
                });
        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        existingUser.setMobile(user.getPhone());
        log.debug("Updated user with ID: {}", user.getId());
        final UserEntity savedEntity = userRepository.save(existingUser);
        log.info("User updated successfully: {}", savedEntity.getId());
        return UserDTOEntityMapper.map(savedEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User getUserById(Integer id) {
        log.info("Fetching user with ID: {}", id);
        if (Objects.isNull(id)) {
            log.error("User ID is null.");
            throw new IllegalArgumentException("User id must not be null.");
        }
        final UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("User not found with ID: {}", id);
                    return new UserNotFoundException("User not found.");
                });
        log.info("User fetched successfully: {}", id);
        return UserDTOEntityMapper.map(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User getUserByUsername(String username) {
        log.info("Fetching user with username: {}", username);
        if (Objects.isNull(username)) {
            log.error("Username is null.");
            throw new IllegalArgumentException("username must not be null.");
        }
        final UserEntity user = userRepository.findByUsername(username);
        log.info("User fetched successfully: {}", username);
        return UserDTOEntityMapper.map(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User deleteUserById(Integer id) {
        log.info("Deleting user with ID: {}", id);
        if (Objects.isNull(id)) {
            log.error("User ID is null.");
            throw new IllegalArgumentException("User data must not be null.");
        }
        final UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("User not found with ID: {}", id);
                    return new UserNotFoundException("User not found.");
                });
        userRepository.deleteById(id);
        log.info("User deleted successfully: {}", id);
        return UserDTOEntityMapper.map(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<User> getAllUsers() {
        log.info("Fetching all users.");
        final List<UserEntity> users = userRepository.findAll();
        log.info("Total users found: {}", users.size());
        return users.stream()
                .map(UserDTOEntityMapper::map)
                .collect(Collectors.toList());
    }


}
