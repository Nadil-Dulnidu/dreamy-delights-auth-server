package com.dreamy_delights.root.controller;

import com.dreamy_delights.root.common.Constants;
import com.dreamy_delights.root.dto.Role;
import com.dreamy_delights.root.security.SecConstants;
import com.dreamy_delights.root.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasAnyAuthority('" + SecConstants.ADMIN + "')")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping(value = "/role",produces = Constants.APPLICATION_JSON, consumes = Constants.APPLICATION_JSON)
    public ResponseEntity<Role> addRole(@RequestBody final Role role) {
        final Role savedDto = roleService.crateUserRoles(role);
        return ResponseEntity.ok(savedDto);
    }

    @GetMapping(value = "/role")
    public ResponseEntity<List<Role>> getRoles() {
        return null;
    }
}
