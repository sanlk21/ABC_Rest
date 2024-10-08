package com.icbt.ABC_Rest.controller;

import com.icbt.ABC_Rest.dto.ReservationDto;
import com.icbt.ABC_Rest.entity.Reservation;
import com.icbt.ABC_Rest.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservations")
@CrossOrigin(origins = "http://127.0.0.1:5501")  // Adjust this to match your frontend origin
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<ReservationDto>> getAllReservations() {
        List<ReservationDto> reservations = reservationService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDto> getReservationById(@PathVariable Long id) {
        ReservationDto reservation = reservationService.getReservationById(id);
        return ResponseEntity.ok(reservation);
    }

    @PostMapping
    public ResponseEntity<ReservationDto> createReservation(@RequestBody ReservationDto reservationDto) {
        ReservationDto newReservation = reservationService.createReservation(reservationDto);
        return ResponseEntity.ok(newReservation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationDto> updateReservation(@PathVariable Long id, @RequestBody ReservationDto reservationDto) {
        // Ensure the status is properly handled if it's not already an enum
        if (reservationDto.getStatus() != null) {
            reservationDto.setStatus(Reservation.ReservationStatus.valueOf(reservationDto.getStatus().toString()));
        }

        ReservationDto updatedReservation = reservationService.updateReservation(id, reservationDto);
        return ResponseEntity.ok(updatedReservation);
    }

    @PutMapping("/{id}/confirm")
    public ResponseEntity<ReservationDto> confirmReservation(@PathVariable Long id) {
        ReservationDto confirmedReservation = reservationService.confirmReservation(id);
        return ResponseEntity.ok(confirmedReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<List<ReservationDto>> getReservationsByUserEmail(@PathVariable String email) {
        List<ReservationDto> reservations = reservationService.findReservationsByUserEmail(email);
        return ResponseEntity.ok(reservations);
    }
}
