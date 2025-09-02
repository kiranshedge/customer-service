package com.rayedge.reservation;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Reservation {
    @Id
    @GeneratedValue
    private UUID id;
    private UUID customerId;
    private UUID hotelId;
    private UUID roomId;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private String status; // PENDING, CONFIRMED, CANCELLED
    private double totalAmount;
}
