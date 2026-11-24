package com.sanaesencia.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {
    @NotBlank(message = "El RUN es obligatorio")
    private String run;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
}
