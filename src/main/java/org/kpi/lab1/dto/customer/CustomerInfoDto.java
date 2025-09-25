package org.kpi.lab1.dto.customer;


import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.kpi.lab1.dto.validation.ExtendedValidation;
import org.kpi.lab1.dto.validation.ValidAddress;

@Value
@Builder(toBuilder = true)
@Jacksonized
@GroupSequence({CustomerInfoDto.class, ExtendedValidation.class})
public class CustomerInfoDto {

    @NotBlank(message = "Name is mandatory field")
    @Size(max = 90, message = "Name cannot exceed 90 characters")
    String name;

    @NotBlank(message = "Phone number is mandatory")
    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Phone number must be valid")
    String phone;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Provided email is invalid")
    String email;

    @NotBlank(message = "Address is mandatory")
    @Size(max = 200, message = "Address cannot exceed 200 characters")
    @ValidAddress(groups = ExtendedValidation.class)
    String address;
}
