package com.sanaesencia.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "appointments", indexes = {
    @Index(name = "idx_appt_patient_run", columnList = "patient_run"),
    @Index(name = "idx_appt_prof_date", columnList = "professional_run, date"),
    @Index(name = "idx_appt_status", columnList = "status, state")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "patient_name", length = 120, nullable = false)
    private String patientName;

    @Column(name = "patient_email", length = 120, nullable = false)
    private String patientEmail;

    @Column(name = "patient_phone", length = 20)
    private String patientPhone;

    @Column(name = "patient_run", length = 12, nullable = false)
    private String patientRun;

    @Column(name = "professional_run", length = 12, nullable = false)
    private String professionalRun;

    @Column(name = "professional_name", length = 120, nullable = false)
    private String professionalName;

    @Column(name = "service_type", length = 100, nullable = false)
    private String serviceType;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "time_slot", length = 20, nullable = false)
    private String timeSlot;

    @Column(name = "is_online", nullable = false)
    @Builder.Default
    private Boolean isOnline = false;

    @Column(name = "box_number")
    private Integer boxNumber;

    @Column(name = "status", length = 30, nullable = false)
    @Builder.Default
    private String status = "Agendada";

    @Column(name = "state", length = 30, nullable = false)
    @Builder.Default
    private String state = "PENDIENTE";

    @Column(name = "patient_notes", length = 4000)
    @Builder.Default
    private String patientNotes = "";

    @Column(name = "turno_id")
    private Long turnoId;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;
}
