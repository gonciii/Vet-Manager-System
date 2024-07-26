package dev.patika.vet.manager.system.business.abstracts;

import dev.patika.vet.manager.system.entities.Appointment;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface IAppointmentService {

    // randevu kayıt oluşturma
    Appointment save(Appointment appointment);

    // ıd'ye göre randevu getirmek için :
    Appointment get(Long id);

    // tüm randevuları getirmek için :
    List<Appointment> getAllAppointments();

    // ranadevu güncelleme :
    Appointment update(Appointment appointment);

    // randevu silmek için :
    boolean delete(Long id);

    // doktor id'sine göre randevu bulmak için :
    List<Appointment> findAppointmentsByDoctor(Long doctorId);

    // randevu aralığı için :
    List<Appointment> findAppointmentsByDateRange(LocalDateTime startDate,LocalDateTime endDate);

    // Hayvan ID'sine göre randevuları bulma
    List<Appointment> findAppointmentsByAnimal(Long animalId);

    List<Appointment>findByAppointmentDateAndAnimalIdAndDoctorIdBetween
            (LocalDateTime startDate, LocalDateTime endDate, Long animalId , Long doctorId);

    List<Appointment>findByAppointmentDateAndDoctorIdBetween
            (LocalDateTime startDate, LocalDateTime endDate, Long doctorId);

    // tarih ve hayvan ID'sine göre randevuları bulma işlemi :
    // değerlendirme 20
    List<Appointment> findAppointmentsByDateRangeAndAnimal(LocalDateTime startDate, LocalDateTime endDate, Long animalId);

    // cursor ! randevu listesi getirme
    Page<Appointment> cursor(int page, int pageSize);

 }
