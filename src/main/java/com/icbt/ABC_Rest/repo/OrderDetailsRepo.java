package com.icbt.ABC_Rest.repo;

import com.icbt.ABC_Rest.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailsRepo extends JpaRepository<OrderDetails, Long> {
    // You can add custom query methods here if needed
}
