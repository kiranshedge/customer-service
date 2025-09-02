package com.rayedge.reservation.repository;
import com.rayedge.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {}
