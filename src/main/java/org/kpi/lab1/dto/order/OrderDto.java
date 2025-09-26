package org.kpi.lab1.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.kpi.lab1.dto.product.ProductItemDto;

import java.util.List;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class OrderDto {

    @NotNull(message = "Items list cannot be null")
    List<ProductItemDto> items;

    @NotNull(message = "Total cannot be null")
    Double total;
}
