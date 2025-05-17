package com.example.ticketsystem.application;

import com.example.ticketsystem.domain.exceptions.TicketException;
import com.example.ticketsystem.domain.model.Ticket;
import com.example.ticketsystem.domain.repositories.TicketRepository;
import com.example.ticketsystem.interfaces.dto.CreateTicketDto;
import com.example.ticketsystem.interfaces.dto.TicketDto;
import com.example.ticketsystem.interfaces.dto.UpdateTicketStatusDto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;

    @Transactional
    public TicketDto createTicket(CreateTicketDto dto) {
        Ticket ticket = Ticket.builder()
                .titulo(dto.getTitulo())
                .descripcion(dto.getDescripcion())
                .fechaVencimiento(dto.getFechaVencimiento())
                .build();
        
        ticket = ticketRepository.save(ticket);
        return convertToDto(ticket);
    }

    @Transactional(readOnly = true)
    public List<TicketDto> getAllTickets() {
        return ticketRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TicketDto getTicketById(Long id) {
        return ticketRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new TicketException("Ticket no encontrado"));
    }

    @Transactional
    public TicketDto updateTicketStatus(Long id, UpdateTicketStatusDto dto) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketException("Ticket no encontrado"));
        
        try {
            Ticket.Status newStatus = Ticket.Status.valueOf(dto.getEstado().toUpperCase());
            
            if (newStatus == Ticket.Status.RESUELTO && dto.getComentario() == null) {
                throw new TicketException("Se requiere un comentario al marcar como resuelto");
            }
            
            ticket.setEstado(newStatus);
            ticket.setComentario(dto.getComentario());
            
            ticket = ticketRepository.save(ticket);
            return convertToDto(ticket);
        } catch (IllegalArgumentException e) {
            throw new TicketException("Estado no válido: " + dto.getEstado());
        }
    }

    @Transactional(readOnly = true)
    public List<TicketDto> getUnresolvedTicketsOlderThan30Days() {
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        return ticketRepository.findUnresolvedTicketsOlderThan(thirtyDaysAgo).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private TicketDto convertToDto(Ticket ticket) {
        return TicketDto.builder()
                .id(ticket.getId())
                .titulo(ticket.getTitulo())
                .descripcion(ticket.getDescripcion())
                .estado(ticket.getEstado().name())
                .fechaCreacion(ticket.getFechaCreacion())
                .fechaActualizacion(ticket.getFechaActualizacion())
                .fechaVencimiento(ticket.getFechaVencimiento())
                .comentario(ticket.getComentario())
                .build();
    }
}