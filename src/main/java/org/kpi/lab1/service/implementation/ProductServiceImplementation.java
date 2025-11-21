package org.kpi.lab1.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kpi.lab1.domain.category.Category;
import org.kpi.lab1.domain.product.Product;
import org.kpi.lab1.dto.product.ProductDto;
import org.kpi.lab1.repository.ProductRepository;
import org.kpi.lab1.repository.entity.ProductEntity;
import org.kpi.lab1.service.ProductService;
import org.kpi.lab1.service.RateService;
import org.kpi.lab1.service.mapper.ProductMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImplementation implements ProductService {
  private final RateService rateService;
  private final ProductMapper productMapper;
  private final ProductRepository productRepository;
  private final ConcurrentHashMap<Long, Product> products = buildProductsMock();

  @Override
  @Transactional(readOnly = true)
  public List<Product> getAllProducts() {
    return productMapper.toProducts(productRepository.findAll());
  }

  @Override
  @Transactional(readOnly = true)
  public Product getProductById(Long id) {
    ProductEntity entity = productRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));

    return productMapper.toProduct(entity);
  }

  @Override
  @Transactional(propagation = Propagation.NESTED)
  public Product addProduct(ProductDto product) {
    try {
      return productMapper.toProduct(productRepository.save(productMapper.toProductEntity(product)));
    } catch (Exception e) {
      log.error("Exception occurred while saving customer details");
      throw new PersistenceException(e);
    }
  }

  @Override
  @Transactional
  public void deleteProduct(Long id) {
    try {
      productRepository.deleteById(id);
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
