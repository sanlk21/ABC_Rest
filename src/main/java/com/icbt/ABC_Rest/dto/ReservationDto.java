package com.icbt.ABC_Rest.dto;

import com.icbt.ABC_Rest.entity.Reservation;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ReservationDto {
    private Long id;
    private String userEmail;  // Corresponds to the User entity's email
    private LocalDateTime date;
    private Reservation.ReservationType type; // Enum type for reservation type
    private Integer numberOfGuests;
    private Reservation.ReservationStatus status; // Enum type for reservation status
}
