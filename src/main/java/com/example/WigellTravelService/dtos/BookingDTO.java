package com.example.WigellTravelService.dtos;


import java.math.BigDecimal;
import java.time.LocalDate;

// DTO used only for returning data
public class BookingDTO {

    private String bookedBy;
    private Long bookingId;
    private String hotelName;
    private String destination;
    private LocalDate startDate;
    private int weeks;
    private BigDecimal totalPriceInSek;
    private BigDecimal totalPriceInEuro;

    public BookingDTO() {

    }

    public BookingDTO(String bookedBy, Long bookingId, String hotelName, String Destination, LocalDate startDate, int weeks, BigDecimal totalPriceInSek, BigDecimal totalPriceInEuro) {
        this.bookedBy = bookedBy;
        this.bookingId = bookingId;
        this.hotelName = hotelName;
        this.destination = Destination;
        this.startDate = startDate;
        this.weeks = weeks;
        this.totalPriceInSek = totalPriceInSek;
        this.totalPriceInEuro = totalPriceInEuro;
    }

    public String getBookedBy() {
        return bookedBy;
    }

    public void setBookedBy(String bookedBy) {
        this.bookedBy = bookedBy;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public int getWeeks() {
        return weeks;
    }

    public void setWeeks(int weeks) {
        this.weeks = weeks;
    }

    public BigDecimal getTotalPriceInSek() {
        return totalPriceInSek;
    }

    public void setTotalPriceInSek(BigDecimal totalPriceInSek) {
        this.totalPriceInSek = totalPriceInSek;
    }

    public BigDecimal getTotalPriceInEuro() {
        return totalPriceInEuro;
    }

    public void setTotalPriceInEuro(BigDecimal totalPriceInEuro) {
        this.totalPriceInEuro = totalPriceInEuro;
    }

    @Override
    public String toString() {
        return "BookingDTO{" +
                "bookedBy='" + bookedBy + '\'' +
                ", bookingId=" + bookingId +
                ", hotelName='" + hotelName + '\'' +
                ", destination='" + destination + '\'' +
                ", startDate=" + startDate +
                ", weeks=" + weeks +
                ", totalPriceInSek=" + totalPriceInSek +
                ", totalPriceInEuro=" + totalPriceInEuro +
                '}';
    }
}
