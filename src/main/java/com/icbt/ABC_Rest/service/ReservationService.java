package com.icbt.ABC_Rest.service;

import com.icbt.ABC_Rest.dto.ReservationDto;
import com.icbt.ABC_Rest.entity.Reservation;
import com.icbt.ABC_Rest.repo.ReservationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepo reservationRepo;

    // Get all reservations
    public List<ReservationDto> getAllReservations() {
        return reservationRepo.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Get a specific reservation by ID
    public ReservationDto getReservationById(Long id) {
        return reservationRepo.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id " + id));
    }

    // Create a new reservation
    public ReservationDto createReservation(ReservationDto reservationDto) {
        Reservation reservation = convertToEntity(reservationDto);
        return convertToDto(reservationRepo.save(reservation));
    }

    // Update an existing reservation
    public ReservationDto updateReservation(Long id, ReservationDto reservationDetails) {
        Reservation reservation = reservationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id " + id));

        reservation.setDate(reservationDetails.getDate());
        reservation.setUserEmail(reservationDetails.getUserEmail());
        reservation.setType(reservationDetails.getType());
        reservation.setNumberOfGuests(reservationDetails.getNumberOfGuests());
        reservation.setStatus(reservationDetails.getStatus());

        return convertToDto(reservationRepo.save(reservation));
    }

    // Delete a reservation
    public void deleteReservation(Long id) {
        Reservation reservation = reservationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id " + id));
        reservationRepo.delete(reservation);
    }

    // Find reservations by user email
    public List<ReservationDto> findReservationsByUserEmail(String userEmail) {
        return reservationRepo.findByUserEmail(userEmail).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Convert entity to DTO
    private ReservationDto convertToDto(Reservation reservation) {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(reservation.getId());
        reservationDto.setUserEmail(reservation.getUserEmail());
        reservationDto.setDate(reservation.getDate());
        reservationDto.setType(reservation.getType());
        reservationDto.setNumberOfGuests(reservation.getNumberOfGuests());
        reservationDto.setStatus(reservation.getStatus());
        return reservationDto;
    }

    // Convert DTO to entity
    private Reservation convertToEntity(ReservationDto reservationDto) {
        Reservation reservation = new Reservation();
        reservation.setUserEmail(reservationDto.getUserEmail());
        reservation.setDate(reservationDto.getDate());
        reservation.setType(reservationDto.getType());
        reservation.setNumberOfGuests(reservationDto.getNumberOfGuests());
        reservation.setStatus(reservationDto.getStatus());
        return reservation;
    }
}
