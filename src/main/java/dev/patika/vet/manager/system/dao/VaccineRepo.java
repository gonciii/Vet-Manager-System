package dev.patika.vet.manager.system.dao;


import dev.patika.vet.manager.system.entities.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface VaccineRepo  extends JpaRepository<Vaccine,Long> {

    // Belirli bir hayvana ait tüm aşı kayıtlarını getirme
    List<Vaccine> findByAnimalId(Long animalId);
    // aşı kodu
    List<Vaccine> findByCode(String code);
    // aşı ismi
    List<Vaccine> findByName(String name);
    //
    Optional<Vaccine> findByAnimalIdAndName(Long animalId, String name);

    // Belirli bir tarih aralığına göre koruma başlangıcı olan aşı kayıtlarını getirme
    List<Vaccine> findByProtectionStartDateBetween(LocalDate startDate, LocalDate endDate);

    // Belirli bir hayvana ait, belirli bir tarih aralığına göre koruma başlangıcı olan aşı kayıtlarını getirme
    List<Vaccine> findByAnimalIdAndProtectionStartDateBetween(Long animalId, LocalDate startDate, LocalDate endDate);

}
