package com.example.WigellTravelService.repositories;

import com.example.WigellTravelService.entities.TravelBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TravelBookingRepository extends JpaRepository<TravelBooking, Long> {

    List<TravelBooking> findByTravelCustomer(String username);

}
