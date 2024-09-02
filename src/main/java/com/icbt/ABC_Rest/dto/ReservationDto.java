package com.icbt.ABC_Rest.dto;

import com.icbt.ABC_Rest.entity.Reservation.ReservationStatus;
import com.icbt.ABC_Rest.entity.Reservation.ReservationType;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReservationDto {
    private Long id;
    private String userEmail;
    private LocalDateTime date;
    private ReservationType type;
    private Integer numberOfGuests;
    private ReservationStatus status;  // Ensure this is of type ReservationStatus
}
