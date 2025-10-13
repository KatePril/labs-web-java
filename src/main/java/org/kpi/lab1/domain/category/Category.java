package org.kpi.lab1.domain.category;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class Category {
  Long id;
  String name;
  String description;
}
