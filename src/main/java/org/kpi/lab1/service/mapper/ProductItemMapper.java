package org.kpi.lab1.service.mapper;


import org.kpi.lab1.domain.ProductItem;
import org.kpi.lab1.domain.category.Category;
import org.kpi.lab1.domain.product.Product;
import org.kpi.lab1.dto.category.CategoryDto;
import org.kpi.lab1.dto.product.ProductDto;
import org.kpi.lab1.dto.product.ProductItemDto;
import org.kpi.lab1.repository.entity.CategoryEntity;
import org.kpi.lab1.repository.entity.ProductEntity;
import org.kpi.lab1.repository.entity.ProductItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductItemMapper {

    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "product", source = "product", qualifiedByName = "toProductDto")
    ProductItemDto toProductItemDto(ProductItem productItem);

    @Named("toProductDto")
    default ProductDto toProductDto(Product product) {
        Category category = product.getCategory();
        return ProductDto.builder()
            .name(product.getName())
            .description(product.getDescription())
            .price(product.getPrice())
            .rating(product.getRating())
            .category(
                CategoryDto.builder()
                    .name(category.getName())
                    .description(category.getDescription())
                    .build())
            .build();
    }

    List<ProductItemEntity> toProductItemsEntity(List<ProductItem> productItems);

    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "product", source = "product", qualifiedByName = "toProduct")
    ProductItem toProductItem(ProductItemEntity productItemEntity);

    @Named("toProduct")
    default Product toProduct(ProductEntity productEntity) {
        CategoryEntity category = productEntity.getCategory();
        return Product.builder()
            .id(productEntity.getId())
            .name(productEntity.getName())
            .description(productEntity.getDescription())
            .price(productEntity.getPrice())
            .rating(productEntity.getRating())
            .category(
                Category.builder()
                    .name(category.getName())
                    .description(category.getDescription())
                    .build())
            .build();
    }

    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "product", source = "product", qualifiedByName = "toProductEntity")
    ProductItemEntity toProductItemEntity(ProductItem productItem);

    @Named("toProductEntity")
    default ProductEntity toProductEntity(Product product) {
        Category category = product.getCategory();
        return ProductEntity.builder()
            .name(product.getName())
            .description(product.getDescription())
            .price(product.getPrice())
            .rating(product.getRating())
            .category(
                CategoryEntity.builder()
                    .name(category.getName())
                    .description(category.getDescription())
                    .build())
            .build();
    }

    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "product", source = "product", qualifiedByName = "toProductFromDto")
    ProductItem toProductItemFromDto(ProductItemDto productItemDto);

    @Named("toProductFromDto")
    default Product toProductFromDto(ProductDto productDto) {
        CategoryDto categoryDto = productDto.getCategory();
        return Product.builder()
            .name(productDto.getName())
            .description(productDto.getDescription())
            .price(productDto.getPrice())
            .rating(productDto.getRating())
            .category(
                Category.builder()
                    .name(categoryDto.getName())
                    .description(categoryDto.getDescription())
                    .build())
            .build();
    }

}
