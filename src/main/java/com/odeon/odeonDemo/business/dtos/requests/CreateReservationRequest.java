package com.odeon.odeonDemo.business.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CreateReservationRequest {
    private int userId;
    private int flightId;
}
