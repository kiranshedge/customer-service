package com.rayedge.reservation.service;

import com.rayedge.reservation.dto.NotificationRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducerService {

    private final KafkaTemplate<String, NotificationRequest> kafkaTemplate;

    public NotificationProducerService(KafkaTemplate<String, NotificationRequest> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendNotification(NotificationRequest request) {
        kafkaTemplate.send("notification-topic", request)
                .whenComplete((SendResult<String, NotificationRequest> result, Throwable ex) -> {
                    if (ex != null) {
                        System.err.println("Failed to send notification: " + ex.getMessage());
                    } else {
                        System.out.println("Notification sent successfully: " + request.getMessage());
                    }
                });
    }
}
