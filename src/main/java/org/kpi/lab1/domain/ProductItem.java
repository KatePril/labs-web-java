package org.kpi.lab1.domain;

import lombok.Builder;
import lombok.Value;
import org.kpi.lab1.domain.product.Product;

@Value
@Builder(toBuilder = true)
public class ProductItem {
    Product product;
    int quantity;
}
