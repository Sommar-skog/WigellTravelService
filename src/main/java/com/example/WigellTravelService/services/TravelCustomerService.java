package com.example.WigellTravelService.services;

import com.example.WigellTravelService.entities.TravelCustomer;

public interface TravelCustomerService {

       /* TravelCustomer findTravelCustomerById(Long customerId);*/

        TravelCustomer findTravelCustomerByUsername(String username);
}
