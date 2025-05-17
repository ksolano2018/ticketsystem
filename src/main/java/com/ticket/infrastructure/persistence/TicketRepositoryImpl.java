package com.ticket.infrastructure.persistence;

import com.ticket.domain.model.Ticket;
import com.ticket.domain.repository.TicketRepository;
import com.ticket.infrastructure.persistence.entity.TicketEntity;
import com.ticket.infrastructure.persistence.repository.JpaTicketRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class TicketRepositoryImpl implements TicketRepository {

    private final JpaTicketRepository jpaRepo;

    public TicketRepositoryImpl(JpaTicketRepository jpaRepo) {
        this.jpaRepo = jpaRepo;
    }

    @Override
    public Ticket save(Ticket ticket) {
        TicketEntity entity = toEntity(ticket);
        TicketEntity saved = jpaRepo.save(entity);
        return toModel(saved);
    }

    @Override
    public Optional<Ticket> findById(Long id) {
        return jpaRepo.findById(id).map(this::toModel);
    }

    @Override
    public List<Ticket> findAll() {
        return jpaRepo.findAll().stream().map(this::toModel).collect(Collectors.toList());
    }

    @Override
    public List<Ticket> findUnresolvedOlderThan(int days) {
        LocalDateTime limite = LocalDateTime.now().minusDays(days);
        return jpaRepo.findUnresolvedOlderThan(limite).stream().map(this::toModel).collect(Collectors.toList());
    }

    private TicketEntity toEntity(Ticket ticket) {
        TicketEntity entity = new TicketEntity();
        entity.setId(ticket.getId());
        entity.setTitulo(ticket.getTitulo());
        entity.setDescripcion(ticket.getDescripcion());
        entity.setEstado(ticket.getEstado());
        entity.setFechaCreacion(ticket.getFechaCreacion());
        entity.setFechaActualizacion(ticket.getFechaActualizacion());
        entity.setFechaVencimiento(ticket.getFechaVencimiento());
        entity.setComentario(ticket.getComentario());
        return entity;
    }

    private Ticket toModel(TicketEntity entity) {
        return new Ticket(
                entity.getId(),
                entity.getTitulo(),
                entity.getDescripcion(),
                entity.getEstado(),
                entity.getFechaCreacion(),
                entity.getFechaActualizacion(),
                entity.getFechaVencimiento(),
                entity.getComentario()
        );
    }
}
