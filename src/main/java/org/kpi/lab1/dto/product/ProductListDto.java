package org.kpi.lab1.dto.product;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class ProductListDto {

  @NotNull(message = "Product list cannot be null")
  List<ProductDto> products;
}
