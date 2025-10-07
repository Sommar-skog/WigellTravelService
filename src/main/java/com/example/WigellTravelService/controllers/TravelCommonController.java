package com.example.WigellTravelService.controllers;

import com.example.WigellTravelService.dtos.TravelPackageDTO;
import com.example.WigellTravelService.dtos.mappers.TravelPackageMapper;
import com.example.WigellTravelService.services.TravelPackageService;
import com.example.WigellTravelService.utils.CurrencyConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
@RestController
public class TravelCommonController {

    private final TravelPackageService travelPackageService;
    private final CurrencyConverter currencyConverter;

    @Autowired
    public TravelCommonController(TravelPackageService travelPackageService, CurrencyConverter currencyConverter) {
        this.travelPackageService = travelPackageService;
        this.currencyConverter = currencyConverter;
    }

    @GetMapping("/travels")
    public ResponseEntity<List<TravelPackageDTO>> getAllTravelPackages(Authentication authentication) {
        String role = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))
                ? "ROLE_ADMIN" : "ROLE_USER";

        List<TravelPackageDTO> dtoList = travelPackageService
                .getAllTravelPackages(role)
                .stream()
                .map(travelPackage -> TravelPackageMapper.toDTO(
                        travelPackage,
                        currencyConverter.convertSekToEur(travelPackage.getWeekPrice())
                ))
                .toList();

        return ResponseEntity.ok(dtoList);
    }
}
