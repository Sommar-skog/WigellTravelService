package com.example.WigellTravelService.repositories;

import com.example.WigellTravelService.entities.TravelPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelPackageRepository extends JpaRepository<TravelPackage, Long> {

}
