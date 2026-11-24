package com.sanaesencia.backend.service;

import com.sanaesencia.backend.dto.LoginRequestDto;
import com.sanaesencia.backend.dto.LoginResponseDto;
import com.sanaesencia.backend.dto.UserDto;
import com.sanaesencia.backend.entity.User;
import com.sanaesencia.backend.repository.UserRepository;
import com.sanaesencia.backend.security.JwtService;
import com.sanaesencia.backend.util.RunValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public LoginResponseDto login(LoginRequestDto request) {
        String normalizedRun = RunValidator.normalizeRun(request.getRun());
        Optional<User> userOpt = userRepository.findById(normalizedRun);

        if (userOpt.isEmpty() || !passwordEncoder.matches(request.getPassword(), userOpt.get().getPasswordHash())) {
            return LoginResponseDto.builder()
                    .success(false)
                    .token(null)
                    .user(null)
                    .message("Credenciales inválidas")
                    .build();
        }

        User user = userOpt.get();
        String token = jwtService.generateToken(user.getRun(), user.getRole());

        return LoginResponseDto.builder()
                .success(true)
                .token(token)
                .user(toDto(user))
                .message("Inicio de sesión exitoso")
                .build();
    }

    @Transactional
    public UserDto register(UserDto dto) {
        String normalizedRun = RunValidator.normalizeRun(dto.getRun());
        if (!RunValidator.isValidRun(normalizedRun)) {
            throw new IllegalArgumentException("RUN inválido: " + dto.getRun());
        }

        if (userRepository.existsByRun(normalizedRun)) {
            throw new IllegalArgumentException("El RUN ya se encuentra registrado.");
        }

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("El correo ya está en uso.");
        }

        String rawPassword = (dto.getPassword() != null && !dto.getPassword().isBlank())
                ? dto.getPassword()
                : "Pass123";

        User user = User.builder()
                .run(normalizedRun)
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .passwordHash(passwordEncoder.encode(rawPassword))
                .role(dto.getRole() != null && !dto.getRole().isBlank() ? dto.getRole().toUpperCase() : "PACIENTE")
                .specialty(dto.getSpecialty() != null ? dto.getSpecialty() : "")
                .build();

        User saved = userRepository.save(user);
        return toDto(saved);
    }

    public UserDto getUserByRun(String run) {
        String normalizedRun = RunValidator.normalizeRun(run);
        User user = userRepository.findById(normalizedRun)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con RUN: " + run));
        return toDto(user);
    }

    public List<UserDto> getUsersByRole(String role) {
        return userRepository.findByRoleIgnoreCase(role).stream()
                .map(this::toDto)
                .toList();
    }

    public UserDto toDto(User user) {
        return UserDto.builder()
                .run(user.getRun())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .specialty(user.getSpecialty())
                .build();
    }
}
