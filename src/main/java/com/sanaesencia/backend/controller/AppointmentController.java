package com.sanaesencia.backend.controller;

import com.sanaesencia.backend.dto.AppointmentDto;
import com.sanaesencia.backend.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
@Tag(name = "Citas Médicas", description = "Gestión de agendamiento, cancelación y consulta de citas")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Operation(summary = "Listar citas", description = "Obtiene listado de citas filtrables por RUN de paciente o profesional")
    @GetMapping
    public ResponseEntity<List<AppointmentDto>> getAppointments(
            @RequestParam(required = false) String patientRun,
            @RequestParam(required = false) String professionalRun
    ) {
        return ResponseEntity.ok(appointmentService.getAppointments(patientRun, professionalRun));
    }

    @Operation(summary = "Crear nueva cita", description = "Crea una cita con verificación de disponibilidad de profesional y box")
    @PostMapping
    public ResponseEntity<AppointmentDto> createAppointment(@Valid @RequestBody AppointmentDto appointmentDto) {
        AppointmentDto created = appointmentService.createAppointment(appointmentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Cancelar cita", description = "Cancela una cita médica liberando el box y el turno asociado")
    @PutMapping("/{id}/cancel")
    public ResponseEntity<AppointmentDto> cancelAppointment(@PathVariable Long id) {
        return ResponseEntity.ok(appointmentService.cancelAppointment(id));
    }

    @Operation(summary = "Eliminar cita", description = "Elimina permanentemente una cita de la base de datos")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }
}
