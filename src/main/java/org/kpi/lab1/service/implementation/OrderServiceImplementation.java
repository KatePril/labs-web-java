package org.kpi.lab1.service.implementation;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kpi.lab1.domain.order.Order;
import org.kpi.lab1.dto.product.ProductItemDto;
import org.kpi.lab1.dto.product.ProductItemListDto;
import org.kpi.lab1.repository.OrderRepository;
import org.kpi.lab1.repository.entity.OrderEntity;
import org.kpi.lab1.service.OrderService;
import org.kpi.lab1.service.mapper.OrderMapper;
import org.kpi.lab1.service.mapper.ProductItemMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImplementation implements OrderService {

  private final OrderRepository orderRepository;
  private final OrderMapper orderMapper;
  private final ProductItemMapper productItemMapper;

  @Override
  @Transactional(readOnly = true)
  public List<Order> getAllOrders() {
    return orderMapper.toOrders(orderRepository.findAll());
  }

  @Override
  @Transactional(readOnly = true)
  public Order getOrderById(Long id) {
    OrderEntity orderEntity =
        orderRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Order not with id: " + id));
    return orderMapper.toOrder(orderEntity);
  }

  @Override
  @Transactional(propagation = Propagation.NESTED)
  public Order addOrder(ProductItemListDto productItems) {
    double total = 0;
    for (ProductItemDto productItem : productItems.getProductItems()) {
      total += productItem.getQuantity() * productItem.getProduct().getPrice();
    }

    Order order =
        Order.builder()
            .items(
                productItems.getProductItems().stream()
                    .map(productItemMapper::toProductItemFromDto)
                    .toList())
            .total(total)
            .build();
    return orderMapper.toOrder(orderRepository.save(orderMapper.toOrderEntity(order)));
  }

  @Override
  @Transactional
  public void deleteOrder(Long id) {
    try {
      orderRepository.deleteById(id);
    } catch (Exception e) {
      log.error(e.getMessage());
    }
  }
}
