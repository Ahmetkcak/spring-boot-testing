package com.odeon.odeonDemo.business.rules;

import com.odeon.odeonDemo.business.messages.FlightMessages;
import com.odeon.odeonDemo.core.utilities.exceptions.types.BusinessException;
import com.odeon.odeonDemo.dataAccess.abstracts.FlightRepository;
import com.odeon.odeonDemo.entities.Flight;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class FlightBusinessRules {
    FlightRepository flightRepository;

    public void validateFlightSchedule(Flight newFlight) {
        LocalDateTime newDepartureDate = newFlight.getDepartureDate();
        LocalDateTime newArrivalDate = newFlight.getArrivalDate();
        String departureLocation = newFlight.getDepartureLocation();
        String arrivalLocation = newFlight.getArrivalLocation();

        boolean hasConflict = flightRepository.findAll().stream()
                .anyMatch(existingFlight -> isConflict(
                        existingFlight,
                        newDepartureDate,
                        newArrivalDate,
                        departureLocation,
                        arrivalLocation
                ));

        if (hasConflict) {
            throw new BusinessException(FlightMessages.FLIGHT_CONFLICT);
        }
    }

    private boolean isConflict(Flight existingFlight, LocalDateTime newDepartureDate, LocalDateTime newArrivalDate, String departureLocation, String arrivalLocation) {
        boolean isSameDepartureLocation = existingFlight.getDepartureLocation().equals(departureLocation);
        boolean isSameArrivalLocation = existingFlight.getArrivalLocation().equals(arrivalLocation);

        boolean isDepartureConflict = newDepartureDate.isBefore(existingFlight.getDepartureDate().plusMinutes(30)) &&
                newArrivalDate.isAfter(existingFlight.getDepartureDate().minusMinutes(30));
        boolean isArrivalConflict = newArrivalDate.isAfter(existingFlight.getArrivalDate().minusMinutes(30)) &&
                newDepartureDate.isBefore(existingFlight.getArrivalDate().plusMinutes(30));

        return (isSameDepartureLocation && isDepartureConflict) ||
                (isSameArrivalLocation && isArrivalConflict);
    }
}
