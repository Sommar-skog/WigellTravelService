package com.example.WigellTravelService.services;

import com.example.WigellTravelService.dtos.AddTravelPackageDTO;
import com.example.WigellTravelService.dtos.UpdateTravelPackageDTO;
import com.example.WigellTravelService.entities.TravelBooking;
import com.example.WigellTravelService.entities.TravelCustomer;
import com.example.WigellTravelService.entities.TravelPackage;
import com.example.WigellTravelService.repositories.TravelPackageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TravelPackageServiceImpUnitTest {

    @Mock
    private TravelPackageRepository mockTravelPackageRepository;

    @InjectMocks
    private TravelPackageServiceImpl travelPackageService;

    private TravelPackage testTravelPackage;
    private AddTravelPackageDTO testAddTravelPackageDTO;
    private UpdateTravelPackageDTO testUpdateTravelPackageDTO;

    @BeforeEach
    void setUp() {
        travelPackageService = new TravelPackageServiceImpl(mockTravelPackageRepository);

        testAddTravelPackageDTO = new AddTravelPackageDTO("Test Hotel", "Test Destination", new BigDecimal("7000.00"));
        testUpdateTravelPackageDTO = new UpdateTravelPackageDTO(-1L, "Test Hotel Update", "Test Destination Update", new BigDecimal("5000.00"));
        testTravelPackage = new TravelPackage(-1L,"TestHotel", "TestDestination", new BigDecimal("10000.00"), true);
    }

    @Test
    void getAllTravelPackagesAsUserShouldReturnActivePackages() {
        when(mockTravelPackageRepository.findAllByActiveTrue()).thenReturn(List.of(testTravelPackage));

        List<TravelPackage> travelPackages = travelPackageService.getAllTravelPackages("ROLE_USER");
        List<TravelPackage> expectedTravelPackages = List.of(testTravelPackage);

        assertNotNull(travelPackages);
        assertEquals(expectedTravelPackages, travelPackages);
        assertEquals(travelPackages.size(), expectedTravelPackages.size());
        verify(mockTravelPackageRepository).findAllByActiveTrue();
    }

    @Test
    void getAllTravelPackagesAsAdminShouldReturnAllPackages() {
        when(mockTravelPackageRepository.findAll()).thenReturn(List.of(testTravelPackage));

        List<TravelPackage> travelPackages = travelPackageService.getAllTravelPackages("ROLE_ADMIN");
        List<TravelPackage> expectedTravelPackages = List.of(testTravelPackage);

        assertNotNull(travelPackages);
        assertEquals(expectedTravelPackages, travelPackages);
        assertEquals(travelPackages.size(), expectedTravelPackages.size());
        verify(mockTravelPackageRepository).findAll();
    }

    @Test
    void getAllTravelPackagesAsAdminShouldReturnEmptyListWhenNoPackagesExist() {
        when(mockTravelPackageRepository.findAll()).thenReturn(List.of());

        List<TravelPackage> travelPackages = travelPackageService.getAllTravelPackages("ROLE_ADMIN");

        assertNotNull(travelPackages);
        assertTrue(travelPackages.isEmpty());
        verify(mockTravelPackageRepository).findAll();
    }

    @Test
    void getAllTravelPackagesAsUserShouldReturnEmptyListWhenNoActivePackagesExist(){
        when(mockTravelPackageRepository.findAllByActiveTrue()).thenReturn(List.of());

        List<TravelPackage> travelPackages = travelPackageService.getAllTravelPackages("ROLE_USER");

        assertNotNull(travelPackages);
        assertTrue(travelPackages.isEmpty());
        verify(mockTravelPackageRepository).findAllByActiveTrue();
    }

    @Test
    void getAllTravelPackagesAsUserShouldReturnDefaultUserResultWhenRoleIsNotAdmin() {
        when(mockTravelPackageRepository.findAllByActiveTrue()).thenReturn(List.of(testTravelPackage));

        List<TravelPackage> travelPackages = travelPackageService.getAllTravelPackages("ROLE_X");
        List<TravelPackage> expectedTravelPackages = List.of(testTravelPackage);

        assertNotNull(travelPackages);
        assertEquals(expectedTravelPackages, travelPackages);
        assertEquals(travelPackages.size(), expectedTravelPackages.size());
        verify(mockTravelPackageRepository).findAllByActiveTrue();
    }

    @Test
    void addTravelPackageShouldAddNewTravelPackage() {
        when(mockTravelPackageRepository.save(any(TravelPackage.class))).thenReturn(testTravelPackage);

        TravelPackage addTravelPackage = travelPackageService.addTravelPackage(testAddTravelPackageDTO);
        assertNotNull(addTravelPackage);

        ArgumentCaptor<TravelPackage> travelPackageArgumentCaptor = ArgumentCaptor.forClass(TravelPackage.class);
        verify(mockTravelPackageRepository).save(travelPackageArgumentCaptor.capture());

        TravelPackage savedTravelPackage = travelPackageArgumentCaptor.getValue();

        assertNotNull(savedTravelPackage);
        assertEquals(testAddTravelPackageDTO.getHotelName(), savedTravelPackage.getHotelName());
        assertEquals(testAddTravelPackageDTO.getDestination(), savedTravelPackage.getDestination());
        assertEquals(testAddTravelPackageDTO.getWeekPrice(), savedTravelPackage.getWeekPrice());
    }

    @Test
     void addTravelPackageShouldThrowExceptionWhenHotelNameIsNull() {
        AddTravelPackageDTO dto = new AddTravelPackageDTO(null, "Test Destination", new BigDecimal("7000.00"));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> travelPackageService.addTravelPackage(dto));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Hotel name is required", exception.getReason());
        verifyNoInteractions(mockTravelPackageRepository);
    }

    @Test
    void addTravelPackageShouldThrowExceptionWhenHotelNameIsBlank() {
        AddTravelPackageDTO dto = new AddTravelPackageDTO(" ", "Test Destination", new BigDecimal("7000.00"));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> travelPackageService.addTravelPackage(dto));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Hotel name is required", exception.getReason());
        verifyNoInteractions(mockTravelPackageRepository);
    }

    @Test
     void addTravelPackageShouldThrowExceptionWhenHotelNameIsMoreThan50Characters(){
        String stringWithMoreThan50Characters = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

        AddTravelPackageDTO dto = new AddTravelPackageDTO(stringWithMoreThan50Characters, "Test Destination", new BigDecimal("7000.00"));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> travelPackageService.addTravelPackage(dto));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Hotel name must be 50 characters or less", exception.getReason());
        verifyNoInteractions(mockTravelPackageRepository);
    }

    @Test
    void addTravelPackageShouldThrowExceptionWhenDestinationIsNull(){
        AddTravelPackageDTO dto = new AddTravelPackageDTO("hotel", null, new BigDecimal("7000.00"));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> travelPackageService.addTravelPackage(dto));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Destination is required", exception.getReason());
        verifyNoInteractions(mockTravelPackageRepository);
    }

    @Test
    void addTravelPackageShouldThrowExceptionWhenDestinationIsBlank(){
        AddTravelPackageDTO dto = new AddTravelPackageDTO("hotel", " ", new BigDecimal("7000.00"));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> travelPackageService.addTravelPackage(dto));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Destination is required", exception.getReason());
        verifyNoInteractions(mockTravelPackageRepository);
    }

    @Test
    void addTravelPackageShouldThrowExceptionWhenDestinationIsMoreThan50Characters() {
        String stringWithMoreThan50Characters = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

        AddTravelPackageDTO dto = new AddTravelPackageDTO("Hotel", stringWithMoreThan50Characters, new BigDecimal("7000.00"));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> travelPackageService.addTravelPackage(dto));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Destination must be 50 characters or less", exception.getReason());
        verifyNoInteractions(mockTravelPackageRepository);
    }

    @Test
    void addTravelPackageShouldThrowExceptionWhenWeekPriceIsNull() {
        AddTravelPackageDTO dto = new AddTravelPackageDTO("hotel", "destination", null);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> travelPackageService.addTravelPackage(dto));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Week price is required", exception.getReason());
        verifyNoInteractions(mockTravelPackageRepository);
    }

    @Test
    void addTravelPackageShouldThrowExceptionWhenWeekPriceIsLessThan0_01(){
        BigDecimal weekPrice = new BigDecimal("0.00");
        AddTravelPackageDTO dto = new AddTravelPackageDTO( "Hotel", "Destination", weekPrice);

        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class, () -> travelPackageService.addTravelPackage(dto));

        assertEquals(HttpStatus.BAD_REQUEST, responseStatusException.getStatusCode());
        assertEquals("Week price must be between 0.01 and 99999.99", responseStatusException.getReason());
        verifyNoInteractions(mockTravelPackageRepository);
    }

    @Test
    void addTravelPackageShouldThrowExceptionWhenWeekPriceIsMoreThan99999_99() {
        BigDecimal weekPrice = new BigDecimal("1000000.00");
        AddTravelPackageDTO dto = new AddTravelPackageDTO( "Hotel", "Destination", weekPrice);

        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class, () -> travelPackageService.addTravelPackage(dto));

        assertEquals(HttpStatus.BAD_REQUEST, responseStatusException.getStatusCode());
        assertEquals("Week price must be between 0.01 and 99999.99", responseStatusException.getReason());
        verifyNoInteractions(mockTravelPackageRepository);
    }

    @Test
    void updateTravelPackageShouldUpdateTravelPackage() {
        when(mockTravelPackageRepository.findById(testUpdateTravelPackageDTO.getTravelPackageId())).thenReturn(Optional.of(testTravelPackage));
        when(mockTravelPackageRepository.save(testTravelPackage)).thenReturn(testTravelPackage);

        TravelPackage updatedTravelPackage = travelPackageService.updateTravelPackage(testUpdateTravelPackageDTO);

        assertNotNull(updatedTravelPackage);

        assertEquals(testUpdateTravelPackageDTO.getHotelName(), updatedTravelPackage.getHotelName());
        assertEquals(testUpdateTravelPackageDTO.getDestination(), updatedTravelPackage.getDestination());
        assertEquals(testUpdateTravelPackageDTO.getWeekPrice(), updatedTravelPackage.getWeekPrice());
        assertEquals(testUpdateTravelPackageDTO.getTravelPackageId(), updatedTravelPackage.getTravelPackageId());

        verify(mockTravelPackageRepository).save(testTravelPackage);
        verify(mockTravelPackageRepository).findById(testUpdateTravelPackageDTO.getTravelPackageId());
    }

    @Test
    void updateTravelPackageShouldNotUpdateHotelNameWhenHotelNameIsNull(){
        TravelPackage travelPackage = new TravelPackage(-2L, "Hotel", "Destination", new BigDecimal("7000.00"), true);
        UpdateTravelPackageDTO dto  = new UpdateTravelPackageDTO(-2L, null, "Destination Updated", new BigDecimal("8000.00"));

        when(mockTravelPackageRepository.findById(dto.getTravelPackageId())).thenReturn(Optional.of(travelPackage));
        when(mockTravelPackageRepository.save(any(TravelPackage.class))).thenReturn(travelPackage);


        TravelPackage updatedTravelPackage = travelPackageService.updateTravelPackage(dto);

        assertNotNull(updatedTravelPackage);
        verify(mockTravelPackageRepository).findById(dto.getTravelPackageId());
        assertEquals("Hotel", updatedTravelPackage.getHotelName());
        assertEquals("Destination Updated", updatedTravelPackage.getDestination());
        assertEquals(new BigDecimal("8000.00"), updatedTravelPackage.getWeekPrice());
        assertSame(travelPackage, updatedTravelPackage);
        verify(mockTravelPackageRepository).save(updatedTravelPackage);
    }

    @Test
    void upUpdateTravelPackageShouldThrowExceptionWhenUpdateTravelPackageNotFound() {
        Long nonExistingTravelPackageId = -2L;
        UpdateTravelPackageDTO dto = new UpdateTravelPackageDTO(nonExistingTravelPackageId, "hotelUpdate", "updatedDestination", new BigDecimal("1000.00"));
        when(mockTravelPackageRepository.findById(nonExistingTravelPackageId)).thenReturn(Optional.empty());

        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class, () -> {
            travelPackageService.updateTravelPackage(dto);
        });

        assertEquals(HttpStatus.NOT_FOUND, responseStatusException.getStatusCode());
        assertEquals("TravelPackage with id -2 not found", responseStatusException.getReason());
        verify(mockTravelPackageRepository).findById(nonExistingTravelPackageId);
        verify(mockTravelPackageRepository, never()).save(any());
    }

    @Test
    void updateTravelPackageShouldThrowExceptionWhenHotelNameIsMoreThan50Characters() {
        String moreThanFiftyCharacters = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        UpdateTravelPackageDTO dto = new UpdateTravelPackageDTO(-2L, moreThanFiftyCharacters, "updatedDestination", new BigDecimal("1000.00"));

        when(mockTravelPackageRepository.findById(dto.getTravelPackageId())).thenReturn(Optional.of(testTravelPackage));

        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class, () -> {
            travelPackageService.updateTravelPackage(dto);
        });

        assertEquals(HttpStatus.BAD_REQUEST, responseStatusException.getStatusCode());
        assertEquals("Hotel name must be 50 characters or less", responseStatusException.getReason());
        verify(mockTravelPackageRepository).findById(dto.getTravelPackageId());
        verify(mockTravelPackageRepository, never()).save(any());
    }

    @Test
    void updateTravelPackageShouldThrowExceptionWhenHotelNameIsBlank(){
        String blankHotelName = " ";
        UpdateTravelPackageDTO dto = new UpdateTravelPackageDTO(-2L, blankHotelName, "updatedDestination", new BigDecimal("1000.00"));

        when(mockTravelPackageRepository.findById(dto.getTravelPackageId())).thenReturn(Optional.of(testTravelPackage));

        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class, () -> {
            travelPackageService.updateTravelPackage(dto);
        });

        assertEquals(HttpStatus.BAD_REQUEST, responseStatusException.getStatusCode());
        assertEquals("Hotel name cannot be blank", responseStatusException.getReason());
        verify(mockTravelPackageRepository).findById(dto.getTravelPackageId());
        verify(mockTravelPackageRepository, never()).save(any());
    }

    @Test
    void updateTravelPackageShouldNotUpdateDestinationWhenDestinationIsNull(){
        TravelPackage travelPackage = new TravelPackage(-2L, "Hotel", "Destination", new BigDecimal("7000.00"), true);
        UpdateTravelPackageDTO dto  = new UpdateTravelPackageDTO(-2L, "Hotel Updated", null, new BigDecimal("8000.00"));

        when(mockTravelPackageRepository.findById(dto.getTravelPackageId())).thenReturn(Optional.of(travelPackage));
        when(mockTravelPackageRepository.save(any(TravelPackage.class))).thenReturn(travelPackage);


        TravelPackage updatedTravelPackage = travelPackageService.updateTravelPackage(dto);

        assertNotNull(updatedTravelPackage);
        verify(mockTravelPackageRepository).findById(dto.getTravelPackageId());
        assertEquals("Hotel Updated", updatedTravelPackage.getHotelName());
        assertEquals("Destination", updatedTravelPackage.getDestination());
        assertEquals(new BigDecimal("8000.00"), updatedTravelPackage.getWeekPrice());
        assertSame(travelPackage, updatedTravelPackage);
        verify(mockTravelPackageRepository).save(updatedTravelPackage);
    }

    @Test
    void updateTravelPackageShouldThrowExceptionWhenDestinationIsMoreThan50Characters() {
        String moreThanFiftyCharacters = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        UpdateTravelPackageDTO dto = new UpdateTravelPackageDTO(-2L, "Hotel", moreThanFiftyCharacters, new BigDecimal("1000.00"));

        when(mockTravelPackageRepository.findById(dto.getTravelPackageId())).thenReturn(Optional.of(testTravelPackage));

        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class, () -> {
            travelPackageService.updateTravelPackage(dto);
        });

        assertEquals(HttpStatus.BAD_REQUEST, responseStatusException.getStatusCode());
        assertEquals("Destination must be 50 characters or less", responseStatusException.getReason());
        verify(mockTravelPackageRepository).findById(dto.getTravelPackageId());
        verify(mockTravelPackageRepository, never()).save(any());
    }

    @Test
    void updateTravelPackageShouldThrowExceptionWhenDestinationIsBlank(){
        String blankString = " ";
        UpdateTravelPackageDTO dto = new UpdateTravelPackageDTO(-2L, "Hotel", blankString, new BigDecimal("1000.00"));

        when(mockTravelPackageRepository.findById(dto.getTravelPackageId())).thenReturn(Optional.of(testTravelPackage));

        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class, () -> {
            travelPackageService.updateTravelPackage(dto);
        });

        assertEquals(HttpStatus.BAD_REQUEST, responseStatusException.getStatusCode());
        assertEquals("Destination cannot be blank", responseStatusException.getReason());
        verify(mockTravelPackageRepository).findById(dto.getTravelPackageId());
        verify(mockTravelPackageRepository, never()).save(any());
    }

    @Test
    void updateTravelPackageShouldNotUpdateWeekPriceWhenWeekPriceIsNull(){
        TravelPackage travelPackage = new TravelPackage(-2L, "Hotel", "Destination", new BigDecimal("7000.00"), true);
        UpdateTravelPackageDTO dto  = new UpdateTravelPackageDTO(-2L, "Hotel Updated", "Destination Updated", null);

        when(mockTravelPackageRepository.findById(dto.getTravelPackageId())).thenReturn(Optional.of(travelPackage));
        when(mockTravelPackageRepository.save(any(TravelPackage.class))).thenReturn(travelPackage);


        TravelPackage updatedTravelPackage = travelPackageService.updateTravelPackage(dto);

        assertNotNull(updatedTravelPackage);
        verify(mockTravelPackageRepository).findById(dto.getTravelPackageId());
        assertEquals("Hotel Updated", updatedTravelPackage.getHotelName());
        assertEquals("Destination Updated", updatedTravelPackage.getDestination());
        assertEquals(new BigDecimal("7000.00"), updatedTravelPackage.getWeekPrice());
        assertSame(travelPackage, updatedTravelPackage);
        verify(mockTravelPackageRepository).save(updatedTravelPackage);
    }

    @Test
    void updateTravelPackageShouldThrowExceptionWhenWhenWeekPriceIsLessThan0_01(){
        BigDecimal weekPrice = new BigDecimal("0.00");
        UpdateTravelPackageDTO dto = new UpdateTravelPackageDTO(-2L, "Hotel", "Destination", weekPrice);

        when(mockTravelPackageRepository.findById(dto.getTravelPackageId())).thenReturn(Optional.of(testTravelPackage));

        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class, () -> {
            travelPackageService.updateTravelPackage(dto);
        });

        assertEquals(HttpStatus.BAD_REQUEST, responseStatusException.getStatusCode());
        assertEquals("Week price must be between 0.01 and 99999.99", responseStatusException.getReason());
        verify(mockTravelPackageRepository).findById(dto.getTravelPackageId());
        verify(mockTravelPackageRepository, never()).save(any());
    }

    @Test
    void updateTravelPackageShouldThrowExceptionWhenWeekPriceIsMoreThan99999_99() {
        BigDecimal weekPrice = new BigDecimal("1000000.00");
        UpdateTravelPackageDTO dto = new UpdateTravelPackageDTO(-2L, "Hotel", "Destination", weekPrice);

        when(mockTravelPackageRepository.findById(dto.getTravelPackageId())).thenReturn(Optional.of(testTravelPackage));

        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class, () -> {
            travelPackageService.updateTravelPackage(dto);
        });

        assertEquals(HttpStatus.BAD_REQUEST, responseStatusException.getStatusCode());
        assertEquals("Week price must be between 0.01 and 99999.99", responseStatusException.getReason());
        verify(mockTravelPackageRepository).findById(dto.getTravelPackageId());
        verify(mockTravelPackageRepository, never()).save(any());
    }

    @Test
    void updateTravelPackageShouldThrowExceptionWhenUpdateTravelPackageNotFound() {
        Long nonExistingTravelPackageId = -2L;
        UpdateTravelPackageDTO dto = new UpdateTravelPackageDTO(nonExistingTravelPackageId, "hotelUpdate", "updatedDestination", new BigDecimal("1000.00"));
        when(mockTravelPackageRepository.findById(nonExistingTravelPackageId)).thenReturn(Optional.empty());

        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class, () -> {
            travelPackageService.updateTravelPackage(dto);
        });

        assertEquals(HttpStatus.NOT_FOUND, responseStatusException.getStatusCode());
        assertEquals("TravelPackage with id -2 not found", responseStatusException.getReason());
        verify(mockTravelPackageRepository).findById(nonExistingTravelPackageId);
        verify(mockTravelPackageRepository, never()).save(any());
    }

    @Test
    void updateTravelPackageShouldThrowExceptionWhenUpdateTravelPackageIdIsNull() {
        UpdateTravelPackageDTO dto = new UpdateTravelPackageDTO(null, "hotelUpdate", "updatedDestination", new BigDecimal("1000.00"));

        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class, () -> {
            travelPackageService.updateTravelPackage(dto);
        });

        assertEquals(HttpStatus.BAD_REQUEST, responseStatusException.getStatusCode());
        assertEquals("travelPackageId is required", responseStatusException.getReason());
        verify(mockTravelPackageRepository, never()).findById(any());
        verify(mockTravelPackageRepository, never()).save(any());
    }

    @Test
    void  updateTravelPackageShouldNotSaveWhenNoUpdatableFieldsProvided(){
        UpdateTravelPackageDTO dto = new UpdateTravelPackageDTO(-1L, null, null, null);

        when(mockTravelPackageRepository.findById(dto.getTravelPackageId())).thenReturn(Optional.of(testTravelPackage));

        TravelPackage updatedTravelPackage = travelPackageService.updateTravelPackage(dto);

        verify(mockTravelPackageRepository, times (1)).findById(-1L);
        verify(mockTravelPackageRepository, never()).save(any());

    }


    @Test
    void removeTravelPackageShouldSetActiveToFalse() {
        when(mockTravelPackageRepository.findById(testTravelPackage.getTravelPackageId())).thenReturn(Optional.of(testTravelPackage));
        when(mockTravelPackageRepository.save(any(TravelPackage.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TravelPackage updated = travelPackageService.removeTravelPackage(testTravelPackage.getTravelPackageId());

        assertNotNull(updated);
        assertFalse(updated.isActive());
        verify(mockTravelPackageRepository).findById(testTravelPackage.getTravelPackageId());
        verify(mockTravelPackageRepository).save(any(TravelPackage.class));
    }

    @Test
    void removeTravelPackageShouldCancelAllBookings(){
        TravelBooking booking = new TravelBooking(-20L, LocalDate.of(2100,1,1), LocalDate.of(2100,1,7), 1, new BigDecimal("7000.00"), false,
                new TravelCustomer(), new TravelPackage());
        testTravelPackage.setBookingList(List.of(booking));
        when(mockTravelPackageRepository.findById(testTravelPackage.getTravelPackageId())).thenReturn(Optional.of(testTravelPackage));
        when(mockTravelPackageRepository.save(any(TravelPackage.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TravelPackage updated = travelPackageService.removeTravelPackage(testTravelPackage.getTravelPackageId());

        assertTrue(updated.getBookingList().stream().allMatch(TravelBooking::isCancelled));
        assertNotNull(updated);
        verify(mockTravelPackageRepository).findById(testTravelPackage.getTravelPackageId());
        verify(mockTravelPackageRepository).save(any(TravelPackage.class));
    }

    @Test
    void removeTravelPackageWithEmptyBookingListShouldNotFail(){

        when(mockTravelPackageRepository.findById(testTravelPackage.getTravelPackageId())).thenReturn(Optional.of(testTravelPackage));
        when(mockTravelPackageRepository.save(any(TravelPackage.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TravelPackage updated = travelPackageService.removeTravelPackage(testTravelPackage.getTravelPackageId());

        assertTrue(updated.getBookingList().isEmpty());
        assertNotNull(updated);
        verify(mockTravelPackageRepository).findById(testTravelPackage.getTravelPackageId());
        verify(mockTravelPackageRepository).save(any(TravelPackage.class));
    }

    @Test
    void removeTravelPackageShouldThrowExceptionWhenTravelPackageDoesNotExist() {
        Long nonExistingTravelPackageId = -2L;
        when(mockTravelPackageRepository.findById(nonExistingTravelPackageId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            travelPackageService.removeTravelPackage(nonExistingTravelPackageId);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("TravelPackage with id -2 not found", exception.getReason());
        verify(mockTravelPackageRepository).findById(nonExistingTravelPackageId);
        verify(mockTravelPackageRepository, never()).save(any());
    }

    @Test
    void removeTravelPackageShouldNotSaveWhenPackageIsInactive() {
       testTravelPackage.setActive(false);

        when(mockTravelPackageRepository.findById(testTravelPackage.getTravelPackageId())).thenReturn(Optional.of(testTravelPackage));

        TravelPackage remove = travelPackageService.removeTravelPackage(testTravelPackage.getTravelPackageId());

        assertFalse(remove.isActive());
        verify(mockTravelPackageRepository, times (1)).findById(testTravelPackage.getTravelPackageId());
        verify(mockTravelPackageRepository, never()).save(any());
    }

    @Test
    void getTravelPackageByIdShouldReturnTravelPackage() {
        when(mockTravelPackageRepository.findById(testTravelPackage.getTravelPackageId())).thenReturn(Optional.of(testTravelPackage));

        TravelPackage travelPackageServiceTravelPackageById = travelPackageService.getTravelPackageById(testTravelPackage.getTravelPackageId());

        assertNotNull(travelPackageServiceTravelPackageById);
        assertEquals(travelPackageServiceTravelPackageById, testTravelPackage);
        verify(mockTravelPackageRepository).findById(testTravelPackage.getTravelPackageId());
    }

    @Test
    void getTravelPackageByIdShouldThrowExceptionWhenTravelPackageDoesNotExist() {
        Long nonExistingTravelPackageId = -2L;
        when(mockTravelPackageRepository.findById(nonExistingTravelPackageId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            travelPackageService.getTravelPackageById(nonExistingTravelPackageId);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("TravelPackage with id -2 not found", exception.getReason());
        verify(mockTravelPackageRepository).findById(nonExistingTravelPackageId);
    }
}