package com.example.WigellTravelService.services;

import com.example.WigellTravelService.entities.TravelCustomer;
import com.example.WigellTravelService.repositories.TravelCustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TravelCustomerServiceImpl implements TravelCustomerService {

    private final TravelCustomerRepository travelCustomerRepository;

    @Autowired
    public TravelCustomerServiceImpl(TravelCustomerRepository travelCustomerRepository) {
        this.travelCustomerRepository = travelCustomerRepository;
    }

    @Override
    public TravelCustomer findTravelCustomerById(Long customerId) {
        return travelCustomerRepository.findById(customerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer with ID " + customerId + " was not found"));
    }

    @Override
    public TravelCustomer findTravelCustomerByUsername(String username) {
        return travelCustomerRepository.findByCustomerUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer with username " + username + " was not found"));
    }
}
