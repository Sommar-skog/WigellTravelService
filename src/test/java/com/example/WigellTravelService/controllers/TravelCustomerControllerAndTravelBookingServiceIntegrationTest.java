package com.example.WigellTravelService.controllers;

import com.example.WigellTravelService.dtos.CreateBookingDTO;
import com.example.WigellTravelService.entities.TravelBooking;
import com.example.WigellTravelService.entities.TravelCustomer;
import com.example.WigellTravelService.entities.TravelPackage;
import com.example.WigellTravelService.repositories.TravelBookingRepository;
import com.example.WigellTravelService.repositories.TravelCustomerRepository;
import com.example.WigellTravelService.services.TravelBookingService;
import com.example.WigellTravelService.services.TravelCustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TravelCustomerControllerAndTravelBookingServiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    // @MockitoSpyBean
    private TravelBookingRepository travelBookingRepository;

    @MockitoBean
    //@MockitoSpyBean
    private TravelCustomerRepository travelCustomerRepository;

    @Autowired
    private TravelCustomerService travelCustomerService;
    @Autowired
    private TravelBookingService travelBookingService;

/*    @Autowired
    TravelCustomerControllerAndTravelBookingServiceIntegrationTest(TravelCustomerService travelCustomerService, TravelBookingService travelBookingService) {
        this.travelCustomerService = travelCustomerService;
        this.travelBookingService = travelBookingService;
    }*/

    @Test
    @WithMockUser(username = "a", roles = "USER")
    void bookTrip() throws Exception {
        TravelCustomer travelCustomer = new TravelCustomer(-1L, "a", "a", "a");
        TravelBooking travelBooking = new TravelBooking(
                -1L,
                LocalDate.of(2025,9,23),
                LocalDate.of(2025,9,29),
                1,
                new BigDecimal("7000.00"),
                false,
                travelCustomer,
                new TravelPackage(-10L, "HotelTest", "Paris, France", new BigDecimal("7000.00"),true));
        when(travelBookingRepository.save(any(TravelBooking.class))).thenReturn(travelBooking);
        when(travelCustomerRepository.findByUsername(any(String.class))).thenReturn(Optional.of(travelCustomer));

        CreateBookingDTO createBookingDTO = new CreateBookingDTO(1L, LocalDate.of(2025, 9, 23), 1);

        mockMvc.perform(post("/booktrip")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(createBookingDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.bookedBy").value("a"))
                .andExpect(jsonPath("$.bookingId").value(-1))
                .andExpect(jsonPath("$.hotelName").value("HotelTest"))
                .andExpect(jsonPath("$.destination").value("Paris, France"))
                .andExpect(jsonPath("$.startDate").value("2025-09-23"))
                .andExpect(jsonPath("$.weeks").value(1))
                .andExpect(jsonPath("$.totalPriceInSek").value(7000.00))
                .andExpect(jsonPath("$.cancelled").value(false));
        verify(travelBookingRepository, times(1)).save(any(TravelBooking.class));
        verify(travelCustomerRepository, times(1)).findByUsername("a");
    }

    @Test
    void cancelTrip() {
    }

    @Test
    void getMyBookings() {
    }
}