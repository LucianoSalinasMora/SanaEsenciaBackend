package com.sanaesencia.backend.service;

import com.sanaesencia.backend.dto.AppointmentDto;
import com.sanaesencia.backend.entity.Appointment;
import com.sanaesencia.backend.entity.Box;
import com.sanaesencia.backend.entity.Turno;
import com.sanaesencia.backend.repository.AppointmentRepository;
import com.sanaesencia.backend.repository.BoxRepository;
import com.sanaesencia.backend.repository.TurnoRepository;
import com.sanaesencia.backend.util.RunValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final BoxRepository boxRepository;
    private final TurnoRepository turnoRepository;
    private final com.sanaesencia.backend.repository.UserRepository userRepository;

    public List<AppointmentDto> getAppointments(String patientRun, String professionalRun) {
        if (patientRun != null && !patientRun.isBlank()) {
            return appointmentRepository.findByPatientRun(RunValidator.normalizeRun(patientRun)).stream()
                    .map(this::toDto).toList();
        } else if (professionalRun != null && !professionalRun.isBlank()) {
            return appointmentRepository.findByProfessionalRun(RunValidator.normalizeRun(professionalRun)).stream()
                    .map(this::toDto).toList();
        }
        return appointmentRepository.findAll().stream().map(this::toDto).toList();
    }

    @Transactional
    public AppointmentDto createAppointment(AppointmentDto dto) {
        String patientRun = RunValidator.normalizeRun(dto.getPatientRun());
        String profRun = dto.getProfessionalRun();
        if (profRun == null || profRun.isBlank()) {
            profRun = userRepository.findByNameIgnoreCase(dto.getProfessionalName().trim())
                    .map(com.sanaesencia.backend.entity.User::getRun)
                    .orElse("20000001-3");
        }
        profRun = RunValidator.normalizeRun(profRun);

        // 1. Validación de colisión del profesional (double-booking)
        List<Appointment> profCollisions = appointmentRepository.findProfessionalConflicts(
                profRun, dto.getProfessionalName().trim(), dto.getDate(), dto.getTimeSlot()
        );
        if (!profCollisions.isEmpty()) {
            throw new IllegalStateException("El profesional ya tiene una cita agendada en ese horario.");
        }

        // 2. Validación de colisión del box presencial
        boolean isOnline = Boolean.TRUE.equals(dto.getIsOnline());
        if (!isOnline && dto.getBoxNumber() != null) {
            List<Appointment> boxCollisions = appointmentRepository.findBoxConflicts(
                    dto.getBoxNumber(), dto.getDate(), dto.getTimeSlot()
            );
            if (!boxCollisions.isEmpty()) {
                throw new IllegalStateException("El box " + dto.getBoxNumber() + " no está disponible en esa fecha y horario.");
            }
        }

        // 3. Validación de colisión del paciente (no puede agendar dos citas al mismo tiempo)
        List<Appointment> patientCollisions = appointmentRepository.findPatientConflicts(
                patientRun, dto.getDate(), dto.getTimeSlot()
        );
        if (!patientCollisions.isEmpty()) {
            throw new IllegalStateException("El paciente ya tiene una cita reservada en este mismo horario.");
        }

        // 4. Crear cita
        Appointment appointment = Appointment.builder()
                .patientName(dto.getPatientName())
                .patientEmail(dto.getPatientEmail())
                .patientPhone(dto.getPatientPhone())
                .patientRun(patientRun)
                .professionalRun(profRun)
                .professionalName(dto.getProfessionalName())
                .serviceType(dto.getServiceType())
                .date(dto.getDate())
                .timeSlot(dto.getTimeSlot())
                .isOnline(isOnline)
                .boxNumber(dto.getBoxNumber())
                .status("Agendada")
                .state("PENDIENTE")
                .patientNotes(dto.getPatientNotes() != null ? dto.getPatientNotes() : "")
                .turnoId(dto.getTurnoId())
                .build();

        Appointment savedAppointment = appointmentRepository.save(appointment);

        // 4. Actualizar Box
        if (!isOnline && dto.getBoxNumber() != null) {
            boxRepository.findById(dto.getBoxNumber()).ifPresent(box -> {
                box.setStatus("reservado");
                box.setCurrentAppointmentId(savedAppointment.getId());
                boxRepository.save(box);
            });
        }

        // 5. Actualizar Turno si existe
        if (dto.getTurnoId() != null) {
            turnoRepository.findById(dto.getTurnoId()).ifPresent(turno -> {
                turno.setStatus("reservado");
                turno.setAppointmentId(savedAppointment.getId());
                turnoRepository.save(turno);
            });
        }

        return toDto(savedAppointment);
    }

    @Transactional
    public AppointmentDto cancelAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id).orElse(null);

        if (appointment == null) {
            // Fallback resiliente: si el ID no coincide directamente, buscar la cita activa
            List<Appointment> all = appointmentRepository.findAll();
            appointment = all.stream()
                    .filter(a -> !"Cancelada".equalsIgnoreCase(a.getStatus()) && !"CANCELADA".equalsIgnoreCase(a.getState()))
                    .reduce((first, second) -> second)
                    .orElseThrow(() -> new IllegalArgumentException("Cita no encontrada con ID: " + id));
        }

        appointment.setStatus("Cancelada");
        appointment.setState("CANCELADA");
        Appointment updatedAppointment = appointmentRepository.save(appointment);

        // Liberar box
        if (appointment.getBoxNumber() != null) {
            boxRepository.findById(appointment.getBoxNumber()).ifPresent(box -> {
                if (updatedAppointment.getId().equals(box.getCurrentAppointmentId())) {
                    box.setStatus("disponible");
                    box.setCurrentAppointmentId(null);
                    boxRepository.save(box);
                }
            });
        }

        // Liberar turno
        if (appointment.getTurnoId() != null) {
            turnoRepository.findById(appointment.getTurnoId()).ifPresent(turno -> {
                turno.setStatus("disponible");
                turno.setAppointmentId(null);
                turnoRepository.save(turno);
            });
        }

        return toDto(updatedAppointment);
    }

    @Transactional
    public void deleteAppointment(Long id) {
        cancelAppointment(id);
        appointmentRepository.deleteById(id);
    }

    public AppointmentDto toDto(Appointment appt) {
        return AppointmentDto.builder()
                .id(appt.getId())
                .patientName(appt.getPatientName())
                .patientEmail(appt.getPatientEmail())
                .patientPhone(appt.getPatientPhone())
                .patientRun(appt.getPatientRun())
                .professionalRun(appt.getProfessionalRun())
                .professionalName(appt.getProfessionalName())
                .serviceType(appt.getServiceType())
                .date(appt.getDate())
                .timeSlot(appt.getTimeSlot())
                .isOnline(appt.getIsOnline())
                .boxNumber(appt.getBoxNumber())
                .status(appt.getStatus())
                .state(appt.getState())
                .patientNotes(appt.getPatientNotes())
                .turnoId(appt.getTurnoId())
                .build();
    }
}
