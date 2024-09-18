package com.odeon.odeonDemo.business.abstracts;

import com.odeon.odeonDemo.business.dtos.requests.CreateFlightRequest;
import com.odeon.odeonDemo.business.dtos.responses.GetAllFlightResponse;

import java.util.List;

public interface FlightService {
    void addFlight(CreateFlightRequest createFlightRequest);
    void deleteFlight(int flightId);
    List<GetAllFlightResponse> getAllFlights();
}