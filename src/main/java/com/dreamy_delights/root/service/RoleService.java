package com.dreamy_delights.root.service;

import com.dreamy_delights.root.dto.Role;
import com.dreamy_delights.root.exception.RoleAlreadyExistsException;
import com.dreamy_delights.root.exception.RoleNotFoundException;

import java.util.List;

public interface RoleService {
    /**
     * Creates a new user role in the system.
     *
     * @param role The {@link Role} object containing the details of the role to be created.
     * @return The created {@link Role} object with any system-generated fields populated (e.g., ID).
     * @throws IllegalArgumentException if role data is null.
     * @throws RoleAlreadyExistsException if same role already exists.
     */
    Role createUserRoles(Role role);

    /**
     * Retrieves a role by its unique identifier.
     *
     * @param id The unique ID of the role.
     * @return The {@link Role} object matching the given ID, or {@code null} if no such role exists.
     * @throws IllegalArgumentException if role id is null.
     * @throws RoleNotFoundException if role not found.
     */
    Role getRoleById(Integer id);

    List<Role> getAllRoles();

    Role deleteUserRoles(Integer id);

    Role updateUserRoles(Role role);
}
