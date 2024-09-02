package com.icbt.ABC_Rest.service;

import com.icbt.ABC_Rest.dto.OrderDto;
import com.icbt.ABC_Rest.dto.OrderDto.OrderDetailsDto;
import com.icbt.ABC_Rest.entity.Order;
import com.icbt.ABC_Rest.entity.OrderDetails;
import com.icbt.ABC_Rest.entity.User;
import com.icbt.ABC_Rest.repo.ItemRepo;
import com.icbt.ABC_Rest.repo.OrderRepo;
import com.icbt.ABC_Rest.repo.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepo orderRepo;
    private final UserRepo userRepo;
    private final ItemRepo itemRepo;
    private final ModelMapper modelMapper;

    // Constructor Injection
    public OrderService(OrderRepo orderRepo, UserRepo userRepo, ItemRepo itemRepo, ModelMapper modelMapper) {
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
        this.itemRepo = itemRepo;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepo.findAll();
        return orders.stream().map(this::convertOrderToDto).collect(Collectors.toList());
    }

    @Transactional
    public List<OrderDto> getNewOrders() {
        List<Order> newOrders = orderRepo.findByStatus(Order.OrderStatus.PENDING);
        return newOrders.stream().map(this::convertOrderToDto).collect(Collectors.toList());
    }

    @Transactional
    public OrderDto getOrderById(Long id) {
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        return convertOrderToDto(order);
    }

    @Transactional
    public List<OrderDto> getOrdersByUserEmail(String email) {
        List<Order> userOrders = orderRepo.findByUser_Email(email);
        return userOrders.stream().map(this::convertOrderToDto).collect(Collectors.toList());
    }

    @Transactional
    public OrderDto createOrder(OrderDto orderDto) {
        // Find the user by email
        User user = userRepo.findByEmail(orderDto.getUserEmail());
        if (user == null) {
            throw new RuntimeException("User with email " + orderDto.getUserEmail() + " not found");
        }

        // Create the Order object and set its basic properties
        Order order = new Order();
        order.setUser(user);
        order.setStartDate(orderDto.getStartDate());
        order.setEndDate(orderDto.getEndDate());
        order.setOrderDate(orderDto.getOrderDate());
        order.setStatus(Order.OrderStatus.valueOf(orderDto.getStatus()));
        order.setType(Order.OrderType.valueOf(orderDto.getType()));
        order.setTotalAmount(orderDto.getTotalAmount());

        // Initialize and set the OrderDetails list
        List<OrderDetails> orderDetailsList = orderDto.getOrderDetails().stream()
                .map(orderDetailsDto -> convertOrderDetailsDtoToEntity(orderDetailsDto, order))
                .collect(Collectors.toList());

        // Associate the order with its details
        order.setOrderDetails(orderDetailsList);

        // Save the order to the database
        Order savedOrder = orderRepo.save(order);

        // Convert the saved order to DTO and return it
        return convertOrderToDto(savedOrder);
    }

    @Transactional
    public OrderDto updateOrder(Long id, OrderDto orderDto) {
        Order existingOrder = orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        User user = userRepo.findByEmail(orderDto.getUserEmail());
        if (user == null) {
            throw new RuntimeException("User with email " + orderDto.getUserEmail() + " not found");
        }

        existingOrder.setUser(user);
        existingOrder.setStartDate(orderDto.getStartDate());
        existingOrder.setEndDate(orderDto.getEndDate());
        existingOrder.setOrderDate(orderDto.getOrderDate());
        existingOrder.setTotalAmount(orderDto.getTotalAmount());
        existingOrder.setStatus(Order.OrderStatus.valueOf(orderDto.getStatus()));
        existingOrder.setType(Order.OrderType.valueOf(orderDto.getType()));

        // Update the order details
        List<OrderDetails> orderDetailsList = orderDto.getOrderDetails().stream()
                .map(orderDetailsDto -> convertOrderDetailsDtoToEntity(orderDetailsDto, existingOrder))
                .collect(Collectors.toList());

        existingOrder.setOrderDetails(orderDetailsList);

        // Save the updated order
        Order updatedOrder = orderRepo.save(existingOrder);

        return convertOrderToDto(updatedOrder);
    }

    @Transactional
    public void deleteOrder(Long id) {
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        orderRepo.delete(order);
    }

    @Transactional
    public List<OrderDto> getOrdersByStatus(String status) {
        List<Order> orders = orderRepo.findByStatus(Order.OrderStatus.valueOf(status));
        return orders.stream().map(this::convertOrderToDto).collect(Collectors.toList());
    }

    @Transactional
    public List<OrderDto> getOrdersByType(String type) {
        List<Order> orders = orderRepo.findByType(Order.OrderType.valueOf(type));
        return orders.stream().map(this::convertOrderToDto).collect(Collectors.toList());
    }

    @Transactional
    public List<OrderDto> getActiveOrders(Date currentDate) {
        List<Order> orders = orderRepo.findByStartDateBeforeAndEndDateAfter(currentDate, currentDate);
        return orders.stream().map(this::convertOrderToDto).collect(Collectors.toList());
    }

    // New method to update order status (confirm/reject)
    @Transactional
    public OrderDto updateOrderStatus(Long id, String status) {
        Order existingOrder = orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        existingOrder.setStatus(Order.OrderStatus.valueOf(status));

        // Save the updated order status
        Order updatedOrder = orderRepo.save(existingOrder);

        return convertOrderToDto(updatedOrder);
    }

    private OrderDto convertOrderToDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }

    private OrderDetails convertOrderDetailsDtoToEntity(OrderDetailsDto orderDetailsDto, Order order) {
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setOrder(order);
        orderDetails.setItem(itemRepo.findById(orderDetailsDto.getItemId())
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + orderDetailsDto.getItemId())));
        orderDetails.setQuantity(orderDetailsDto.getQuantity());
        orderDetails.setPrice(orderDetailsDto.getPrice());
        return orderDetails;
    }
}
