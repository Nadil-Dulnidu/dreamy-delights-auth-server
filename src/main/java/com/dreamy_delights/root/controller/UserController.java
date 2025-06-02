package com.dreamy_delights.root.controller;

import com.dreamy_delights.root.common.Constants;
import com.dreamy_delights.root.dto.User;
import com.dreamy_delights.root.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Validated
@Slf4j
@Tag(name = "User Management", description = "APIs for managing users")
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAnyAuthority('" + Constants.ADMIN + "','" + Constants.USER + "')")
    @Operation(summary = "Update a user", description = "Updates the details of an existing user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "Invalid user data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Authentication required"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PutMapping()
    public ResponseEntity<User> updateUser(
            @Parameter(description = "User object include user details")
            @Valid @RequestBody User user) {
        User updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(updatedUser);
    }

    @PreAuthorize("hasAnyAuthority('" + Constants.ADMIN + "','" + Constants.USER + "')")
    @Operation(summary = "Get user by ID", description = "Fetches user details by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Authentication required"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getUser(
            @Parameter(description = "User ID")
            @Valid @PathVariable Integer id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasAuthority('" + Constants.ADMIN + "')")
    @Operation(summary = "Get all users", description = "Returns a list of all registered users (Admin only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User list fetched",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Authentication required"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasAnyAuthority('" + Constants.ADMIN + "','" + Constants.USER + "')")
    @Operation(summary = "Get user by username", description = "Fetches user details using username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Authentication required"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @GetMapping(value = "/{username}")
    public ResponseEntity<User> getUserByUsername(@Valid @PathVariable String username) {
        User user = userService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasAnyAuthority('" + Constants.ADMIN + "','" + Constants.USER + "')")
    @Operation(summary = "Delete user by ID", description = "Deletes a user from the system by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Authentication required"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<User> deleteUser(@Valid @PathVariable Integer id) {
        User deletedUser = userService.deleteUserById(id);
        return ResponseEntity.ok(deletedUser);
    }
}
