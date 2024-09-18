package com.odeon.odeonDemo.business.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class CreateFlightRequest {
    private String departureLocation;
    private String arrivalLocation;
    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;
}
