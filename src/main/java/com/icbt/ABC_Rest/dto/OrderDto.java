package com.icbt.ABC_Rest.dto;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class OrderDto {
    private Long id;
    private String userEmail;  // Corresponds to the User entity's email
    private Date startDate;
    private Date endDate;
    private Date orderDate;
    private double totalAmount;
    private String status;
    private String type;
    private List<OrderDetailsDto> orderDetails;

    @Data
    public static class OrderDetailsDto {
        private Long itemId;
        private int quantity;
        private double price;
    }
}
