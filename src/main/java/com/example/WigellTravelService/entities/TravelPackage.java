package com.example.WigellTravelService.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "travel_package")
public class TravelPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long travelPackageId;

    @Column(nullable = false, length = 50)
    private String hotelName;

    @Column(nullable = false, length = 50)
    private String destination;

    @Column(nullable = false, precision = 7, scale = 2)
    private BigDecimal weekPrice;

    @Column(nullable = false)
    private boolean active;

    @OneToMany(mappedBy = "travelPackage")
    private List<TravelBooking> bookingList;

    public TravelPackage() {
        active = true;
        bookingList = new ArrayList<>();
    }

    public TravelPackage(Long travelPackageId, String hotelName, String destination, BigDecimal weekPrice, Boolean active) {
        this.travelPackageId = travelPackageId;
        this.hotelName = hotelName;
        this.destination = destination;
        this.weekPrice = weekPrice;
        this.active = active;
        this.bookingList = new ArrayList<>();
    }

    public Long getTravelPackageId() {
        return travelPackageId;
    }

    public void setTravelPackageId(Long tripId) {
        this.travelPackageId = tripId;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<TravelBooking> getBookingList() {
        return bookingList;
    }

    public void setBookingList(List<TravelBooking> bookingList) {
        this.bookingList = bookingList;
    }

    @Override
    public String toString() {
        return "TravelPackage{" +
                "travelPackageId=" + travelPackageId +
                ", hotelName='" + hotelName + '\'' +
                ", destination='" + destination + '\'' +
                ", weekPrice=" + weekPrice +
                ", active=" + active +
                ", bookingList=" + bookingList +
                '}';
    }
}
