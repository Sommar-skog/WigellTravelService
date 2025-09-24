package com.example.WigellTravelService.controllers;

import com.example.WigellTravelService.dtos.TravelPackageDTO;
import com.example.WigellTravelService.dtos.mappers.TravelPackageMapper;
import com.example.WigellTravelService.services.TravelPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
@RestController
public class TravelCommonController {

    private final TravelPackageService travelPackageService;

    @Autowired
    public TravelCommonController(TravelPackageService travelPackageService) {
        this.travelPackageService = travelPackageService;
    }

    @GetMapping("/travels")
    public ResponseEntity<List<TravelPackageDTO>> getAllTravelPackages() {
        List<TravelPackageDTO> dtoList = travelPackageService
                .getAllTravelPackages()
                .stream()
                .map(TravelPackageMapper::toDTO)
                .toList();
        return ResponseEntity.ok(dtoList);
    }

}
