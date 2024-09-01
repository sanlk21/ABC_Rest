package com.icbt.ABC_Rest.service;

import com.icbt.ABC_Rest.entity.Reservation;
import com.icbt.ABC_Rest.repo.ReservationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepo reservationRepo;

    @Transactional
    public Reservation createReservation(Reservation reservation) {
        reservation.setStatus(Reservation.ReservationStatus.PENDING); // Set initial status to PENDING
        return reservationRepo.save(reservation);
    }

    @Transactional
    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepo.findById(id);
    }

    @Transactional
    public List<Reservation> getAllReservations() {
        return reservationRepo.findAll();
    }

    @Transactional
    public Reservation updateReservation(Long id, Reservation updatedReservation) {
        return reservationRepo.findById(id).map(existingReservation -> {
            existingReservation.setUserEmail(updatedReservation.getUserEmail());
            existingReservation.setDate(updatedReservation.getDate());
            existingReservation.setType(updatedReservation.getType());
            existingReservation.setNumberOfGuests(updatedReservation.getNumberOfGuests());
            existingReservation.setStatus(updatedReservation.getStatus());
            return reservationRepo.save(existingReservation);
        }).orElseThrow(() -> new RuntimeException("Reservation not found with id: " + id));
    }

    @Transactional
    public void deleteReservation(Long id) {
        reservationRepo.deleteById(id);
    }

    @Transactional
    public List<Reservation> getReservationsByUserEmail(String userEmail) {
        return reservationRepo.findByUserEmail(userEmail);
    }
}
