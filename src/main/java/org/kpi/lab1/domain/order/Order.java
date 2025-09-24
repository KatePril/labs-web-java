package org.kpi.lab1.domain.order;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder(toBuilder = true)
public class Order {
    Long id;
    List<OrderItem> items;
    Double total;
}
