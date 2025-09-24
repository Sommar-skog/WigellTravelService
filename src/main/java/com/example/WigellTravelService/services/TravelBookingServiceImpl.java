package com.example.WigellTravelService.services;

import com.example.WigellTravelService.dtos.CancelBookingDTO;
import com.example.WigellTravelService.dtos.CreateBookingDTO;
import com.example.WigellTravelService.entities.TravelBooking;
import com.example.WigellTravelService.entities.TravelPackage;
import com.example.WigellTravelService.repositories.TravelBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Service
public class TravelBookingServiceImpl implements TravelBookingService {

    private final TravelBookingRepository travelBookingRepository;
    private final TravelPackageService travelPackageService;
    private final TravelCustomerService travelCustomerService;

    @Autowired
    public TravelBookingServiceImpl(TravelBookingRepository travelBookingRepository, TravelPackageService travelPackageService, TravelCustomerService travelCustomerService) {
        this.travelBookingRepository = travelBookingRepository;
        this.travelPackageService = travelPackageService;
        this.travelCustomerService = travelCustomerService;
    }

    //TODO kom ihåg DTOs!


    //TODO Få in totalpriset i Euro också!
    @Override
    public TravelBooking bookTrip(CreateBookingDTO createBookingDTO, Principal principal) {
        validateCreateBooking(createBookingDTO);

        TravelPackage trip = travelPackageService.getTravelPackageById(createBookingDTO.getTravelPackageId());

        TravelBooking newBooking = new TravelBooking();
        newBooking.setTravelPackage(trip);
        newBooking.setStartDate(createBookingDTO.getStartDate());
        newBooking.setNumberOfWeeks(createBookingDTO.getNumberOfWeeks());
        newBooking.setEndDate(getTripEndDate(createBookingDTO.getStartDate(), createBookingDTO.getNumberOfWeeks()));
        newBooking.setTotalPrice(getTotalPrice(trip.getWeekPrice(), createBookingDTO.getNumberOfWeeks()));
        newBooking.setTravelCustomer(travelCustomerService.findTravelCustomerByUsername(principal.getName()));

        return travelBookingRepository.save(newBooking);
    }

    @Override
    public TravelBooking cancelTrip(CancelBookingDTO cancelBookingDTO, Principal principal) {
         TravelBooking trip = validateCancelTrip(cancelBookingDTO, principal);
         if (isTripUpcoming(trip.getStartDate())) {
                trip.setCancelled(true);
         }
        return travelBookingRepository.save(trip);
    }

    @Override
    public List<TravelBooking> getMyBookings(Principal principal) {
        return travelBookingRepository.findByTravelCustomerUsernameAndCancelledFalse(principal.getName());
    }

    @Override
    public List<TravelBooking> listCanceledBookings() {
        return travelBookingRepository.findByCancelledIsTrue();
    }

    @Override
    public List<TravelBooking> listUpcomingBookings() {
        return travelBookingRepository.findByStartDateGreaterThanEqualAndCancelledFalse(LocalDate.now());
    }

    @Override
    public List<TravelBooking> listPastBookings() {
        return travelBookingRepository.findByEndDateBeforeAndCancelledIsFalse(LocalDate.now());
    }

    private void validateCreateBooking(CreateBookingDTO createBookingDTO) {
        if (createBookingDTO.getTravelPackageId() == null) {
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

    private TravelBooking validateCancelTrip(CancelBookingDTO cancelBookingDTO, Principal principal) {
        TravelBooking booking = travelBookingRepository.findById(cancelBookingDTO.getBookingId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found"));

        if (!booking.getTravelCustomer().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the owner of the booking");
        }

        return booking;
    }

    private boolean isTripUpcoming(LocalDate startDate) {
        return startDate.isAfter(LocalDate.now());
    }

}
