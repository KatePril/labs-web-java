package org.kpi.lab1.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kpi.lab1.domain.product.Product;
import org.kpi.lab1.dto.product.ProductDto;
import org.kpi.lab1.repository.ProductRepository;
import org.kpi.lab1.repository.entity.ProductEntity;
import org.kpi.lab1.service.ProductService;
import org.kpi.lab1.service.RateService;
import org.kpi.lab1.service.mapper.ProductMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImplementation implements ProductService {
  private final RateService rateService;
  private final ProductMapper productMapper;
  private final ProductRepository productRepository;

  @Override
  @Transactional(readOnly = true)
  public List<Product> getAllProducts() {
    return productMapper.toProducts(productRepository.findAll());
  }

  @Override
  @Transactional(readOnly = true)
  public Product getProductById(Long id) {
    ProductEntity entity =
        productRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));

    return productMapper.toProduct(entity);
  }

  @Override
  @Transactional(propagation = Propagation.NESTED)
  public Product addProduct(ProductDto product) {
    try {
      return productMapper.toProduct(
          productRepository.save(productMapper.toProductEntity(product)));
    } catch (Exception e) {
      log.error("Exception occurred while saving customer details");
      throw new PersistenceException(e);
    }
  }

  @Override
  @Transactional
  public void deleteProduct(Long id) {
    try {
      productRepository.deleteById(id);
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }
}
