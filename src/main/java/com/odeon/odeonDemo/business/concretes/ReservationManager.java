package com.odeon.odeonDemo.business.concretes;

import com.odeon.odeonDemo.business.abstracts.ReservationService;
import com.odeon.odeonDemo.business.dtos.requests.CreateReservationRequest;
import com.odeon.odeonDemo.core.utilities.mapping.ModelMapperService;
import com.odeon.odeonDemo.dataAccess.abstracts.ReservationRepository;
import com.odeon.odeonDemo.entities.Reservation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ReservationManager implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final ModelMapperService modelMapperService;

    @Override
    public void add(CreateReservationRequest reservationRequest) {
        Reservation reservation = modelMapperService.forRequest().map(reservationRequest, Reservation.class);
        reservationRepository.save(reservation);
    }

    @Override
    public void delete(int id) {
        reservationRepository.deleteById(id);
    }
}
