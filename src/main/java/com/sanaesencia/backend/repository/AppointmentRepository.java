package com.sanaesencia.backend.repository;

import com.sanaesencia.backend.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatientRun(String patientRun);
    List<Appointment> findByProfessionalRun(String professionalRun);

    @Query("SELECT a FROM Appointment a WHERE (a.professionalRun = :profRun OR LOWER(a.professionalName) = LOWER(:profName)) AND a.date = :date AND a.timeSlot = :timeSlot AND UPPER(a.status) != 'CANCELADA' AND UPPER(a.state) != 'CANCELADA'")
    List<Appointment> findProfessionalConflicts(
            @Param("profRun") String profRun,
            @Param("profName") String profName,
            @Param("date") LocalDate date,
            @Param("timeSlot") String timeSlot
    );

    @Query("SELECT a FROM Appointment a WHERE a.boxNumber = :boxNumber AND a.date = :date AND a.timeSlot = :timeSlot AND UPPER(a.status) != 'CANCELADA' AND UPPER(a.state) != 'CANCELADA'")
    List<Appointment> findBoxConflicts(
            @Param("boxNumber") Integer boxNumber,
            @Param("date") LocalDate date,
            @Param("timeSlot") String timeSlot
    );

    @Query("SELECT a FROM Appointment a WHERE a.patientRun = :patientRun AND a.date = :date AND a.timeSlot = :timeSlot AND UPPER(a.status) != 'CANCELADA' AND UPPER(a.state) != 'CANCELADA'")
    List<Appointment> findPatientConflicts(
            @Param("patientRun") String patientRun,
            @Param("date") LocalDate date,
            @Param("timeSlot") String timeSlot
    );
}
