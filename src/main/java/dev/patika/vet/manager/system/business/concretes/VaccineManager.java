package dev.patika.vet.manager.system.business.concretes;

import dev.patika.vet.manager.system.business.abstracts.IVaccineService;
import dev.patika.vet.manager.system.core.config.modelmapper.IModelMapperService;
import dev.patika.vet.manager.system.core.exception.NotFoundException;
import dev.patika.vet.manager.system.core.utilies.Msg;
import dev.patika.vet.manager.system.core.utilies.ResultHelper;
import dev.patika.vet.manager.system.dao.VaccineRepo;
import dev.patika.vet.manager.system.dto.request.vaccine.VaccineSaveRequest;
import dev.patika.vet.manager.system.dto.response.vaccine.VaccineResponse;
import dev.patika.vet.manager.system.entities.Animal;
import dev.patika.vet.manager.system.entities.Vaccine;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class VaccineManager implements IVaccineService {
    private final VaccineRepo vaccineRepo;
    private IModelMapperService modelMapper;



    public VaccineManager(VaccineRepo vaccineRepo) {
        this.vaccineRepo = vaccineRepo;
    }



    @Override
    public Vaccine save(Vaccine vaccine) {
        if(vaccineRepo.existsByName(vaccine.getName())) {
            throw new NotFoundException(Msg.SAME_VACCINE_NAME_AND_CODE);
        }
        if (vaccineRepo.existsByCode(vaccine.getCode())) {
        throw new NotFoundException(Msg.SAME_VACCINE_NAME_AND_CODE);
        }

        return this.vaccineRepo.save(vaccine);
    }

    // aşı güncelleme işlemi :
    @Override
    public Vaccine update(Vaccine vaccine) {
        // Check if the vaccine exists
        Vaccine existingVaccine = this.vaccineRepo.findById(vaccine.getId())
                .orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));

        // Check if a vaccine with the same name or code already exists
        if (vaccineRepo.existsByName(vaccine.getName())) {
            throw new NotFoundException(Msg.SAME_VACCINE_NAME_AND_CODE);
        }
        if (vaccineRepo.existsByCode(vaccine.getCode())) {
            throw new NotFoundException(Msg.SAME_VACCINE_NAME_AND_CODE);
        }

        // Update the vaccine
        existingVaccine.setName(vaccine.getName());
        existingVaccine.setCode(vaccine.getCode());
        existingVaccine.setProtectionStartDate(vaccine.getProtectionStartDate());
        existingVaccine.setProtectionFinishDate(vaccine.getProtectionFinishDate());

        return this.vaccineRepo.save(existingVaccine);
    }

    // id'ye göre aşı sil
    @Override
    public boolean delete(Long id) {
        Optional<Vaccine> vaccine = vaccineRepo.findById(id);
        if(vaccine.isEmpty()) {
            throw new NotFoundException(id + " id'li kayıt sistemde bulunamadı.");
        }
        // eğer varsa kayıt sil
        vaccineRepo.deleteById(id);
        return true;
    }

    // tüm aşılar
    @Override
    public List<Vaccine> getAllVaccines() {
        return vaccineRepo.findAll();
    }
    // id'ye göre aşı getir
    @Override
    public Vaccine get(Long id) {
        return vaccineRepo.findById(id).orElseThrow(()
                -> new NotFoundException(Msg.NOT_FOUND));
    }

    // aşı sayfalamak için :
    @Override
    public Page<Vaccine> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page,pageSize);
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
            throw new NotFoundException("Başlangıç tarihi bitiş tarihinden sonra olamaz.");
        }

        return vaccineRepo.findByProtectionFinishDateBetween(startDate, endDate);
    }


}
