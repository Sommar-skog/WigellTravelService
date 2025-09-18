package com.example.WigellTravelService.dtos;

public class CancelBookingDTO {

    private Long bookingId;

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    @Override
    public String toString() {
        return "CancelBookingDTO{" +
                "bookingId=" + bookingId +
                '}';
    }
}
