package com.example.WigellTravelService.controllers;


import com.example.WigellTravelService.dtos.AddTravelPackageDTO;
import com.example.WigellTravelService.dtos.TravelBookingDTO;
import com.example.WigellTravelService.dtos.UpdateTravelPackageDTO;
import com.example.WigellTravelService.dtos.mappers.TravelBookingMapper;
import com.example.WigellTravelService.entities.TravelBooking;
import com.example.WigellTravelService.entities.TravelPackage;
import com.example.WigellTravelService.services.TravelBookingService;
import com.example.WigellTravelService.services.TravelPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/api/wigelltravels/v1")
public class TravelAdminController {

    private final TravelBookingService travelBookingService;

    @Autowired
    public TravelAdminController(TravelBookingService travelBookingService) {
        this.travelBookingService = travelBookingService;
    }

    @GetMapping("/listcanceled")
    public ResponseEntity<List<TravelBookingDTO>> listCanceledBookings() {
        List<TravelBookingDTO> dtoList = travelBookingService
                .listCanceledBookings()
                .stream()
                .map(TravelBookingMapper::toDTO)
                .toList();
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/listupcoming")
    public ResponseEntity<List<TravelBookingDTO>> listUpcomingBookings() {
        List<TravelBookingDTO> dtoList = travelBookingService
                .listUpcomingBookings()
                .stream()
                .map(TravelBookingMapper::toDTO)
                .toList();
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/listpast")
    public ResponseEntity<List<TravelBookingDTO>> listPastBookings() {
        List<TravelBookingDTO> dtoList = travelBookingService
                .listPastBookings()
                .stream()
                .map(TravelBookingMapper::toDTO)
                .toList();
        return ResponseEntity.ok(dtoList);
    }

    @PostMapping("/addtravel")
    public ResponseEntity<TravelPackage> addTrip(@RequestBody AddTravelPackageDTO addTravelPackageDTO) { //TODO Ta in DAO returnera DAO
        return null;
    }

    @PutMapping("updatetravel")
    public ResponseEntity<TravelPackage> updateTrip(@RequestBody UpdateTravelPackageDTO updateTravelPackageDTO) { //TODO ta in och returnera DAO
        return null;
    }

    @DeleteMapping("/removetravel/{id}")
    public ResponseEntity<String> removeTrip(@PathVariable Long id) {
        return null;
    }
}
