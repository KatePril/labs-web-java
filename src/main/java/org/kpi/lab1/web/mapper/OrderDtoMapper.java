package org.kpi.lab1.web.mapper;

import org.kpi.lab1.domain.ProductItem;
import org.kpi.lab1.domain.order.Order;
import org.kpi.lab1.dto.order.OrderDto;
import org.kpi.lab1.dto.product.ProductItemDto;
import org.kpi.lab1.repository.entity.OrderEntity;
import org.kpi.lab1.repository.entity.ProductItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderDtoMapper extends ProductItemDtoMapper {
    @Mapping(target = "total", source = "total")
    @Mapping(target = "items", source = "items", qualifiedByName = "toProductItemDtoList")
    OrderDto toOrderDto(Order order);

    @Named("toProductItemDtoList")
    default List<ProductItemDto> toProductItemDtoList(List<ProductItem> productItems) {
        return productItems.stream().map(this::toProductItemDto).toList();
    }

    @Mapping(target = "total", source = "total")
    @Mapping(target = "items", source = "items", qualifiedByName = "toProductItemDtoListFromEntity")
    OrderDto toOrderDtoFromEntity(Order orderEntity);

    @Named("toProductItemDtoListFromEntity")
    default List<ProductItemDto> toProductItemDtoListFromEntity(List<ProductItemEntity> productItemEntities) {
        return productItemEntities.stream().map(this::toProductItemDtoFromEntity).toList();
    }


    List<OrderDto> toOrdersDto(List<Order> orders);

}
