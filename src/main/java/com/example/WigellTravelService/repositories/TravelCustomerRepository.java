package com.example.WigellTravelService.repositories;

import com.example.WigellTravelService.entities.TravelCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelCustomerRepository  extends JpaRepository<TravelCustomer, Long> {

}
