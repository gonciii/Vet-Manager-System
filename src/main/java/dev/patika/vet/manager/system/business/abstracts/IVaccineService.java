package dev.patika.vet.manager.system.business.abstracts;

import dev.patika.vet.manager.system.dto.request.vaccine.VaccineSaveRequest;
import dev.patika.vet.manager.system.dto.request.vaccine.VaccineUpdateRequest;
import dev.patika.vet.manager.system.dto.response.vaccine.VaccineResponse;
import dev.patika.vet.manager.system.entities.Vaccine;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface IVaccineService {
    VaccineResponse save(VaccineSaveRequest vaccineSaveRequest); // Aşı kaydetme
    VaccineResponse update(VaccineUpdateRequest vaccineUpdateRequest); // Aşı güncelleme
    boolean delete(Long id);
    List<Vaccine> getAllVaccines();
    Vaccine get(Long id);
    Page<Vaccine> cursor(int page, int pageSize);
    List<Vaccine> getVaccinesByAnimalId(Long animalId);
    List<Vaccine> getVaccinesByDateRange(LocalDate startDate, LocalDate endDate);
}