package com.dreamy_delights.root.service;

import com.dreamy_delights.root.dto.Role;

public interface RoleService {
    /**
     * create new user roles
     *
     * @param role the data transfer object containing role details
     * @return create role object
     */
    Role crateUserRoles(Role role);

    /**
     * get role object by id
     * @param id role id
     * @return matching role object
     */
    Role getRoleById(Integer id);
}
