package com.icbt.ABC_Rest.controller;

import com.icbt.ABC_Rest.dto.ReservationDto;
import com.icbt.ABC_Rest.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservations")
@CrossOrigin(origins = "http://127.0.0.1:5501")  // Ensure this matches the frontend origin

public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    // Get all reservations
    @GetMapping
    public ResponseEntity<List<ReservationDto>> getAllReservations() {
        List<ReservationDto> reservations = reservationService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }

    // Get a specific reservation by ID
    @GetMapping("/{id}")
    public ResponseEntity<ReservationDto> getReservationById(@PathVariable Long id) {
        ReservationDto reservation = reservationService.getReservationById(id);
        return ResponseEntity.ok(reservation);
    }

    // Create a new reservation
    @PostMapping
    public ResponseEntity<ReservationDto> createReservation(@RequestBody ReservationDto reservationDto) {
        ReservationDto newReservation = reservationService.createReservation(reservationDto);
        return ResponseEntity.ok(newReservation);
    }

    // Update an existing reservation
    @PutMapping("/{id}")
    public ResponseEntity<ReservationDto> updateReservation(@PathVariable Long id, @RequestBody ReservationDto reservationDetails) {
        ReservationDto updatedReservation = reservationService.updateReservation(id, reservationDetails);
        return ResponseEntity.ok(updatedReservation);
    }

    // Delete a reservation
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }

    // Get reservations by user email
    @GetMapping("/user/{email}")
    public ResponseEntity<List<ReservationDto>> getReservationsByUserEmail(@PathVariable String email) {
        List<ReservationDto> reservations = reservationService.findReservationsByUserEmail(email);
        return ResponseEntity.ok(reservations);
    }
}
