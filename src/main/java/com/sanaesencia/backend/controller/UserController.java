package com.sanaesencia.backend.controller;

import com.sanaesencia.backend.dto.LoginRequestDto;
import com.sanaesencia.backend.dto.LoginResponseDto;
import com.sanaesencia.backend.dto.UserDto;
import com.sanaesencia.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Usuarios y Autenticación", description = "Endpoints para registro, login y consulta de usuarios")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Inicio de sesión", description = "Autentica usuarios mediante RUN chileno y contraseña")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
        LoginResponseDto response = userService.login(request);
        if (!response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Registro de usuario", description = "Registra un nuevo usuario/paciente con validación de RUN")
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserDto request) {
        UserDto created = userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Consultar usuario por RUN", description = "Obtiene perfil de usuario por su RUN chileno")
    @GetMapping("/{run}")
    public ResponseEntity<UserDto> getUserByRun(@PathVariable String run) {
        return ResponseEntity.ok(userService.getUserByRun(run));
    }

    @Operation(summary = "Listar usuarios por rol", description = "Filtra usuarios por su rol (PACIENTE, PROFESIONAL, ADMIN)")
    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserDto>> getUsersByRole(@PathVariable String role) {
        return ResponseEntity.ok(userService.getUsersByRole(role));
    }
}
