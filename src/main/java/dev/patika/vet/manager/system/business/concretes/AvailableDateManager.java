package dev.patika.vet.manager.system.business.concretes;

import dev.patika.vet.manager.system.business.abstracts.IAvailableDateService;
import dev.patika.vet.manager.system.core.exception.NotFoundException;
import dev.patika.vet.manager.system.core.utilies.Msg;
import dev.patika.vet.manager.system.dao.AvailableDateRepo;
import dev.patika.vet.manager.system.dao.DoctorRepo;
import dev.patika.vet.manager.system.entities.AvailableDate;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service // servis katman anatasyonu
public class AvailableDateManager implements IAvailableDateService {

    private final AvailableDateRepo availableDateRepo;
    private  final DoctorRepo doctorRepo;


    public AvailableDateManager(AvailableDateRepo availableDateRepo, DoctorRepo doctorRepo) {
        this.availableDateRepo = availableDateRepo;
        this.doctorRepo = doctorRepo;
    }

    // SAVE
    @Override
    public AvailableDate save(AvailableDate availableDate) {
        Optional<AvailableDate>  existedAvailableDate = availableDateRepo.findByDoctorIdAndAvailableDate(availableDate.getDoctor().getId(),availableDate.getAvailableDate());
        if(! existedAvailableDate.isEmpty() && existedAvailableDate.get().getAvailableDate().equals(availableDate.getAvailableDate())
        && existedAvailableDate.get().getDoctor().getId() == availableDate.getDoctor().getId()
        ) {
            // doktorun boş olmama kontrolü
            throw new IllegalArgumentException("Uyarı : Doktor'un bu tarihte boş zamanı bulunmamaktadır.");
        }
        LocalDate today = LocalDate.now();
        // geçmiş tarih kontrolü !!
        if (availableDate.getAvailableDate().isBefore(today)) {
            throw new IllegalArgumentException("Uyarı : Girdiğiniz tarih geçmiş zamanlıdır.Lütfen güncel bir tarih giriniz.");
        }
        return this.availableDateRepo.save(availableDate);
    }

    // GET
    @Override
    public AvailableDate get(Long id) {
        return this.availableDateRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    // GET ALL
    @Override
    public List<AvailableDate> getAllAvailableDates() {
        List<AvailableDate> availableDates = availableDateRepo.findAll();
        return this.availableDateRepo.findAll();
    }

    // UPDATE
    @Override
    public AvailableDate update(AvailableDate availableDate) {
        // buraya bak!!!
        if (availableDate == null || availableDate.getId() <= 0) {
            throw new IllegalArgumentException(Msg.NOT_FOUND);
        }
        get(availableDate.getId());
        return this.availableDateRepo.save(availableDate);
    }

    @Override
    public boolean delete(Long id) {
        this.get(id);
        if (id == null || id <= 0) {
            throw  new IllegalArgumentException(Msg.NOT_FOUND);
        }
        get(id);
        AvailableDate availableDate = get(id);
        return true;
    }

    @Override
    public Page<AvailableDate> cursor(int page, int pageSize) {
        return null;
    }

    @Override
    public List<AvailableDate> getAvailableDatesByDoctorId(Long doctorId) {
        return List.of();
    }

    @Override
    public boolean isDoctorAvailableOnDate(Long doctorId, LocalDate date) {
        return false;
    }

    @Override
    public List<AvailableDate> getAvailableDatesByDoctorIdAndDateRange(Long doctorId, LocalDate startDate, LocalDate endDate) {
        return List.of();
    }
}
