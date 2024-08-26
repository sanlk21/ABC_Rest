package com.icbt.ABC_Rest.repo;

import com.icbt.ABC_Rest.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepo extends JpaRepository<CartItem, Long> {
}

