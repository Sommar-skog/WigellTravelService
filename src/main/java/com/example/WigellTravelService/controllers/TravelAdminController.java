package com.example.WigellTravelService.controllers;


import com.example.WigellTravelService.dtos.AddTravelPackageDTO;
import com.example.WigellTravelService.dtos.TravelBookingDTO;
import com.example.WigellTravelService.dtos.TravelPackageDTO;
import com.example.WigellTravelService.dtos.UpdateTravelPackageDTO;
import com.example.WigellTravelService.dtos.mappers.TravelBookingMapper;
import com.example.WigellTravelService.dtos.mappers.TravelPackageMapper;
import com.example.WigellTravelService.entities.TravelPackage;
import com.example.WigellTravelService.services.TravelBookingService;
import com.example.WigellTravelService.services.TravelPackageService;
import com.example.WigellTravelService.utils.CurrencyConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasRole('ADMIN')")
@RestController
public class TravelAdminController {

    private final TravelBookingService travelBookingService;
    private final TravelPackageService travelPackageService;
    private final CurrencyConverter currencyConverter;

    @Autowired
    public TravelAdminController(TravelBookingService travelBookingService, TravelPackageService travelPackageService, CurrencyConverter currencyConverter) {
        this.travelBookingService = travelBookingService;
        this.travelPackageService = travelPackageService;
        this.currencyConverter = currencyConverter;
    }

    @GetMapping("/listcanceled")
    public ResponseEntity<List<TravelBookingDTO>> listCanceledBookings() {
        List<TravelBookingDTO> dtoList = travelBookingService
                .listCanceledBookings()
                .stream()
                .map(booking -> TravelBookingMapper.toDTO(
                        booking,
                        currencyConverter.convertSekToEur(booking.getTotalPrice())
                ))
                .toList();
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/listupcoming")
    public ResponseEntity<List<TravelBookingDTO>> listUpcomingBookings() {
        List<TravelBookingDTO> dtoList = travelBookingService
                .listUpcomingBookings()
                .stream()
                .map(booking -> TravelBookingMapper.toDTO(
                        booking,
                        currencyConverter.convertSekToEur(booking.getTotalPrice())
                ))
                .toList();
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/listpast")
    public ResponseEntity<List<TravelBookingDTO>> listPastBookings() {
        List<TravelBookingDTO> dtoList = travelBookingService
                .listPastBookings()
                .stream()
                .map(booking -> TravelBookingMapper.toDTO(
                        booking,
                        currencyConverter.convertSekToEur(booking.getTotalPrice())
                ))
                .toList();
        return ResponseEntity.ok(dtoList);
    }

    @PostMapping("/addtravel")
    public ResponseEntity<TravelPackageDTO> addTravelPackage(@RequestBody AddTravelPackageDTO addTravelPackageDTO) {
        TravelPackage travelPackage = travelPackageService.addTravelPackage(addTravelPackageDTO);
        TravelPackageDTO dto = TravelPackageMapper.toDTO(travelPackage, currencyConverter.convertSekToEur(travelPackage.getWeekPrice()));
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @PutMapping("updatetravel")
    public ResponseEntity<TravelPackageDTO> updateTravelPackage(@RequestBody UpdateTravelPackageDTO updateTravelPackageDTO) {
        TravelPackage travelPackage = travelPackageService.updateTravelPackage(updateTravelPackageDTO);
        TravelPackageDTO dto = TravelPackageMapper.toDTO(travelPackage, currencyConverter.convertSekToEur(travelPackage.getWeekPrice()));
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/removetravel/{id}")
    public ResponseEntity<TravelPackageDTO> removeTravelPackage(@PathVariable Long id) {
        TravelPackage travelPackage = travelPackageService.removeTravelPackage(id);
        TravelPackageDTO dto = TravelPackageMapper.toDTO(travelPackage, currencyConverter.convertSekToEur(travelPackage.getWeekPrice()));
        return ResponseEntity.ok(dto);
    }
}
