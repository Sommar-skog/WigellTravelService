package com.example.WigellTravelService.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "travel_trip")
public class TravelPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tripId;

    @Column(nullable = false, length = 50)
    private String hotelName;

    @Column(nullable = false, length = 50)
    private String destination;

    @Column(nullable = false, precision = 7, scale = 2)
    private BigDecimal weekPrice;

    public TravelPackage() {

    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotellName) {
        this.hotelName = hotellName;
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

    public void setWeekPrice(BigDecimal price) {
        this.weekPrice = price;
    }

    @Override
    public String toString() {
        return "TravelPackage{" +
                "tripId=" + tripId +
                ", hotelName='" + hotelName + '\'' +
                ", destination='" + destination + '\'' +
                ", weekPrice=" + weekPrice +
                '}';
    }
}
