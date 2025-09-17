package com.example.WigellTravelService.services;

import com.example.WigellTravelService.entities.TravelBooking;

import java.util.List;

public interface TravelBookingService {

    //TODO returnera och ta emot DAO

    TravelBooking bookTrip(TravelBooking travelBooking);

    TravelBooking cancelTrip(TravelBooking travelBooking);

    List<TravelBooking> getMyBookings();
}
