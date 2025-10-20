package org.kpi.lab1.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.kpi.lab1.config.MappersTestConfiguration;
import org.kpi.lab1.domain.category.Category;
import org.kpi.lab1.domain.product.Product;
import org.kpi.lab1.service.implementation.ProductServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

@SpringBootTest(classes = {ProductServiceImplementation.class})
@Import(MappersTestConfiguration.class)
@DisplayName("Product Service Tests")
@TestMethodOrder(OrderAnnotation.class)
public class ProductServiceTest {
  private static final Long ID = 1L;
  private static final String PRODUCT_NAME = "product";
  private static final double PRODUCT_PRICE = 10.1;
  private static final Category CATEGORY = Category.builder().id(1L).name("test category").build();
  private static final Product DEFAULT_PRODUCT = buildProduct(ID);

  @Autowired
  private ProductService productService;

  @Test
  @Order(1)
  @DisplayName("Test get all products method")
  public void testGetAllProducts() {
    List<Product> products = productService.getAllProducts();
    assertNotNull(products);
    assertEquals(3, products.size());
    assertIterableEquals(
        products.stream().map(Product::getName).collect(Collectors.toList()),
        new ArrayList<>(Arrays.asList("Book", "T-shirt", "Comet pencil")));
  }

  private static Product buildProduct(Long id) {
    return Product.builder()
        .id(id)
        .name(PRODUCT_NAME)
        .price(PRODUCT_PRICE)
        .category(CATEGORY)
        .build();
  }
}
