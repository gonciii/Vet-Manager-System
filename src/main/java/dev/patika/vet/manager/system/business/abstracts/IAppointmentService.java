package dev.patika.vet.manager.system.business.abstracts;

import dev.patika.vet.manager.system.entities.Animal;
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

    // cursor ! randevu listesi getirme
    Page<Appointment> cursor(int page, int pageSize);

    //  belirtilen tarih aralığında belirtilen doktorun randevuları listesi.
    List<Appointment> findByDoctorIdAndAppointmentDateBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, Long doctorId);

    // Belirtilen tarih aralığında belirtilen hayvanın randevuları listesi.
    List<Appointment> findByAppointmentDateBetweenAndAnimal(LocalDateTime startDateTime, LocalDateTime endDateTime, Animal animal);




}
