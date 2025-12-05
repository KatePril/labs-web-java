package org.kpi.lab1.web.mapper;

import java.util.List;
import org.kpi.lab1.domain.order.Order;
import org.kpi.lab1.dto.order.OrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = {ProductItemDtoMapper.class})
public interface OrderDtoMapper {
  @Mapping(target = "total", source = "total")
  @Mapping(target = "items", source = "items", qualifiedByName = "toProductItemDtoList")
  OrderDto toOrderDto(Order order);

  List<OrderDto> toOrdersDto(List<Order> orders);
}
