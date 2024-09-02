package com.icbt.ABC_Rest.repo;


import com.icbt.ABC_Rest.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepo extends JpaRepository<Reservation, Long> {
    // Custom query methods can be added here if needed
}
