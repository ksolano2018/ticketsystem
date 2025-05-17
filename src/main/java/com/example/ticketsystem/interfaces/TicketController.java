package com.example.ticketsystem.interfaces;

import com.example.ticketsystem.application.TicketService;
import com.example.ticketsystem.interfaces.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @PostMapping
    public ResponseEntity<TicketDto> createTicket(@RequestBody CreateTicketDto dto) {
        TicketDto created = ticketService.createTicket(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping
    public ResponseEntity<List<TicketDto>> getAllTickets() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketDto> getTicketById(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.getTicketById(id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TicketDto> updateTicketStatus(
            @PathVariable Long id, 
            @RequestBody UpdateTicketStatusDto dto) {
        return ResponseEntity.ok(ticketService.updateTicketStatus(id, dto));
    }

    @GetMapping("/unresolved-old")
    public ResponseEntity<List<TicketDto>> getUnresolvedTicketsOlderThan30Days() {
        return ResponseEntity.ok(ticketService.getUnresolvedTicketsOlderThan30Days());
    }
}