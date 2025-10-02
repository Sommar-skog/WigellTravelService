package com.example.WigellTravelService.controllers;

import com.example.WigellTravelService.entities.TravelBooking;
import com.example.WigellTravelService.entities.TravelCustomer;
import com.example.WigellTravelService.entities.TravelPackage;
import com.example.WigellTravelService.repositories.TravelBookingRepository;
import com.example.WigellTravelService.services.TravelBookingService;
import org.junit.jupiter.api.BeforeEach;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TravelAdminControllerAndTravelBookingServiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TravelBookingRepository mockTravelBookingRepository;

    @MockitoSpyBean
    private TravelBookingService travelBookingService;

    private TravelBooking travelBooking;

    @BeforeEach
    void setUp() {
        TravelCustomer travelCustomer = new TravelCustomer(-10L, "a", "a", "a");
        TravelPackage travelPackage = new TravelPackage(-10L, "HotelTest", "Paris, France", new BigDecimal("7000.00"), true);
        travelBooking = new TravelBooking(
                -1L,
                LocalDate.of(2100, 1, 1),
                LocalDate.of(2100, 1, 7),
                1,
                new BigDecimal("7000.00"),
                false,
                travelCustomer,
                travelPackage
        );
    }

    @Test
    @WithMockUser(username = "b", roles = "ADMIN")
    void listCanceledBookingsShouldListCanceledBookings() throws Exception {
        travelBooking.setCancelled(true);
        when(mockTravelBookingRepository.findByCancelledIsTrue()).thenReturn(List.of(travelBooking));

        mockMvc.perform(get("/listcanceled")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].bookedBy").value("a"))
                .andExpect(jsonPath("$[0].bookingId").value(-1))
                .andExpect(jsonPath("$[0].cancelled").value(true));

        verify(travelBookingService, times(1)).listCanceledBookings();

        verify(mockTravelBookingRepository, times(1)).findByCancelledIsTrue();
    }

    @Test
    @WithMockUser(username = "b", roles = "ADMIN")
    void listCanceledBookingsShouldReturnEmptyListWhenNoBookingsExist() throws Exception {
        when(mockTravelBookingRepository.findByCancelledIsTrue()).thenReturn(List.of());

        mockMvc.perform(get("/listcanceled")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));

        verify(travelBookingService, times(1)).listCanceledBookings();
        verify(mockTravelBookingRepository, times(1)).findByCancelledIsTrue();
    }

    @Test
    @WithMockUser(username = "b", roles = "ADMIN")
    void listUpcomingBookingsShouldListUpcomingBookings() throws Exception {
        when(mockTravelBookingRepository.findByStartDateGreaterThanEqualAndCancelledFalse(LocalDate.now())).thenReturn(List.of(travelBooking));

        mockMvc.perform(get("/listupcoming")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].bookedBy").value("a"))
                .andExpect(jsonPath("$[0].bookingId").value(-1))
                .andExpect(jsonPath("$[0].cancelled").value(false));

        verify(travelBookingService, times(1)).listUpcomingBookings();
        verify(mockTravelBookingRepository, times(1)).findByStartDateGreaterThanEqualAndCancelledFalse(LocalDate.now());
    }

    @Test
    @WithMockUser(username = "a", roles = "USER")
    void listCanceledBookingsShouldReturnForbiddenWhenUserRoleIsNotAdmin() throws Exception {

        mockMvc.perform(get("/listupcoming")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "b", roles = "ADMIN")
    void listUpcomingBookingsShouldReturnEmptyListWhenNoBookingsExist() throws Exception {
        when(mockTravelBookingRepository.findByStartDateGreaterThanEqualAndCancelledFalse(LocalDate.now())).thenReturn(List.of());

        mockMvc.perform(get("/listupcoming")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));

        verify(travelBookingService, times(1)).listUpcomingBookings();
        verify(mockTravelBookingRepository, times(1)).findByStartDateGreaterThanEqualAndCancelledFalse(LocalDate.now());
    }

    @Test
    @WithMockUser(username = "b", roles = "ADMIN")
    void listPastBookingsShouldListPastBookings() throws Exception {
        travelBooking.setStartDate(LocalDate.now().minusDays(8));
        travelBooking.setEndDate(LocalDate.now().minusDays(1));
        when(mockTravelBookingRepository.findByEndDateBeforeAndCancelledIsFalse(LocalDate.now())).thenReturn(List.of(travelBooking));

        mockMvc.perform(get("/listpast")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].bookedBy").value("a"))
                .andExpect(jsonPath("$[0].bookingId").value(-1))
                .andExpect(jsonPath("$[0].cancelled").value(false));

        verify(travelBookingService, times(1)).listPastBookings();

        verify(mockTravelBookingRepository, times(1)).findByEndDateBeforeAndCancelledIsFalse(LocalDate.now());
    }

    @Test
    @WithMockUser(username = "b", roles = "ADMIN")
    void listPastBookingsShouldReturnEmptyListWhenNoBookingsExist() throws Exception {
        when(mockTravelBookingRepository.findByEndDateBeforeAndCancelledIsFalse(LocalDate.now())).thenReturn(List.of());

        mockMvc.perform(get("/listpast")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));

        verify(travelBookingService, times(1)).listPastBookings();
        verify(mockTravelBookingRepository, times(1)).findByEndDateBeforeAndCancelledIsFalse(LocalDate.now());
    }

}