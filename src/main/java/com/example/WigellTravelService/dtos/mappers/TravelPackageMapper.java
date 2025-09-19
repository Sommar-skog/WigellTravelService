package com.example.WigellTravelService.dtos.mappers;

import com.example.WigellTravelService.entities.TravelPackage;

public class TravelPackageMapper {

    public static TravelPackage toDTO(TravelPackage travelPackage) {
        TravelPackage dto = new TravelPackage();
        dto.setTravelPackageId(travelPackage.getTravelPackageId());
        dto.setDestination(travelPackage.getDestination());
        dto.setHotelName(travelPackage.getHotelName());
        dto.setWeekPrice(travelPackage.getWeekPrice());
        return dto;
    }
}
