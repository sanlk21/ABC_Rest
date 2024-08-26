package com.icbt.ABC_Rest.repo;

import com.icbt.ABC_Rest.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {


    // Method to find orders by status
    List<Order> findByStatus(Order.OrderStatus status);

    // Method to find orders by type
    List<Order> findByType(Order.OrderType type);

    // Method to find orders where startDate is before a certain date and endDate is after a certain date
    List<Order> findByStartDateBeforeAndEndDateAfter(Date startDate, Date endDate);
}
