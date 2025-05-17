package com.example.ticketsystem.interfaces.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTicketDto {
    @NotBlank(message = "El título es obligatorio")
    private String titulo;
    
    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;
    
    @NotNull(message = "La fecha de vencimiento es obligatoria")
    private LocalDateTime fechaVencimiento;
}