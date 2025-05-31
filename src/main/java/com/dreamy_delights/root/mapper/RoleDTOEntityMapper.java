package com.dreamy_delights.root.mapper;

import com.dreamy_delights.root.dto.Role;
import com.dreamy_delights.root.entity.RoleEntity;

public class RoleDTOEntityMapper {
    public static Role map(RoleEntity roleEntity) {
        Role role = new Role();
        role.setId(roleEntity.getId());
        role.setName(roleEntity.getName());
        return role;
    }

    public static RoleEntity map(Role role) {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(role.getId());
        roleEntity.setName(role.getName());
        return roleEntity;
    }
}
