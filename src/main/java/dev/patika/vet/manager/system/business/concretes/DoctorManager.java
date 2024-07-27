package dev.patika.vet.manager.system.business.concretes;


import dev.patika.vet.manager.system.business.abstracts.IDoctorService;
import dev.patika.vet.manager.system.core.exception.NotFoundException;
import dev.patika.vet.manager.system.core.utilies.Msg;
import dev.patika.vet.manager.system.dao.AvailableDateRepo;
import dev.patika.vet.manager.system.dao.DoctorRepo;
import dev.patika.vet.manager.system.entities.AvailableDate;
import dev.patika.vet.manager.system.entities.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DoctorManager  implements IDoctorService {
    private final DoctorRepo doctorRepo;
    private final AvailableDateRepo availableDateRepo;


    // DI !
    public DoctorManager(DoctorRepo doctorRepo, AvailableDateRepo availableDateRepo) {
        this.doctorRepo = doctorRepo;
        this.availableDateRepo = availableDateRepo;
    }

    // save ---> doktor kaydetme
    @Override
    public Doctor save(Doctor doctor) {
        if(doctorRepo.existsByMail(doctor.getMail())) {
            throw new NotFoundException(Msg.SAME_EMAIL);
        }

        return this.doctorRepo.save(doctor);
    }

    @Override
    public Doctor get(long id) {
        return this.doctorRepo.findById(id).orElseThrow(()
                -> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public Page<Doctor> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page,pageSize);
        return this.doctorRepo.findAll(pageable);
    }

    @Override
    public Doctor update(Doctor doctor) {
        this.get(doctor.getId());
        return this.doctorRepo.save(doctor);
    }

    @Override
    public boolean delete(long id) {
        Doctor doctor = this.get(id);
        this.doctorRepo.delete(doctor);
        return true;
    }

    @Override
    public List<Doctor> getAllDoctors() {
        return this.doctorRepo.findAll();
    }

    @Override
    public boolean isDoctorAvailable(long doctorId, LocalDate date) {
        List<AvailableDate> availableDates = availableDateRepo.findByDoctorId(doctorId);
        return availableDates.stream().anyMatch(d -> d.getAvailableDate().equals(date));
    }

}
