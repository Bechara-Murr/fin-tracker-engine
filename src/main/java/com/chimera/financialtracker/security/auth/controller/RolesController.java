package com.chimera.financialtracker.security.auth.controller;

import com.chimera.financialtracker.security.auth.model.Role;
import com.chimera.financialtracker.security.auth.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RolesController {

    private final RolesService rolesService;

    public RolesController(RolesService rolesService){
        this.rolesService = rolesService;
    }

    @GetMapping
    public List<Role> getRoles() {
        return rolesService.getRoles();
    }

    @PostMapping
    public Role createRole(@RequestBody Role role){
        return rolesService.createRole(role);
    }
}
