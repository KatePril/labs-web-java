package org.kpi.lab1.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.kpi.lab1.config.MappersTestConfiguration;
import org.kpi.lab1.domain.category.Category;
import org.kpi.lab1.domain.product.Product;
import org.kpi.lab1.service.implementation.ProductServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

@SpringBootTest(classes = {ProductServiceImplementation.class})
@Import(MappersTestConfiguration.class)
@DisplayName("Product Service Tests")
@TestMethodOrder(OrderAnnotation.class)
public class ProductServiceTest {
  private static final String PRODUCT_NAME = "product";
  private static final double PRODUCT_PRICE = 10.1;
  private static final Category CATEGORY = Category.builder().id(1L).name("test category").build();

  @MockBean
  private RateService rateService;

  @Autowired
  private ProductServiceImplementation productService;

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

  @Test
  @Order(2)
  @DisplayName("Test get product by id")
  public void testGetProductById() {
    Product product = productService.getProductById(1L);
    assertNotNull(product);
    assertEquals("Book", product.getName());
    assertEquals(1L, product.getId());
    assertEquals("An interesting galaxy one", product.getDescription());
    assertEquals(10.4, product.getPrice());
    assertNotNull(product.getCategory());
    assertEquals("School supplies", product.getCategory().getName());
  }

  @Test
  @Order(3)
  @DisplayName("Should add a new product")
  public void testAddProduct() {
    when(rateService.getProductById(99L)).thenReturn(4.9);
    Product newProduct = buildProduct(99L);

    Product added = productService.addProduct(newProduct);
    assertEquals(newProduct, added);

    Product fetched = productService.getProductById(99L);
    assertNotNull(fetched);
    assertEquals(newProduct, fetched);
    assertEquals(4, productService.getAllProducts().size());
  }

  @Test
  @Order(4)
  @DisplayName("Should update existing product")
  public void testUpdateProduct() {
    when(rateService.getProductById(99L)).thenReturn(4.9);
    Product updatedProduct =
        Product.builder().id(99L).name("Updated product").price(55.5).category(CATEGORY).build();

    Product result = productService.updateProduct(99L, updatedProduct);

    assertEquals(updatedProduct, result);
    Product fetched = productService.getProductById(99L);
    assertNotNull(fetched);
    assertEquals("Updated product", fetched.getName());
    assertEquals(55.5, fetched.getPrice());
  }

  @Test
  @Order(5)
  @DisplayName("test delete product by ID")
  void testDeleteProduct() {
    productService.deleteProduct(99L);

    Product deleted = productService.getProductById(99L);
    assertNull(deleted);
    assertEquals(3, productService.getAllProducts().size());
  }

  @Test
  @Order(6)
  @DisplayName("Should handle deleting non-existent product gracefully")
  void testHandleDeletingNonExistentProduct() {
    Assertions.assertDoesNotThrow(() -> productService.deleteProduct(1000L));
    assertNull(productService.getProductById(1000L));
  }

  private Product buildProduct(Long id) {
    return Product.builder()
        .id(id)
        .name(PRODUCT_NAME)
        .price(PRODUCT_PRICE)
        .category(CATEGORY)
        .build();
  }
}
