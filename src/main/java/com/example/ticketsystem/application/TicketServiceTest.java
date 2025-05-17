package com.example.ticketsystem.application;

import com.example.ticketsystem.domain.exceptions.TicketException;
import com.example.ticketsystem.domain.model.Ticket;
import com.example.ticketsystem.domain.repositories.TicketRepository;
import com.example.ticketsystem.interfaces.dto.CreateTicketDto;
import com.example.ticketsystem.interfaces.dto.TicketDto;
import com.example.ticketsystem.interfaces.dto.UpdateTicketStatusDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {
    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    private Ticket ticket;
    private CreateTicketDto createDto;
    private UpdateTicketStatusDto updateDto;

    @BeforeEach
    void setUp() {
        ticket = new Ticket();
        ticket.setId(1L);
        ticket.setTitulo("Test Ticket");
        ticket.setDescripcion("Test Description");
        ticket.setEstado(Ticket.Status.ABIERTO);
        ticket.setFechaCreacion(LocalDateTime.now());
        ticket.setFechaVencimiento(LocalDateTime.now().plusDays(7));

        createDto = new CreateTicketDto();
        createDto.setTitulo("Test Ticket");
        createDto.setDescripcion("Test Description");
        createDto.setFechaVencimiento(LocalDateTime.now().plusDays(7));

        updateDto = new UpdateTicketStatusDto();
        updateDto.setEstado("EN_PROCESO");
    }

    @Test
    void createTicket_ShouldReturnTicketDto() {
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        TicketDto result = ticketService.createTicket(createDto);

        assertNotNull(result);
        assertEquals(ticket.getId(), result.getId());
        assertEquals(ticket.getTitulo(), result.getTitulo());
        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }

    @Test
    void getAllTickets_ShouldReturnListOfTicketDtos() {
        List<Ticket> tickets = Collections.singletonList(ticket);
        when(ticketRepository.findAll()).thenReturn(tickets);

        List<TicketDto> result = ticketService.getAllTickets();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(ticketRepository, times(1)).findAll();
    }

    @Test
    void getTicketById_WhenTicketExists_ShouldReturnTicketDto() {
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));

        TicketDto result = ticketService.getTicketById(1L);

        assertNotNull(result);
        assertEquals(ticket.getId(), result.getId());
        verify(ticketRepository, times(1)).findById(1L);
    }

    @Test
    void getTicketById_WhenTicketNotExists_ShouldThrowException() {
        when(ticketRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TicketException.class, () -> ticketService.getTicketById(1L));
        verify(ticketRepository, times(1)).findById(1L);
    }

    @Test
    void updateTicketStatus_ShouldUpdateStatus() {
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        TicketDto result = ticketService.updateTicketStatus(1L, updateDto);

        assertNotNull(result);
        assertEquals("EN_PROCESO", result.getEstado());
        verify(ticketRepository, times(1)).findById(1L);
        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }

    @Test
    void updateTicketStatus_WhenMarkAsResolvedWithoutComment_ShouldThrowException() {
        updateDto.setEstado("RESUELTO");
        updateDto.setComentario(null);
        
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));

        assertThrows(TicketException.class, () -> ticketService.updateTicketStatus(1L, updateDto));
        verify(ticketRepository, times(1)).findById(1L);
        verify(ticketRepository, never()).save(any(Ticket.class));
    }
}