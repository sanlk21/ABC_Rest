package com.icbt.ABC_Rest.Mapper;

import com.icbt.ABC_Rest.dto.ReservationDto;
import com.icbt.ABC_Rest.entity.Reservation;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {

    public ReservationDto toDTO(Reservation reservation) {
        if (reservation == null) {
            return null;
        }

        ReservationDto dto = new ReservationDto();
        dto.setId(reservation.getId());
        dto.setUserId(reservation.getUserId());
        dto.setDate(reservation.getDate());
        dto.setType(reservation.getType());
        dto.setNumberOfGuests(reservation.getNumberOfGuests());
        dto.setStatus(reservation.getStatus());

        return dto;
    }

    public Reservation toEntity(ReservationDto dto) {
        if (dto == null) {
            return null;
        }

        Reservation reservation = new Reservation();
        reservation.setId(dto.getId());
        reservation.setUserId(dto.getUserId());
        reservation.setDate(dto.getDate());
        reservation.setType(dto.getType());
        reservation.setNumberOfGuests(dto.getNumberOfGuests());
        reservation.setStatus(dto.getStatus());

        return reservation;
    }
}
