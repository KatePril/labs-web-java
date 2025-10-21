package org.kpi.lab1.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.kpi.lab1.domain.category.Category;
import org.kpi.lab1.domain.product.Product;
import org.kpi.lab1.dto.category.CategoryDto;
import org.kpi.lab1.dto.product.ProductDto;
import org.kpi.lab1.service.implementation.ProductServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Product Controller IT")
@Tag("product-service")
public class ProductControllerIT {
  private static final String PRODUCT_NAME = "Comet product";
  private static final String PRODUCT_DESCRIPTION = "Comet product";
  private static final Double PRODUCT_PRICE = 10.7;
  private static final CategoryDto PRODUCT_CATEGORY =
      CategoryDto.builder().name("Comet products").build();

  @Autowired private ObjectMapper objectMapper;

  @Autowired private MockMvc mockMvc;

  @MockitoSpyBean private ProductServiceImplementation productService;

  private WireMockServer wireMockServer;

  @BeforeEach
  void setUp() {
    wireMockServer = new WireMockServer(8089);
    wireMockServer.start();
    configureFor("localhost", 8089);
    reset(productService);
  }

  @Test
  @SneakyThrows
  void createProduct() {
    ProductDto product = buildProduct();
    stubFor(
        WireMock.post("/api/v1/products")
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                    .withBody(objectMapper.writeValueAsBytes(product))));

    mockMvc
        .perform(
            post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$.name").value(PRODUCT_NAME))
        .andExpect(jsonPath("$.description").value(PRODUCT_DESCRIPTION))
        .andExpect(jsonPath("$.price").value(PRODUCT_PRICE));
  }

  @Test
  @SneakyThrows
  void getAllProducts() {
    ProductDto product = buildProduct();
    stubFor(
        WireMock.get("/api/v1/products")
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                    .withBody(objectMapper.writeValueAsBytes(List.of(product)))));
    mockMvc
        .perform(get("/api/v1/products").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].name").value(PRODUCT_NAME))
        .andExpect(jsonPath("$[0].price").value(PRODUCT_DESCRIPTION));

    verify(productService, times(1)).getAllProducts();
  }

  @Test
  @SneakyThrows
  void getProductById() {
    ProductDto product = buildProduct();
    stubFor(
        WireMock.get("/api/v1/products/1")
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                    .withBody(objectMapper.writeValueAsBytes(product))));
    mockMvc
        .perform(get("/api/v1/products/{id}", 1L).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value(PRODUCT_NAME))
        .andExpect(jsonPath("$.price").value(PRODUCT_DESCRIPTION));

    verify(productService, times(1)).getProductById(1L);
  }

  @Test
  @SneakyThrows
  void updateProduct() {
    ProductDto updatedProduct =
        ProductDto.builder()
            .name("Updated Comet product")
            .description("Updated description")
            .price(30.0)
            .category(PRODUCT_CATEGORY)
            .build();

    stubFor(
        WireMock.put("/api/v1/products/1")
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                    .withBody(objectMapper.writeValueAsBytes(updatedProduct))));

    mockMvc
        .perform(
            put("/api/v1/products/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedProduct)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Updated Comet product"))
        .andExpect(jsonPath("$.price").value(30.0))
        .andExpect(jsonPath("$.description").value("Updated description"));

    verify(productService, times(1)).updateProduct(eq(1L), any());
  }

  @Test
  @SneakyThrows
  void deleteProduct() {
    stubFor(WireMock.delete("/api/v1/products/1").willReturn(aResponse().withStatus(204)));

    mockMvc.perform(delete("/api/v1/products/{id}", 1L)).andExpect(status().isNoContent());

    verify(productService, times(1)).deleteProduct(1L);
  }

  private static ProductDto buildProduct() {
    return ProductDto.builder()
        .name(PRODUCT_NAME)
        .description(PRODUCT_DESCRIPTION)
        .price(PRODUCT_PRICE)
        .category(PRODUCT_CATEGORY)
        .build();
  }
}
