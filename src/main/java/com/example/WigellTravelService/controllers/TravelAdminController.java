package com.example.WigellTravelService.controllers;


import com.example.WigellTravelService.dtos.AddTravelPackageDTO;
import com.example.WigellTravelService.dtos.TravelBookingDTO;
import com.example.WigellTravelService.dtos.TravelPackageDTO;
import com.example.WigellTravelService.dtos.UpdateTravelPackageDTO;
import com.example.WigellTravelService.dtos.mappers.TravelBookingMapper;
import com.example.WigellTravelService.dtos.mappers.TravelPackageMapper;
import com.example.WigellTravelService.services.TravelBookingService;
import com.example.WigellTravelService.services.TravelPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/api/wigelltravels/v1")
public class TravelAdminController {

    private final TravelBookingService travelBookingService;
    private final TravelPackageService travelPackageService;

    @Autowired
    public TravelAdminController(TravelBookingService travelBookingService, TravelPackageService travelPackageService) {
        this.travelBookingService = travelBookingService;
        this.travelPackageService = travelPackageService;
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
    public ResponseEntity<TravelPackageDTO> addTravelPackage(@RequestBody AddTravelPackageDTO addTravelPackageDTO) {
        return new ResponseEntity<>(TravelPackageMapper.toDTO(travelPackageService.addTravelPackage(addTravelPackageDTO)), HttpStatus.CREATED);
    }

    @PutMapping("updatetravel")
    public ResponseEntity<TravelPackageDTO> updateTravelPackage(@RequestBody UpdateTravelPackageDTO updateTravelPackageDTO) {
        return ResponseEntity.ok(TravelPackageMapper.toDTO(travelPackageService.updateTravelPackage(updateTravelPackageDTO)));
    }

    @DeleteMapping("/removetravel/{id}")
    public ResponseEntity<String> removeTravelPackage(@PathVariable Long id) {
        return ResponseEntity.ok(travelPackageService.removeTravelPackage(id));
    }
}
