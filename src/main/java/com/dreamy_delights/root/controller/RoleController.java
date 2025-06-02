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
            @Parameter(description = "Role object to be created")
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
        return null;
    }
}
