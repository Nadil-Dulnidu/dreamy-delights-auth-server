package com.dreamy_delights.root.controller;

import com.dreamy_delights.root.common.Constants;
import com.dreamy_delights.root.dto.Role;
import com.dreamy_delights.root.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Validated
@Slf4j
@PreAuthorize("hasAuthority('" + Constants.ADMIN + "')")
@Tag(name = "Role Management", description = "APIs for managing user roles, accessible only to Admins")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Operation(
            summary = "Add a new role",
            description = "Allows an admin to create a new user role"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role added successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Role.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden: Insufficient privileges"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),

    })
    @PostMapping(value = "/role",produces = Constants.APPLICATION_JSON, consumes = Constants.APPLICATION_JSON)
    public ResponseEntity<Role> addRole(
            @Parameter(description = "Role object to be created", required = true)
            @Valid @RequestBody final Role role) {
        final Role savedDto = roleService.createUserRoles(role);
        return ResponseEntity.ok(savedDto);
    }

    @Operation(
            summary = "Get all roles",
            description = "Retrieves a list of all user roles defined in the system"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of roles retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden: Insufficient privileges"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(value = "/role",produces = Constants.APPLICATION_JSON)
    public ResponseEntity<List<Role>> getRoles() {
        List<Role> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    @Operation(summary = "Update a role", description = "Updates an existing role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Role.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PutMapping(value = "/role",produces = Constants.APPLICATION_JSON, consumes = Constants.APPLICATION_JSON)
    public ResponseEntity<Role> updateRole(
            @Parameter(description = "Updated Role object", required = true)
            @Valid @RequestBody Role role) {
        final Role savedDto = roleService.updateUserRoles(role);
        return ResponseEntity.ok(savedDto);
    }

    @Operation(summary = "Delete a role", description = "Deletes a role by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role deleted successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Role.class))),
            @ApiResponse(responseCode = "404", description = "Role not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized: Authentication required"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @DeleteMapping(value = "/role/{id}",produces = Constants.APPLICATION_JSON, consumes = Constants.APPLICATION_JSON)
    public ResponseEntity<Role> deleteRole(
            @Parameter(description = "ID of the role to delete", required = true)
            @Valid @Min(value = 1, message = "Role ID must be a positive integer") @PathVariable Integer id) {
        Role deletedDto = roleService.deleteUserRoles(id);
        return ResponseEntity.ok(deletedDto);
    }
}
