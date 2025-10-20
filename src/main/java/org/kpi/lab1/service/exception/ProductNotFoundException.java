package org.kpi.lab1.service.exception;

public class ProductNotFoundException extends RuntimeException {
  private static final String EXCEPTION_MSG = "No product with id %s found";

  public ProductNotFoundException(Long productId) {
    super(String.format(EXCEPTION_MSG, productId));
  }
}
