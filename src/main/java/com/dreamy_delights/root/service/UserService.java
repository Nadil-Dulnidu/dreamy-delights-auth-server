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
     * Registers a new user in the system.
     *
     * <p>This method saves a new {@link User} to the data source after performing
     * necessary validations (unique username and email, required fields). It is typically used
     * during user sign-up or account creation.</p>
     *
     * @param user the {@link User} object containing registration details; must not be {@code null}.
     *             Required fields typically include username,email,password,phone and role_id.
     * @return the registered {@link User} object after being persisted, including any generated ID.
     * @throws IllegalArgumentException if the user is {@code null} or required fields are missing.
     * @throws UserAlreadyExistsException if a user with the same unique identifier (username) already exists.
     * @throws EmailAlreadyExistsException if the email already exists.
     * @throws InvalidPasswordException if password does not match required validations.
     */
    User registerUser(User user);

    /**
     * Authenticates a user and generates authentication tokens.
     *
     * <p>This method validates the provided credentials in the {@link AuthRequest} object.
     * If authentication is successful, it returns a map containing authentication tokens
     * such as an access token and optionally a refresh token.</p>
     *
     * @param authRequest the {@link AuthRequest} object containing the user's login credentials
     *                    (username and password); must not be {@code null}.
     * @return a {@link Map} with authentication details. Common keys include:
     *         <ul>
     *           <li>"accessToken" &ndash; a JWT or session token</li>
     *           <li>"refreshToken" &ndash; a token used to obtain a new access token</li>
     *         </ul>
     * @throws InvalidCredentialsException if the login credentials are invalid.
     * @throws RoleNotFoundException if the role is not exists.
     * @throws IllegalArgumentException if the {@code authRequest} is {@code null} or missing required fields.
     */
    Map<String,String> loginUser(AuthRequest authRequest);

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
    List<User> getAllUsers();

    /**
     * Generates a new access token using the provided refresh token.
     *
     * <p>This method validates the given refresh token and, if valid,
     * generates a new access token for continued user authentication. The returned map typically includes
     * the new access token and may include a refresh token.</p>
     *
     * @param refreshToken the refresh token string used to obtain a new access token; must not be {@code null}.
     * @return a {@link Map} containing token information ("accessToken", "refreshToken").
     *         Returns an empty map or throws an exception if the refresh token is invalid or expired.
     * @throws TokenExpiredException if the provided refresh token is invalid or expired.
     * @throws IllegalArgumentException if the refresh token is {@code null} or empty.
     */
    Map<String,String> refreshToken(String refreshToken);
}
