package dev.patika.vet.manager.system.business.concretes;

import dev.patika.vet.manager.system.business.abstracts.IVaccineService;
import dev.patika.vet.manager.system.core.config.modelmapper.IModelMapperService;
import dev.patika.vet.manager.system.core.exception.NotFoundException;
import dev.patika.vet.manager.system.core.utilies.Msg;
import dev.patika.vet.manager.system.dao.AnimalRepo;
import dev.patika.vet.manager.system.dao.VaccineRepo;
import dev.patika.vet.manager.system.dto.request.vaccine.VaccineSaveRequest;
import dev.patika.vet.manager.system.dto.request.vaccine.VaccineUpdateRequest;
import dev.patika.vet.manager.system.dto.response.vaccine.VaccineResponse;
import dev.patika.vet.manager.system.entities.Animal;
import dev.patika.vet.manager.system.entities.Vaccine;
import jakarta.validation.ValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class VaccineManager implements IVaccineService {

    private final VaccineRepo vaccineRepo;
    private final AnimalRepo animalRepo;
    private final IModelMapperService modelMapper;

    public VaccineManager(VaccineRepo vaccineRepo, AnimalRepo animalRepo, IModelMapperService modelMapper) {
        this.vaccineRepo = vaccineRepo;
        this.animalRepo = animalRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public VaccineResponse save(VaccineSaveRequest vaccineSaveRequest) {
        Animal animal = animalRepo.findById(vaccineSaveRequest.getAnimalId())
                .orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));

        if (vaccineSaveRequest.getProtectionFinishDate().isBefore(LocalDate.now())) {
            throw new ValidationException("Aşı koruyuculuk bitiş tarihi geçmiş.");
        }

        boolean exists = vaccineRepo.existsByAnimal_IdAndCode(animal.getId(), vaccineSaveRequest.getCode());

        if (exists) {
            throw new ValidationException("Bu hayvan için bu aşı zaten kayıtlı.");
        }

        Vaccine vaccine = new Vaccine();
        vaccine.setName(vaccineSaveRequest.getName());
        vaccine.setCode(vaccineSaveRequest.getCode());
        vaccine.setProtectionStartDate(vaccineSaveRequest.getProtectionStartDate());
        vaccine.setProtectionFinishDate(vaccineSaveRequest.getProtectionFinishDate());
        vaccine.setAnimal(animal);

        Vaccine savedVaccine = vaccineRepo.save(vaccine);
        return modelMapper.forResponse().map(savedVaccine, VaccineResponse.class);
    }
    // aşı güncelleme işlemi :
    @Override
    public VaccineResponse update(VaccineUpdateRequest vaccineUpdateRequest) {
        Vaccine vaccine = vaccineRepo.findById(vaccineUpdateRequest.getId())
                .orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
        Animal animal = animalRepo.findById(vaccineUpdateRequest.getAnimalId())
                .orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));

        vaccine.setName(vaccineUpdateRequest.getName());
        vaccine.setCode(vaccineUpdateRequest.getCode());
        vaccine.setProtectionStartDate(vaccineUpdateRequest.getProtectionStartDate());
        vaccine.setProtectionFinishDate(vaccineUpdateRequest.getProtectionFinishDate());
        vaccine.setAnimal(animal);

        Vaccine updatedVaccine = vaccineRepo.save(vaccine);
        return modelMapper.forResponse().map(updatedVaccine, VaccineResponse.class);
    }

    @Override
    public boolean delete(Long id) {
        Optional<Vaccine> vaccine = vaccineRepo.findById(id);
        if (vaccine.isEmpty()) {
            throw new NotFoundException(id + " id'li kayıt sistemde bulunamadı.");
        }
        vaccineRepo.deleteById(id);
        return true;
    }

    @Override
    public List<Vaccine> getAllVaccines() {
        return vaccineRepo.findAll();
    }

    @Override
    public Vaccine get(Long id) {
        return vaccineRepo.findById(id).orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public Page<Vaccine> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return this.vaccineRepo.findAll(pageable);
    }

    @Override
    public List<Vaccine> getVaccinesByAnimalId(Long animalId) {
        return vaccineRepo.findByAnimalId(animalId);
    }

    @Override
    public List<Vaccine> getVaccinesByDateRange(LocalDate startDate, LocalDate endDate) {
        Objects.requireNonNull(startDate, Msg.NULL_DATE);
        Objects.requireNonNull(endDate, Msg.NULL_DATE);
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Başlangıç tarihi bitiş tarihinden sonra olamaz.");
        }
        return vaccineRepo.findByProtectionFinishDateBetween(startDate, endDate);
    }
}