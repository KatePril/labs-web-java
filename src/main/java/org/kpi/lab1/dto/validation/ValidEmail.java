package org.kpi.lab1.dto.validation;

import jakarta.validation.Payload;

public @interface ValidEmail {
    String VALID_EMAIL_MESSAGE = "Email adress should contain a valid name and domain. Example: myemail@space.com";

    String message() default VALID_EMAIL_MESSAGE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
