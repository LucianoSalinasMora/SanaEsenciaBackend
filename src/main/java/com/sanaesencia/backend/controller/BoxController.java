package com.sanaesencia.backend.controller;

import com.sanaesencia.backend.dto.BoxDto;
import com.sanaesencia.backend.service.BoxService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boxes")
@RequiredArgsConstructor
@Tag(name = "Boxes de Atención", description = "Gestión de los 8 boxes de atención presencial en Los Vilos")
public class BoxController {

    private final BoxService boxService;

    @Operation(summary = "Listar todos los boxes", description = "Devuelve el estado de los 8 boxes de atención")
    @GetMapping
    public ResponseEntity<List<BoxDto>> getAllBoxes() {
        return ResponseEntity.ok(boxService.getAllBoxes());
    }

    @Operation(summary = "Listar boxes disponibles", description = "Devuelve únicamente los boxes con estado 'disponible'")
    @GetMapping("/available")
    public ResponseEntity<List<BoxDto>> getAvailableBoxes() {
        return ResponseEntity.ok(boxService.getAvailableBoxes());
    }

    @Operation(summary = "Actualizar estado de box", description = "Cambia el estado de un box (disponible / reservado)")
    @PutMapping("/{boxNumber}/status")
    public ResponseEntity<BoxDto> updateBoxStatus(
            @PathVariable Integer boxNumber,
            @RequestParam String status
    ) {
        return ResponseEntity.ok(boxService.updateBoxStatus(boxNumber, status));
    }
}
