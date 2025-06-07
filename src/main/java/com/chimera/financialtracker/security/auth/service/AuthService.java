package com.chimera.financialtracker.security.auth.service;

import com.chimera.financialtracker.security.auth.dto.LoginDTO;
import com.chimera.financialtracker.security.auth.model.Users;
import com.chimera.financialtracker.security.auth.model.VerificationToken;
import com.chimera.financialtracker.security.auth.repository.TokenRepository;
import com.chimera.financialtracker.security.auth.repository.UserRepo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final TokenRepository tokenRepository;
    private final UserRepo userRepository;

    public AuthService(
            AuthenticationManager authenticationManager,
            JWTService jwtService,
            TokenRepository tokenRepository,
            UserRepo userRepository
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
    }

    public String loginUser(LoginDTO login) throws UsernameNotFoundException, Exception{
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));

        if(authentication.isAuthenticated())
            return jwtService.generateToken(login.getUsername());

        return "Fail";
    }

    public void createVerificationToken(Users user, String token){
        VerificationToken verificationToken = new VerificationToken(user, token);
        tokenRepository.save(verificationToken);
    }

    public String verifyRegistration(String token) {
        VerificationToken verificationToken = tokenRepository.getByToken(token);

        if(verificationToken == null){
            return "The provided token is invalid";
        }

        Users user = verificationToken.getUser();

        Calendar cal = Calendar.getInstance();
        if((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0){
            return "Your token has expired, please request a new token";
        }

        user.setEnabled(true);
        userRepository.save(user);

        return "Your account has been successfully verified";
    }
}
