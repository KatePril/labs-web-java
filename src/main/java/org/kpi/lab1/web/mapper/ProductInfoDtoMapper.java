package org.kpi.lab1.web.mapper;

import org.kpi.lab1.domain.product.ProductInfo;
import org.kpi.lab1.dto.product.ProductInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductInfoDtoMapper {

    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "price", source = "price")
    ProductInfoDto toProductInfoDto(ProductInfo productInfo);
}
