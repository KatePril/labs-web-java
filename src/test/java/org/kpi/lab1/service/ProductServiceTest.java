package org.kpi.lab1.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.util.*;
import java.util.stream.Collectors;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.kpi.lab1.config.MappersTestConfiguration;
import org.kpi.lab1.domain.category.Category;
import org.kpi.lab1.domain.product.Product;
import org.kpi.lab1.dto.category.CategoryDto;
import org.kpi.lab1.dto.product.ProductDto;
import org.kpi.lab1.repository.ProductRepository;
import org.kpi.lab1.repository.entity.CategoryEntity;
import org.kpi.lab1.repository.entity.ProductEntity;
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
  private static final double PRODUCT_RATING = 4.9;
  private static final Category CATEGORY = Category.builder().id(1L).name("test category").build();

  @MockBean private RateService rateService;

  @MockBean private ProductRepository productRepository;

  @Autowired private ProductService productService;

  private Map<Long, ProductEntity> db;

  @BeforeEach
  void setupMockRepository() {
    db = new HashMap<>();

    CategoryEntity category1 = CategoryEntity.builder().id(1L).name("School supplies").build();
    CategoryEntity category2 = CategoryEntity.builder().id(1L).name("Clothes").build();

    db.put(1L, new ProductEntity(1L, "Book", "An interesting galaxy one", 10.4, 0.0, category1));
    db.put(2L, new ProductEntity(2L, "T-shirt", "A comfy cotton T-shirt", 15.0, 0.0, category2));
    db.put(3L, new ProductEntity(3L, "Comet pencil", "Smooth graphite pencil", 2.5, 0.0, category1));

    when(productRepository.findAll()).thenAnswer(inv -> new ArrayList<>(db.values()));
    when(rateService.getProductById(anyLong())).thenReturn(PRODUCT_RATING);
  }

  @Test
  @Order(1)
  @DisplayName("Test get all products method")
  public void testGetAllProducts() {
    List<Product> products = productService.getAllProducts();
    assertNotNull(products);
    assertEquals(3, products.size());
    assertIterableEquals(
        products.stream().map(Product::getName).collect(Collectors.toList()),
        Arrays.asList("Book", "T-shirt", "Comet pencil"));
  }

  @Test
  @Order(2)
  @DisplayName("Test get product by id")
  public void testGetProductById() {
    when(productRepository.findById(anyLong()))
        .thenAnswer(inv -> Optional.ofNullable(db.get(inv.getArgument(0))));

    Product product = productService.getProductById(1L);
    assertNotNull(product);
    assertEquals("Book", product.getName());
    assertEquals(1L, product.getId());
    assertEquals("An interesting galaxy one", product.getDescription());
    assertEquals(10.4, product.getPrice());
  }

  @Test
  @Order(3)
  @DisplayName("Should add a new product")
  public void testAddProduct() {
    when(productRepository.save(any(ProductEntity.class)))
        .thenAnswer(
            inv -> {
              ProductEntity p = inv.getArgument(0);
              if (p.getId() == null) {
                long nextId = db.keySet().stream().max(Long::compare).orElse(0L) + 1;
                p.setId(nextId);
              }
              db.put(p.getId(), p);
              return p;
            });

    ProductDto newProduct = buildProductDto();

    Product added = productService.addProduct(newProduct);
    assertNotNull(added);
    assertNotNull(added.getId());
    assertEquals(PRODUCT_RATING, added.getRating());
  }

  @Test
  @Order(4)
  @DisplayName("Test delete product by ID")
  void testDeleteProduct() {
    doAnswer(inv -> {
      db.remove(inv.getArgument(0));
      return null;
    }).when(productRepository).deleteById(anyLong());

    assertEquals(3, productService.getAllProducts().size());
    productService.deleteProduct(1L);
    assertEquals(2, productService.getAllProducts().size());
  }

  @Test
  @Order(5)
  @DisplayName("Should handle deleting non-existent product gracefully")
  void testHandleDeletingNonExistentProduct() {
    assertDoesNotThrow(() -> productService.deleteProduct(1000L));
    assertThrows(EntityNotFoundException.class,
            () -> productService.getProductById(1000L));
  }

  private ProductDto buildProductDto() {
    return ProductDto.builder()
        .name(PRODUCT_NAME)
        .price(PRODUCT_PRICE)
        .rating(PRODUCT_RATING)
        .category(CategoryDto.builder().name(CATEGORY.getName()).build())
        .build();
  }
}
