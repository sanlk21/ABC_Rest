package com.icbt.ABC_Rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDto {

    private Long id;
    private Long userId;
    private List<OrderDetailsDto> orderDetails;
    private Date startDate;
    private Date endDate;
    private Date orderDate;
    private double totalAmount;
    private String status;
    private String type;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class OrderDetailsDto {
        private Long itemId;
        private int quantity;
        private double price;
    }
}
