package com.icbt.ABC_Rest.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userEmail;  // Corresponds to the User entity's email

    @Column(nullable = false)
    private LocalDateTime date; // Date and time of the reservation

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationType type; // Type of reservation (e.g., Dine-in, Delivery)

    @Column(nullable = false)
    private Integer numberOfGuests; // Number of guests for the reservation

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status; // Status of the reservation (e.g., Confirmed, Pending, Cancelled)

    public enum ReservationType {
        DINE_IN,
        DELIVERY
    }

    public enum ReservationStatus {
        CONFIRMED,
        PENDING,
        CANCELLED
    }
}
