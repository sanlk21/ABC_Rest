package com.icbt.ABC_Rest.service;

import com.icbt.ABC_Rest.dto.OfferDto;
import com.icbt.ABC_Rest.entity.Offer;
import com.icbt.ABC_Rest.repo.OfferRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfferService {

    @Autowired
    private OfferRepo offerRepo;

    @Autowired
    private ModelMapper modelMapper;

    public List<OfferDto> getAllOffers() {
        List<Offer> offers = offerRepo.findAll();
        return offers.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public OfferDto getOfferById(Long id) {
        Offer offer = offerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Offer not found with id: " + id));
        return convertToDto(offer);
    }

    public OfferDto createOffer(OfferDto offerDto) {
        Offer offer = convertToEntity(offerDto);
        Offer savedOffer = offerRepo.save(offer);
        return convertToDto(savedOffer);
    }

    public OfferDto updateOffer(Long id, OfferDto offerDto) {
        Offer existingOffer = offerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Offer not found with id: " + id));

        existingOffer.setTitle(offerDto.getTitle());
        existingOffer.setDescription(offerDto.getDescription());
        existingOffer.setDiscountPercentage(offerDto.getDiscountPercentage());
        existingOffer.setStartDate(offerDto.getStartDate());
        existingOffer.setEndDate(offerDto.getEndDate());
        existingOffer.setStatus(convertToEntityStatus(offerDto.getStatus()));

        Offer updatedOffer = offerRepo.save(existingOffer);
        return convertToDto(updatedOffer);
    }

    public void deleteOffer(Long id) {
        Offer offer = offerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Offer not found with id: " + id));
        offerRepo.delete(offer);
    }

    public List<OfferDto> getOffersByStatus(Offer.OfferStatus status) {
        List<Offer> offers = offerRepo.findByStatus(status);
        return offers.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<OfferDto> searchOffersByTitle(String title) {
        List<Offer> offers = offerRepo.findByTitleContainingIgnoreCase(title);
        return offers.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<OfferDto> getActiveOffers(Date currentDate) {
        List<Offer> offers = offerRepo.findByStartDateBeforeAndEndDateAfter(currentDate, currentDate);
        return offers.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private OfferDto convertToDto(Offer offer) {
        return modelMapper.map(offer, OfferDto.class);
    }

    private Offer convertToEntity(OfferDto offerDto) {
        return modelMapper.map(offerDto, Offer.class);
    }

    private Offer.OfferStatus convertToEntityStatus(OfferDto.OfferStatus dtoStatus) {
        return Offer.OfferStatus.valueOf(dtoStatus.name());
    }
}
