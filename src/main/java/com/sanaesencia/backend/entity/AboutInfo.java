package com.sanaesencia.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "about_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AboutInfo {

    @Id
    @Builder.Default
    private Integer id = 1;

    @Column(name = "center_title", length = 150, nullable = false)
    private String centerTitle;

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "address", length = 200, nullable = false)
    private String address;

    @Column(name = "phone", length = 30, nullable = false)
    private String phone;

    @Column(name = "email", length = 120, nullable = false)
    private String email;
}
