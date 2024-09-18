package com.odeon.odeonDemo.dataAccess.abstracts;

import com.odeon.odeonDemo.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
}
