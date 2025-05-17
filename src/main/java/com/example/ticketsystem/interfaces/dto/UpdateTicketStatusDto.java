package com.example.ticketsystem.interfaces.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateTicketStatusDto {
    @NotBlank(message = "El estado es obligatorio")
    private String estado;
    
    private String comentario;
}