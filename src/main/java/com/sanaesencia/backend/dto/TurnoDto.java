package com.sanaesencia.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TurnoDto {
    private Long id;

    @NotNull(message = "La fecha es obligatoria")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotBlank(message = "El bloque horario es obligatorio")
    private String timeSlot;

    @NotBlank(message = "El RUN del profesional es obligatorio")
    private String professionalRun;

    @NotBlank(message = "El nombre del profesional es obligatorio")
    private String professionalName;

    @NotBlank(message = "El tipo de servicio es obligatorio")
    private String serviceType;

    private Integer boxNumber;
    private String status;
    private Boolean isOnline;
    private Long appointmentId;
}
