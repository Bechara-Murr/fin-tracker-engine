package com.chimera.financialtracker.security.auth.controller;

import com.chimera.financialtracker.security.auth.model.Users;
import com.chimera.financialtracker.security.auth.service.FTUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuthenticationController {

    private final FTUserDetailsService ftUserDetailsService;

    public AuthenticationController(FTUserDetailsService ftUserDetailsService){
        this.ftUserDetailsService = ftUserDetailsService;
    }

    @GetMapping("/getUser")
    List<Users> getUser() {
        return ftUserDetailsService.getAllUsers();
    }

    @PostMapping("/createuser")
    Users createUser(@RequestBody Users newUser) throws Exception {
        return ftUserDetailsService.createUser(newUser.getUsername(), newUser.getEmail(), newUser.getPassword(), newUser.getConfirmPassword(), newUser.getRoles());
    }
}
