package org.kpi.lab1.service.mapper;

import org.kpi.lab1.domain.ProductItem;
import org.kpi.lab1.domain.order.Order;
import org.kpi.lab1.dto.order.OrderDto;
import org.kpi.lab1.dto.product.ProductItemDto;
import org.kpi.lab1.repository.entity.ProductItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper extends ProductItemMapper {

    @Mapping(target = "total", source = "total")
    @Mapping(target = "items", source = "items", qualifiedByName = "toProductItemDtoList")
    OrderDto toOrderDto(Order order);

    @Named("toProductItemDtoList")
    default List<ProductItemDto> toOrderDtoList(List<ProductItem> orders) {
        return orders.stream().map(this::toProductItemDto).toList();
    }

    @Mapping(target = "total", source = "total")
    @Mapping(target = "items", source = "items", qualifiedByName = "toProductItemEntityList")
    Order toOrderEntity(OrderDto orderDto);

    @Named("toProductItemEntityList")
    default List<ProductItemEntity> toProductItemEntityList(List<ProductItemDto> productItemDtos) {
        return productItemDtos.stream().map(this::toProductItemEntity).toList();
    }

    @Mapping(target = "total", source = "total")
    @Mapping(target = "items", source = "items", qualifiedByName = "toProductItemList")
    Order toOrder(OrderDto orderDto);

    default  List<ProductItem> toProductItemList(List<ProductItemDto> productItemDtos) {
        return productItemDtos.stream().map(this::toProductItemFromDto).toList();
    }
}
