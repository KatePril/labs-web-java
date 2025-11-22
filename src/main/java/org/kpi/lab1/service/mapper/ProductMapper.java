package org.kpi.lab1.service.mapper;

import java.util.List;
import org.kpi.lab1.domain.category.Category;
import org.kpi.lab1.domain.product.Product;
import org.kpi.lab1.dto.category.CategoryDto;
import org.kpi.lab1.dto.product.ProductDto;
import org.kpi.lab1.dto.product.ProductListDto;
import org.kpi.lab1.repository.entity.CategoryEntity;
import org.kpi.lab1.repository.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ProductMapper {

  @Mapping(target = "name", source = "name")
  @Mapping(target = "description", source = "description")
  @Mapping(target = "price", source = "price")
  @Mapping(target = "rating", source = "rating")
  @Mapping(target = "category", source = "category", qualifiedByName = "toCategoryDto")
  ProductDto toProductDto(Product product);

  @Named("toCategoryDto")
  default CategoryDto toCategoryDto(Category category) {
    return CategoryDto.builder()
            .name(category.getName())
            .description(category.getDescription())
            .build();
  }

  default ProductListDto toProductListDto(List<Product> products) {
    return ProductListDto.builder().products(toProductsDto(products)).build();
  }

  List<ProductDto> toProductsDto(List<Product> products);

  List<Product> toProducts(List<ProductEntity> products);

  @Mapping(target = "name", source = "name")
  @Mapping(target = "description", source = "description")
  @Mapping(target = "price", source = "price")
  @Mapping(target = "rating", source = "rating")
  @Mapping(target = "category", source = "category", qualifiedByName = "toCategory")
  Product toProduct(ProductEntity product);

  @Named("toCategory")
  default Category toCategory(CategoryEntity category) {
    return Category.builder()
            .name(category.getName())
            .description(category.getDescription())
            .build();
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

  ProductEntity toProductEntity(ProductDto productDto);

}
