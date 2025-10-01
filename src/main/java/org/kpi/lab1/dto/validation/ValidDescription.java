package org.kpi.lab1.dto.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE_USE, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CosmicWordValidator.class)
@Documented
public @interface ValidDescription {

    String INVALID_DESCRIPTION = "The provided description is invalid. The description should contain one of te following words: 'star', 'galaxy', 'comet'";

    String message() default INVALID_DESCRIPTION;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
