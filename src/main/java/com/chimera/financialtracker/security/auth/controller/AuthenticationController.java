package com.chimera.financialtracker.security.auth.controller;

import com.chimera.financialtracker.security.auth.dto.LoginDTO;
import com.chimera.financialtracker.security.auth.dto.UserDTO;
import com.chimera.financialtracker.security.auth.model.Users;
import com.chimera.financialtracker.security.auth.service.AuthService;
import com.chimera.financialtracker.security.auth.service.FTUserDetailsService;
import jakarta.persistence.RollbackException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuthenticationController {

    private final FTUserDetailsService ftUserDetailsService;
    private final AuthService authService;

    @Autowired
    private Validator validator;

    public AuthenticationController(FTUserDetailsService ftUserDetailsService, AuthService authService){
        this.ftUserDetailsService = ftUserDetailsService;
        this.authService = authService;
    }

    @GetMapping("/getUser")
    List<Users> getUser() {
        return ftUserDetailsService.getAllUsers();
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody LoginDTO userLogin) throws Exception{
        try{
            return authService.loginUser(userLogin);
        }catch(Exception e) {
            throw e;
        }
    }

    @PostMapping("/createuser")
    public Users createUser(@RequestBody @Valid UserDTO newUser) throws Exception {
        try{
            return ftUserDetailsService.createUser(newUser.getUsername(), newUser.getEmail(), newUser.getPassword(), newUser.getConfirmPassword(), newUser.getRoles());
        }catch(Exception e) {
            Throwable cause = e.getCause();
            while (cause != null) {
                if (cause instanceof RollbackException rollbackEx) {
                    Throwable rollbackCause = rollbackEx.getCause();
                    if (rollbackCause instanceof ConstraintViolationException validationEx) {
                        for (ConstraintViolation<?> violation : validationEx.getConstraintViolations()) {
                            System.out.println("Validation error: " + violation.getMessage());
                            // Optionally throw a custom exception here
                        }
                        // Stop further unwrapping
                        break;
                    }
                }
                cause = cause.getCause(); // keep unwrapping if needed
            }
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            throw  e;
        }
    }
}
