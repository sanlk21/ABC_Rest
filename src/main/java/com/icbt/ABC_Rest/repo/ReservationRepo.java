package com.icbt.ABC_Rest.repo;


import com.icbt.ABC_Rest.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepo extends JpaRepository<Reservation, Long> {
<<<<<<< HEAD
    List<Reservation> findByUserEmail(String userEmail);
=======
    // Custom query methods can be added here if needed
>>>>>>> 993f74eb03e8ada2d85c60964da13da5f6a936fd
}
