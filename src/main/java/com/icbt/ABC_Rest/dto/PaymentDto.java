package com.icbt.ABC_Rest.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class PaymentDto {

    private Long id;
    private String userId;  // Changed to String to store email
    private Long orderId;
    private double amount;
    private PaymentMethod paymentMethod;
    private PaymentStatus status;
    private Date paymentDate;

    public enum PaymentMethod {
        CREDIT_CARD, DEBIT_CARD, PAYPAL
    }

    public enum PaymentStatus {
        PENDING, COMPLETED, FAILED
    }
}
