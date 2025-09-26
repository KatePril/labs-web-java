package org.kpi.lab1.dto.category;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class CategoryDto {

    @NotBlank(message = "Category name is mandatory")
    String name;

    String description;
}
