package com.example.WigellTravelService.controllers;

import com.example.WigellTravelService.dtos.CancelBookingDTO;
import com.example.WigellTravelService.dtos.CreateBookingDTO;
import com.example.WigellTravelService.dtos.TravelBookingDTO;
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
import java.util.List;
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
    private TravelPackage travelPackage;

    @BeforeEach
    void setUp() {
        travelCustomer = new TravelCustomer(-10L, "a", "a", "a");
        travelPackage = new TravelPackage(-10L, "HotelTest", "Paris, France", new BigDecimal("7000.00"), true);
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

        when(mockTravelBookingRepository.save(any(TravelBooking.class))).thenReturn(travelBooking);
        when(mockTravelCustomerRepository.findByUsername("a")).thenReturn(Optional.of(travelCustomer));
    }


    @Test
    @WithMockUser(username = "a", roles = "USER")
    void bookTripShouldBookTrip() throws Exception {
        CreateBookingDTO createBookingDTO = new CreateBookingDTO(1L, LocalDate.of(2100, 1, 1), 1);

        mockMvc.perform(post("/booktrip")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(createBookingDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.bookedBy").value("a"))
                .andExpect(jsonPath("$.bookingId").value(-1))
                .andExpect(jsonPath("$.hotelName").value("HotelTest"))
                .andExpect(jsonPath("$.destination").value("Paris, France"))
                .andExpect(jsonPath("$.startDate").value("20100-01-01"))
                .andExpect(jsonPath("$.endDate").value("20100-01-07"))
                .andExpect(jsonPath("$.weeks").value(1))
                .andExpect(jsonPath("$.totalPriceInSek").value(7000.00))
                .andExpect(jsonPath("$.totalPriceInEuro").exists())
                .andExpect(jsonPath("$.cancelled").value(false));

        ArgumentCaptor<CreateBookingDTO> dtoCaptor = ArgumentCaptor.forClass(CreateBookingDTO.class);
        ArgumentCaptor<Principal> principalCaptor = ArgumentCaptor.forClass(Principal.class);

        verify(travelBookingService).bookTrip(dtoCaptor.capture(), principalCaptor.capture());

        CreateBookingDTO capturedDto = dtoCaptor.getValue();
        Principal capturedPrincipal = principalCaptor.getValue();

        assertEquals(1L, capturedDto.getTravelPackageId());
        assertEquals(1, capturedDto.getNumberOfWeeks());
        assertEquals(LocalDate.of(2100,1,1), capturedDto.getStartDate());
        assertEquals("a", capturedPrincipal.getName());

        verify(travelCustomerService, times(1))
                .findTravelCustomerByUsername("a");

        verify(mockTravelBookingRepository, times(1)).save(any(TravelBooking.class));
        verify(mockTravelCustomerRepository, times(1)).findByUsername("a");
    }

    @Test
    @WithMockUser(username = "a", roles = "USER")
    void bookTripWithTravelPackageIdNullShouldThrowException() throws Exception {
        CreateBookingDTO createBookingDTO = new CreateBookingDTO(null, LocalDate.of(2011, 1, 1), 1);

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
        CreateBookingDTO createBookingDTO = new CreateBookingDTO(1L, LocalDate.of(2100, 1, 1), null);

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
        CreateBookingDTO createBookingDTO = new CreateBookingDTO(1L, LocalDate.of(2100, 1, 1), 1);

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
    @WithMockUser(username = "a", roles = "USER")
    void cancelTripShouldCancelTrip() throws Exception {
        CancelBookingDTO cancelBookingDTO = new CancelBookingDTO(-1L);

        when(mockTravelBookingRepository.findById(-1L)).thenReturn(Optional.of(travelBooking));
        when(mockTravelBookingRepository.save(any(TravelBooking.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));


        mockMvc.perform(put("/canceltrip")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cancelBookingDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.bookedBy").value("a"))
                .andExpect(jsonPath("$.bookingId").value(-1))
                .andExpect(jsonPath("$.hotelName").value("HotelTest"))
                .andExpect(jsonPath("$.destination").value("Paris, France"))
                .andExpect(jsonPath("$.startDate").value("2100-01-01"))
                .andExpect(jsonPath("$.weeks").value(1))
                .andExpect(jsonPath("$.totalPriceInSek").value(7000.00))
                .andExpect(jsonPath("$.totalPriceInEuro").exists())
                .andExpect(jsonPath("$.cancelled").value(true));

        ArgumentCaptor<CancelBookingDTO> dtoCaptor = ArgumentCaptor.forClass(CancelBookingDTO.class);
        ArgumentCaptor<Principal> principalCaptor = ArgumentCaptor.forClass(Principal.class);

        verify(travelBookingService).cancelTrip(dtoCaptor.capture(), principalCaptor.capture());

        CancelBookingDTO capturedDto = dtoCaptor.getValue();
        Principal capturedPrincipal = principalCaptor.getValue();

        assertEquals(-1L, capturedDto.getBookingId());
        assertEquals("a", capturedPrincipal.getName());

        verify(mockTravelBookingRepository,times(1)).findById(-1L);
        verify(mockTravelBookingRepository, times(1)).save(any(TravelBooking.class));
    }

    @Test
    @WithMockUser(username = "a", roles = "USER")
    void cancelTripShouldCancelTripShouldThrowExceptionWhenBookingIdDoseNotExist() throws Exception {
        CancelBookingDTO dto = new CancelBookingDTO(-10L);

        when(mockTravelBookingRepository.findById(dto.getBookingId())).thenReturn(Optional.empty());

        mockMvc.perform(put("/canceltrip")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound())
                .andExpect(status().reason("Booking not found"));

        verify(travelBookingService, times(1))
                .cancelTrip(any(CancelBookingDTO.class),any(Principal.class));

        verify(mockTravelBookingRepository, never()).save(any());
        verify(mockTravelBookingRepository, times(1)).findById(-10L);
    }

    @Test
    @WithMockUser(username = "a", roles = "USER")
    void cancelTripShouldCancelTripShouldThrowExceptionWhenBookingIdIsNull() throws Exception {
        CancelBookingDTO dto = new CancelBookingDTO(null);

        mockMvc.perform(put("/canceltrip")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("Booking id is required"));

        verify(travelBookingService, times(1))
                .cancelTrip(any(CancelBookingDTO.class),any(Principal.class));

        verify(mockTravelBookingRepository, never()).save(any());
        verify(mockTravelBookingRepository, never()).findById(any());

    }

    @Test
    @WithMockUser(username = "b", roles = "USER")
    void cancelTripShouldCancelTripShouldThrowExceptionWhenWrongCustomer() throws Exception {
        CancelBookingDTO dto = new CancelBookingDTO(-1L);

        when(mockTravelBookingRepository.findById(-1L)).thenReturn(Optional.of(travelBooking));

        mockMvc.perform(put("/canceltrip")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isForbidden())
                .andExpect(status().reason("You are not the owner of the booking"));

        verify(travelBookingService, times(1))
                .cancelTrip(any(CancelBookingDTO.class),any(Principal.class));

        verify(mockTravelBookingRepository, never()).save(any());
        verify(mockTravelBookingRepository, times(1)).findById(-1L);
    }

    @Test
    @WithMockUser(username = "a", roles = "USER")
    void cancelTripShouldCancelTripShouldThrowExceptionWhenTripAlreadyStarted() throws Exception {
        TravelBooking booking = new TravelBooking();
        booking.setBookingId(-5L);
        booking.setStartDate(LocalDate.now());
        booking.setEndDate(LocalDate.now().plusDays(7));
        booking.setTravelCustomer(travelCustomer);
        booking.setTravelPackage(travelPackage);
        CancelBookingDTO dto = new CancelBookingDTO(-5L);

        when(mockTravelBookingRepository.findById(-5L)).thenReturn(Optional.of(booking));

        mockMvc.perform(put("/canceltrip")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("Trip already started or finished"));

        verify(travelBookingService, times(1))
                .cancelTrip(any(CancelBookingDTO.class),any(Principal.class));

        verify(mockTravelBookingRepository, never()).save(any());
        verify(mockTravelBookingRepository, times(1)).findById(eq(-5L));

    }

    @Test
    @WithMockUser(username = "a", roles = "USER")
    void cancelTripShouldCancelTripShouldThrowExceptionWhenTripAlreadyFinished() throws Exception {
        TravelBooking booking = new TravelBooking();
        booking.setBookingId(-5L);
        booking.setStartDate(LocalDate.now().minusDays(8));
        booking.setEndDate(LocalDate.now().minusDays(1));
        booking.setTravelCustomer(travelCustomer);
        booking.setTravelPackage(travelPackage);
        CancelBookingDTO dto = new CancelBookingDTO(-5L);

        when(mockTravelBookingRepository.findById(-5L)).thenReturn(Optional.of(booking));

        mockMvc.perform(put("/canceltrip")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("Trip already started or finished"));

        verify(travelBookingService, times(1))
                .cancelTrip(any(CancelBookingDTO.class),any(Principal.class));

        verify(mockTravelBookingRepository, never()).save(any());
        verify(mockTravelBookingRepository, times(1)).findById(eq(-5L));
    }

    @Test
    @WithMockUser(username = "a", roles = "USER")
    void getMyBookingsShouldReturnsBookingsForLoggedInUser() throws Exception {
        when(mockTravelBookingRepository.findByTravelCustomerUsernameAndCancelledFalse("a")).thenReturn(List.of(travelBooking));

        mockMvc.perform(get("/mybookings")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].bookedBy").value("a"))
                .andExpect(jsonPath("$[0].bookingId").value(-1))
                .andExpect(jsonPath("$[0].cancelled").value(false));

        ArgumentCaptor<Principal> principalCaptor = ArgumentCaptor.forClass(Principal.class);

        verify(travelBookingService).getMyBookings(principalCaptor.capture());

        Principal capturedPrincipal = principalCaptor.getValue();

        assertEquals("a", capturedPrincipal.getName());

        verify(travelBookingService, times(1))
                .getMyBookings(any(Principal.class));

        verify(mockTravelBookingRepository, times(1)).findByTravelCustomerUsernameAndCancelledFalse("a");
    }

    @Test
    @WithMockUser(username = "a", roles = "USER")
    void getMyBookingsShouldReturnsEmptyListWhenNoBookingsExist() throws Exception {

        when(mockTravelBookingRepository.findByTravelCustomerUsernameAndCancelledFalse("a")).thenReturn(List.of());

        mockMvc.perform(get("/mybookings")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                        .andExpect(jsonPath("$.length()").value(0));

        verify(travelBookingService, times(1))
                .getMyBookings(any(Principal.class));

        verify(mockTravelBookingRepository, times(1)).findByTravelCustomerUsernameAndCancelledFalse("a");
    }
}