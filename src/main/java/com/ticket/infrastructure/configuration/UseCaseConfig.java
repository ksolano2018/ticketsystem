package com.ticket.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ticket.application.usecase.CreateTicketUseCase;
import com.ticket.application.usecase.GetAllTicketsUseCase;
import com.ticket.domain.repository.TicketRepository;

@Configuration
public class UseCaseConfig {

    @Bean
    public CreateTicketUseCase createTicketUseCase(TicketRepository repository) {
        return new CreateTicketUseCase(repository);
    }
    @Bean
    public GetAllTicketsUseCase getAllTicketsUseCase(TicketRepository repository) {
    return new GetAllTicketsUseCase(repository);
}
}
