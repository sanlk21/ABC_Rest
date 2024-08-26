package com.icbt.ABC_Rest.service;

import com.icbt.ABC_Rest.entity.*;
import com.icbt.ABC_Rest.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private CartItemRepo cartItemRepo;

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private PaymentRepo paymentRepo;

    public Cart getOrCreateCart(String userEmail) {
        Optional<Cart> cartOptional = cartRepo.findByUserEmail(userEmail);
        return cartOptional.orElseGet(() -> createNewCart(userEmail));
    }

    private Cart createNewCart(String userEmail) {
        User user = userRepo.findByEmail(userEmail);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setTotalPrice(0.0);
        return cartRepo.save(cart);
    }

    public Cart addItemToCart(String userEmail, Long itemId, int quantity) {
        Cart cart = getOrCreateCart(userEmail);

        Item item = itemRepo.findById(itemId).orElse(null);
        if (item == null) {
            throw new RuntimeException("Item not found");
        }

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setItem(item);
        cartItem.setQuantity(quantity);
        cartItem.setPrice(item.getPrice() * quantity);

        cart.getCartItems().add(cartItem);
        cart.setTotalPrice(cart.getTotalPrice() + cartItem.getPrice());

        cartItemRepo.save(cartItem);
        return cartRepo.save(cart);
    }

    public Cart removeItemFromCart(String userEmail, Long cartItemId) {
        Cart cart = getOrCreateCart(userEmail);

        CartItem cartItem = cartItemRepo.findById(cartItemId).orElse(null);
        if (cartItem == null) {
            throw new RuntimeException("CartItem not found");
        }

        cart.getCartItems().remove(cartItem);
        cart.setTotalPrice(cart.getTotalPrice() - cartItem.getPrice());

        cartItemRepo.delete(cartItem);

        return cartRepo.save(cart);
    }

    public Cart clearCart(String userEmail) {
        Cart cart = getOrCreateCart(userEmail);
        cartItemRepo.deleteAll(cart.getCartItems());
        cart.getCartItems().clear();
        cart.setTotalPrice(0.0);
        return cartRepo.save(cart);
    }

    public Order checkout(String userEmail, Payment.PaymentMethod paymentMethod) {
        Cart cart = getOrCreateCart(userEmail);

        if (cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty. Cannot proceed with checkout.");
        }

        // Create an order from the cart
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setTotalAmount(cart.getTotalPrice());
        order.setOrderDate(new Date());
        order.setStartDate(new Date());
        order.setEndDate(new Date());  // Assuming endDate is the same as startDate
        order.setStatus(Order.OrderStatus.PENDING);  // Initial status
        order.setType(Order.OrderType.DELIVERY);  // Assuming default type is DELIVERY

        List<OrderDetails> orderDetailsList = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()) {
            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setOrder(order);
            orderDetails.setItem(cartItem.getItem());
            orderDetails.setQuantity(cartItem.getQuantity());
            orderDetails.setPrice(cartItem.getPrice());

            orderDetailsList.add(orderDetails);
        }
        order.setOrderDetails(orderDetailsList);

        orderRepo.save(order);  // Save the order

        // Create a payment for the order
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setUser(order.getUser());
        payment.setAmount(order.getTotalAmount());
        payment.setPaymentMethod(paymentMethod);
        payment.setStatus(Payment.PaymentStatus.PENDING);  // Initial payment status
        payment.setPaymentDate(new Date());

        paymentRepo.save(payment);  // Save the payment

        // Clear the cart after checkout
        clearCart(userEmail);

        return order;
    }
}
