package com.example.WigellTravelService.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class TravelTrip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tripId;

    @Column(nullable = false, length = 50)
    private String hotellName;

    @Column(nullable = false, length = 50)
    private String destination;

    @Column(nullable = false, precision = 7, scale = 2)
    private BigDecimal price;

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public String getHotellName() {
        return hotellName;
    }

    public void setHotellName(String hotellName) {
        this.hotellName = hotellName;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "TravelTrip{" +
                "tripId=" + tripId +
                ", hotellName='" + hotellName + '\'' +
                ", destination='" + destination + '\'' +
                ", price=" + price +
                '}';
    }
}
