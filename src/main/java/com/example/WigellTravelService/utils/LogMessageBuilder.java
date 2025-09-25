package com.example.WigellTravelService.utils;

import com.example.WigellTravelService.dtos.UpdateTravelPackageDTO;
import com.example.WigellTravelService.entities.TravelPackage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LogMessageBuilder {
    public static String userBookedTrip(String destination, int weeks) {
        return String.format("user booked %d weeks of travel to [%s]", weeks, destination);
    }

    public static String userCanceledTrip(String destination, int weeks) {
        return String.format("user canceled %d weeks of travel to [%s]", weeks, destination);
    }

    public static String adminAddedNewTravelPackade(Long id,String destination) {
        return String.format("admin added a new travel packade to [%s] with ID [%s]", destination, id);
    }

    public static String adminRemovedTravelPackade(Long id) {
        return String.format("admin removed travel packade with ID [%s]. Travel package is now set to inactive", id);
    }

    public static String adminUpdatedTravelPackade(Long id, TravelPackage oldValue, UpdateTravelPackageDTO newValue) {
        List<String> changes = new ArrayList<>();
        if (!Objects.equals(oldValue.getDestination(), newValue.getDestination())) {
            changes.add(String.format("destination: %s -> %s", oldValue.getDestination(), newValue.getDestination()));
        }

        if (!Objects.equals(oldValue.getHotelName(), newValue.getHotelName())) {
            changes.add(String.format("hotelName: %s -> %s", oldValue.getHotelName(), newValue.getHotelName()));
        }

        if (!Objects.equals(oldValue.getWeekPrice(), newValue.getWeekPrice())) {
            changes.add(String.format("weekPrice: %s -> %s", oldValue.getWeekPrice(), newValue.getWeekPrice()));
        }

        if (changes.isEmpty()){
            return null;
        }

        return String.format("admin updated travel packade with ID [%s]. Data changed: [%s]", id, String.join(", ", changes));
    }
}
