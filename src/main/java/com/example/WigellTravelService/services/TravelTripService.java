package com.example.WigellTravelService.services;

import com.example.WigellTravelService.entities.TravelTrip;

import java.util.List;

public interface TravelTripService {

    //TODO ta in och returnera DAO

    List<TravelTrip> getAllTrips();

    TravelTrip addTrip(TravelTrip trip);

    TravelTrip updateTrip(TravelTrip trip);

    String removeTrip(Long tripId);

    TravelTrip getTripById(Long tripId);
}
