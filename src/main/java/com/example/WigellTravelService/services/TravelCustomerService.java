package com.example.WigellTravelService.services;

import com.example.WigellTravelService.entities.TravelCustomer;

public interface TravelCustomerService {

        TravelCustomer findTravelCustomerByUsername(String username);
}
