package com.example.WigellTravelService.services;

import com.example.WigellTravelService.dtos.CreateBookingDTO;
import com.example.WigellTravelService.entities.TravelBooking;
import com.example.WigellTravelService.entities.TravelTrip;
import com.example.WigellTravelService.repositories.TravelBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class TravelBookingServiceImpl implements TravelBookingService {

    private final TravelBookingRepository travelBookingRepository;
    private final TravelTripServiceImpl travelTripService;

    @Autowired
    public TravelBookingServiceImpl(TravelBookingRepository travelBookingRepository, TravelTripServiceImpl travelTripService) {
        this.travelBookingRepository = travelBookingRepository;
        this.travelTripService = travelTripService;
    }

    //TODO kom ihåg DTOs!

    @Override
    public TravelBooking bookTrip(CreateBookingDTO createBookingDTO) {
        //Behöver få in principal som användarnamn
        validateCreateBooking(createBookingDTO);

        TravelTrip trip = travelTripService.getTripById(createBookingDTO.getTripId());

        TravelBooking newBooking = new TravelBooking();
        newBooking.setTrip(trip);
        newBooking.setStartDate(createBookingDTO.getStartDate());
        newBooking.setNumberOfWeeks(createBookingDTO.getNumberOfWeeks());
        newBooking.setEndDate(getTripEndDate(createBookingDTO.getStartDate(), createBookingDTO.getNumberOfWeeks()));
        newBooking.setTotalPrice(getTotalPrice(trip.getWeekPrice(), createBookingDTO.getNumberOfWeeks()));


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
        return List.of();
    }

    private void validateCreateBooking(CreateBookingDTO createBookingDTO) {
        if (createBookingDTO.getTripId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Trip id is required");
        }
        if (createBookingDTO.getStartDate() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start date is required");
        }
        if (createBookingDTO.getNumberOfWeeks() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Number of weeks is required");
        }
    }

    private LocalDate getTripEndDate(LocalDate startDate, int weeks) {
        return startDate.plusWeeks(weeks);
    }

    private BigDecimal getTotalPrice(BigDecimal weekPrice, int weeks) {
        return weekPrice.multiply(BigDecimal.valueOf(weeks));
    }

}
