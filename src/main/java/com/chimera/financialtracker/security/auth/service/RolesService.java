package com.chimera.financialtracker.security.auth.service;

import com.chimera.financialtracker.security.auth.model.Role;
import com.chimera.financialtracker.security.auth.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolesService {

    private final RolesRepository rolesRepository;

    public RolesService(RolesRepository rolesRepository){
        this.rolesRepository = rolesRepository;
    }

    public List<Role> getRoles(){
        return rolesRepository.findAll();
    }

    public Role createRole(Role role){
        return rolesRepository.save(role);
    }
}
