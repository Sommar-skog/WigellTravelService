package com.example.WigellTravelService.services;

import com.example.WigellTravelService.entities.TravelPackage;

import java.util.List;

public interface TravelPackageService {

    //TODO ta in och returnera DAO

    List<TravelPackage> getAllTrips();

    TravelPackage addTrip(TravelPackage trip);

    TravelPackage updateTrip(TravelPackage trip);

    String removeTrip(Long tripId);

    TravelPackage getTripById(Long tripId);
}
