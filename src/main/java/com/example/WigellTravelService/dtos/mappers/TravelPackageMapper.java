package com.example.WigellTravelService.dtos.mappers;

import com.example.WigellTravelService.dtos.TravelPackageDTO;
import com.example.WigellTravelService.entities.TravelPackage;


public class TravelPackageMapper {

    public static TravelPackageDTO toDTO(TravelPackage travelPackage) {
        TravelPackageDTO dto = new TravelPackageDTO();
        dto.setTravelPackageId(travelPackage.getTravelPackageId());
        dto.setDestination(travelPackage.getDestination());
        dto.setHotelName(travelPackage.getHotelName());
        dto.setWeekPrice(travelPackage.getWeekPrice());
        dto.setActive(travelPackage.isActive());
        return dto;
    }
}
