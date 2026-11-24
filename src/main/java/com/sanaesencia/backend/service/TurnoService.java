package com.sanaesencia.backend.service;

import com.sanaesencia.backend.dto.TurnoDto;
import com.sanaesencia.backend.entity.Turno;
import com.sanaesencia.backend.repository.TurnoRepository;
import com.sanaesencia.backend.util.RunValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TurnoService {

    private final TurnoRepository turnoRepository;

    public List<TurnoDto> getTurnos(LocalDate date, String professionalRun, String status) {
        String normProfRun = professionalRun != null ? RunValidator.normalizeRun(professionalRun) : null;

        if (date != null && normProfRun != null && status != null) {
            return turnoRepository.findByDateAndProfessionalRunAndStatusIgnoreCase(date, normProfRun, status).stream()
                    .map(this::toDto).toList();
        } else if (date != null && normProfRun != null) {
            return turnoRepository.findByDateAndProfessionalRun(date, normProfRun).stream()
                    .map(this::toDto).toList();
        } else if (date != null) {
            return turnoRepository.findByDate(date).stream().map(this::toDto).toList();
        } else if (normProfRun != null) {
            return turnoRepository.findByProfessionalRun(normProfRun).stream().map(this::toDto).toList();
        } else if (status != null) {
            return turnoRepository.findByStatusIgnoreCase(status).stream().map(this::toDto).toList();
        }
        return turnoRepository.findAll().stream().map(this::toDto).toList();
    }

    @Transactional
    public TurnoDto createTurno(TurnoDto dto) {
        String normalizedProfRun = RunValidator.normalizeRun(dto.getProfessionalRun());

        Turno turno = Turno.builder()
                .date(dto.getDate())
                .timeSlot(dto.getTimeSlot())
                .professionalRun(normalizedProfRun)
                .professionalName(dto.getProfessionalName())
                .serviceType(dto.getServiceType())
                .boxNumber(dto.getBoxNumber())
                .status(dto.getStatus() != null ? dto.getStatus() : "disponible")
                .isOnline(dto.getIsOnline() != null ? dto.getIsOnline() : false)
                .appointmentId(dto.getAppointmentId())
                .build();

        return toDto(turnoRepository.save(turno));
    }

    @Transactional
    public TurnoDto updateTurnoStatus(Long id, String status) {
        Turno turno = turnoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Turno no encontrado con id: " + id));
        turno.setStatus(status.toLowerCase());
        if ("disponible".equalsIgnoreCase(status)) {
            turno.setAppointmentId(null);
        }
        return toDto(turnoRepository.save(turno));
    }

    public TurnoDto toDto(Turno turno) {
        return TurnoDto.builder()
                .id(turno.getId())
                .date(turno.getDate())
                .timeSlot(turno.getTimeSlot())
                .professionalRun(turno.getProfessionalRun())
                .professionalName(turno.getProfessionalName())
                .serviceType(turno.getServiceType())
                .boxNumber(turno.getBoxNumber())
                .status(turno.getStatus())
                .isOnline(turno.getIsOnline())
                .appointmentId(turno.getAppointmentId())
                .build();
    }
}
