package org.kpi.lab1.service;

import org.kpi.lab1.domain.ProductItem;
import org.kpi.lab1.domain.order.Order;
import org.kpi.lab1.dto.product.ProductItemDto;
import org.kpi.lab1.dto.product.ProductItemListDto;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();
    Order getOrderById(Long id);
    Order addOrder(ProductItemListDto productItemsDto);
    void deleteOrder(Long id);
}
