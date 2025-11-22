package org.kpi.lab1.service.mapper;

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
public interface OrderMapper extends ProductItemMapper {



    @Mapping(target = "total", source = "total")
    @Mapping(target = "items", source = "items", qualifiedByName = "toProductItemEntityList")
    OrderEntity toOrderEntity(Order order);

    @Named("toProductItemEntityList")
    default List<ProductItemEntity> toProductItemEntityList(List<ProductItem> productItems) {
        return productItems.stream().map(this::toProductItemEntity).toList();
    }

    @Mapping(target = "total", source = "total")
    @Mapping(target = "items", source = "items", qualifiedByName = "toProductItemList")
    Order toOrder(OrderEntity order);

    @Named("toProductItemList")
    default  List<ProductItem> toProductItemList(List<ProductItemEntity> productItemEntities) {
        return productItemEntities.stream().map(this::toProductItem).toList();
    }

    List<Order> toOrders(List<OrderEntity> orders);
    List<OrderDto> toOrdersDto(List<Order> orders);
}
