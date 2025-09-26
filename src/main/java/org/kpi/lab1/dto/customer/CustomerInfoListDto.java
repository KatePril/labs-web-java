package org.kpi.lab1.dto.customer;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class CustomerInfoListDto {

    @NotNull(message = "Customers list cannot be null")
    List<CustomerInfoDto> customerInfoList;

}
