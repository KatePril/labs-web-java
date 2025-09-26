package org.kpi.lab1.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class OrderListDto {

    @NotNull(message = "Orders list cannot be null")
    List<OrderDto> orders;
}
