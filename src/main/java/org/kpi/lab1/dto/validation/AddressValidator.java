package org.kpi.lab1.dto.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class AddressValidator implements ConstraintValidator<ValidAddress, String> {

    private static final String ADDRESS_PATTERN = "Street [A-Za-z]+, Building \\d+-\\d+[a-z]?$";

    private static final Pattern ADDRESS_PATTERN_PATTERN = Pattern.compile(ADDRESS_PATTERN);

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return ADDRESS_PATTERN_PATTERN.matcher(s).matches();
    }
}
