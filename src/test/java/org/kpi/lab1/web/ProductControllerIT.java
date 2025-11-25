package org.kpi.lab1.web;

import static com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.HttpHeaders.CONTENT_TYPE;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.kpi.lab1.AbstractIt;
import org.kpi.lab1.domain.product.Product;
import org.kpi.lab1.dto.category.CategoryDto;
import org.kpi.lab1.dto.product.ProductDto;
import org.kpi.lab1.dto.validation.ValidDescription;
import org.kpi.lab1.service.implementation.ProductServiceImplementation;
import org.kpi.lab1.service.implementation.RateServiceImplementation;
import org.kpi.lab1.web.mapper.ProductDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@DisplayName("Product Controller Integration Tests (with Mapper)")
@Tag("product-service")
public class ProductControllerIT extends AbstractIt {

  private static final String PRODUCT_NAME = "Comet product";
  private static final String PRODUCT_DESCRIPTION = "Comet product";
  private static final Double PRODUCT_PRICE = 10.7;
  private static final double PRODUCT_RATING = 5.0;
  private static final CategoryDto PRODUCT_CATEGORY =
      CategoryDto.builder().name("Comet products").build();

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Autowired private ProductDtoMapper productDtoMapper;

  @MockitoBean private ProductServiceImplementation productService;

  @MockitoBean private RateServiceImplementation rateService;

  @BeforeEach
  void setUp() {
    reset(productService);
  }

  private static ProductDto buildProductDto() {
    return ProductDto.builder()
        .name(PRODUCT_NAME)
        .description(PRODUCT_DESCRIPTION)
        .price(PRODUCT_PRICE)
        .rating(PRODUCT_RATING)
        .category(PRODUCT_CATEGORY)
        .build();
  }

  @Test
  @SneakyThrows
  void testCreateProduct() {
    ProductDto productDto = buildProductDto();
    Product product = productDtoMapper.toProduct(productDto);

    stubFor(
        WireMock.post("/rating-service/v1/ratings")
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                    .withBody(objectMapper.writeValueAsBytes(5.0))));
    when(productService.addProduct(any(ProductDto.class))).thenReturn(product);

    mockMvc
        .perform(
            post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDto)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$.name").value(PRODUCT_NAME))
        .andExpect(jsonPath("$.description").value(PRODUCT_DESCRIPTION))
        .andExpect(jsonPath("$.price").value(PRODUCT_PRICE));

    verify(productService, times(1)).addProduct(any(ProductDto.class));
  }

  @Test
  @SneakyThrows
  void testGetAllProducts() {
    ProductDto productDto = buildProductDto();
    Product product = productDtoMapper.toProduct(productDto);

    stubFor(
        WireMock.post("/rating-service/v1/ratings")
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                    .withBody(objectMapper.writeValueAsBytes(5.0))));
    when(productService.getAllProducts()).thenReturn(List.of(product));

    mockMvc
        .perform(get("/api/v1/products").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].name").value(PRODUCT_NAME))
        .andExpect(jsonPath("$[0].description").value(PRODUCT_DESCRIPTION))
        .andExpect(jsonPath("$[0].price").value(PRODUCT_PRICE));

    verify(productService, times(1)).getAllProducts();
  }

  @Test
  @SneakyThrows
  void testGetProductById() {
    ProductDto productDto = buildProductDto();
    Product product = productDtoMapper.toProduct(productDto);

    stubFor(
        WireMock.post("/rating-service/v1/ratings")
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                    .withBody(objectMapper.writeValueAsBytes(5.0))));
    when(productService.getProductById(1L)).thenReturn(product);

    mockMvc
        .perform(get("/api/v1/products/{id}", 1L).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value(PRODUCT_NAME))
        .andExpect(jsonPath("$.description").value(PRODUCT_DESCRIPTION))
        .andExpect(jsonPath("$.price").value(PRODUCT_PRICE));

    verify(productService, times(1)).getProductById(1L);
  }

  @Test
  @SneakyThrows
  void testDeleteProduct() {
    doNothing().when(productService).deleteProduct(1L);

    mockMvc.perform(delete("/api/v1/products/{id}", 1L)).andExpect(status().isNoContent());

    verify(productService, times(1)).deleteProduct(1L);
  }

  @Test
  @SneakyThrows
  void testCreateProduct_invalidData_returnsBadRequest() {
    stubFor(
        WireMock.post("/rating-service/v1/ratings")
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                    .withBody(objectMapper.writeValueAsBytes(5.0))));
    ProductDto invalidProduct =
        ProductDto.builder()
            .name("Product")
            .description("Updated description")
            .price(14.0)
            .rating(PRODUCT_RATING)
            .category(null)
            .build();

    mockMvc
        .perform(
            post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidProduct)))
        .andExpect(status().isBadRequest())
        .andExpect(
            jsonPath("$.InvalidParams[0].reason").value(ValidDescription.INVALID_DESCRIPTION));

    verify(productService, never()).addProduct(any(ProductDto.class));
  }

  @Test
  @SneakyThrows
  void testGetProductById_notFound() {
    stubFor(
        WireMock.post("/rating-service/v1/ratings")
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                    .withBody(objectMapper.writeValueAsBytes(5.0))));
    when(productService.getProductById(99L)).thenReturn(null);

    mockMvc
        .perform(get("/api/v1/products/{id}", 99L).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());

    verify(productService, times(1)).getProductById(99L);
  }

  @Test
  @SneakyThrows
  void testGetAllProducts_emptyList() {
    when(productService.getAllProducts()).thenReturn(List.of());

    mockMvc
            .perform(get("/api/v1/products").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(0));

    verify(productService, times(1)).getAllProducts();
  }
}
