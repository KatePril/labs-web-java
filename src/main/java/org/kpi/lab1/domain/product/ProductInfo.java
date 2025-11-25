package org.kpi.lab1.domain.product;

import lombok.Builder;


@Builder(toBuilder = true)
public record ProductInfo(String name, String description, Double price) {
}
