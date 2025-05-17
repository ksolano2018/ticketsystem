package com.ticket.domain.repository;

import java.util.List;
import java.util.Optional;

import com.ticket.domain.model.Ticket;

public interface TicketRepository {
    Ticket save(Ticket ticket);
    Optional<Ticket> findById(Long id);
    List<Ticket> findAll();
    List<Ticket> findUnresolvedOlderThan(int days);
}
