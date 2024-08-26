package com.icbt.ABC_Rest.controller;

import com.icbt.ABC_Rest.entity.Cart;
import com.icbt.ABC_Rest.entity.Order;
import com.icbt.ABC_Rest.entity.Payment;
import com.icbt.ABC_Rest.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "http://127.0.0.1:5501")  // Ensure this matches the frontend origin

public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<Cart> addItemToCart(@RequestParam String userEmail, @RequestParam Long itemId,
                                              @RequestParam int quantity) {
        try {
            Cart cart = cartService.addItemToCart(userEmail, itemId, quantity);
            return ResponseEntity.ok(cart);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
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
