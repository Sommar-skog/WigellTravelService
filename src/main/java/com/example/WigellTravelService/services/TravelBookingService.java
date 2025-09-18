package com.example.WigellTravelService.services;

import com.example.WigellTravelService.dtos.CreateBookingDTO;
import com.example.WigellTravelService.entities.TravelBooking;

import java.util.List;

public interface TravelBookingService {

    //TODO returnera och ta emot DAO

    TravelBooking bookTrip(CreateBookingDTO createBookingDTO);

    TravelBooking cancelTrip(TravelBooking travelBooking);

    List<TravelBooking> getMyBookings();

    List<TravelBooking> listCanceledBookings();

    List<TravelBooking> listUpcomingBookings();

    List<TravelBooking> listPastBookings();

}
