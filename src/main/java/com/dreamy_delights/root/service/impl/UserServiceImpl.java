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


}
