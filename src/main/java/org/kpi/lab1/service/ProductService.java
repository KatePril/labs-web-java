package org.kpi.lab1.service;

import org.kpi.lab1.domain.product.Product;
import org.kpi.lab1.domain.product.ProductInfo;
import org.kpi.lab1.dto.product.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

  List<Product> getAllProducts();

  Product getProductById(Long id);

  Product addProduct(ProductDto product);

  void deleteProduct(Long id);

  Page<ProductInfo> getProductByCategory(String category, int start, int pageSize);
}
