package com.example.WigellTravelService.dtos;

import java.time.LocalDate;

public class CreateBookingDTO {

    private Long tripId;

    private LocalDate startDate;

    private int numberOfWeeks;

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public int getNumberOfWeeks() {
        return numberOfWeeks;
    }

    public void setNumberOfWeeks(int numberOfWeeks) {
        this.numberOfWeeks = numberOfWeeks;
    }

    @Override
    public String toString() {
        return "CreateBookingDTO{" +
                "tripId=" + tripId +
                ", startDate=" + startDate +
                ", numberOfWeeks=" + numberOfWeeks +
                '}';
    }
}
