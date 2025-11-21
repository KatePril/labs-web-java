package org.kpi.lab1.service;

import org.kpi.lab1.domain.ProductItem;
import org.kpi.lab1.domain.order.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();
    Order getOrderById(Long id);
    Order addOrder(List<ProductItem> productItems);
    void deleteOrder(Long id);
}
