package com.example.WigellTravelService.services;

import com.example.WigellTravelService.dtos.CreateBookingDTO;
import com.example.WigellTravelService.entities.TravelBooking;

import java.security.Principal;
import java.util.List;

public interface TravelBookingService {

    //TODO returnera och ta emot DAO

    TravelBooking bookTrip(CreateBookingDTO createBookingDTO, Principal principal);

    TravelBooking cancelTrip(TravelBooking travelBooking, Principal principal);

    List<TravelBooking> getMyBookings(Principal principal);

    List<TravelBooking> listCanceledBookings();

    List<TravelBooking> listUpcomingBookings();

    List<TravelBooking> listPastBookings();

}
