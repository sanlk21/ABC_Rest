package com.icbt.ABC_Rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemDto {

    private Long id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private Long categoryId;
    private String imagePath; // Field to store the path of the uploaded image
}
