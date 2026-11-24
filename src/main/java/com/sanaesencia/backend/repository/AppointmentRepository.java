package com.sanaesencia.backend.repository;

import com.sanaesencia.backend.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatientRun(String patientRun);
    List<Appointment> findByProfessionalRun(String professionalRun);
    List<Appointment> findByProfessionalRunAndDateAndTimeSlotAndStatusNot(
        String professionalRun, LocalDate date, String timeSlot, String status
    );
    List<Appointment> findByBoxNumberAndDateAndTimeSlotAndStatusNot(
        Integer boxNumber, LocalDate date, String timeSlot, String status
    );
}
