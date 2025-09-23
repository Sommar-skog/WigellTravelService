package com.example.WigellTravelService.controllers;

import com.example.WigellTravelService.dtos.CreateBookingDTO;
import com.example.WigellTravelService.entities.TravelBooking;
import com.example.WigellTravelService.entities.TravelCustomer;
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
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class TravelCustomerControllerAndTravelBookingServiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TravelBookingRepository travelBookingRepository;

    @MockitoBean
    private TravelCustomerRepository travelCustomerRepository;

    private final TravelCustomerService travelCustomerService;
    private final TravelBookingService travelBookingService;

    @Autowired
    TravelCustomerControllerAndTravelBookingServiceIntegrationTest(TravelCustomerService travelCustomerService, TravelBookingService travelBookingService) {
        this.travelCustomerService = travelCustomerService;
        this.travelBookingService = travelBookingService;
    }



    @Test
    void bookTrip() throws Exception {
        TravelCustomer travelCustomer = new TravelCustomer(-1L, "a", "a", "a");
        TravelBooking travelBooking = new TravelBooking(
                -1L,
                LocalDate.of(2025,9,23),
                LocalDate.of(2025,9,29),
                1,
                new BigDecimal("700000.00"),
                false,
                travelCustomer);
        when(travelBookingRepository.save(any(TravelBooking.class))).thenReturn(travelBooking);
        when(travelCustomerRepository.findByUsername(any(String.class))).thenReturn(Optional.of(travelCustomer));

        CreateBookingDTO createBookingDTO = new CreateBookingDTO(1L, LocalDate.of(2025, 9, 23), 1);

        mockMvc.perform(post("/booktrip")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(createBookingDTO))
                    .principal(() -> "a"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.bookedBy").value("a"))
                .andExpect(jsonPath("$.bookingId").value(-1))
                .andExpect(jsonPath("$.hotelName").value("HotelTest"))
                .andExpect(jsonPath("$.destination").value("Paris"))
                .andExpect(jsonPath("$.startDate").value("2025-09-23"))
                .andExpect(jsonPath("$.weeks").value(1))
                .andExpect(jsonPath("$.totalPriceInSek").value(700000.00))
                .andExpect(jsonPath("$.cancelled").value(false));
        verify(travelBookingService).bookTrip(any(CreateBookingDTO.class), any(Principal.class));
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