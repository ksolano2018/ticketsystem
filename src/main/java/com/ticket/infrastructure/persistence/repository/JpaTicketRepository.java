package  com.ticket.infrastructure.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ticket.infrastructure.persistence.entity.TicketEntity;

public interface JpaTicketRepository extends JpaRepository<TicketEntity, Long> {

    @Query("SELECT t FROM TicketEntity t WHERE t.estado != 'Resuelto' AND t.fechaCreacion < :fechaLimite")
    List<TicketEntity> findUnresolvedOlderThan(LocalDateTime fechaLimite);
}
