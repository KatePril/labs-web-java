package org.kpi.lab1.dto.customer;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class CustomerInfoItem {
    Long id;
    String name;
    String phone;
    String email;
    String address;
}
