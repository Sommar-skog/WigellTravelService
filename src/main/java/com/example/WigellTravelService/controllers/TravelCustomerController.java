package com.example.WigellTravelService.controllers;

import com.example.WigellTravelService.dtos.CancelBookingDTO;
import com.example.WigellTravelService.dtos.CreateBookingDTO;
import com.example.WigellTravelService.dtos.TravelBookingDTO;
import com.example.WigellTravelService.dtos.mappers.TravelBookingMapper;
import com.example.WigellTravelService.services.TravelBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@PreAuthorize("hasRole('USER')")
@RestController
@RequestMapping("/api/wigelltravels/v1")
public class TravelCustomerController {


    private final TravelBookingService travelBookingService;

    @Autowired
    public TravelCustomerController(TravelBookingService travelBookingService) {
        this.travelBookingService = travelBookingService;
    }


    @PostMapping("/booktrip")
    public ResponseEntity<TravelBookingDTO> bookTrip(@RequestBody CreateBookingDTO createBookingDTO, Principal principal) {
        return new ResponseEntity<>(TravelBookingMapper.toDTO(travelBookingService.bookTrip(createBookingDTO, principal)), HttpStatus.CREATED);
    }

    @PutMapping("/canceltrip")
    public ResponseEntity<TravelBookingDTO> cancelTrip(@RequestBody CancelBookingDTO cancelBookingDTO, Principal principal) {
        return ResponseEntity.ok(TravelBookingMapper.toDTO(travelBookingService.cancelTrip(cancelBookingDTO, principal)));
    }

    @GetMapping("/mybookings")
    public ResponseEntity<List<TravelBookingDTO>> getMyBookings(Principal principal) {
        List<TravelBookingDTO> dtoList = travelBookingService
                .getMyBookings(principal)
                .stream()
                .map(TravelBookingMapper::toDTO)
                .toList();
        return ResponseEntity.ok(dtoList);
    }
}
