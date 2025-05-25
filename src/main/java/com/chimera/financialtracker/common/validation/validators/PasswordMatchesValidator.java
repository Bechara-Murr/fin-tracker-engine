package com.chimera.financialtracker.common.validation.validators;

import com.chimera.financialtracker.common.validation.annotations.PasswordMatches;
import com.chimera.financialtracker.security.auth.model.Users;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation){
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        Users user = (Users) obj;
        return user.getPassword().equals(user.getConfirmPassword());
    }
}
