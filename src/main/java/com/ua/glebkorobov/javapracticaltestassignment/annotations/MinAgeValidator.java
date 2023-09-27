package com.ua.glebkorobov.javapracticaltestassignment.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.Period;


public class MinAgeValidator implements ConstraintValidator<MinAge, LocalDate> {

    @Value("${user.minAge}")
    private int minAge;

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        LocalDate now = LocalDate.now();
        Period period = Period.between(value, now);
        return period.getYears() >= minAge;
    }


}

