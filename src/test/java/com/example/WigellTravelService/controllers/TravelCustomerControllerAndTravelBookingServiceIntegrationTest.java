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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Optional;

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
    private TravelBookingRepository mockTravelBookingRepository;

    @MockitoBean
    private TravelCustomerRepository mockTravelCustomerRepository;

    @MockitoSpyBean
    private TravelCustomerService travelCustomerService;

    @MockitoSpyBean
    private TravelBookingService travelBookingService;

    private TravelCustomer travelCustomer;
    private TravelBooking travelBooking;

    @BeforeEach
    void setUp() {
        travelCustomer = new TravelCustomer(-1L, "a", "a", "a");
        travelBooking = new TravelBooking(
                -1L,
                LocalDate.of(2025, 9, 23),
                LocalDate.of(2025, 9, 29),
                1,
                new BigDecimal("7000.00"),
                false,
                travelCustomer,
                new TravelPackage(-10L, "HotelTest", "Paris, France", new BigDecimal("7000.00"), true)
        );

        when(mockTravelBookingRepository.save(any(TravelBooking.class))).thenReturn(travelBooking);
        when(mockTravelCustomerRepository.findByUsername("a")).thenReturn(Optional.of(travelCustomer));
    }


    @Test
    @WithMockUser(username = "a", roles = "USER")
    void bookTripShouldBookTrip() throws Exception {
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

        ArgumentCaptor<CreateBookingDTO> dtoCaptor = ArgumentCaptor.forClass(CreateBookingDTO.class);
        ArgumentCaptor<Principal> principalCaptor = ArgumentCaptor.forClass(Principal.class);

        verify(travelBookingService).bookTrip(dtoCaptor.capture(), principalCaptor.capture());

        CreateBookingDTO capturedDto = dtoCaptor.getValue();
        Principal capturedPrincipal = principalCaptor.getValue();

        assertEquals(1L, capturedDto.getTravelPackageId());
        assertEquals(1, capturedDto.getNumberOfWeeks());
        assertEquals(LocalDate.of(2025, 9, 23), capturedDto.getStartDate());
        assertEquals("a", capturedPrincipal.getName());

        verify(travelCustomerService, times(1))
                .findTravelCustomerByUsername("a");

        verify(mockTravelBookingRepository, times(1)).save(any(TravelBooking.class));
        verify(mockTravelCustomerRepository, times(1)).findByUsername("a");
    }

    @Test
    @WithMockUser(username = "a", roles = "USER")
    void bookTripWithTravelPackageIdNullShouldThrowException() throws Exception {
        CreateBookingDTO createBookingDTO = new CreateBookingDTO(null, LocalDate.of(2025, 9, 23), 1);

        mockMvc.perform(post("/booktrip")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBookingDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("Trip id is required"));

        verify(travelBookingService, times(1))
                .bookTrip(any(CreateBookingDTO.class), any(Principal.class));

        verify(mockTravelBookingRepository, never()).save(any());
        verify(mockTravelCustomerRepository, never()).findByUsername(any());
    }

    @Test
    @WithMockUser(username = "a", roles = "USER")
    void bookTripWithStartDateNullShouldThrowException() throws Exception {
        CreateBookingDTO createBookingDTO = new CreateBookingDTO(1L, null, 1);

        mockMvc.perform(post("/booktrip")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBookingDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("Start date is required"));

        verify(travelBookingService, times(1))
                .bookTrip(any(CreateBookingDTO.class), any(Principal.class));

        verify(mockTravelBookingRepository, never()).save(any());
        verify(mockTravelCustomerRepository, never()).findByUsername(any());
    }

    @Test
    @WithMockUser(username = "a", roles = "USER")
    void bookTripWithNumberOfWeeksNullShouldThrowException() throws Exception {
        CreateBookingDTO createBookingDTO = new CreateBookingDTO(1L, LocalDate.of(2025, 9, 23), null);

        mockMvc.perform(post("/booktrip")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBookingDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("Number of weeks is required"));

        verify(travelBookingService, times(1))
                .bookTrip(any(CreateBookingDTO.class), any(Principal.class));

        verify(mockTravelBookingRepository, never()).save(any());
        verify(mockTravelCustomerRepository, never()).findByUsername(any());

    }

    @Test
    @WithMockUser(username = "a", roles = "USER")
    void bookTripWithWrongCustomerShouldThrowException() throws Exception {
        CreateBookingDTO createBookingDTO = new CreateBookingDTO(1L, LocalDate.of(2025, 9, 23), 1);

        when(mockTravelCustomerRepository.findByUsername("a")).thenReturn(Optional.empty());

        mockMvc.perform(post("/booktrip")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBookingDTO)))
                .andExpect(status().isNotFound())
                .andExpect(status().reason("Customer with username a was not found"));

        ArgumentCaptor<CreateBookingDTO> dtoCaptor = ArgumentCaptor.forClass(CreateBookingDTO.class);
        ArgumentCaptor<Principal> principalCaptor = ArgumentCaptor.forClass(Principal.class);

        verify(travelBookingService).bookTrip(dtoCaptor.capture(), principalCaptor.capture());

        CreateBookingDTO capturedDto = dtoCaptor.getValue();
        Principal capturedPrincipal = principalCaptor.getValue();

        assertEquals(1L, capturedDto.getTravelPackageId());
        assertEquals(1, capturedDto.getNumberOfWeeks());
        assertEquals(LocalDate.of(2025, 9, 23), capturedDto.getStartDate());
        assertEquals("a", capturedPrincipal.getName());

        verify(travelCustomerService, times(1))
                .findTravelCustomerByUsername("a");

        verify(mockTravelBookingRepository, never()).save(any());
        verify(mockTravelCustomerRepository, times(1)).findByUsername("a");
    }

    @Test
    void cancelTrip() {
    }

    @Test
    void getMyBookings() {
    }
}