package org.kpi.lab1.web;

import jakarta.validation.Valid;
import org.kpi.lab1.domain.product.Product;
import org.kpi.lab1.dto.product.ProductDto;
import org.kpi.lab1.service.ProductService;
import org.kpi.lab1.web.mapper.ProductDtoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

  private final ProductService productService;
  private final ProductDtoMapper productDtoMapper;

  public ProductController(ProductService productService, ProductDtoMapper productDtoMapper) {
    this.productService = productService;
    this.productDtoMapper = productDtoMapper;
  }

  @PostMapping
  public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
    Product product = productDtoMapper.toProduct(productDto);
    Product created = productService.addProduct(product);
    return new ResponseEntity<>(productDtoMapper.toProductDto(created), HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<ProductDto>> getAllProducts() {
    List<ProductDto> dtos = productService.getAllProducts().stream()
            .map(productDtoMapper::toProductDto)
            .toList();
    return ResponseEntity.ok(dtos);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
    Product product = productService.getProductById(id);
    if (product == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(productDtoMapper.toProductDto(product));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProductDto> updateProduct(
          @PathVariable Long id, @Valid @RequestBody ProductDto productDto) {
    Product updated = productService.updateProduct(id, productDtoMapper.toProduct(productDto));
    if (updated == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(productDtoMapper.toProductDto(updated));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProductById(@PathVariable Long id) {
    productService.deleteProduct(id);
    return ResponseEntity.noContent().build();
  }
}
