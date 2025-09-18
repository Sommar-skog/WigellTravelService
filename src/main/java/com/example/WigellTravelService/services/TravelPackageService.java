package com.example.WigellTravelService.services;

import com.example.WigellTravelService.entities.TravelPackage;

import java.util.List;

public interface TravelPackageService {

    //TODO ta in och returnera DAO

    List<TravelPackage> getAllTravelPackages();

    TravelPackage addTravelPackage(TravelPackage trip);

    TravelPackage updateTravelPackage(TravelPackage trip);

    String removeTravelPackage(Long tripId);

    TravelPackage getTripById(Long tripId);
}
