package com.example.WigellTravelService.repositories;

import com.example.WigellTravelService.entities.TravelCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TravelCustomerRepository  extends JpaRepository<TravelCustomer, Long> {

    Optional<TravelCustomer> findByCustomerUsername(String customerId);

}
