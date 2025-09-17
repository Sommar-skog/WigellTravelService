package com.example.WigellTravelService.repositories;

import com.example.WigellTravelService.entities.TravelBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TravelBookingRepository extends JpaRepository<TravelBooking, Long> {

}
