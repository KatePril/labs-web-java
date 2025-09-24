package org.kpi.lab1.domain.customer;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class Customer {
    Long id;
    String name;
    String address;
    String phone;
    String email;
}
