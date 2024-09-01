package com.icbt.ABC_Rest.service;

import com.icbt.ABC_Rest.Mapper.ReservationMapper;
import com.icbt.ABC_Rest.dto.ReservationDto;
import com.icbt.ABC_Rest.entity.Reservation;
import com.icbt.ABC_Rest.repo.ReservationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepo reservationRepo;

    @Autowired
    private ReservationMapper reservationMapper;

    public ReservationDto createReservation(ReservationDto reservationDTO) {
        Reservation reservation = reservationMapper.toEntity(reservationDTO);
        Reservation savedReservation = reservationRepo.save(reservation);
        return reservationMapper.toDTO(savedReservation);
    }

    public ReservationDto getReservation(Long id) {
        Optional<Reservation> reservation = reservationRepo.findById(id);
        return reservation.map(reservationMapper::toDTO).orElse(null);
    }

    public List<ReservationDto> getAllReservations() {
        return reservationRepo.findAll().stream()
                .map(reservationMapper::toDTO)
                .toList();
    }

    public ReservationDto updateReservation(Long id, ReservationDto reservationDTO) {
        if (reservationRepo.existsById(id)) {
            Reservation reservation = reservationMapper.toEntity(reservationDTO);
            reservation.setId(id);
            Reservation updatedReservation = reservationRepo.save(reservation);
            return reservationMapper.toDTO(updatedReservation);
        }
        return null;
    }

    public void deleteReservation(Long id) {
        reservationRepo.deleteById(id);
    }

    // New method to get reservations by user email
    public List<ReservationDto> getReservationsByUserEmail(String email) {
        List<Reservation> reservations = reservationRepo.findByUserEmail(email);
        return reservations.stream()
                .map(reservationMapper::toDTO)
                .toList();
    }
}
