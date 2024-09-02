package com.icbt.ABC_Rest.service;

import com.icbt.ABC_Rest.entity.Reservation;
import com.icbt.ABC_Rest.repo.ReservationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> 993f74eb03e8ada2d85c60964da13da5f6a936fd
import org.springframework.transaction.annotation.Transactional;

=======
>>>>>>> parent of e3d1a58 (update reservation)
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepo ReservationRepo;

<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> 993f74eb03e8ada2d85c60964da13da5f6a936fd
    @Transactional
    public Reservation createReservation(Reservation reservation) {
        reservation.setStatus(Reservation.ReservationStatus.PENDING); // Set initial status to PENDING
        return reservationRepo.save(reservation);
<<<<<<< HEAD
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
=======
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
=======
    @Autowired
    private ReservationMapper reservationMapper;

    public ReservationDto createReservation(ReservationDto reservationDTO) {
        Reservation reservation = reservationMapper.toEntity(reservationDTO);
        Reservation savedReservation = ReservationRepo.save(reservation);
        return reservationMapper.toDTO(savedReservation);
    }

    public ReservationDto getReservation(Long id) {
        Optional<Reservation> reservation = ReservationRepo.findById(id);
        return reservation.map(reservationMapper::toDTO).orElse(null);
    }

    public List<ReservationDto> getAllReservations() {
        return ReservationRepo.findAll().stream()
                .map(reservationMapper::toDTO)
                .toList();
    }

    public ReservationDto updateReservation(Long id, ReservationDto reservationDTO) {
        if (ReservationRepo.existsById(id)) {
            Reservation reservation = reservationMapper.toEntity(reservationDTO);
            reservation.setId(id);
            Reservation updatedReservation = ReservationRepo.save(reservation);
            return reservationMapper.toDTO(updatedReservation);
        }
        return null;
>>>>>>> parent of e3d1a58 (update reservation)
>>>>>>> 993f74eb03e8ada2d85c60964da13da5f6a936fd
    }

    @Transactional
    public void deleteReservation(Long id) {
<<<<<<< HEAD
        reservationRepo.deleteById(id);
    }

    @Transactional
    public List<Reservation> getReservationsByUserEmail(String userEmail) {
        return reservationRepo.findByUserEmail(userEmail);
<<<<<<< HEAD
=======
=======
        ReservationRepo.deleteById(id);
>>>>>>> parent of e3d1a58 (update reservation)
>>>>>>> 993f74eb03e8ada2d85c60964da13da5f6a936fd
    }
}
