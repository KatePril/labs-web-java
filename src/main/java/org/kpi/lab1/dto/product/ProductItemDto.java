package org.kpi.lab1.dto.product;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class ProductItemDto {

  @NotNull(message = "Product cannot be null")
  ProductDto product;

  @NotNull(message = "Quantity cannot be null")
  int quantity;
}
