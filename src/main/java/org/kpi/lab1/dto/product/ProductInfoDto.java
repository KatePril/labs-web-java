package org.kpi.lab1.dto.product;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class ProductInfoDto {
    @NotNull(message = "Product name cannot be null")
    String name;

    String description;

    @NotNull(message = "Price description cannot be null")
    Double price;
}
