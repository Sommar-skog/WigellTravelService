package com.example.WigellTravelService.services;

import com.example.WigellTravelService.dtos.AddTravelPackageDTO;
import com.example.WigellTravelService.dtos.UpdateTravelPackageDTO;
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
    public TravelPackage updateTravelPackage(UpdateTravelPackageDTO updateTravelPackageDTO) {
        TravelPackage travelPackageToUpdate = getTravelPackageById(updateTravelPackageDTO.getTravelPackageId());
        validateUpdateTravelPackage(updateTravelPackageDTO);

        travelPackageToUpdate.setHotelName(updateTravelPackageDTO.getHotelName().trim());
        travelPackageToUpdate.setDestination(updateTravelPackageDTO.getDestination().trim());
        travelPackageToUpdate.setWeekPrice(updateTravelPackageDTO.getWeekPrice());

        return travelPackageRepository.save(travelPackageToUpdate);
    }

    @Override
    public String removeTravelPackage(Long travelPackageId) {
        TravelPackage travelPackageToRemove = getTravelPackageById(travelPackageId);
        travelPackageRepository.delete(travelPackageToRemove);
        return "Travel Package Removed with id  [" + travelPackageId + "] successfully";
    }

    public TravelPackage getTravelPackageById(Long travelPackageId) {
        return travelPackageRepository.findById(travelPackageId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trip with id " + travelPackageId + " not found"));
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

    private void validateUpdateTravelPackage(UpdateTravelPackageDTO updateTravelPackageDTO) {
        if (updateTravelPackageDTO.getHotelName() != null) {
            if (updateTravelPackageDTO.getHotelName().length() > 50) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Hotel name must be less than 50 characters");
            }
            if (updateTravelPackageDTO.getHotelName().isBlank()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Hotel name cannot be blank");
            }

            if (updateTravelPackageDTO.getDestination() != null) {
                if (updateTravelPackageDTO.getDestination().length() > 50) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Destination must be less than 50 characters");
                }
                if (updateTravelPackageDTO.getDestination().isBlank()){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Destination cannot be blank");
                }
            }

            BigDecimal maxWeekPrice = new BigDecimal("99999.99");
            if (updateTravelPackageDTO.getWeekPrice() != null) {
                if (updateTravelPackageDTO.getWeekPrice().compareTo(BigDecimal.ZERO) <= 0 || updateTravelPackageDTO.getWeekPrice().compareTo(maxWeekPrice) >0) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Week price must be between 0.01 and 99999.99");
                }
            }
        }

    }
}
