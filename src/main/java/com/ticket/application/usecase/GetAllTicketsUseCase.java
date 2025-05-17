package com.ticket.application.usecase;

import java.util.List;

import com.ticket.domain.model.Ticket;
import com.ticket.domain.repository.TicketRepository;

public class GetAllTicketsUseCase {
    private final TicketRepository repository;

    public GetAllTicketsUseCase(TicketRepository repository) {
        this.repository = repository;
    }

    public List<Ticket> execute() {
        return repository.findAll();
    }
}
