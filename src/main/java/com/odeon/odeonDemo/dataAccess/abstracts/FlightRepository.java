package com.odeon.odeonDemo.dataAccess.abstracts;

import com.odeon.odeonDemo.entities.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight,Integer> {
}
