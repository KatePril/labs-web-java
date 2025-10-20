package org.kpi.lab1.domain.cart;

import lombok.Builder;
import lombok.Value;
import org.kpi.lab1.domain.ProductItem;

import java.util.List;

@Value
@Builder(toBuilder = true)
public class Cart {
  Long id;
  Long customerId;
  List<ProductItem> items;
}
