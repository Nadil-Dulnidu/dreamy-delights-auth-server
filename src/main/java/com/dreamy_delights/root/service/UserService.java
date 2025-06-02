package com.dreamy_delights.root.service;

import com.dreamy_delights.root.dto.AuthRequest;
import com.dreamy_delights.root.dto.User;
import com.dreamy_delights.root.exception.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface UserService {
    /**
     * Updates an existing user's information.
     *
     * @param user the {@link User} object with updated details. Must not be {@code null}.
     *             It should include the user ID to identify which user to update.
     * @return the updated {@link User} object after the changes have been persisted.
     * @throws IllegalArgumentException if the provided user is {@code null} or has no valid ID.
     * @throws UserNotFoundException if no existing user is found with the given ID.
     */
    User updateUser(User user);

    /**
     * Retrieves a user by their unique identifier.
     *
     * @param id the ID of the user to retrieve; must not be {@code null}.
     * @return the {@link User} object with the specified ID.
     * @throws IllegalArgumentException if the ID is {@code null}.
     * @throws UserNotFoundException if no user is found with the given ID.
     */
    User getUserById(Integer id);

    /**
     * Retrieves a user by their username.
     *
     * @param username the username of the user to retrieve; must not be {@code null}.
     * @return the {@link User} object with the specified username.
     * @throws IllegalArgumentException if the username is {@code null}.
     * @throws UserNotFoundException if no user is found with the given ID.
     */
    User getUserByUsername(String username);

    /**
     * Deletes a user by their unique identifier.
     *
     * @param id the ID of the user to delete; must not be {@code null}.
     * @return the {@link User} object that was deleted.
     * @throws IllegalArgumentException if the ID is {@code null}.
     * @throws UserNotFoundException if no user exists with the given ID.
     */
    User deleteUserById(Integer id);

    /**
     * Retrieves a list of all users in the system.
     *
     * @return a {@link List} of all {@link User} objects. Never {@code null}, but may be empty.
     */
    List<User> getAllUsers(Integer role);
}
