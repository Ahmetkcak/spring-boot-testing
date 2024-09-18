package com.odeon.odeonDemo.api.controllers;

import com.odeon.odeonDemo.business.abstracts.ReservationService;
import com.odeon.odeonDemo.business.dtos.requests.CreateReservationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationsController {
    private final ReservationService reservationService;

    @PostMapping("/create")
    public void createReservation(@RequestBody CreateReservationRequest createReservationRequest) {
        reservationService.add(createReservationRequest);
    }

    @DeleteMapping("/delete/{reservationId}")
    public void deleteReservation(@PathVariable int reservationId) {
        reservationService.delete(reservationId);
    }
}
