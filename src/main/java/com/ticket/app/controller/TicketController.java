package com.ticket.app.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticket.app.dto.TicketRequest;
import com.ticket.app.dto.TicketResponse;
import com.ticket.application.usecase.CreateTicketUseCase;
import com.ticket.application.usecase.GetAllTicketsUseCase;
import com.ticket.domain.model.Ticket;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final CreateTicketUseCase createTicketUseCase;
    private final GetAllTicketsUseCase getAllTicketsUseCase;

    public TicketController(CreateTicketUseCase createTicketUseCase, GetAllTicketsUseCase getAllTicketsUseCase) {
    this.createTicketUseCase = createTicketUseCase;
    this.getAllTicketsUseCase = getAllTicketsUseCase;
}

    @GetMapping
public List<TicketResponse> getAllTickets() {
    return getAllTicketsUseCase.execute()
        .stream()
        .map(this::toResponse)
        .collect(Collectors.toList());
}

    @PostMapping
    public TicketResponse createTicket(@RequestBody TicketRequest request) {
        Ticket ticket = createTicketUseCase.execute(
                request.getTitulo(),
                request.getDescripcion(),
                request.getFechaVencimiento()
        );
        return toResponse(ticket);
    }

    private TicketResponse toResponse(Ticket ticket) {
        TicketResponse response = new TicketResponse();
        response.setId(ticket.getId());
        response.setTitulo(ticket.getTitulo());
        response.setDescripcion(ticket.getDescripcion());
        response.setEstado(ticket.getEstado());
        response.setFechaCreacion(ticket.getFechaCreacion());
        response.setFechaActualizacion(ticket.getFechaActualizacion());
        response.setFechaVencimiento(ticket.getFechaVencimiento());
        response.setComentario(ticket.getComentario());
        return response;
    }
}
