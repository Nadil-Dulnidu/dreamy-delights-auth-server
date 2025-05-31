package com.dreamy_delights.root.service;

import com.dreamy_delights.root.dto.AuthRequest;
import com.dreamy_delights.root.dto.User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface UserService {
    /**
     * register a new userDTO to the system
     *
     * @param user the data transfer object containing userDTO details
     * @return he created userDTO object
     */
    User registerUser(User user);

    /**
     * verify the user
     *
     * @param authRequest data transfer object containing auth details
     * @return created access token
     */
    Map<String,String> loginUser(AuthRequest authRequest);

    /**
     * create new access token
     *
     * @param refreshToken refresh token
     * @return new create access token and refresh token
     */
    Map<String,String> refreshToken(String refreshToken);
}
