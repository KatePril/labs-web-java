package org.kpi.lab1.dto.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE_USE, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AddressValidator.class)
@Documented
public @interface ValidAddress {

  String INVALID_ADDRESS_MSG =
      "The provided address is invalid. Valid address example: Street Green, Building 86-3";

  String message() default INVALID_ADDRESS_MSG;

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
