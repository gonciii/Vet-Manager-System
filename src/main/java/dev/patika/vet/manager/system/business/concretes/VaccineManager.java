package dev.patika.vet.manager.system.business.concretes;

import dev.patika.vet.manager.system.business.abstracts.IVaccineService;
import dev.patika.vet.manager.system.core.exception.NotFoundException;
import dev.patika.vet.manager.system.core.utilies.Msg;
import dev.patika.vet.manager.system.dao.VaccineRepo;
import dev.patika.vet.manager.system.entities.Animal;
import dev.patika.vet.manager.system.entities.Vaccine;
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


    public VaccineManager(VaccineRepo vaccineRepo) {
        this.vaccineRepo = vaccineRepo;
    }

    // aşı kaydetme işlemi :
    @Override
    public Vaccine save(Vaccine vaccine) {
        // aynı id ile kaydedilmeyi engellemek için :
        if(vaccine.getId() >0 && this.vaccineRepo.existsById(vaccine.getId())) {
            throw new NotFoundException("Aynı ID ile aşı kaydedilmez : " + vaccine.getId());
        }

        Objects.requireNonNull(vaccine, "Aşı bulunamadı.");
        Objects.requireNonNull(vaccine.getProtectionStartDate(), "Başlangıç tarihi boş olamaz.");
        Objects.requireNonNull(vaccine.getProtectionFinishDate(), "Bitiş tarihi boş olamaz.");

        LocalDate today = LocalDate.now();

        // Aynı hayvan ID ve ad ile var olan bir aşı kaydı olup olmadığını kontrol et
        Optional<Vaccine> existingVaccine = vaccineRepo.findByAnimalIdAndName(
                vaccine.getAnimal().getId(), vaccine.getName());

        // Eğer mevcut bir aşı varsa ve koruyuculuk tarihi bitmemişse hata fırlat
        if (existingVaccine.isPresent() && existingVaccine.get().getProtectionFinishDate().isAfter(today)) {
            throw new NotFoundException("Aynı ad, kod ve hayvan ID'sine sahip bir aşı kaydı zaten mevcut ve koruyuculuk süresi devam ediyor!");
        }

        // Başlangıç tarihinin bitiş tarihinden sonra olup olmadığını kontrol et
        if (vaccine.getProtectionStartDate().isAfter(vaccine.getProtectionFinishDate())) {
            throw new NotFoundException("Başlangıç tarihi, bitiş tarihinden sonra olamaz.");
        }

        return vaccineRepo.save(vaccine);
    }

    // aşı güncelleme işlemi :
    @Override
    public Vaccine update(Vaccine vaccine) {
        Objects.requireNonNull(vaccine, Msg.NOT_FOUND);
        Objects.requireNonNull(vaccine.getProtectionStartDate(),Msg.NULL_DATE);
        Objects.requireNonNull(vaccine.getProtectionFinishDate(),Msg.NULL_DATE);

        // // Koruyuculuk başlangıç tarihinin bitiş tarihinden sonra olup olmadığını kontrol et
        if(vaccine.getProtectionStartDate().isAfter(vaccine.getProtectionFinishDate())) {
            throw new NotFoundException("Başlangıç tarihi,bitiş tarihinden sonra olamaz.");
        }

        get(vaccine.getId());
        return vaccineRepo.save(vaccine);
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

        return vaccineRepo.findByProtectionStartDateBetween(startDate, endDate);
    }

    @Override
    public List<Animal> getAnimalsWithUpcomingVaccines(LocalDate startDate, LocalDate endDate) {
        Objects.requireNonNull(startDate, Msg.NULL_DATE);
        Objects.requireNonNull(endDate, Msg.NULL_DATE);

        if (startDate.isAfter(endDate)) {
            throw new NotFoundException("Başlangıç tarihi bitiş tarihinden sonra olamaz.");
        }

        return vaccineRepo.findByProtectionStartDateBetween(startDate, endDate)
                .stream()
                .map(Vaccine::getAnimal)
                .distinct()
                .toList();
    }

    @Override
    public List<Vaccine> findByAnimalIdAndProtectionStartDateBetween(Long animalId, LocalDate startDate, LocalDate endDate) {
        Objects.requireNonNull(startDate, Msg.NULL_DATE);
        Objects.requireNonNull(endDate, Msg.NULL_DATE);

        if (startDate.isAfter(endDate)) {
            throw new NotFoundException("Başlangıç tarihi bitiş tarihinden sonra olamaz.");
        }

        return vaccineRepo.findByAnimalIdAndProtectionStartDateBetween(animalId, startDate, endDate);
    }


}
