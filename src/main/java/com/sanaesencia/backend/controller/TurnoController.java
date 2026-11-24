package com.sanaesencia.backend.controller;

import com.sanaesencia.backend.dto.TurnoDto;
import com.sanaesencia.backend.service.TurnoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/turnos")
@RequiredArgsConstructor
@Tag(name = "Turnos y Horarios", description = "Consulta y publicación de turnos de profesionales")
public class TurnoController {

    private final TurnoService turnoService;

    @Operation(summary = "Listar turnos", description = "Consulta turnos con filtros opcionales de fecha, profesional y estado")
    @GetMapping
    public ResponseEntity<List<TurnoDto>> getTurnos(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) String professionalRun,
            @RequestParam(required = false) String status
    ) {
        return ResponseEntity.ok(turnoService.getTurnos(date, professionalRun, status));
    }

    @Operation(summary = "Crear nuevo turno", description = "Publica un módulo de atención disponible")
    @PostMapping
    public ResponseEntity<TurnoDto> createTurno(@Valid @RequestBody TurnoDto turnoDto) {
        TurnoDto created = turnoService.createTurno(turnoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Actualizar estado del turno", description = "Cambia el estado del turno (disponible / reservado)")
    @PutMapping("/{id}/status")
    public ResponseEntity<TurnoDto> updateTurnoStatus(
            @PathVariable Long id,
            @RequestParam String status
    ) {
        return ResponseEntity.ok(turnoService.updateTurnoStatus(id, status));
    }
}
