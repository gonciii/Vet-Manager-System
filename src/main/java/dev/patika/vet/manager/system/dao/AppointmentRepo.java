package dev.patika.vet.manager.system.dao;



import dev.patika.vet.manager.system.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


// DATA ACCESS LAYER


@Repository
public interface AppointmentRepo  extends JpaRepository<Appointment,Long> {

    // şuna bak
    Optional<Appointment> findByDoctorIdAndAppointmentDate(Long doctorId, LocalDateTime appointmentDate);

    @Query("SELECT a FROM Appointment a WHERE a.appointmentDate BETWEEN :startDate AND :endDate AND a.animal.id = :animalId")
    List<Appointment> findByAppointmentDateAndAnimalIdBetween(
            @Param("startDate") LocalDateTime startDatetime,
            @Param("endDate") LocalDateTime endDatetime,
            @Param("animalId") Long animalId
    );

    @Query("SELECT a FROM Appointment a WHERE a.appointmentDate BETWEEN :startDate AND :endDate AND a.doctor.id = :doctorId")
    List<Appointment> findByAppointmentDateAndDoctorIdBetween(
            @Param("startDate") LocalDateTime startDatetime,
            @Param("endDate") LocalDateTime endDatetime,
            @Param("doctorId") Long doctorId
    );

}
