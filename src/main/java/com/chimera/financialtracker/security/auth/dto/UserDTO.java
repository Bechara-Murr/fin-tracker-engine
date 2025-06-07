package com.chimera.financialtracker.security.auth.dto;

import com.chimera.financialtracker.common.validation.annotations.PasswordMatches;
import com.chimera.financialtracker.security.auth.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

@PasswordMatches
public class UserDTO {

    private String username;

    @NotNull(message="Email cannot be null")
    @NotBlank(message="Email cannot be blank")
    @Email(message="Please enter a valid email address")
    private String email;

    @NotNull
    @NotEmpty
    private String password;
    private String confirmPassword;

    private Set<Role> roles = new HashSet<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
