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

    // Get all orders
    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<OrderDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    // Get new orders
    @GetMapping("/new")
    public ResponseEntity<List<OrderDto>> getNewOrders() {
        List<OrderDto> newOrders = orderService.getNewOrders();
        return ResponseEntity.ok(newOrders);
    }

    // Get order by ID
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id) {
        OrderDto order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    // Get orders by user email
    @GetMapping("/user/{email}")
    public ResponseEntity<List<OrderDto>> getOrdersByUserEmail(@PathVariable String email) {
        List<OrderDto> userOrders = orderService.getOrdersByUserEmail(email);
        return ResponseEntity.ok(userOrders);
    }

    // Create a new order
    @PostMapping("/saveOrder")
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {
        OrderDto createdOrder = orderService.createOrder(orderDto);
        return ResponseEntity.ok(createdOrder);
    }

    // Update an order
    @PutMapping("/{id}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long id, @RequestBody OrderDto orderDto) {
        OrderDto updatedOrder = orderService.updateOrder(id, orderDto);
        return ResponseEntity.ok(updatedOrder);
    }

    // Delete an order
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    
    @PutMapping("/{id}/confirm")
    public ResponseEntity<OrderDto> confirmOrder(@PathVariable Long id) {
        OrderDto confirmedOrder = orderService.updateOrderStatus(id, "COMPLETED");
        return ResponseEntity.ok(confirmedOrder);
    }

    // Reject an order (Change status to CANCELLED)
    @PutMapping("/{id}/reject")
    public ResponseEntity<OrderDto> rejectOrder(@PathVariable Long id) {
        OrderDto rejectedOrder = orderService.updateOrderStatus(id, "CANCELLED");
        return ResponseEntity.ok(rejectedOrder);
    }

    // Get orders by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrderDto>> getOrdersByStatus(@PathVariable String status) {
        List<OrderDto> orders = orderService.getOrdersByStatus(status);
        return ResponseEntity.ok(orders);
    }

    // Get orders by type
    @GetMapping("/type/{type}")
    public ResponseEntity<List<OrderDto>> getOrdersByType(@PathVariable String type) {
        List<OrderDto> orders = orderService.getOrdersByType(type);
        return ResponseEntity.ok(orders);
    }

    // Get active orders (orders in progress)
    @GetMapping("/active")
    public ResponseEntity<List<OrderDto>> getActiveOrders() {
        Date currentDate = new Date();
        List<OrderDto> activeOrders = orderService.getActiveOrders(currentDate);
        return ResponseEntity.ok(activeOrders);
    }
}
