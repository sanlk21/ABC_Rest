package com.icbt.ABC_Rest.repo;

import com.icbt.ABC_Rest.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CartRepo extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserEmail(String email);
}

