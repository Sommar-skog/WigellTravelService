package com.example.WigellTravelService.services;

import com.example.WigellTravelService.entities.TravelBooking;
import com.example.WigellTravelService.repositories.TravelBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TravelBookingServiceImpl implements TravelBookingService {

    private final TravelBookingRepository travelBookingRepository;

    @Autowired
    public TravelBookingServiceImpl(TravelBookingRepository travelBookingRepository) {
        this.travelBookingRepository = travelBookingRepository;
    }

    //TODO kom ihåg DTOs!

    @Override
    public TravelBooking bookTrip(TravelBooking travelBooking) {
        return null;
    }

    @Override
    public TravelBooking cancelTrip(TravelBooking travelBooking) {
        return null;
    }

    @Override
    public List<TravelBooking> getMyBookings() {
        return List.of();
    }

    @Override
    public List<TravelBooking> listCanceledBookings() {
        return List.of();
    }

    @Override
    public List<TravelBooking> listUpcomingBookings() {
        return List.of();
    }

    @Override
    public List<TravelBooking> listPastBookings() {
        //kontrollera bokningsdatum och längd för att se när slutdatum för resan är och om detta är paserat är det pastBookings
        return List.of();
    }
}
