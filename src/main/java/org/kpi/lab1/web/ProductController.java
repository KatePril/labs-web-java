package org.kpi.lab1.web;

import jakarta.validation.Valid;
import java.util.List;
import org.kpi.lab1.domain.product.Product;
import org.kpi.lab1.domain.product.ProductInfo;
import org.kpi.lab1.dto.product.ProductDto;
import org.kpi.lab1.dto.product.ProductInfoDto;
import org.kpi.lab1.dto.product.ProductItemDto;
import org.kpi.lab1.service.ProductService;
import org.kpi.lab1.web.mapper.ProductDtoMapper;
import org.kpi.lab1.web.mapper.ProductInfoDtoMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

  private final ProductService productService;
  private final ProductDtoMapper productDtoMapper;
  private final ProductInfoDtoMapper productInfoDtoMapper;

  public ProductController(ProductService productService, ProductDtoMapper productDtoMapper, ProductInfoDtoMapper productInfoDtoMapper) {
    this.productService = productService;
    this.productDtoMapper = productDtoMapper;
    this.productInfoDtoMapper = productInfoDtoMapper;
  }

  @PostMapping
  public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
    Product created = productService.addProduct(productDto);
    return new ResponseEntity<>(productDtoMapper.toProductDto(created), HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<ProductDto>> getAllProducts() {
    List<ProductDto> dtos =
        productService.getAllProducts().stream().map(productDtoMapper::toProductDto).toList();
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

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProductById(@PathVariable Long id) {
    productService.deleteProduct(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/by-category")
  public ResponseEntity<Page<ProductInfoDto>> getProductsByCategory(
          @RequestParam String category,
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "10") int size
  ) {
    Page<ProductInfo> products = productService.getProductByCategory(category, page, size);

    Page<ProductInfoDto> dtoPage = products.map(productInfoDtoMapper::toProductInfoDto);

    return ResponseEntity.ok(dtoPage);
  }

}
