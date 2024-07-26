package dev.patika.vet.manager.system.business.concretes;

import dev.patika.vet.manager.system.business.abstracts.IVaccineService;
import dev.patika.vet.manager.system.dao.VaccineRepo;
import dev.patika.vet.manager.system.entities.Animal;
import dev.patika.vet.manager.system.entities.Vaccine;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VaccineManager implements IVaccineService {
    private final VaccineRepo vaccineRepo;


    public VaccineManager(VaccineRepo vaccineRepo) {
        this.vaccineRepo = vaccineRepo;
    }


    @Override
    public Vaccine save(Vaccine vaccine) {
        return null;
    }

    @Override
    public Vaccine update(Vaccine vaccine) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public List<Vaccine> getAllVaccines() {
        return List.of();
    }

    @Override
    public Vaccine get(Long id) {
        return null;
    }

    @Override
    public Page<Vaccine> cursor(int page, int pageSize) {
        return null;
    }

    @Override
    public List<Vaccine> getVaccinesByAnimalId(Long animalId) {
        return List.of();
    }

    @Override
    public List<Vaccine> getVaccinesByDateRange(LocalDate startDate, LocalDate endDate) {
        return List.of();
    }

    @Override
    public List<Animal> getAnimalsWithUpcomingVaccines(LocalDate startDate, LocalDate endDate) {
        return List.of();
    }
}
