package com.dreamy_delights.root.service;

import com.dreamy_delights.root.dto.AuthRequest;
import com.dreamy_delights.root.dto.User;
import com.dreamy_delights.root.exception.*;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface AuthService {

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
     * @throws InvalidUserInputException if password or username does not match required validations.
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
