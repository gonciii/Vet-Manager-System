package dev.patika.vet.manager.system.business.concretes;


import dev.patika.vet.manager.system.business.abstracts.IAppointmentService;
import dev.patika.vet.manager.system.core.exception.NotFoundException;
import dev.patika.vet.manager.system.core.utilies.Msg;
import dev.patika.vet.manager.system.dao.AppointmentRepo;
import dev.patika.vet.manager.system.dao.AvailableDateRepo;
import dev.patika.vet.manager.system.entities.Animal;
import dev.patika.vet.manager.system.entities.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentManager implements IAppointmentService {
    private final AppointmentRepo appointmentRepo;
    private  final AvailableDateRepo availableDateRepo;


    public AppointmentManager(AppointmentRepo appointmentRepo, AvailableDateRepo availableDateRepo) {
        this.appointmentRepo = appointmentRepo;
        this.availableDateRepo = availableDateRepo;
    }

    // save -- yeni randevu kayıt etme
    @Override
    public Appointment save(Appointment appointment) {
        return this.appointmentRepo.save(appointment);
    }

    // id'ye göre randevu getirme
    @Override
    public Appointment get(Long id) {
        return this.appointmentRepo.findById(id).orElseThrow(()
                -> new NotFoundException(Msg.NOT_FOUND));
    }


    // randevu sayfalama işlemi :
    @Override
    public Page<Appointment> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page,pageSize);
        return this.appointmentRepo.findAll(pageable);
    }


    @Override
    public List<Appointment> findByDoctorIdAndAppointmentDateBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, Long doctorId) {
        return this.appointmentRepo.findByAppointmentDateAndDoctorIdBetween(startDateTime,endDateTime,doctorId);
    }

    @Override
    public List<Appointment> findByAppointmentDateBetweenAndAnimal(LocalDateTime startDateTime, LocalDateTime endDateTime, Animal animal) {
        return this.appointmentRepo.findByAppointmentDateAndAnimalIdBetween(startDateTime,endDateTime, animal.getId());
    }

    // randevu listeleme işlemi :
    @Override
    public List<Appointment> getAllAppointments() {
        return this.appointmentRepo.findAll();
    }

    // randevu güncelleme
    @Override
    public Appointment update(Appointment appointment) {
        this.get(appointment.getId());
        return this.appointmentRepo.save(appointment);
    }
    // id'ye göre randevu silme işlemi :
    @Override
    public boolean delete(Long id) {
        Appointment appointment = this.get(id);
        this.appointmentRepo.delete(appointment);
        return true;
    }






}
