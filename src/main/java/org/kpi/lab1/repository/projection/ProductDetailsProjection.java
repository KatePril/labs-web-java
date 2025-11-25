package org.kpi.lab1.repository.projection;

public record ProductDetailsProjection(
        String productName,
        Double productPrice,
        String productDescription
) {}
