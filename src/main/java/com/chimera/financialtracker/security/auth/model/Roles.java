package com.chimera.financialtracker.security.auth.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name="Roles")
public class Roles {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @Column(name="id", updatable = false, nullable = false, columnDefinition = "UUID DEFAULT gen_random_uuid()")
    private UUID id;

    private String name;

}
