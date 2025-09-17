package com.example.WigellTravelService.repositories;

import com.example.WigellTravelService.entities.TravelTrip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelTripRepository extends JpaRepository<TravelTrip, Long> {

}
