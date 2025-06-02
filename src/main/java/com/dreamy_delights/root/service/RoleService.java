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

    /**
     * Retrieves all available user roles from the system.
     *
     * @return a list of all {@link Role} entities
     */
    List<Role> getAllRoles();

    /**
     * Deletes a user role by its unique identifier.
     *
     * @param id the ID of the role to delete
     * @return the deleted {@link Role} object
     * @throws RoleNotFoundException if the role with the given ID does not exist
     */
    Role deleteUserRoles(Integer id);

    /**
     * Updates an existing user role with new values.
     *
     * @param role the {@link Role} object containing updated information
     * @return the updated {@link Role} entity
     * @throws RoleNotFoundException if the role to update does not exist
     * @throws IllegalArgumentException if the input role is null
     */
    Role updateUserRoles(Role role);
}
