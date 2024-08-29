package com.icbt.ABC_Rest.controller;

import com.icbt.ABC_Rest.dto.OrderDto;
import com.icbt.ABC_Rest.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/v1/orders")
@CrossOrigin(origins = "http://127.0.0.1:5501")  // Ensure this matches the frontend origin
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<OrderDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/new")
    public ResponseEntity<List<OrderDto>> getNewOrders() {
        List<OrderDto> newOrders = orderService.getNewOrders();
        return ResponseEntity.ok(newOrders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id) {
        OrderDto order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<List<OrderDto>> getOrdersByUserEmail(@PathVariable String email) {
        List<OrderDto> userOrders = orderService.getOrdersByUserEmail(email);
        return ResponseEntity.ok(userOrders);
    }

    @PostMapping("/saveOrder")
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {
        OrderDto createdOrder = orderService.createOrder(orderDto);
        return ResponseEntity.ok(createdOrder);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long id, @RequestBody OrderDto orderDto) {
        OrderDto updatedOrder = orderService.updateOrder(id, orderDto);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrderDto>> getOrdersByStatus(@PathVariable String status) {
        List<OrderDto> orders = orderService.getOrdersByStatus(status);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<OrderDto>> getOrdersByType(@PathVariable String type) {
        List<OrderDto> orders = orderService.getOrdersByType(type);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/active")
    public ResponseEntity<List<OrderDto>> getActiveOrders() {
        Date currentDate = new Date();
        List<OrderDto> activeOrders = orderService.getActiveOrders(currentDate);
        return ResponseEntity.ok(activeOrders);
    }

}
