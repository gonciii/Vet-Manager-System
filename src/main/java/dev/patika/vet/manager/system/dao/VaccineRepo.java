package dev.patika.vet.manager.system.dao;

import dev.patika.vet.manager.system.entities.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VaccineRepo extends JpaRepository<Vaccine, Long> {
    List<Vaccine> findByAnimalId(Long animalId);
    List<Vaccine> findByProtectionFinishDateBetween(LocalDate startDate, LocalDate endDate);
    boolean existsByAnimal_IdAndCode(Long animalId, String code);
}