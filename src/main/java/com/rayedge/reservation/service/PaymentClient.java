package com.rayedge.reservation.service;

import com.rayedge.reservation.dto.PaymentRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.UUID;

@Component
public class PaymentClient {
    private final RestTemplate rt = new RestTemplate();
    // docker-compose will expose payment-service at http://payment-service:8080
    public String authorizePayment(UUID reservationId, double amount) {
        try {
            var resp = rt.postForObject("http://payment-service:8080/payments/authorize",
                    new PaymentRequest(reservationId, amount), String.class);
            return resp;
        } catch (Exception e) {
            return "ERROR";
        }
    }
}