package com.example.ticketsystem.domain.exceptions;


public class TicketException extends RuntimeException {
    public TicketException(String message) {
        super(message);
    }
}