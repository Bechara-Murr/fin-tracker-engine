package com.chimera.financialtracker.security.auth.service;

import com.chimera.financialtracker.security.auth.model.UserPrincipal;
import com.chimera.financialtracker.security.auth.model.Users;
import com.chimera.financialtracker.security.auth.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class FTUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = userRepository.findByUsername(username);
        if(user == null){
            System.out.println("Not found");
            throw new UsernameNotFoundException("User not found");
        }

        return new UserPrincipal(user);
    }

    public Users createUser(String username, String password){
        Users user = new Users();
        user.setUsername(username);
        user.setPassword(password);

        return userRepository.save(user);
    }
}
