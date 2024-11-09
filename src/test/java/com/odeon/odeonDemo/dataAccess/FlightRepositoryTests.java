package com.odeon.odeonDemo.dataAccess;

import com.odeon.odeonDemo.dataAccess.abstracts.FlightRepository;
import com.odeon.odeonDemo.entities.Flight;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FlightRepositoryTests {

    @Autowired
    private FlightRepository flightRepository;

    @Test
    public void FlightRepository_SaveAll_ReturnSavedFlights() {
        // Arrange
        Flight flight = Flight.builder()
                .departureLocation("Istanbul")
                .arrivalLocation("Ankara")
                .departureDate(LocalDateTime.of(2024, 11, 10, 14, 30))
                .arrivalDate(LocalDateTime.of(2024, 11, 10, 16, 45))
                .build();


        // Act
        Flight savedFlight = flightRepository.save(flight);

        // Assert
        Assertions.assertThat(savedFlight).isNotNull();
        Assertions.assertThat(savedFlight.getId()).isGreaterThan(0);
    }


    @Test
    public void FlightRepository_GetAll_ReturnAllFlights() {
        // Arrange
        Flight flight1 = Flight.builder()
                .departureLocation("Istanbul")
                .arrivalLocation("Ankara")
                .departureDate(LocalDateTime.of(2024, 10, 10, 14, 30))
                .arrivalDate(LocalDateTime.of(2024, 10, 10, 16, 45))
                .build();

        Flight flight2 = Flight.builder()
                .departureLocation("Istanbul")
                .arrivalLocation("Izmir")
                .departureDate(LocalDateTime.of(2024, 11, 10, 14, 30))
                .arrivalDate(LocalDateTime.of(2024, 11, 10, 16, 45))
                .build();

        flightRepository.save(flight1);
        flightRepository.save(flight2);
        
        List<Flight> flightList = flightRepository.findAll();

        Assertions.assertThat(flightList).isNotNull();
        Assertions.assertThat(flightList.size()).isEqualTo(2);
    }



    @Test
    public void FlightRepository_FindById_ReturnFlight() {
        Flight flight = Flight.builder()
                .departureLocation("Istanbul")
                .arrivalLocation("Ankara")
                .departureDate(LocalDateTime.of(2024, 10, 10, 14, 30))
                .arrivalDate(LocalDateTime.of(2024, 10, 10, 16, 45))
                .build();

        Flight savedFlight = flightRepository.save(flight);


        Flight foundFlight = flightRepository.findById(savedFlight.getId()).orElse(null);


        Assertions.assertThat(foundFlight).isNotNull();
        Assertions.assertThat(foundFlight.getId()).isEqualTo(savedFlight.getId());
    }


    @Test
    public void FlightRepository_UpdateFlight_ReturnUpdatedFlight() {
        Flight flight = Flight.builder()
                .departureLocation("Istanbul")
                .arrivalLocation("Ankara")
                .departureDate(LocalDateTime.of(2024, 10, 10, 14, 30))
                .arrivalDate(LocalDateTime.of(2024, 10, 10, 16, 45))
                .build();

        Flight savedFlight = flightRepository.save(flight);
        Flight flightSave = flightRepository.findById(savedFlight.getId()).orElseThrow();
        flightSave.setDepartureLocation("Bursa");
        flightSave.setArrivalLocation("Antalya");
        flightSave.setDepartureDate(LocalDateTime.of(2024, 11, 10, 14, 30));
        flightSave.setArrivalDate(LocalDateTime.of(2024, 11, 10, 16, 45));


        Flight updatedFlight = flightRepository.save(flightSave);

        Assertions.assertThat(updatedFlight).isNotNull();
        Assertions.assertThat(updatedFlight.getId()).isEqualTo(savedFlight.getId());
        Assertions.assertThat(updatedFlight.getDepartureLocation()).isEqualTo("Bursa");
        Assertions.assertThat(updatedFlight.getArrivalLocation()).isEqualTo("Antalya");
    }



    @Test
    public void FlightRepository_DeleteById_ReturnFlightIsEmpty() {
        Flight flight = Flight.builder()
                .departureLocation("Istanbul")
                .arrivalLocation("Ankara")
                .departureDate(LocalDateTime.of(2024, 11, 10, 14, 30))
                .arrivalDate(LocalDateTime.of(2024, 11, 10, 16, 45))
                .build();

        Flight savedFlight = flightRepository.save(flight);

        flightRepository.deleteById(savedFlight.getId());
        Optional<Flight> pokemonReturn = flightRepository.findById(savedFlight.getId());

        Assertions.assertThat(flightRepository.findById(savedFlight.getId()).isEmpty()).isTrue();
        Assertions.assertThat(pokemonReturn).isEmpty();
    }
}
