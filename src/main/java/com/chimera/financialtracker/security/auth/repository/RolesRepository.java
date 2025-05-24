package com.chimera.financialtracker.security.auth.repository;

import com.chimera.financialtracker.security.auth.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface RolesRepository extends JpaRepository<Role, UUID> {
}
