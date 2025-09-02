package com.rayedge.reservation.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class PaymentRequest {
    private UUID id;
    private double amount;

    public PaymentRequest() {}

    public PaymentRequest(UUID id, double amount) {
        this.id = id;
        this.amount = amount;
    }
}