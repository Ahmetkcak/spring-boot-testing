package com.odeon.odeonDemo.api.controllers;

import com.odeon.odeonDemo.business.abstracts.FlightService;
import com.odeon.odeonDemo.business.dtos.requests.CreateFlightRequest;
import com.odeon.odeonDemo.business.dtos.responses.GetAllFlightResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
public class FlightsController {
    private final FlightService flightService;

    @PostMapping("/create")
    public void createFlight(@RequestBody CreateFlightRequest createFlightRequest) {
        flightService.addFlight(createFlightRequest);
    }

    @DeleteMapping("/delete/{flightId}")
    public void deleteFlight(@PathVariable int flightId) {
        flightService.deleteFlight(flightId);
    }

    @GetMapping("/getAll")
    public List<GetAllFlightResponse> getAllFlights() {
        return flightService.getAllFlights();
    }
}
