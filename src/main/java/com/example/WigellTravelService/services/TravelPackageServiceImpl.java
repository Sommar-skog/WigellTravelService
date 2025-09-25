package com.example.WigellTravelService.services;

import com.example.WigellTravelService.dtos.AddTravelPackageDTO;
import com.example.WigellTravelService.dtos.UpdateTravelPackageDTO;
import com.example.WigellTravelService.entities.TravelBooking;
import com.example.WigellTravelService.entities.TravelPackage;
import com.example.WigellTravelService.repositories.TravelPackageRepository;
import com.example.WigellTravelService.utils.LogMessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class TravelPackageServiceImpl implements TravelPackageService {

    private final TravelPackageRepository travelPackageRepository;

    private static final Logger USER_LOGGER = LogManager.getLogger("userLog");

    @Autowired
    public TravelPackageServiceImpl(TravelPackageRepository travelPackageRepository) {
        this.travelPackageRepository = travelPackageRepository;
    }

    @Override
    public List<TravelPackage> getAllTravelPackages() {
        return travelPackageRepository.findAllByActiveTrue();
    }

    @Override
    public TravelPackage addTravelPackage(AddTravelPackageDTO addTravelPackageDTO) {
        validateAddTravelPackage(addTravelPackageDTO);

        TravelPackage travelPackage = new TravelPackage();
        travelPackage.setHotelName(addTravelPackageDTO.getHotelName().trim());
        travelPackage.setDestination(addTravelPackageDTO.getDestination().trim());
        travelPackage.setWeekPrice(addTravelPackageDTO.getWeekPrice());
        travelPackage.setActive(true);

        USER_LOGGER.info(LogMessageBuilder.adminAddedNewTravelPackade(addTravelPackageDTO.getDestination()));

        return travelPackageRepository.save(travelPackage);
    }

    @Override
    public TravelPackage updateTravelPackage(UpdateTravelPackageDTO updateTravelPackageDTO) {
        TravelPackage travelPackageToUpdate = getTravelPackageById(updateTravelPackageDTO.getTravelPackageId());
        validateUpdateTravelPackage(updateTravelPackageDTO);

        if (updateTravelPackageDTO.getHotelName() != null) {
            travelPackageToUpdate.setHotelName(updateTravelPackageDTO.getHotelName());
        }

        if (updateTravelPackageDTO.getDestination() != null) {
            travelPackageToUpdate.setDestination(updateTravelPackageDTO.getDestination());
        }

        if (updateTravelPackageDTO.getWeekPrice() != null) {
            travelPackageToUpdate.setWeekPrice(updateTravelPackageDTO.getWeekPrice());
        }

        String logMessage = LogMessageBuilder.adminUpdatedTravelPackade(travelPackageToUpdate.getTravelPackageId(),travelPackageToUpdate,updateTravelPackageDTO);
        if (logMessage != null) {
            USER_LOGGER.info(logMessage);
        }

        return travelPackageRepository.save(travelPackageToUpdate);
    }

    //Instead of deleting travel-packaged it's set to Active=False and bookings are cancelled. So that statistics
    //will not be affected
    @Override
    public TravelPackage removeTravelPackage(Long travelPackageId) {
        TravelPackage travelPackageToRemove = getTravelPackageById(travelPackageId);
        cancelBookingsOnTravelPackage(travelPackageToRemove.getBookingList());
        travelPackageToRemove.setActive(false);

        USER_LOGGER.info(LogMessageBuilder.adminRemovedTravelPackade(travelPackageToRemove.getTravelPackageId()));

        return travelPackageRepository.save(travelPackageToRemove);
    }

    public TravelPackage getTravelPackageById(Long travelPackageId) {
        return travelPackageRepository.findById(travelPackageId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "TravelPackage with id " + travelPackageId + " not found"));
    }

    private void validateAddTravelPackage(AddTravelPackageDTO travelPackage) {
      if (travelPackage.getHotelName() == null || travelPackage.getHotelName().isBlank()) {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Hotel name is required");
      }
      if (travelPackage.getHotelName().length() > 50) {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Hotel name must be 50 characters or less");
      }

      if (travelPackage.getDestination() == null || travelPackage.getDestination().isBlank()) {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Destination is required");
      }

      if (travelPackage.getDestination().length() > 50) {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Destination must be 50 characters or less");
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
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Hotel name must be 50 characters or less");
            }
            if (updateTravelPackageDTO.getHotelName().isBlank()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Hotel name cannot be blank");
            }
            updateTravelPackageDTO.setHotelName(updateTravelPackageDTO.getHotelName().trim());
        }

        if (updateTravelPackageDTO.getDestination() != null) {
            if (updateTravelPackageDTO.getDestination().length() > 50) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Destination must be 50 characters or less");
            }
            if (updateTravelPackageDTO.getDestination().isBlank()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Destination cannot be blank");
            }
            updateTravelPackageDTO.setDestination(updateTravelPackageDTO.getDestination().trim());
        }

        BigDecimal maxWeekPrice = new BigDecimal("99999.99");
        if (updateTravelPackageDTO.getWeekPrice() != null) {
            if (updateTravelPackageDTO.getWeekPrice().compareTo(BigDecimal.ZERO) <= 0 || updateTravelPackageDTO.getWeekPrice().compareTo(maxWeekPrice) >0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Week price must be between 0.01 and 99999.99");
            }
        }
    }

    private void cancelBookingsOnTravelPackage(List<TravelBooking> bookings) {
        bookings.forEach(booking -> {
            booking.setCancelled(true);
        });
    }
}
