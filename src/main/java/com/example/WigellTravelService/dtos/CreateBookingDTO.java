package com.example.WigellTravelService.dtos;

import java.time.LocalDate;

public class CreateBookingDTO {

    private Long travelPackageId;

    private LocalDate startDate;

    private Integer numberOfWeeks;

    public CreateBookingDTO() {}

    public CreateBookingDTO(Long travelPackageId, LocalDate startDate, Integer numberOfWeeks) {
        this.travelPackageId = travelPackageId;
        this.startDate = startDate;
        this.numberOfWeeks = numberOfWeeks;
    }

    public Long getTravelPackageId() {
        return travelPackageId;
    }

    public void setTravelPackageId(Long travelPackageId) {
        this.travelPackageId = travelPackageId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Integer getNumberOfWeeks() {
        return numberOfWeeks;
    }

    public void setNumberOfWeeks(Integer numberOfWeeks) {
        this.numberOfWeeks = numberOfWeeks;
    }

    @Override
    public String toString() {
        return "CreateBookingDTO{" +
                "travelPackageId=" + travelPackageId +
                ", startDate=" + startDate +
                ", numberOfWeeks=" + numberOfWeeks +
                '}';
    }
}
