package com.odeon.odeonDemo.business.dtos.requests;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CreateFlightRequest {
    private String departureLocation;
    private String arrivalLocation;
    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;
}
