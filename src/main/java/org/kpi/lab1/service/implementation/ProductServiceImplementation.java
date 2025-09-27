package org.kpi.lab1.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.kpi.lab1.domain.category.Category;
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
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst().orElse(null);
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
        Category category = Category.builder().name("School supplies").build();
        Category category1 = Category.builder().name("Clothes").build();
        return List.of(
            Product.builder()
                .id(1L)
                .name("Book")
                .description("An interesting one")
                .price(10.4)
                .category(category)
                .build(),
            Product.builder()
                .id(2L)
                .name("T-shirt")
                .description("A comfortable shirt")
                .price(16.2)
                .category(category1)
                .build(),
            Product.builder()
                .id(3L)
                .name("Pencil")
                .price(5.3)
                .category(category)
                .build()
        );
    };
}
