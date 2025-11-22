package org.kpi.lab1.service.mapper;

import java.util.List;
import org.kpi.lab1.domain.ProductItem;
import org.kpi.lab1.domain.order.Order;
import org.kpi.lab1.dto.order.OrderDto;
import org.kpi.lab1.repository.entity.OrderEntity;
import org.kpi.lab1.repository.entity.ProductItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(
        componentModel = "spring",
        uses = { ProductItemMapper.class }
)
public interface OrderMapper extends ProductItemMapper {

    @Mapping(target = "total", source = "total")
    @Mapping(target = "items", source = "items", qualifiedByName = "toProductItemEntityList")
    OrderEntity toOrderEntity(Order order);

    @Mapping(target = "total", source = "total")
    @Mapping(target = "items", source = "items", qualifiedByName = "toProductItemList")
    Order toOrder(OrderEntity order);

    List<Order> toOrders(List<OrderEntity> orders);
    List<OrderDto> toOrdersDto(List<Order> orders);
}
