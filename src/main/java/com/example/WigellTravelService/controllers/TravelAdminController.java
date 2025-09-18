package com.example.WigellTravelService.controllers;


import com.example.WigellTravelService.dtos.AddTravelPackageDTO;
import com.example.WigellTravelService.dtos.UpdateTravelPackageDTO;
import com.example.WigellTravelService.entities.TravelBooking;
import com.example.WigellTravelService.entities.TravelPackage;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/api/wigelltravels/v1")
public class TravelAdminController {

    @GetMapping("/listcanceled")
    public ResponseEntity<List<TravelBooking>> listCanceledBookings() { //TODO uppdatera till att returnera DAO
        return null;
    }

    @GetMapping("/listupcoming")
    public ResponseEntity<List<TravelBooking>> listUpcomingBookings() { //TODO uppdatera till att returnera DAO
        return null;
    }

    @GetMapping("/listpast")
    public ResponseEntity<List<TravelBooking>> listPastBookings() { //TODO uppdatera till att returnera DAO
        return null;
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
