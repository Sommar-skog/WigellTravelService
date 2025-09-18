package com.example.WigellTravelService.dtos;

import java.math.BigDecimal;

public class AddTravelPackageDTO {

    private String hotelName;
    private String destination;
    private BigDecimal weekPrice;

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public BigDecimal getWeekPrice() {
        return weekPrice;
    }

    public void setWeekPrice(BigDecimal weekPrice) {
        this.weekPrice = weekPrice;
    }

    @Override
    public String toString() {
        return "AddTravelPackageDTO{" +
                "hotelName='" + hotelName + '\'' +
                ", destination='" + destination + '\'' +
                ", weekPrice=" + weekPrice +
                '}';
    }
}
