package dev.patika.vet.manager.system.business.concretes;

import dev.patika.vet.manager.system.business.abstracts.IAvailableDateService;
import dev.patika.vet.manager.system.core.exception.NotFoundException;
import dev.patika.vet.manager.system.core.utilies.Msg;
import dev.patika.vet.manager.system.dao.AvailableDateRepo;
import dev.patika.vet.manager.system.dao.DoctorRepo;
import dev.patika.vet.manager.system.entities.AvailableDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        // BURAYA TEKRAR BAKKK !
        if (availableDate.getId() > 0 && this.availableDateRepo.existsById(availableDate.getId())) {
            throw new NotFoundException("Aynı ID ile kayıt yapılamaz: " + availableDate.getId());
        }

        Optional<AvailableDate> existedAvailableDate = availableDateRepo.findByDoctorIdAndAvailableDate(availableDate.getDoctor().getId(), availableDate.getAvailableDate());

        if (existedAvailableDate.isPresent()
                && existedAvailableDate.get().getAvailableDate().equals(availableDate.getAvailableDate())
                && existedAvailableDate.get().getDoctor().getId() == availableDate.getDoctor().getId()) {
            // doktorun boş olmama kontrolü
            throw new NotFoundException("Uyarı : Doktor'un bu tarihte boş zamanı bulunmamaktadır.");
        }

        LocalDate today = LocalDate.now();
        // geçmiş tarih kontrolü
        if (availableDate.getAvailableDate().isBefore(today)) {
            throw new NotFoundException("Uyarı : Girdiğiniz tarih geçmiş zamanlıdır. Lütfen güncel bir tarih giriniz.");
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
    // cursor ----> müsait gün sayfalama
    @Override
    public Page<AvailableDate> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page,pageSize);
        return this.availableDateRepo.findAll(pageable);
    }

    @Override
    public List<AvailableDate> getAvailableDatesByDoctorId(Long doctorId) {
        if (doctorId == null || doctorId <= 0) {
            throw new NotFoundException("Doktor kimliği null veya sıfırdan küçük/eşit olamaz.");
        }
        return this.availableDateRepo.findByDoctorId(doctorId);
    }

    @Override
    public boolean isDoctorAvailableOnDate(Long doctorId, LocalDate date) {
        if(doctorId == null || doctorId <= 0 || date == null) {
            throw new NotFoundException(Msg.NOT_FOUND);
        }
        List<AvailableDate> availableDates = this.availableDateRepo.findByDoctorId(doctorId);

        // doktorun uyggunluğuna göre kontrol yaparsak:
        for (AvailableDate availableDate : availableDates) {
            if(availableDate.getAvailableDate().equals(date)) {
                // doktor müsaitse --> true
                return true;
            }
        }
        return false;    // doktor müsait değil
    }

    @Override
    public List<AvailableDate> getAvailableDatesByDoctorIdAndDateRange(Long doctorId, LocalDate startDate, LocalDate endDate) {
        if (doctorId == null || doctorId <= 0 || startDate == null || endDate == null || startDate.isAfter(endDate)) {
            throw new NotFoundException("Doktor kimliği geçersiz, başlangıç veya bitiş tarihi null ya da başlangıç tarihi bitiş tarihinden sonra olamaz.");
        }

        List<AvailableDate> availableDates = this.availableDateRepo.findByDoctorIdAndAvailableDateBetween(doctorId, startDate, endDate);

        if (availableDates == null || availableDates.isEmpty()) {
            throw new NotFoundException("Doktorun girilen tarih aralığına ait boş zamani bulunamadı");
        }

        return availableDates;
    }


}
