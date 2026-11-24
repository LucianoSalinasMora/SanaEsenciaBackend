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

    @Builder.Default
    private String professionalRun = "";

    @NotBlank(message = "El nombre del profesional es obligatorio")
    private String professionalName;

    @NotBlank(message = "El tipo de servicio es obligatorio")
    private String serviceType;

    @NotNull(message = "La fecha es obligatoria")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @com.fasterxml.jackson.annotation.JsonSetter("date")
    public void setDateFlexible(String dateStr) {
        if (dateStr == null || dateStr.isBlank()) {
            this.date = null;
            return;
        }
        String clean = dateStr.trim();
        try {
            if (clean.contains("/")) {
                String[] parts = clean.split("/");
                if (parts.length == 3) {
                    if (parts[0].length() == 4) {
                        this.date = LocalDate.of(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                    } else {
                        this.date = LocalDate.of(Integer.parseInt(parts[2]), Integer.parseInt(parts[1]), Integer.parseInt(parts[0]));
                    }
                    return;
                }
            }
            this.date = LocalDate.parse(clean);
        } catch (Exception e) {
            this.date = null;
        }
    }

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
