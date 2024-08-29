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
@CrossOrigin(origins = "http://127.0.0.1:5501")

public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/view")
    public ResponseEntity<?> viewCart(@RequestParam(required = false) String userEmail) {
        if (userEmail == null || userEmail.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User email is required");
        }
        try {
            Cart cart = cartService.getOrCreateCart(userEmail);
            return ResponseEntity.ok(cart);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addItemToCart(
            @RequestParam(required = false) String userEmail,
            @RequestParam(required = false) Long itemId,
            @RequestParam(required = false) Integer quantity) {
        if (userEmail == null || userEmail.isEmpty() || itemId == null || quantity == null || quantity <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input parameters");
        }
        try {
            Cart updatedCart = cartService.addItemToCart(userEmail, itemId, quantity);
            return ResponseEntity.ok(updatedCart);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> removeItemFromCart(
            @RequestParam(required = false) String userEmail,
            @RequestParam(required = false) Long cartItemId) {
        if (userEmail == null || userEmail.isEmpty() || cartItemId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input parameters");
        }
        try {
            Cart updatedCart = cartService.removeItemFromCart(userEmail, cartItemId);
            return ResponseEntity.ok(updatedCart);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/clear")
    public ResponseEntity<?> clearCart(@RequestParam(required = false) String userEmail) {
        if (userEmail == null || userEmail.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User email is required");
        }
        try {
            Cart clearedCart = cartService.clearCart(userEmail);
            return ResponseEntity.ok(clearedCart);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(
            @RequestParam(required = false) String userEmail,
            @RequestParam(required = false) Payment.PaymentMethod paymentMethod) {
        if (userEmail == null || userEmail.isEmpty() || paymentMethod == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input parameters");
        }
        try {
            Order order = cartService.checkout(userEmail, paymentMethod);
            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
