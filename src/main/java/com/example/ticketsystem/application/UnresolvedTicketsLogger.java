package com.example.ticketsystem.application;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Component
public class UnresolvedTicketsLogger {
    @PersistenceContext
    private EntityManager entityManager;

    @Scheduled(cron = "0 0 0 * * ?") // Ejecutar a medianoche cada día
    @Transactional
    public void logUnresolvedTickets() {
        entityManager.createNativeQuery("EXEC log_unresolved_tickets").executeUpdate();
    }
}