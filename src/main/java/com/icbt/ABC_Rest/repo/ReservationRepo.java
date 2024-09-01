package com.icbt.ABC_Rest.repo;


import com.icbt.ABC_Rest.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepo extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUserEmail(String email);
}
