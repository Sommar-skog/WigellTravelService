package com.example.WigellTravelService.services;

import com.example.WigellTravelService.entities.TravelTrip;
import com.example.WigellTravelService.repositories.TravelTripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TravelTripServiceImpl implements TravelTripService {

    private final TravelTripRepository travelTripRepository;

    @Autowired
    public TravelTripServiceImpl(TravelTripRepository travelTripRepository) {
        this.travelTripRepository = travelTripRepository;
    }

    @Override
    public List<TravelTrip> getAllTrips() {
        return List.of();
    }

    @Override
    public TravelTrip addTrip(TravelTrip trip) {
        return null;
    }

    @Override
    public TravelTrip updateTrip(TravelTrip trip) {
        return null;
    }

    @Override
    public String removeTrip(Long tripId) {
        return "";
    }

    public TravelTrip getTripById(Long tripId) {
        return travelTripRepository.findById(tripId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trip with id " + tripId + " not found"));
    }
}
