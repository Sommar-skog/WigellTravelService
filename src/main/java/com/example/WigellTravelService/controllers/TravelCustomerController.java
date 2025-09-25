package com.example.WigellTravelService.controllers;

import com.example.WigellTravelService.dtos.CancelBookingDTO;
import com.example.WigellTravelService.dtos.CreateBookingDTO;
import com.example.WigellTravelService.dtos.TravelBookingDTO;
import com.example.WigellTravelService.dtos.mappers.TravelBookingMapper;
import com.example.WigellTravelService.entities.TravelBooking;
import com.example.WigellTravelService.services.TravelBookingService;
import com.example.WigellTravelService.utils.CurrencyConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@PreAuthorize("hasRole('USER')")
@RestController
public class TravelCustomerController {


    private final TravelBookingService travelBookingService;
    private final CurrencyConverter currencyConverter;

    @Autowired
    public TravelCustomerController(TravelBookingService travelBookingService, CurrencyConverter currencyConverter) {
        this.travelBookingService = travelBookingService;
        this.currencyConverter = currencyConverter;
    }


    @PostMapping("/booktrip")
    public ResponseEntity<TravelBookingDTO> bookTrip(@RequestBody CreateBookingDTO createBookingDTO, Principal principal) {
        TravelBooking bookedTrip = travelBookingService.bookTrip(createBookingDTO, principal);
        TravelBookingDTO bookingDTO = TravelBookingMapper.toDTO(bookedTrip,currencyConverter.convertSekToEur(bookedTrip.getTotalPrice()));

        return new ResponseEntity<>(bookingDTO, HttpStatus.CREATED);
    }

    @PutMapping("/canceltrip")
    public ResponseEntity<TravelBookingDTO> cancelTrip(@RequestBody CancelBookingDTO cancelBookingDTO, Principal principal) {
        TravelBooking canceledTrip = travelBookingService.cancelTrip(cancelBookingDTO, principal);
        TravelBookingDTO bookingDTO = TravelBookingMapper.toDTO(canceledTrip,currencyConverter.convertSekToEur(canceledTrip.getTotalPrice()));
        return ResponseEntity.ok(bookingDTO);
    }

    @GetMapping("/mybookings")
    public ResponseEntity<List<TravelBookingDTO>> getMyBookings(Principal principal) {
        List<TravelBookingDTO> dtoList = travelBookingService
                .getMyBookings(principal)
                .stream()
                .map(booking -> TravelBookingMapper.toDTO(
                        booking,
                        currencyConverter.convertSekToEur(booking.getTotalPrice())
                ))
                .toList();
        return ResponseEntity.ok(dtoList);
    }
}
