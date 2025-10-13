package org.kpi.lab1.domain.product;

import lombok.Builder;
import lombok.Value;
import org.kpi.lab1.domain.category.Category;

@Value
@Builder(toBuilder = true)
public class Product {
  Long id;
  String name;
  String description;
  Double price;
  Category category;
}
