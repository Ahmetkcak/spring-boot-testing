package com.odeon.odeonDemo.business.abstracts;

import com.odeon.odeonDemo.business.dtos.requests.CreateReservationRequest;

public interface ReservationService {
    void add(CreateReservationRequest reservationRequest);
    void delete(int id);
}
