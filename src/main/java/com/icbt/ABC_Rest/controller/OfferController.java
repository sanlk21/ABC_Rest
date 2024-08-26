package com.icbt.ABC_Rest.controller;

import com.icbt.ABC_Rest.dto.OfferDto;
import com.icbt.ABC_Rest.entity.Offer;
import com.icbt.ABC_Rest.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/offers")
public class OfferController {

    @Autowired
    private OfferService offerService;

    @GetMapping
    public ResponseEntity<List<OfferDto>> getAllOffers() {
        List<OfferDto> offers = offerService.getAllOffers();
        return new ResponseEntity<>(offers, HttpStatus.OK);
    }

    @GetMapping("/getOffer")
    public ResponseEntity<OfferDto> getOfferById(@PathVariable Long id) {
        OfferDto offer = offerService.getOfferById(id);
        return new ResponseEntity<>(offer, HttpStatus.OK);
    }

    @PostMapping("/saveOffer")
    public ResponseEntity<OfferDto> createOffer(@RequestBody OfferDto offerDto) {
        OfferDto createdOffer = offerService.createOffer(offerDto);
        return new ResponseEntity<>(createdOffer, HttpStatus.CREATED);
    }

    @PutMapping("/updateOffer")
    public ResponseEntity<OfferDto> updateOffer(@PathVariable Long id, @RequestBody OfferDto offerDto) {
        OfferDto updatedOffer = offerService.updateOffer(id, offerDto);
        return new ResponseEntity<>(updatedOffer, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOffer(@PathVariable Long id) {
        offerService.deleteOffer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<OfferDto>> getOffersByStatus(@PathVariable OfferDto.OfferStatus status) {
        List<OfferDto> offers = offerService.getOffersByStatus(Offer.OfferStatus.valueOf(status.name()));
        return new ResponseEntity<>(offers, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<OfferDto>> searchOffersByTitle(@RequestParam String title) {
        List<OfferDto> offers = offerService.searchOffersByTitle(title);
        return new ResponseEntity<>(offers, HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity<List<OfferDto>> getActiveOffers(@RequestParam Date currentDate) {
        List<OfferDto> offers = offerService.getActiveOffers(currentDate);
        return new ResponseEntity<>(offers, HttpStatus.OK);
    }
}
