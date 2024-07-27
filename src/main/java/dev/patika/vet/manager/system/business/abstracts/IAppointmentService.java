package dev.patika.vet.manager.system.business.abstracts;

import dev.patika.vet.manager.system.dto.request.appointment.AppointmentSaveRequest;
import dev.patika.vet.manager.system.dto.response.appointment.AppointmentResponse;
import dev.patika.vet.manager.system.entities.Animal;
import dev.patika.vet.manager.system.entities.Appointment;
import org.springframework.data.domain.Page;
import dev.patika.vet.manager.system.core.result.ResultData;

import java.time.LocalDateTime;
import java.util.List;

public interface IAppointmentService {

    AppointmentResponse save(AppointmentSaveRequest appointmentSaveRequest);

    Appointment get(Long id);

    List<Appointment> getAllAppointments();

    Appointment update(Appointment appointment);

    boolean delete(Long id);

    Page<Appointment> cursor(int page, int pageSize);

    ResultData<List<AppointmentResponse>> getAppointmentsByDoctorAndDate(LocalDateTime startDateTime, LocalDateTime endDateTime, Long doctorId);

    ResultData<List<AppointmentResponse>> getAppointmentsByAnimalAndDate(Long animalId , LocalDateTime startDateTime, LocalDateTime endDateTime);
}
