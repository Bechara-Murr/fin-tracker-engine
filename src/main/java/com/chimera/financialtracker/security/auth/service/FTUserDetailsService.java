package com.chimera.financialtracker.security.auth.service;

import com.chimera.financialtracker.security.auth.model.Role;
import com.chimera.financialtracker.security.auth.model.UserPrincipal;
import com.chimera.financialtracker.security.auth.model.Users;
import com.chimera.financialtracker.security.auth.repository.UserRepo;
import jakarta.persistence.RollbackException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
public class FTUserDetailsService implements UserDetailsService {

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
    private final UserRepo userRepository;

    public FTUserDetailsService(UserRepo userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = userRepository.findByUsername(username);
        if(user == null){
            System.out.println("Not found");
            throw new UsernameNotFoundException("User not found");
        }

        return new UserPrincipal(user);
    }

    public List<Users> getAllUsers(){
        return userRepository.findAll();
    }

    public Users createUser(String username, String email, String password, String confirmPassword, Set<Role> roles) throws Exception{

        try {

            Users user = new Users();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password);
            user.setConfirmPassword(confirmPassword);
            user.setRoles(roles);


            return userRepository.save(user);
        }
        catch(Exception e) {
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
