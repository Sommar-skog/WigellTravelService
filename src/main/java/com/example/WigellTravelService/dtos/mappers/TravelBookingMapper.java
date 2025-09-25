package com.example.WigellTravelService.dtos.mappers;

import com.example.WigellTravelService.dtos.TravelBookingDTO;
import com.example.WigellTravelService.entities.TravelBooking;

import java.math.BigDecimal;


public class TravelBookingMapper {

    public static TravelBookingDTO toDTO(TravelBooking booking, BigDecimal priceEur) {
        TravelBookingDTO dto = new TravelBookingDTO();
        dto.setBookedBy(booking.getTravelCustomer().getUsername());
        dto.setBookingId(booking.getBookingId());
        dto.setDestination(booking.getTravelPackage().getDestination());
        dto.setHotelName(booking.getTravelPackage().getHotelName());
        dto.setStartDate(booking.getStartDate());
        dto.setWeeks(booking.getNumberOfWeeks());
        dto.setTotalPriceInSek(booking.getTotalPrice());
        dto.setTotalPriceInEuro(priceEur);
        dto.setCancelled(booking.isCancelled());
        return dto;
    }
}
