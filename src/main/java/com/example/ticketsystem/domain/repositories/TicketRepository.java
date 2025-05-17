package com.example.ticketsystem.domain.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.ticketsystem.domain.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query("SELECT t FROM Ticket t WHERE t.estado != 'RESUELTO' AND t.fechaCreacion < ?1")
    List<Ticket> findUnresolvedTicketsOlderThan(LocalDateTime date);
}