package org.kpi.lab1.service;

import org.kpi.lab1.domain.product.Product;

import java.util.List;

public interface ProductService {

  List<Product> getAllProducts();

  Product getProductById(Long id);

  Product addProduct(Product product);

  Product updateProduct(Long id, Product product);

  void deleteProduct(Long id);
}
