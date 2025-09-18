package com.example.WigellTravelService.services;

import com.example.WigellTravelService.entities.TravelPackage;
import com.example.WigellTravelService.repositories.TravelPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TravelPackageServiceImpl implements TravelPackageService {

    private final TravelPackageRepository travelPackageRepository;

    @Autowired
    public TravelPackageServiceImpl(TravelPackageRepository travelPackageRepository) {
        this.travelPackageRepository = travelPackageRepository;
    }

    @Override
    public List<TravelPackage> getAllTrips() {
        return List.of();
    }

    @Override
    public TravelPackage addTrip(TravelPackage trip) {
        return null;
    }

    @Override
    public TravelPackage updateTrip(TravelPackage trip) {
        return null;
    }

    @Override
    public String removeTrip(Long tripId) {
        return "";
    }

    public TravelPackage getTripById(Long tripId) {
        return travelPackageRepository.findById(tripId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trip with id " + tripId + " not found"));
    }
}
