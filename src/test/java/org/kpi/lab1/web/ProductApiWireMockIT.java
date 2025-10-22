package org.kpi.lab1.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.kpi.lab1.dto.category.CategoryDto;
import org.kpi.lab1.dto.product.ProductDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;

class ProductApiWireMockIT {
  @RegisterExtension
  static WireMockExtension wireMockServer =
      WireMockExtension.newInstance().options(wireMockConfig().dynamicPort()).build();

  private final ObjectMapper objectMapper = new ObjectMapper();
  private RestTemplate restTemplate;
  private String baseUrl;

  @BeforeEach
  void setup() {
    wireMockServer.resetAll();
    baseUrl = "http://localhost:" + wireMockServer.getPort();
    restTemplate = new RestTemplate();
  }

  @Test
  void testGetProductById() throws Exception {
    ProductDto dto =
        ProductDto.builder()
            .name("Comet product")
            .description("Comet product")
            .price(10.7)
            .category(CategoryDto.builder().name("Comet products").build())
            .build();
    String jsonBody = objectMapper.writeValueAsString(dto);

    wireMockServer.stubFor(
        get(urlEqualTo("/api/v1/products/1"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .withBody(jsonBody)));

    ProductDto result = restTemplate.getForObject(baseUrl + "/api/v1/products/1", ProductDto.class);
    assertThat(result).isNotNull();
    assertThat(result.getName()).isEqualTo("Comet product");
    assertThat(result.getPrice()).isEqualTo(10.7);
    wireMockServer.verify(getRequestedFor(urlEqualTo("/api/v1/products/1")));
  }

  @Test
  void testGetAllProducts() throws Exception {
    List<ProductDto> products =
        List.of(
            ProductDto.builder()
                .name("Prod A")
                .price(12.3)
                .category(CategoryDto.builder().name("A").build())
                .build(),
            ProductDto.builder()
                .name("Prod B")
                .price(9.9)
                .category(CategoryDto.builder().name("B").build())
                .build());

    String json = objectMapper.writeValueAsString(products);

    wireMockServer.stubFor(
        get(urlEqualTo("/api/v1/products"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .withBody(json)));

    String response = restTemplate.getForObject(baseUrl + "/api/v1/products", String.class);
    List<ProductDto> result = objectMapper.readValue(response, new TypeReference<>() {});

    assertThat(result).hasSize(2);
    assertThat(result.get(0).getName()).isEqualTo("Prod A");

    wireMockServer.verify(getRequestedFor(urlEqualTo("/api/v1/products")));
  }

  @Test
  void testCreateProduct() throws Exception {
    ProductDto dto =
        ProductDto.builder()
            .name("New product")
            .description("Cool new product")
            .price(15.0)
            .category(CategoryDto.builder().name("Cool stuff").build())
            .build();
    String json = objectMapper.writeValueAsString(dto);

    wireMockServer.stubFor(
        post(urlEqualTo("/api/v1/products"))
            .withRequestBody(equalToJson(json))
            .willReturn(
                aResponse()
                    .withStatus(201)
                    .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .withBody(json)));

    ProductDto created =
        restTemplate.postForObject(baseUrl + "/api/v1/products", dto, ProductDto.class);
    assertThat(created).isNotNull();
    assertThat(created.getName()).isEqualTo("New product");
    wireMockServer.verify(postRequestedFor(urlEqualTo("/api/v1/products")));
  }

  @Test
  void testUpdateProduct() throws Exception {
    ProductDto updated =
        ProductDto.builder()
            .name("Updated name")
            .description("Updated description")
            .price(20.5)
            .category(CategoryDto.builder().name("Updated cat").build())
            .build();

    String json = objectMapper.writeValueAsString(updated);

    wireMockServer.stubFor(
        put(urlEqualTo("/api/v1/products/1"))
            .withRequestBody(equalToJson(json))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .withBody(json)));

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<ProductDto> request = new HttpEntity<>(updated, headers);

    ProductDto response =
        restTemplate
            .exchange(baseUrl + "/api/v1/products/1", HttpMethod.PUT, request, ProductDto.class)
            .getBody();

    assertThat(response).isNotNull();
    assertThat(response.getName()).isEqualTo("Updated name");

    wireMockServer.verify(putRequestedFor(urlEqualTo("/api/v1/products/1")));
  }

  @Test
  void testDeleteProduct() {
    wireMockServer.stubFor(
        delete(urlEqualTo("/api/v1/products/1")).willReturn(aResponse().withStatus(204)));

    restTemplate.delete(baseUrl + "/api/v1/products/1");

    wireMockServer.verify(deleteRequestedFor(urlEqualTo("/api/v1/products/1")));
  }
}
