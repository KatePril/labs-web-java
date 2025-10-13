package org.kpi.lab1.dto.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class CosmicWordValidator implements ConstraintValidator<ValidDescription, String> {

  private static final Pattern PATTERN =
      Pattern.compile("\\b(star|galaxy|comet)\\b", Pattern.CASE_INSENSITIVE);

  @Override
  public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
    return PATTERN.matcher(s).find();
  }
}
