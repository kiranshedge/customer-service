package com.rayedge.reservation.service;

import com.rayedge.reservation.dto.NotificationRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class NotificationClient {
    private final RestTemplate rt = new RestTemplate();

    public String sendNotification(NotificationRequest request) {
        try {
            var resp = rt.postForObject(
                    "http://notification-service:8080/notifications",
                    request,
                    String.class
            );
            return resp;
        } catch (Exception e) {
            return "ERROR";
        }
    }
}