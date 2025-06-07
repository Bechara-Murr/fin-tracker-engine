package com.chimera.financialtracker.security.auth.controller;

import com.chimera.financialtracker.security.auth.dto.LoginDTO;
import com.chimera.financialtracker.security.auth.dto.UserDTO;
import com.chimera.financialtracker.security.auth.event.OnRegistrationCompleteEvent;
import com.chimera.financialtracker.security.auth.model.Users;
import com.chimera.financialtracker.security.auth.service.AuthService;
import com.chimera.financialtracker.security.auth.service.FTUserDetailsService;
import jakarta.persistence.RollbackException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RestController
public class AuthenticationController {

    private final FTUserDetailsService ftUserDetailsService;
    private final AuthService authService;

    private final ApplicationEventPublisher eventPublisher;


    public AuthenticationController(FTUserDetailsService ftUserDetailsService,
                                    AuthService authService,
                                    ApplicationEventPublisher eventPublisher){
        this.ftUserDetailsService = ftUserDetailsService;
        this.authService = authService;
        this.eventPublisher = eventPublisher;
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
    public ResponseEntity createUser(@RequestBody @Valid UserDTO newUser, HttpServletRequest request) {
        try{
            Users registered = ftUserDetailsService.createUser(newUser.getUsername(), newUser.getEmail(), newUser.getPassword(), newUser.getConfirmPassword(), newUser.getRoles());
            String appUrl = request.getContextPath();
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered,
                    request.getLocale(), appUrl));

            return new ResponseEntity(
                    "You have successfully created a new account, please proceed to verify it",
                    HttpStatus.CREATED
            );
        }catch(Exception e) {
            return new ResponseEntity("The account could not be created.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/registrationConfirm")
    public String confirmRegistration(WebRequest request, @RequestParam("token") String token){
        try {
            return authService.verifyRegistration(token);
        } catch (Exception e){
            throw e;
        }
    }
}
