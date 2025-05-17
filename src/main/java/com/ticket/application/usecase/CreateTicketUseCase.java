package com.ticket.application.usecase;

import com.ticket.domain.model.Ticket;
import com.ticket.domain.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateTicketUseCaseTest {

    private TicketRepository repository;
    private CreateTicketUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(TicketRepository.class);
        useCase = new CreateTicketUseCase(repository);
    }

    @Test
    void shouldCreateTicketCorrectly() {
        String titulo = "Error en login";
        String descripcion = "No puedo ingresar al sistema";
        LocalDateTime vencimiento = LocalDateTime.now().plusDays(7);

        Ticket ticketMock = new Ticket();
        ticketMock.setId(1L);
        ticketMock.setTitulo(titulo);
        ticketMock.setDescripcion(descripcion);
        ticketMock.setEstado("Abierto");
        ticketMock.setFechaCreacion(any());
        ticketMock.setFechaVencimiento(vencimiento);

        when(repository.save(any(Ticket.class))).thenReturn(ticketMock);

        Ticket result = useCase.execute(titulo, descripcion, vencimiento);

        assertNotNull(result);
        assertEquals(titulo, result.getTitulo());
        assertEquals("Abierto", result.getEstado());
    }
}
