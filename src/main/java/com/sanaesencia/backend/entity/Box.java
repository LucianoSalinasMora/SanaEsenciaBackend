package com.sanaesencia.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "boxes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Box {

    @Id
    @Column(name = "box_number", nullable = false)
    private Integer boxNumber;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "status", length = 20, nullable = false)
    @Builder.Default
    private String status = "disponible";

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "current_appointment_id")
    private Long currentAppointmentId;
}
