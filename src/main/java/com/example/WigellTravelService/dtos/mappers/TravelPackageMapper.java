package com.example.WigellTravelService.dtos.mappers;

import com.example.WigellTravelService.dtos.TravelPackageDTO;
import com.example.WigellTravelService.entities.TravelPackage;

import java.math.BigDecimal;


public class TravelPackageMapper {

    public static TravelPackageDTO toDTO(TravelPackage travelPackage, BigDecimal eur) {
        TravelPackageDTO dto = new TravelPackageDTO();
        dto.setTravelPackageId(travelPackage.getTravelPackageId());
        dto.setDestination(travelPackage.getDestination());
        dto.setHotelName(travelPackage.getHotelName());
        dto.setWeekPrice(travelPackage.getWeekPrice());
        dto.setWeekPriceEur(eur);
        dto.setActive(travelPackage.isActive());
        return dto;
    }
}
