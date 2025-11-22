package org.kpi.lab1.web.mapper;

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
public interface ProductItemDtoMapper {

    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "product", source = "product", qualifiedByName = "toProductDtoFromEntity")
    ProductItemDto toProductItemDtoFromEntity(ProductItemEntity productItemEntity);

    @Named("toProductDtoFromEntity")
    default ProductDto toProductDtoFromEntity(ProductEntity product) {
        CategoryEntity category = product.getCategory();
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

    @Named("toProductItemDtoList")
    default List<ProductItemDto> toProductItemDtoList(List<ProductItem> productItems) {
        return productItems.stream().map(this::toProductItemDto).toList();
    }

    @Named("toProductItemDtoListFromEntity")
    default List<ProductItemDto> toProductItemDtoListFromEntity(List<ProductItemEntity> productItemEntities) {
        return productItemEntities.stream().map(this::toProductItemDtoFromEntity).toList();
    }
}
