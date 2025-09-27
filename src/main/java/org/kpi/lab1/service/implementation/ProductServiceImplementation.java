package org.kpi.lab1.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.kpi.lab1.domain.product.Product;
import org.kpi.lab1.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProductServiceImplementation implements ProductService {
    private final List<Product> products = buildProductsMock();

    @Override
    public List<Product> getAllProducts() {
        return products;
    }

    @Override
    public Product getProductById(Long id) {
        return products.stream().filter(product -> product.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public Product addProduct(Product product) {
        return null;
    }

    @Override
    public Product updateProduct(Product product) {
        return null;
    }

    @Override
    public void deleteProduct(Long id) {

    }

    private List<Product> buildProductsMock() {
        return List.of();
    };
}
