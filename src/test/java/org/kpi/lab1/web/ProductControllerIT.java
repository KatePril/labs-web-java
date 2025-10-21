package org.kpi.lab1.web;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

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

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    mockMvc.perform(post("/api/v1/products")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(product)))
        .andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$.name").value(PRODUCT_NAME))
        .andExpect(jsonPath("$.description").value(PRODUCT_DESCRIPTION))
        .andExpect(jsonPath("$.price").value(PRODUCT_PRICE));
  }

//  @Test
//  @SneakyThrows
//  void getAllProducts() {
//
//  }

  private static ProductDto buildProduct() {
    return ProductDto.builder()
        .name(PRODUCT_NAME)
        .description(PRODUCT_DESCRIPTION)
        .price(PRODUCT_PRICE)
        .category(PRODUCT_CATEGORY)
        .build();
  }
}
