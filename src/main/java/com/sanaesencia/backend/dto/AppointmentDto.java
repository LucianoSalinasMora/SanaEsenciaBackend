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
public class AppointmentDto {
    private Long id;

    @NotBlank(message = "El nombre del paciente es obligatorio")
    private String patientName;

    @NotBlank(message = "El email del paciente es obligatorio")
    private String patientEmail;

    private String patientPhone;

    @NotBlank(message = "El RUN del paciente es obligatorio")
    private String patientRun;

    @NotBlank(message = "El RUN del profesional es obligatorio")
    private String professionalRun;

    @NotBlank(message = "El nombre del profesional es obligatorio")
    private String professionalName;

    @NotBlank(message = "El tipo de servicio es obligatorio")
    private String serviceType;

    @NotNull(message = "La fecha es obligatoria")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotBlank(message = "El bloque horario es obligatorio")
    private String timeSlot;

    private Boolean isOnline;
    private Integer boxNumber;

    @Builder.Default
    private String status = "Agendada";

    @Builder.Default
    private String state = "PENDIENTE";

    private String patientNotes;
    private Long turnoId;
}
