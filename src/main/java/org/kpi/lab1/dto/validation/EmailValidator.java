package org.kpi.lab1.dto.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class EmailValidator implements ConstraintValidator<ValidEmail, String> {
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    private static final Pattern EMAIL_PATTERN_PATTERN = Pattern.compile(EMAIL_PATTERN);

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return EMAIL_PATTERN_PATTERN.matcher(s).matches();
    }
}
