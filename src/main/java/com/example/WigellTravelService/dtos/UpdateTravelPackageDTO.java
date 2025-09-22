package com.example.WigellTravelService.dtos;

import java.math.BigDecimal;

public class UpdateTravelPackageDTO {

    private Long travelPackageId;
    private String hotelName;
    private String destination;
    private BigDecimal weekPrice;

    public UpdateTravelPackageDTO() {

    }

    public UpdateTravelPackageDTO(Long travelPackageId, String hotelName, String destination, BigDecimal weekPrice) {
        this.travelPackageId = travelPackageId;
        this.hotelName = hotelName;
        this.destination = destination;
        this.weekPrice = weekPrice;
    }

    public Long getTravelPackageId() {
        return travelPackageId;
    }

    public void setTravelPackageId(Long travelPackageId) {
        this.travelPackageId = travelPackageId;
    }

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
        return "UpdateTravelPackageDTO{" +
                "travelPackageId=" + travelPackageId +
                ", hotelName='" + hotelName + '\'' +
                ", destination='" + destination + '\'' +
                ", weekPrice=" + weekPrice +
                '}';
    }
}
