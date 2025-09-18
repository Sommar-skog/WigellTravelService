package com.example.WigellTravelService.controllers;

import com.example.WigellTravelService.entities.TravelBooking;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasRole('USER')")
@RestController
@RequestMapping("/api/wigelltravels/v1")
public class TravelCustomerController {

    @PostMapping("/booktrip")
    public ResponseEntity<TravelBooking> bookTrip(@RequestBody TravelBooking travelBooking) { //TODO ta in och returnera DAO
        return null;
    }

    @PutMapping("/canceltrip")
    public ResponseEntity<TravelBooking> cancelTrip(@RequestBody TravelBooking travelBooking) { //TODO ta in och returnera DAO
        return null;
    }

    @GetMapping("/mybookings")
    public ResponseEntity<List<TravelBooking>> getMyBookings() { //TODO returnera DAO
        return null;
    }
}
