package com.example.WigellTravelService.controllers;

import com.example.WigellTravelService.entities.TravelTrip;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
@RestController
@RequestMapping("/api/wigelltravels/v1")
public class TravelCommonController {

    @GetMapping("/travels")
    public ResponseEntity<List<TravelTrip>> getAllTrips() { //TODO returnera DAO
        return null;
    }

}
