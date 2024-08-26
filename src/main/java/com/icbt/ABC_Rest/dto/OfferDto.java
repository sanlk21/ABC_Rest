package com.icbt.ABC_Rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OfferDto {
    private Long id;
    private String title;
    private String description;
    private double discountPercentage;
    private Date startDate;
    private Date endDate;
    private OfferStatus status;

    public enum OfferStatus {
        ACTIVE,
        INACTIVE,
        EXPIRED
    }

}
