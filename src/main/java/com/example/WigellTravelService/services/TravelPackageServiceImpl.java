package com.example.WigellTravelService.services;

import com.example.WigellTravelService.dtos.AddTravelPackageDTO;
import com.example.WigellTravelService.entities.TravelPackage;
import com.example.WigellTravelService.repositories.TravelPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TravelPackageServiceImpl implements TravelPackageService {

    private final TravelPackageRepository travelPackageRepository;

    @Autowired
    public TravelPackageServiceImpl(TravelPackageRepository travelPackageRepository) {
        this.travelPackageRepository = travelPackageRepository;
    }

    @Override
    public List<TravelPackage> getAllTravelPackages() {
        return travelPackageRepository.findAll();
    }

    @Override
    public TravelPackage addTravelPackage(AddTravelPackageDTO addTravelPackageDTO) {
        validateAddTravelPackage(addTravelPackageDTO);

        TravelPackage travelPackage = new TravelPackage();
        travelPackage.setHotelName(addTravelPackageDTO.getHotelName().trim());
        travelPackage.setDestination(addTravelPackageDTO.getDestination().trim());
        travelPackage.setWeekPrice(addTravelPackageDTO.getWeekPrice());

        return travelPackageRepository.save(travelPackage);
    }

    @Override
    public TravelPackage updateTravelPackage(TravelPackage trip) {
        return null;
    }

    @Override
    public String removeTravelPackage(Long tripId) {
        return "";
    }

    public TravelPackage getTripById(Long tripId) {
        return travelPackageRepository.findById(tripId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trip with id " + tripId + " not found"));
    }

    private void validateAddTravelPackage(AddTravelPackageDTO travelPackage) {
      if (travelPackage.getHotelName() == null || travelPackage.getHotelName().isBlank()) {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Hotel name is required");
      }
      if (travelPackage.getHotelName().length() > 50) {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Hotel name must be less than 50 characters");
      }

      if (travelPackage.getDestination() == null || travelPackage.getDestination().isBlank()) {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Destination is required");
      }

      if (travelPackage.getDestination().length() > 50) {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Destination must be less than 50 characters");
      }

      if (travelPackage.getWeekPrice() == null) {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Week price is required");
      }

      BigDecimal maxWeekPrice = new BigDecimal("99999.99");
      if (travelPackage.getWeekPrice().compareTo(BigDecimal.ZERO) <= 0 || travelPackage.getWeekPrice().compareTo(maxWeekPrice) >0) {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Week price must be between 0.01 and 99999.99");
      }
    }
}
