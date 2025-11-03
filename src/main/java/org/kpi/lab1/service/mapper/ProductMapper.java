package org.kpi.lab1.service.mapper;

import java.util.List;
import org.kpi.lab1.domain.category.Category;
import org.kpi.lab1.domain.product.Product;
import org.kpi.lab1.dto.category.CategoryDto;
import org.kpi.lab1.dto.product.ProductDto;
import org.kpi.lab1.dto.product.ProductListDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ProductMapper {

  @Mapping(target = "name", source = "name")
  @Mapping(target = "description", source = "description")
  @Mapping(target = "price", source = "price")
  @Mapping(target = "rating", source = "rating")
  @Mapping(target = "category", source = "category", qualifiedByName = "toCategory")
  ProductDto toProductDto(Product product);

  default ProductListDto toProductListDto(List<Product> products) {
    return ProductListDto.builder().products(toProductsDto(products)).build();
  }

  List<ProductDto> toProductsDto(List<Product> products);

  @Named("toCategory")
  default CategoryDto toCategoryDto(Category category) {
    return null;
  }

  default Product toProduct(Product product, long newId, double rating) {
    Long id = product.getId() == null ? newId : product.getId();
    return Product.builder()
        .id(id)
        .name(product.getName())
        .description(product.getDescription())
        .price(product.getPrice())
        .rating(rating)
        .category(product.getCategory())
        .build();
  }
}
