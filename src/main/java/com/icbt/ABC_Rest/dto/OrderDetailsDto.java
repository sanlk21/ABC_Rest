// OrderDetailsDto.java
package com.icbt.ABC_Rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsDto {
    private Long itemId;
    private int quantity;
    private double price;
}
