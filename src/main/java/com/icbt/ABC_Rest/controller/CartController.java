package com.icbt.ABC_Rest.controller;

import com.icbt.ABC_Rest.entity.Cart;
import com.icbt.ABC_Rest.entity.Order;
import com.icbt.ABC_Rest.entity.Payment;
import com.icbt.ABC_Rest.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<Cart> addItemToCart(@RequestParam String userEmail, @RequestParam Long itemId,
                                              @RequestParam int quantity) {
        Cart cart = cartService.addItemToCart(userEmail, itemId, quantity);
        return ResponseEntity.ok(cart);
    }

    @GetMapping("/view")
    public ResponseEntity<Cart> viewCart(@RequestParam String userEmail) {
        Cart cart = cartService.getOrCreateCart(userEmail);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Cart> removeItemFromCart(@RequestParam String userEmail, @RequestParam Long cartItemId) {
        Cart cart = cartService.removeItemFromCart(userEmail, cartItemId);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Cart> clearCart(@RequestParam String userEmail) {
        Cart cart = cartService.clearCart(userEmail);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/checkout")
    public ResponseEntity<Order> checkout(@RequestParam String userEmail, @RequestParam Payment.PaymentMethod paymentMethod) {
        Order order = cartService.checkout(userEmail, paymentMethod);
        return ResponseEntity.ok(order);
    }
}
