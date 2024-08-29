package com.icbt.ABC_Rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long id;
    private String userEmail;
    private List<OrderDetailsDto> orderDetails; // Ensure this list is used in your service methods
    private Date startDate;
    private Date endDate;
    private Date orderDate;
    private double totalAmount;
    private String status;
    private String type;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderDetailsDto {
        private Long itemId;
        private int quantity;
        private double price;
    }
}
