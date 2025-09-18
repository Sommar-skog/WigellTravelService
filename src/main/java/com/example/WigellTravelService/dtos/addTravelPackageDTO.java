package com.example.WigellTravelService.dtos;

import java.math.BigDecimal;

public class addTravelPackageDTO {

    private String hotelName;
    private String description;
    private BigDecimal pricePerWeek;

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPricePerWeek() {
        return pricePerWeek;
    }

    public void setPricePerWeek(BigDecimal pricePerWeek) {
        this.pricePerWeek = pricePerWeek;
    }

    @Override
    public String toString() {
        return "addTravelPackageDTO{" +
                "hotelName='" + hotelName + '\'' +
                ", description='" + description + '\'' +
                ", pricePerWeek=" + pricePerWeek +
                '}';
    }
}
