package org.kpi.lab1.service.mapper;

import org.kpi.lab1.domain.category.Category;
import org.kpi.lab1.domain.product.Product;
import org.kpi.lab1.dto.category.CategoryDto;
import org.kpi.lab1.dto.product.ProductDto;
import org.kpi.lab1.dto.product.ProductListDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

  @Mapping(target = "name", source = "name")
  @Mapping(target = "description", source = "description")
  @Mapping(target = "price", source = "price")
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
}
