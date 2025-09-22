package com.example.WigellTravelService.services;

import com.example.WigellTravelService.dtos.AddTravelPackageDTO;
import com.example.WigellTravelService.dtos.UpdateTravelPackageDTO;
import com.example.WigellTravelService.entities.TravelPackage;
import com.example.WigellTravelService.repositories.TravelPackageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TravelPackageServiceImplTest {

    @Mock
    private TravelPackageRepository mockTravelPackageRepository;

    private TravelPackageServiceImpl travelPackageService;

    private TravelPackage testTravelPackage;
    private AddTravelPackageDTO testAddTravelPackageDTO;
    private UpdateTravelPackageDTO testUpdateTravelPackageDTO;

    @BeforeEach
    void setUp() {
        travelPackageService = new TravelPackageServiceImpl(mockTravelPackageRepository);

        testAddTravelPackageDTO = new AddTravelPackageDTO("Test Hotel", "Test Destination", new BigDecimal("7000.00"));
        testUpdateTravelPackageDTO = new UpdateTravelPackageDTO(-1L, "Test Hotel Update", "Test Destination Update", new BigDecimal("5000.00"));
        testTravelPackage = new TravelPackage(-1L,"TestHotel", "TestDestinaton", new BigDecimal("10000.00"));
    }


    @Test
    void getAllTravelPackages() {
    }

    @Test
    void addTravelPackage() {
    }

    @Test
    void updateTravelPackage() {
    }

    @Test
    void removeTravelPackage() {
    }

    @Test
    void getTravelPackageById() {
    }
}