package com.icbt.ABC_Rest.service;

import com.icbt.ABC_Rest.dto.OrderDto;
import com.icbt.ABC_Rest.dto.OrderDto.OrderDetailsDto;
import com.icbt.ABC_Rest.entity.*;
import com.icbt.ABC_Rest.repo.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private ModelMapper modelMapper;

    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepo.findAll();
        return orders.stream().map(this::convertOrderToDto).collect(Collectors.toList());
    }

    public List<OrderDto> getNewOrders() {
        List<Order> newOrders = orderRepo.findByStatus(Order.OrderStatus.PENDING);
        return newOrders.stream().map(this::convertOrderToDto).collect(Collectors.toList());
    }

    public OrderDto getOrderById(Long id) {
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        return convertOrderToDto(order);
    }

    public List<OrderDto> getOrdersByUserEmail(String email) {
        List<Order> userOrders = orderRepo.findByUser_Email(email);
        return userOrders.stream().map(this::convertOrderToDto).collect(Collectors.toList());
    }

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
                .map(orderDetailsDto -> {
                    // Find the item associated with this OrderDetails
                    Item item = itemRepo.findById(orderDetailsDto.getItemId())
                            .orElseThrow(() -> new RuntimeException("Item with ID " + orderDetailsDto.getItemId() + " not found"));

                    // Create the OrderDetails object
                    OrderDetails orderDetails = new OrderDetails();
                    orderDetails.setOrder(order);  // Set the order reference
                    orderDetails.setItem(item);
                    orderDetails.setQuantity(orderDetailsDto.getQuantity());
                    orderDetails.setPrice(orderDetailsDto.getPrice());

                    return orderDetails;
                }).collect(Collectors.toList());

        // Associate the order with its details
        order.setOrderDetails(orderDetailsList);

        // Save the order to the database
        Order savedOrder = orderRepo.save(order);

        // Convert the saved order to DTO and return it
        return convertOrderToDto(savedOrder);
    }

    public OrderDto updateOrder(Long id, OrderDto orderDto) {
        Order existingOrder = orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

        User user = userRepo.findByEmail(orderDto.getUserEmail());
        if (user == null) {
            throw new RuntimeException("User with email " + orderDto.getUserEmail() + " not found");
        }

        existingOrder.setUser(user);
        existingOrder.setOrderDetails(orderDto.getOrderDetails().stream()
                .map(this::convertOrderDetailsDtoToEntity).collect(Collectors.toList()));
        existingOrder.setOrderDate(orderDto.getOrderDate());
        existingOrder.setStartDate(orderDto.getStartDate());
        existingOrder.setEndDate(orderDto.getEndDate());
        existingOrder.setTotalAmount(orderDto.getTotalAmount());
        existingOrder.setStatus(Order.OrderStatus.valueOf(orderDto.getStatus()));
        existingOrder.setType(Order.OrderType.valueOf(orderDto.getType()));

        Order updatedOrder = orderRepo.save(existingOrder);
        return convertOrderToDto(updatedOrder);
    }

    public void deleteOrder(Long id) {
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        orderRepo.delete(order);
    }

    public List<OrderDto> getOrdersByStatus(String status) {
        List<Order> orders = orderRepo.findByStatus(Order.OrderStatus.valueOf(status));
        return orders.stream().map(this::convertOrderToDto).collect(Collectors.toList());
    }

    public List<OrderDto> getOrdersByType(String type) {
        List<Order> orders = orderRepo.findByType(Order.OrderType.valueOf(type));
        return orders.stream().map(this::convertOrderToDto).collect(Collectors.toList());
    }

    public List<OrderDto> getActiveOrders(Date currentDate) {
        List<Order> orders = orderRepo.findByStartDateBeforeAndEndDateAfter(currentDate, currentDate);
        return orders.stream().map(this::convertOrderToDto).collect(Collectors.toList());
    }

    private OrderDto convertOrderToDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }

    private OrderDetails convertOrderDetailsDtoToEntity(OrderDetailsDto orderDetailsDto) {
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setItem(itemRepo.findById(orderDetailsDto.getItemId())
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + orderDetailsDto.getItemId())));
        orderDetails.setQuantity(orderDetailsDto.getQuantity());
        orderDetails.setPrice(orderDetailsDto.getPrice());
        return orderDetails;
    }
}
