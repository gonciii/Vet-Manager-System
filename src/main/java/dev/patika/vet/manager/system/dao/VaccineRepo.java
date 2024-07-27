package dev.patika.vet.manager.system.dao;


import dev.patika.vet.manager.system.entities.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface VaccineRepo  extends JpaRepository<Vaccine,Long> {

    List<Vaccine> findByAnimalId(Long animalId);

    List<Vaccine> findByNameAndCode(String name, String code);

    List<Vaccine> findByProtectionFinishDateBetween(LocalDate startDate, LocalDate endDate);


    boolean existsByName(String name);

    boolean existsByCode(String code);

}
