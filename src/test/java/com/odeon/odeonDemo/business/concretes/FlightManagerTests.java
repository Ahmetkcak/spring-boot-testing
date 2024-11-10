package com.odeon.odeonDemo.business.concretes;

import com.odeon.odeonDemo.business.dtos.requests.CreateFlightRequest;
import com.odeon.odeonDemo.business.dtos.responses.CreatedFlightResponse;
import com.odeon.odeonDemo.business.dtos.responses.GetAllFlightResponse;
import com.odeon.odeonDemo.business.rules.FlightBusinessRules;
import com.odeon.odeonDemo.core.utilities.exceptions.types.BusinessException;
import com.odeon.odeonDemo.core.utilities.mapping.ModelMapperManager;
import com.odeon.odeonDemo.core.utilities.mapping.ModelMapperService;
import com.odeon.odeonDemo.dataAccess.abstracts.FlightRepository;
import com.odeon.odeonDemo.entities.Flight;
import org.junit.jupiter.api.*;
import org.modelmapper.ModelMapper;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FlightManagerTests {

    private FlightManager flightManager;
    private FlightRepository flightRepository;

    @BeforeEach
    void setUp() {
        flightRepository = Mockito.mock(FlightRepository.class);
        ModelMapper modelMapper = new ModelMapper();
        ModelMapperService modelMapperService = new ModelMapperManager(modelMapper);
        FlightBusinessRules flightBusinessRules = new FlightBusinessRules(flightRepository);
        flightManager = new FlightManager(flightRepository, modelMapperService, flightBusinessRules);
    }

    @AfterEach
    void cleanUp() {
    }

    @Test
    public void FlightManager_AddFlightSuccessfully_ShouldReturnCreatedFlightResponse() {
        Flight flight = Flight.builder()
                .departureLocation("Istanbul")
                .arrivalLocation("Ankara")
                .departureDate(LocalDateTime.of(2024, 11, 10, 14, 30))
                .arrivalDate(LocalDateTime.of(2024, 11, 10, 16, 45))
                .build();

        CreateFlightRequest newFlight = CreateFlightRequest.builder()
                .departureLocation("Istanbul")
                .arrivalLocation("Ankara")
                .departureDate(LocalDateTime.of(2024, 11, 10, 14, 30))
                .arrivalDate(LocalDateTime.of(2024, 11, 10, 16, 45))
                .build();

        Mockito.when(flightRepository.save(Mockito.any(Flight.class))).thenReturn(flight);
        CreatedFlightResponse result = flightManager.addFlight(newFlight);

        assertNotNull(result);
    }

    @Test
    public void FlightManager_AddFlightWithExistingConflict_ShouldThrowBusinessException() {
        Flight existingFlight = Flight.builder()
                .departureLocation("Istanbul")
                .arrivalLocation("Ankara")
                .departureDate(LocalDateTime.of(2024, 11, 10, 14, 30))
                .arrivalDate(LocalDateTime.of(2024, 11, 10, 16, 45))
                .build();

        CreateFlightRequest newFlight = CreateFlightRequest.builder()
                .departureLocation("Istanbul")
                .arrivalLocation("Ankara")
                .departureDate(LocalDateTime.of(2024, 11, 10, 14, 45))
                .arrivalDate(LocalDateTime.of(2024, 11, 10, 16, 30))
                .build();


        Mockito.when(flightRepository.findAll()).thenReturn(List.of(existingFlight));


        assertThrows(BusinessException.class, () -> flightManager.addFlight(newFlight));
    }


    @Test
    public void FlightManager_GetAllFlightsSuccessfully_ShouldReturnListOfGetAllFlightResponse() {
        Flight flight = Flight.builder()
                .departureLocation("Istanbul")
                .arrivalLocation("Ankara")
                .departureDate(LocalDateTime.of(2024, 11, 10, 14, 30))
                .arrivalDate(LocalDateTime.of(2024, 11, 10, 16, 45))
                .build();

        Mockito.when(flightRepository.findAll()).thenReturn(List.of(flight));

        List<GetAllFlightResponse> result = flightManager.getAllFlights();

        assertFalse(result.isEmpty());
    }

    @Test
    public void FlightManager_DeleteFlight_ShouldDeleteFlight(){
        Flight flight = Flight.builder()
                .departureLocation("Istanbul")
                .arrivalLocation("Ankara")
                .departureDate(LocalDateTime.of(2024, 11, 10, 14, 30))
                .arrivalDate(LocalDateTime.of(2024, 11, 10, 16, 45))
                .build();

        Mockito.when(flightRepository.findById(1)).thenReturn(Optional.of(flight));

        flightManager.deleteFlight(1);

        Mockito.verify(flightRepository, Mockito.times(1)).deleteById(1);
    }
}
