package com.icbt.ABC_Rest.service;

import com.icbt.ABC_Rest.entity.*;
import com.icbt.ABC_Rest.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Cart getOrCreateCart(String userEmail) {
        Optional<Cart> cartOptional = cartRepo.findByUserEmail(userEmail);
        if (cartOptional.isPresent()) {
            return cartOptional.get();
        } else {
            return createNewCart(userEmail);
        }
    }

    private Cart createNewCart(String userEmail) {
        User user = userRepo.findByEmail(userEmail);
        if (user == null) {
            // Log error or handle the situation as needed
            throw new RuntimeException("User not found for email: " + userEmail);
        }

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setTotalPrice(0.0);
        cart.setCartItems(new ArrayList<>());
        return cartRepo.save(cart);
    }

    @Transactional
    public Cart addItemToCart(String userEmail, Long itemId, int quantity) {
        Cart cart = getOrCreateCart(userEmail);

        Item item = itemRepo.findById(itemId).orElse(null);
        if (item == null) {
            // Log error or handle the situation as needed
            throw new RuntimeException("Item not found with ID: " + itemId);
        }

        Optional<CartItem> existingCartItem = cart.getCartItems().stream()
                .filter(ci -> ci.getItem().getId().equals(itemId))
                .findFirst();

        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem.setPrice(item.getPrice() * cartItem.getQuantity());
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setItem(item);
            cartItem.setQuantity(quantity);
            cartItem.setPrice(item.getPrice() * quantity);
            cart.getCartItems().add(cartItem);
        }

        cart.setTotalPrice(cart.getCartItems().stream().mapToDouble(CartItem::getPrice).sum());

        return cartRepo.save(cart);
    }

    @Transactional
    public Cart removeItemFromCart(String userEmail, Long cartItemId) {
        Cart cart = getOrCreateCart(userEmail);

        CartItem cartItem = cart.getCartItems().stream()
                .filter(ci -> ci.getId().equals(cartItemId))
                .findFirst()
                .orElse(null);

        if (cartItem == null) {
            // Log error or handle the situation as needed
            throw new RuntimeException("CartItem not found with ID: " + cartItemId);
        }

        cart.getCartItems().remove(cartItem);
        cart.setTotalPrice(cart.getCartItems().stream().mapToDouble(CartItem::getPrice).sum());

        cartItemRepo.delete(cartItem);

        return cartRepo.save(cart);
    }

    @Transactional
    public Cart clearCart(String userEmail) {
        Cart cart = getOrCreateCart(userEmail);
        cartItemRepo.deleteAll(cart.getCartItems());
        cart.getCartItems().clear();
        cart.setTotalPrice(0.0);
        return cartRepo.save(cart);
    }

    @Transactional
    public Order checkout(String userEmail, Payment.PaymentMethod paymentMethod) {
        Cart cart = getOrCreateCart(userEmail);

        if (cart.getCartItems().isEmpty()) {
            // Log error or handle the situation as needed
            throw new RuntimeException("Cart is empty. Cannot proceed with checkout.");
        }

        Order order = new Order();
        order.setUser(cart.getUser());
        order.setTotalAmount(cart.getTotalPrice());
        order.setOrderDate(new Date());
        order.setStatus(Order.OrderStatus.PENDING);
        order.setType(Order.OrderType.DELIVERY);

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

        orderRepo.save(order);

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setUser(order.getUser());
        payment.setAmount(order.getTotalAmount());
        payment.setPaymentMethod(paymentMethod);
        payment.setStatus(Payment.PaymentStatus.PENDING);
        payment.setPaymentDate(new Date());

        paymentRepo.save(payment);

        clearCart(userEmail);

        return order;
    }
}
