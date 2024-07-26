package dev.patika.vet.manager.system.business.abstracts;

import dev.patika.vet.manager.system.entities.Animal;
import dev.patika.vet.manager.system.entities.Vaccine;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface IVaccineService {

    // save ---> yeni aşı kaydetme
    Vaccine save(Vaccine vaccine);

    // update --> aşı güncelleme
    Vaccine update(Vaccine vaccine);

    // delete---> aşı kaydı silme
    boolean delete(Long id);

    // tüm aşı kaydı getirmek için
    List<Vaccine> getAllVaccines();

    // id'ye göre aşı kaydı getirme işlemi
    Vaccine get(Long id);

    // aşı sayfalama ---> page
    Page<Vaccine> cursor(int page,int pageSize);

    // belirli bir hayvana göre aşı kaydı getirmek için
    List<Vaccine> getVaccinesByAnimalId(Long animalId);

    // Belirli bir tarih aralığına göre aşı kayıtlarını getirme
    List<Vaccine> getVaccinesByDateRange(LocalDate startDate, LocalDate endDate);

    // Belirli bir tarih aralığına göre koruma başlangıcı olan aşı kayıtlarını getirme
    List<Animal> getAnimalsWithUpcomingVaccines(LocalDate startDate, LocalDate endDate);

    // Belirli bir hayvana ait, belirli bir tarih aralığına göre koruma başlangıcı olan aşı kayıtlarını getirme
    List<Vaccine> findByAnimalIdAndProtectionStartDateBetween(Long animalId, LocalDate startDate, LocalDate endDate);
}
