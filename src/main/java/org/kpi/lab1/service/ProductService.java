package org.kpi.lab1.service;

import org.kpi.lab1.domain.product.Product;
import org.kpi.lab1.dto.product.ProductDto;

import java.util.List;

public interface ProductService {

  List<Product> getAllProducts();

  Product getProductById(Long id);

  Product addProduct(ProductDto product);

  void deleteProduct(Long id);
}
