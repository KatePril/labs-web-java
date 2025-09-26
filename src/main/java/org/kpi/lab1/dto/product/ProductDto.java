package org.kpi.lab1.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.kpi.lab1.dto.category.CategoryDto;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class ProductDto {

    @NotBlank(message = "Name is mandatory field")
    @Size(max = 90, message = "Name cannot exceed 90 characters")
    String name;

    String description;

    @NotNull(message = "Price cannot be null")
    Double price;

    CategoryDto category;
}
