package org.kpi.lab1.domain.order;

import lombok.Builder;
import lombok.Value;
import org.kpi.lab1.domain.product.Product;

@Value
@Builder(toBuilder = true)
public class OrderItem {
    Product product;
    int quantity;
}
