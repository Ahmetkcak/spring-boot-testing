package com.odeon.odeonDemo.entities;


import com.odeon.odeonDemo.core.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "flights")
public class Flight extends BaseEntity<Integer> {
    @Column(name = "departure_location")
    private String departureLocation;

    @Column(name = "arrival_location")
    private String arrivalLocation;

    @Column(name = "departure_date")
    private LocalDateTime departureDate;

    @Column(name = "arrival_date")
    private LocalDateTime arrivalDate;

    @OneToMany(mappedBy = "flight")
    private Set<Reservation> reservations;
}
