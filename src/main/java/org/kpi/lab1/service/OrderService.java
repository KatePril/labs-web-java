package org.kpi.lab1.service;

import java.util.List;
import org.kpi.lab1.domain.order.Order;
import org.kpi.lab1.dto.product.ProductItemListDto;

public interface OrderService {
  List<Order> getAllOrders();

  Order getOrderById(Long id);

  Order addOrder(ProductItemListDto productItemsDto);

  void deleteOrder(Long id);
}
