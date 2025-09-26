package org.kpi.lab1.dto.cart;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.kpi.lab1.dto.product.ProductItemDto;

import java.util.List;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class CartDto {

    @NotNull(message = "Customer id cannot be null")
    Long customerId;

    List<ProductItemDto> items;
}
