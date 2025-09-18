package com.example.WigellTravelService.repositories;

import com.example.WigellTravelService.entities.TravelBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface TravelBookingRepository extends JpaRepository<TravelBooking, Long> {

    List<TravelBooking> findByTravelCustomerUsername(String username);

    List<TravelBooking> findByCancelledIsTrue();

    List<TravelBooking> findByEndDateBeforeAndCancelledIsFalse(LocalDate endDateBefore);

    List<TravelBooking> findByStartDateGreaterThanEqualAndCancelledFalse(LocalDate today);


}
