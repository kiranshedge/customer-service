package com.rayedge.reservation.controller;

import com.rayedge.reservation.dto.NotificationRequest;
import com.rayedge.reservation.service.NotificationClient;
import com.rayedge.reservation.service.PaymentClient;
import com.rayedge.reservation.Reservation;
import com.rayedge.reservation.repository.ReservationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationRepository repo;
    private final PaymentClient paymentClient;
    private final NotificationClient notificationClient;

    public ReservationController(ReservationRepository repo,
                                 PaymentClient paymentClient,
                                 NotificationClient notificationClient) {
        this.repo = repo;
        this.paymentClient = paymentClient;
        this.notificationClient = notificationClient;
    }

    @PostMapping
    public ResponseEntity<Reservation> create(@RequestBody Reservation r) {
        r.setStatus("PENDING");
        var saved = repo.save(r);

        var payResp = paymentClient.authorizePayment(saved.getId(), saved.getTotalAmount());
        if (payResp.equals("OK")) {
            saved.setStatus("CONFIRMED");
            repo.save(saved);
            NotificationRequest notification = new NotificationRequest();
            notification.setMessage("Reservation confirmed: " + saved.getId());
            notificationClient.sendNotification(notification);
            return ResponseEntity.ok(saved);
        } else {
            saved.setStatus("CANCELLED");
            repo.save(saved);
            NotificationRequest notification = new NotificationRequest();
            notification.setMessage("Reservation failed: " + saved.getId());
            notificationClient.sendNotification(notification);
            return ResponseEntity.status(502).body(saved);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> get(@PathVariable UUID id) {
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
