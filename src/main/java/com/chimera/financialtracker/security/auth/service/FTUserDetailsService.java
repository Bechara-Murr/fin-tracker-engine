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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
public class FTUserDetailsService implements UserDetailsService {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
    private final UserRepo userRepository;


    public FTUserDetailsService(UserRepo userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with email: " + email));

        return new UserPrincipal(user);
    }

    public List<Users> getAllUsers(){
        List<Users> allusers = userRepository.findAll();

        return allusers;
    }

    public Users createUser(String firstName, String lastName, String email, String phoneNumber, String password, String confirmPassword, Set<Role> roles) throws Exception{

        try {

            Users user = new Users();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setPhoneNumber(phoneNumber);
            user.setPassword(passwordEncoder.encode(password));
            user.setConfirmPassword(passwordEncoder.encode(confirmPassword));
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
            throw  e;
        }
    }
}
