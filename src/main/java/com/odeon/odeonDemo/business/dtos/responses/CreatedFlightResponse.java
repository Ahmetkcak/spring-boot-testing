package com.odeon.odeonDemo.business.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatedFlightResponse {
    private int id;
    private String departureLocation;
    private String arrivalLocation;
    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;
}
