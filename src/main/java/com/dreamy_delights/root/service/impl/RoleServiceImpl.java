package com.dreamy_delights.root.service.impl;

import com.dreamy_delights.root.dto.Role;
import com.dreamy_delights.root.entity.RoleEntity;
import com.dreamy_delights.root.exception.RoleAlreadyExistsException;
import com.dreamy_delights.root.exception.RoleNotFoundException;
import com.dreamy_delights.root.mapper.RoleDTOEntityMapper;
import com.dreamy_delights.root.repository.RoleRepository;
import com.dreamy_delights.root.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Role createUserRoles(Role role) {
        log.info("Role creation request received.");
        if (Objects.isNull(role)) {
            log.error("Role creation failed: Role data is null.");
            throw new IllegalArgumentException("Role data can not be null");
        }
        log.info("Checking if role '{}' already exists.", role.getName());
        if (roleRepository.findByName(role.getName()) != null) {
            log.error("Role '{}' already exists.", role.getName());
            throw new RoleAlreadyExistsException("Role already exists");
        }
        log.debug("Mapping role DTO to entity for role '{}'.", role.getName());
        final RoleEntity roleEntity = RoleDTOEntityMapper.map(role);
        log.info("Saving role '{}' to database.", role.getName());
        final RoleEntity savedRoleEntity = roleRepository.save(roleEntity);
        log.info("Role '{}' successfully created with ID {}.", savedRoleEntity.getName(), savedRoleEntity.getId());
        return RoleDTOEntityMapper.map(savedRoleEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Role getRoleById(Integer id) {
        log.info("Fetching role by ID: {}", id);
        if (Objects.isNull(id)) {
            log.error("Role fetch failed: Role ID is null.");
            throw new IllegalArgumentException("Role id can not be null");
        }
        final Optional<RoleEntity> roleEntityOptional = roleRepository.findById(id);
        if (roleEntityOptional.isEmpty()) {
            log.error("No role found with ID: {}", id);
            throw new RoleNotFoundException("Role not found with ID: " + id);
        }
        log.info("Role with ID {} successfully retrieved.", id);
        return RoleDTOEntityMapper.map(roleEntityOptional.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Role> getAllRoles() {
        log.info("Fetching all roles.");
        List<RoleEntity> roleEntities = roleRepository.findAll();
        log.info("Fetching all roles successfully.");
        return roleEntities.stream()
                .map(RoleDTOEntityMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Role deleteUserRoles(Integer id) {
        log.info("Deleting role with ID: {}", id);
        RoleEntity roleEntity =  roleRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("No role found with ID: {}", id);
                    return new RoleNotFoundException("Role with ID: " + id);
                });
        roleRepository.delete(roleEntity);
        log.info("Role with ID {} successfully deleted.", id);
        return RoleDTOEntityMapper.map(roleEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Role updateUserRoles(Role role) {
        log.info("Updating role with ID: {}", role.getId());
        RoleEntity roleEntity = roleRepository.findById(role.getId())
                .orElseThrow(() -> {
                    log.warn("No role found with ID: {}", role.getId());
                    return new RoleNotFoundException("Role with ID: " + role.getId());
                });
        roleEntity.setName(role.getName());
        log.debug("Updated role DTO to entity for role '{}'.", role.getName());
        roleRepository.save(roleEntity);
        log.info("Role with ID {} successfully updated.", role.getId());
        return RoleDTOEntityMapper.map(roleEntity);
    }

}
