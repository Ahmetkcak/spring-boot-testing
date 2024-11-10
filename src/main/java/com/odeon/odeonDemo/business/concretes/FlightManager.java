package com.odeon.odeonDemo.business.concretes;

import com.odeon.odeonDemo.business.abstracts.FlightService;
import com.odeon.odeonDemo.business.dtos.requests.CreateFlightRequest;
import com.odeon.odeonDemo.business.dtos.responses.CreatedFlightResponse;
import com.odeon.odeonDemo.business.dtos.responses.GetAllFlightResponse;
import com.odeon.odeonDemo.business.rules.FlightBusinessRules;
import com.odeon.odeonDemo.core.utilities.mapping.ModelMapperService;
import com.odeon.odeonDemo.dataAccess.abstracts.FlightRepository;
import com.odeon.odeonDemo.entities.Flight;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class FlightManager implements FlightService {

    private final FlightRepository flightRepository;
    private final ModelMapperService modelMapperService;
    private final FlightBusinessRules flightBusinessRules;

    @Override
    public CreatedFlightResponse addFlight(CreateFlightRequest createFlightRequest) {

        flightBusinessRules.validateFlightSchedule(modelMapperService.forRequest().map(createFlightRequest, Flight.class));

        Flight flight = modelMapperService.forRequest().map(createFlightRequest, Flight.class);
        return modelMapperService.forResponse().map(flightRepository.save(flight), CreatedFlightResponse.class);
    }

    @Override
    public void deleteFlight(int flightId) {
        flightRepository.deleteById(flightId);
    }

    @Override
    public List<GetAllFlightResponse> getAllFlights() {
        List<Flight> flights = flightRepository.findAll();
        List<GetAllFlightResponse> flightsResponse = flights.stream().map(flight -> modelMapperService.forResponse().map(flight, GetAllFlightResponse.class)).toList();
        return flightsResponse;
    }
}
