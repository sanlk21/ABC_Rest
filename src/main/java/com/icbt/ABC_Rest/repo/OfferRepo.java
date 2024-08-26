package com.icbt.ABC_Rest.repo;

import com.icbt.ABC_Rest.entity.Offer;
import com.icbt.ABC_Rest.entity.Offer.OfferStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
public interface OfferRepo extends JpaRepository<Offer, Long> {
    List<Offer> findByStatus(OfferStatus status);
    List<Offer> findByTitleContainingIgnoreCase(String title);
    List<Offer> findByStartDateBeforeAndEndDateAfter(Date startDate, Date endDate);
}