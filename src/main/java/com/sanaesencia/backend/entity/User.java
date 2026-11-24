package com.sanaesencia.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;

@Entity
@Table(name = "users", indexes = {
    @Index(name = "idx_users_email", columnList = "email"),
    @Index(name = "idx_users_role", columnList = "role")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @Column(name = "run", length = 12, nullable = false)
    private String run;

    @Column(name = "name", length = 120, nullable = false)
    private String name;

    @Column(name = "email", length = 120, nullable = false, unique = true)
    private String email;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "password_hash", length = 255, nullable = false)
    private String passwordHash;

    @Column(name = "role", length = 20, nullable = false)
    @Builder.Default
    private String role = "PACIENTE";

    @Column(name = "security_question", length = 255)
    private String securityQuestion;

    @Column(name = "security_answer", length = 255)
    private String securityAnswer;

    @Column(name = "specialty", length = 100)
    private String specialty;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;
}
