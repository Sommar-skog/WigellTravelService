package com.example.WigellTravelService.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name ="travel_booking")
public class TravelBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private int numberOfWeeks;

    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal totalPrice;

    @Column(nullable = false)
    private boolean cancelled;

    @ManyToOne
    @JoinColumn(name= "customer_id")
    private TravelCustomer customer;

    @ManyToOne
    @JoinColumn(name= "trip_id", nullable = false)
    private TravelTrip trip;

    public TravelBooking() {

    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate departureDate) {
        this.startDate = departureDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getNumberOfWeeks() {
        return numberOfWeeks;
    }

    public void setNumberOfWeeks(int numberOfWeeks) {
        this.numberOfWeeks = numberOfWeeks;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public TravelCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(TravelCustomer customer) {
        this.customer = customer;
    }

    public TravelTrip getTrip() {
        return trip;
    }

    public void setTrip(TravelTrip trip) {
        this.trip = trip;
    }

    @Override
    public String toString() {
        return "TravelBooking{" +
                "bookingId=" + bookingId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", totalPrice=" + totalPrice +
                ", cancelled=" + cancelled +
                ", customer=" + customer +
                ", trip=" + trip +
                '}';
    }
}
