package com.sanaesencia.backend.repository;

import com.sanaesencia.backend.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Long> {
    List<Turno> findByDate(LocalDate date);
    List<Turno> findByProfessionalRun(String professionalRun);
    List<Turno> findByStatusIgnoreCase(String status);
    List<Turno> findByDateAndProfessionalRun(LocalDate date, String professionalRun);
    List<Turno> findByDateAndProfessionalRunAndStatusIgnoreCase(LocalDate date, String professionalRun, String status);
}
