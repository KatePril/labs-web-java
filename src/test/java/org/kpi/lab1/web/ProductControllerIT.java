package org.kpi.lab1.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.kpi.lab1.config.MappersTestConfiguration;
import org.kpi.lab1.domain.product.Product;
import org.kpi.lab1.dto.category.CategoryDto;
import org.kpi.lab1.dto.product.ProductDto;
import org.kpi.lab1.service.implementation.ProductServiceImplementation;
import org.kpi.lab1.web.mapper.ProductDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc
@Import(MappersTestConfiguration.class)
@DisplayName("Product Controller Integration Tests (with Mapper)")
@Tag("product-service")
public class ProductControllerIT {

  private static final String PRODUCT_NAME = "Comet product";
  private static final String PRODUCT_DESCRIPTION = "Comet product";
  private static final Double PRODUCT_PRICE = 10.7;
  private static final CategoryDto PRODUCT_CATEGORY =
          CategoryDto.builder().name("Comet products").build();

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private ProductDtoMapper productDtoMapper;

  @MockitoBean
  private ProductServiceImplementation productService;

  @BeforeEach
  void setUp() {
    reset(productService);
  }

  private static ProductDto buildProductDto() {
    return ProductDto.builder()
            .name(PRODUCT_NAME)
            .description(PRODUCT_DESCRIPTION)
            .price(PRODUCT_PRICE)
            .category(PRODUCT_CATEGORY)
            .build();
  }

  @Test
  @SneakyThrows
  void createProduct() {
    ProductDto productDto = buildProductDto();
    Product product = productDtoMapper.toProduct(productDto);

    when(productService.addProduct(any(Product.class)))
            .thenReturn(product);

    mockMvc.perform(post("/api/v1/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(productDto)))
            .andExpect(status().is2xxSuccessful())
            .andExpect(jsonPath("$.name").value(PRODUCT_NAME))
            .andExpect(jsonPath("$.description").value(PRODUCT_DESCRIPTION))
            .andExpect(jsonPath("$.price").value(PRODUCT_PRICE));

    verify(productService, times(1)).addProduct(any(Product.class));
  }

  @Test
  @SneakyThrows
  void getAllProducts() {
    ProductDto productDto = buildProductDto();
    Product product = productDtoMapper.toProduct(productDto);

    when(productService.getAllProducts()).thenReturn(List.of(product));

    mockMvc.perform(get("/api/v1/products")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].name").value(PRODUCT_NAME))
            .andExpect(jsonPath("$[0].description").value(PRODUCT_DESCRIPTION))
            .andExpect(jsonPath("$[0].price").value(PRODUCT_PRICE));

    verify(productService, times(1)).getAllProducts();
  }

  @Test
  @SneakyThrows
  void getProductById() {
    ProductDto productDto = buildProductDto();
    Product product = productDtoMapper.toProduct(productDto);

    when(productService.getProductById(1L)).thenReturn(product);

    mockMvc.perform(get("/api/v1/products/{id}", 1L)
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value(PRODUCT_NAME))
            .andExpect(jsonPath("$.description").value(PRODUCT_DESCRIPTION))
            .andExpect(jsonPath("$.price").value(PRODUCT_PRICE));

    verify(productService, times(1)).getProductById(1L);
  }

  @Test
  @SneakyThrows
  void updateProduct() {
    ProductDto updatedDto = ProductDto.builder()
            .name("Updated Comet product")
            .description("Updated description")
            .price(30.0)
            .category(PRODUCT_CATEGORY)
            .build();

    Product updatedProduct = productDtoMapper.toProduct(updatedDto);

    when(productService.updateProduct(eq(1L), any(Product.class)))
            .thenReturn(updatedProduct);

    mockMvc.perform(put("/api/v1/products/{id}", 1L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(updatedDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Updated Comet product"))
            .andExpect(jsonPath("$.description").value("Updated description"))
            .andExpect(jsonPath("$.price").value(30.0));

    verify(productService, times(1)).updateProduct(eq(1L), any(Product.class));
  }

  @Test
  @SneakyThrows
  void deleteProduct() {
    doNothing().when(productService).deleteProduct(1L);

    mockMvc.perform(delete("/api/v1/products/{id}", 1L))
            .andExpect(status().isNoContent());

    verify(productService, times(1)).deleteProduct(1L);
  }
}
