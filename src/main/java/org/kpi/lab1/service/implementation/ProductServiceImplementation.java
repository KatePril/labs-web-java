package org.kpi.lab1.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.kpi.lab1.domain.category.Category;
import org.kpi.lab1.domain.product.Product;
import org.kpi.lab1.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class ProductServiceImplementation implements ProductService {
  private final ConcurrentHashMap<Long, Product> products = buildProductsMock();

  @Override
  public List<Product> getAllProducts() {
    return new ArrayList<>(products.values());
  }

  @Override
  public Product getProductById(Long id) {
    return products.get(id);
  }

  @Override
  public Product addProduct(Product product) {
    products.put(product.getId(), product);
    return product;
  }

  @Override
  public Product updateProduct(Long id, Product product) {
    products.put(id, product);
    return product;
  }

  @Override
  public void deleteProduct(Long id) {
    try {
      products.remove(id);
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }

  private ConcurrentHashMap<Long, Product> buildProductsMock() {
    Category category = Category.builder().name("School supplies").build();
    Category category1 = Category.builder().name("Clothes").build();
    ConcurrentHashMap<Long, Product> products = new ConcurrentHashMap<>();
    products.put(
        1L,
        Product.builder()
            .id(1L)
            .name("Book")
            .description("An interesting galaxy one")
            .price(10.4)
            .category(category)
            .build());
    products.put(
        2L,
        Product.builder()
            .id(2L)
            .name("T-shirt")
            .description("A comfortable star shirt")
            .price(16.2)
            .category(category1)
            .build());
    products.put(
        3L, Product.builder().id(3L).name("Comet pencil").price(5.3).category(category).build());
    return products;
  }
  ;
}
