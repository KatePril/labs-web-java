package org.kpi.lab1.domain.order;

import lombok.Builder;
import lombok.Value;
import org.kpi.lab1.domain.ProductItem;

import java.util.List;

@Value
@Builder(toBuilder = true)
public class Order {
  Long id;
  List<ProductItem> items;
  Double total;
}
