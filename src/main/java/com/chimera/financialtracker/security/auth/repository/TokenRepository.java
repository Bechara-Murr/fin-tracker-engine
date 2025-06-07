package com.chimera.financialtracker.security.auth.repository;

import com.chimera.financialtracker.security.auth.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<VerificationToken, UUID> {
    VerificationToken getByToken(String token);
}
