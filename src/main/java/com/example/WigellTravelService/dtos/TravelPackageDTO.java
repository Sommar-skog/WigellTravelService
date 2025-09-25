package com.example.WigellTravelService.dtos;

import java.math.BigDecimal;

public class TravelPackageDTO {

    private Long travelPackageId;
    private String destination;
    private String hotelName;
    private BigDecimal weekPrice;
    private BigDecimal weekPriceEur;
    private boolean active;

    public TravelPackageDTO() {

    }

    public TravelPackageDTO(Long travelPackageId, String destination, String hotelName, BigDecimal weekPrice, BigDecimal weekPriceEur, boolean active) {
        this.travelPackageId = travelPackageId;
        this.destination = destination;
        this.hotelName = hotelName;
        this.weekPrice = weekPrice;
        this.weekPriceEur = weekPriceEur;
        this.active = active;
    }

    public Long getTravelPackageId() {
        return travelPackageId;
    }

    public void setTravelPackageId(Long travelPackageId) {
        this.travelPackageId = travelPackageId;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public BigDecimal getWeekPrice() {
        return weekPrice;
    }

    public void setWeekPrice(BigDecimal weekPrice) {
        this.weekPrice = weekPrice;
    }

    public BigDecimal getWeekPriceEur() {
        return weekPriceEur;
    }

    public void setWeekPriceEur(BigDecimal weekPriceEur) {
        this.weekPriceEur = weekPriceEur;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "TravelPackageDTO{" +
                "travelPackageId=" + travelPackageId +
                ", destination='" + destination + '\'' +
                ", hotelName='" + hotelName + '\'' +
                ", weekPrice=" + weekPrice +
                ", weekPriceEur=" + weekPriceEur +
                ", active=" + active +
                '}';
    }
}
