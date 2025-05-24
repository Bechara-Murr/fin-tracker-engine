package com.chimera.financialtracker.security.auth.service;

import com.chimera.financialtracker.security.auth.model.Role;
import com.chimera.financialtracker.security.auth.model.UserPrincipal;
import com.chimera.financialtracker.security.auth.model.Users;
import com.chimera.financialtracker.security.auth.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
public class FTUserDetailsService implements UserDetailsService {

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

    public Users createUser(String username, String password, Set<Role> roles){
        Users user = new Users();
        user.setUsername(username);
        user.setPassword(password);
        user.setRoles(roles);

        return userRepository.save(user);
    }
}
