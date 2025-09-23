package com.example.WigellTravelService.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "travel_customer")
public class TravelCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    @Column(nullable = false, length = 50)
    private String customerName;

    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 50)
    private String password;

    @OneToMany(mappedBy = "travelCustomer")
    private List<TravelBooking> bookingList;

    public TravelCustomer() {

    }

    public TravelCustomer(Long customerId, String customerName, String username, String password) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.username = username;
        this.password = password;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<TravelBooking> getBookingList() {
        return bookingList;
    }

    public void setBookingList(List<TravelBooking> bookingList) {
        this.bookingList = bookingList;
    }

    @Override
    public String toString() {
        return "TravelCustomer{" +
                "customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + "'[PROTECTED]'" + '\'' +
                ", bookingList=" + bookingList +
                '}';
    }
}
