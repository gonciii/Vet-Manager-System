package dev.patika.vet.manager.system.dao;


import dev.patika.vet.manager.system.entities.AvailableDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AvailableDateRepo  extends JpaRepository<AvailableDate,Long> {

    Optional<AvailableDate> findByDoctorIdAndAvailableDate(Long doctorId, LocalDate date);

    List<AvailableDate> findByDoctorId(long doctorId);
}
