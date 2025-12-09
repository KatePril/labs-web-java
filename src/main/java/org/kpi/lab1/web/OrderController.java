package org.kpi.lab1.web;

import jakarta.validation.Valid;
import java.util.List;

import org.kpi.lab1.domain.order.Order;
import org.kpi.lab1.dto.order.OrderDto;
import org.kpi.lab1.dto.product.ProductItemListDto;
import org.kpi.lab1.service.OrderService;
import org.kpi.lab1.web.mapper.OrderDtoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/orders")
public class OrderController {
  private final OrderService orderService;
  private final OrderDtoMapper orderMapper;

  public OrderController(OrderService orderService, OrderDtoMapper orderMapper) {
    this.orderService = orderService;
    this.orderMapper = orderMapper;
  }

  @PostMapping
  public ResponseEntity<OrderDto> createOrder(
      @Valid @RequestBody ProductItemListDto productItemListDto) {
    Order created = orderService.addOrder(productItemListDto);
    return new ResponseEntity<>(orderMapper.toOrderDto(created), HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<OrderDto>> getAllOrders() {
    List<OrderDto> orders = orderMapper.toOrdersDto(orderService.getAllOrders());
    return new ResponseEntity<>(orders, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<OrderDto> getOrder(@PathVariable Long id) {
    Order order = orderService.getOrderById(id);
    if (order == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(orderMapper.toOrderDto(order), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteOrderById(@PathVariable Long id) {
    orderService.deleteOrder(id);
    return ResponseEntity.noContent().build();
  }
}
