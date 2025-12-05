package org.kpi.lab1.dto.product;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class ProductItemListDto {

    @NotNull(message = "Product items list cannot be null")
    List<ProductItemDto> productItems;
}
