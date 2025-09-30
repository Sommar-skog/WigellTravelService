package com.example.WigellTravelService.services;

import com.example.WigellTravelService.dtos.AddTravelPackageDTO;
import com.example.WigellTravelService.dtos.TravelPackageDTO;
import com.example.WigellTravelService.dtos.UpdateTravelPackageDTO;
import com.example.WigellTravelService.entities.TravelPackage;

import java.util.List;

public interface TravelPackageService {

    //TODO ta in och returnera DAO

    List<TravelPackage> getAllTravelPackages(String role);

    TravelPackage addTravelPackage(AddTravelPackageDTO addTravelPackageDTO);

    TravelPackage updateTravelPackage(UpdateTravelPackageDTO updateTravelPackageDTO);

    TravelPackage removeTravelPackage(Long tripId);

    TravelPackage getTravelPackageById(Long tripId);
}
