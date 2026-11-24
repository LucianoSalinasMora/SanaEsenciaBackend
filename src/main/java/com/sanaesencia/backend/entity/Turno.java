package com.sanaesencia.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "turnos", indexes = {
    @Index(name = "idx_turnos_date_prof", columnList = "date, professional_run"),
    @Index(name = "idx_turnos_status", columnList = "status")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "time_slot", length = 20, nullable = false)
    private String timeSlot;

    @Column(name = "professional_run", length = 12, nullable = false)
    private String professionalRun;

    @Column(name = "professional_name", length = 120, nullable = false)
    private String professionalName;

    @Column(name = "service_type", length = 100, nullable = false)
    private String serviceType;

    @Column(name = "box_number")
    private Integer boxNumber;

    @Column(name = "status", length = 20, nullable = false)
    @Builder.Default
    private String status = "disponible";

    @Column(name = "is_online", nullable = false)
    @Builder.Default
    private Boolean isOnline = false;

    @Column(name = "appointment_id")
    private Long appointmentId;
}
