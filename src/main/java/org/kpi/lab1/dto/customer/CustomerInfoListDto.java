package org.kpi.lab1.dto.customer;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class CustomerInfoListDto {

    List<CustomerInfoDto> customerInfoList;

}
