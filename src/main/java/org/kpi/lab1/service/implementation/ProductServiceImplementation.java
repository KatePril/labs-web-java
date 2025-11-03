package org.kpi.lab1.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kpi.lab1.domain.category.Category;
import org.kpi.lab1.domain.product.Product;
import org.kpi.lab1.service.ProductService;
import org.kpi.lab1.service.RateService;
import org.kpi.lab1.service.mapper.ProductMapper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImplementation implements ProductService {
  private final RateService rateService;
  private final ProductMapper productMapper;
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
    long newId = products.size() + 1L;
    double rating = rateService.getProductById(product.getId() == null ? newId : product.getId());
    Product mappedProduct = productMapper.toProduct(product, newId, rating);
    products.put(mappedProduct.getId(), mappedProduct);
    return mappedProduct;
  }

  @Override
  public Product updateProduct(Long id, Product product) {
    product =
        Product.builder()
            .id(product.getId())
            .name(product.getName())
            .description(product.getDescription())
            .price(product.getPrice())
            .rating(rateService.getProductById(product.getId()))
            .category(product.getCategory())
            .build();
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
            .rating(4.5)
            .category(category)
            .build());
    products.put(
        2L,
        Product.builder()
            .id(2L)
            .name("T-shirt")
            .description("A comfortable star shirt")
            .price(16.2)
            .rating(3.5)
            .category(category1)
            .build());
    products.put(
        3L,
        Product.builder()
            .id(3L)
            .name("Comet pencil")
            .price(5.3)
            .rating(5.0)
            .category(category)
            .build());
    return products;
  }
  ;
}
