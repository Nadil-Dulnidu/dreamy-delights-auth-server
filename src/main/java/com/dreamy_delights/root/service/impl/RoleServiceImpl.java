package com.dreamy_delights.root.service.impl;

import com.dreamy_delights.root.dto.Role;
import com.dreamy_delights.root.entity.RoleEntity;
import com.dreamy_delights.root.exception.RoleAlreadyExistsException;
import com.dreamy_delights.root.exception.RoleNotFoundException;
import com.dreamy_delights.root.mapper.RoleDTOEntityMapper;
import com.dreamy_delights.root.repository.RoleRepository;
import com.dreamy_delights.root.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Role createUserRoles(Role role) {
        if(Objects.isNull(role)) throw new IllegalArgumentException("Role data can not be null");
        if(roleRepository.findByName(role.getName()) != null)
            throw new RoleAlreadyExistsException("Role already exists");
        final RoleEntity roleEntity = RoleDTOEntityMapper.map(role);
        final RoleEntity savedRoleEntity = roleRepository.save(roleEntity);
        return RoleDTOEntityMapper.map(savedRoleEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Role getRoleById(Integer id) {
        if(Objects.isNull(id)) throw new IllegalArgumentException("Role id can not be null");
        final Optional<RoleEntity> roleEntityOptional = roleRepository.findById(id);
        return roleEntityOptional
                .map(RoleDTOEntityMapper::map)
                .orElseThrow(() -> new RoleNotFoundException("Role not found with ID: " + id));
    }
}
