package com.sanaesencia.backend.controller;

import com.sanaesencia.backend.entity.AboutInfo;
import com.sanaesencia.backend.repository.AboutInfoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/about")
@RequiredArgsConstructor
@Tag(name = "Información Institucional", description = "Endpoints para consultar y actualizar datos del centro")
public class AboutInfoController {

    private final AboutInfoRepository aboutInfoRepository;

    @Operation(summary = "Consultar datos del centro", description = "Obtiene la información institucional del centro Sanaesencia")
    @GetMapping
    public ResponseEntity<AboutInfo> getAboutInfo() {
        return ResponseEntity.ok(aboutInfoRepository.findById(1).orElseGet(() ->
                AboutInfo.builder()
                        .id(1)
                        .centerTitle("Centro Integrativo Sanaesencia")
                        .description("Salud mental infanto-juvenil, familia y bienestar integrativo. Atendemos de forma presencial en nuestros 8 boxes de Los Vilos y online para todo Chile y el extranjero.")
                        .address("Caupolicán 220, Los Vilos, Región de Coquimbo, Chile")
                        .phone("+56 9 2366 5073")
                        .email("contacto@centrosanaesencia.cl")
                        .build()
        ));
    }

    @Operation(summary = "Actualizar datos del centro", description = "Actualiza la información institucional del centro por el administrador")
    @PutMapping
    public ResponseEntity<AboutInfo> updateAboutInfo(@RequestBody AboutInfo info) {
        info.setId(1);
        AboutInfo saved = aboutInfoRepository.save(info);
        return ResponseEntity.ok(saved);
    }
}
