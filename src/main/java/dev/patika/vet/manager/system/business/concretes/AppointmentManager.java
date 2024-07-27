package dev.patika.vet.manager.system.business.concretes;


import dev.patika.vet.manager.system.business.abstracts.IAppointmentService;
import dev.patika.vet.manager.system.core.exception.NotFoundException;
import dev.patika.vet.manager.system.core.exception.ScheduleConflictException;
import dev.patika.vet.manager.system.core.result.ResultData;
import dev.patika.vet.manager.system.core.utilies.Msg;
import dev.patika.vet.manager.system.dao.AnimalRepo;
import dev.patika.vet.manager.system.dao.AppointmentRepo;
import dev.patika.vet.manager.system.dao.AvailableDateRepo;
import dev.patika.vet.manager.system.dao.DoctorRepo;
import dev.patika.vet.manager.system.dto.request.appointment.AppointmentSaveRequest;
import dev.patika.vet.manager.system.dto.response.appointment.AppointmentResponse;
import dev.patika.vet.manager.system.entities.Animal;
import dev.patika.vet.manager.system.entities.Appointment;
import dev.patika.vet.manager.system.entities.Doctor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentManager implements IAppointmentService {
    private final AppointmentRepo appointmentRepo;
    private  final AvailableDateRepo availableDateRepo;
    private final AnimalRepo animalRepo;
    private final DoctorRepo doctorRepo;
    private final DoctorManager doctorManager;
    private final ModelMapper modelMapper;


    public AppointmentManager(AppointmentRepo appointmentRepo, AvailableDateRepo availableDateRepo , AnimalRepo animalRepo, DoctorRepo doctorRepo, DoctorManager doctorManager, ModelMapper modelMapper) {
        this.appointmentRepo = appointmentRepo;
        this.availableDateRepo = availableDateRepo;
        this.doctorRepo = doctorRepo;
        this.animalRepo = animalRepo;
        this.doctorManager = doctorManager;
        this.modelMapper = modelMapper;
    }

    // save -- yeni randevu kayıt etme
    @Override
    @Transactional(rollbackFor = {ScheduleConflictException.class, RuntimeException.class})
    public AppointmentResponse save(AppointmentSaveRequest appointmentSaveRequest) {
        // Belirtilen hayvan ve doktoru getir
        Animal animal = animalRepo.findById(appointmentSaveRequest.getAnimalId())
                .orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
        Doctor doctor = doctorRepo.findById(appointmentSaveRequest.getDoctorId())
                .orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));

        LocalDateTime appointmentDateTime = appointmentSaveRequest.getAppointmentDate();
        // Doktorun belirtilen tarihte müsait olup olmadığını kontrol et
        if (!doctorManager.isDoctorAvailable(doctor.getId(), appointmentDateTime.toLocalDate())) {
            throw new ScheduleConflictException(Msg.DOCTOR_NOT_AVAILABLE);
        }

        // Randevu çakışmasını kontrol et
        if (appointmentRepo.existsByDoctor_IdAndAppointmentDate(doctor.getId(), appointmentDateTime)) {
            throw new ScheduleConflictException(Msg.APPOINTMENT_CONFLICT);
        }

        // Randevu oluştur ve kaydet
        Appointment appointment = new Appointment();
        appointment.setAnimal(animal);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(appointmentDateTime);

        appointment = appointmentRepo.save(appointment);

        AppointmentResponse response = modelMapper.map(appointment, AppointmentResponse.class);
        response.setMessage(Msg.CREATED);

        return response;
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
    public ResultData<List<AppointmentResponse>> getAppointmentsByDoctorAndDate(LocalDateTime startDateTime, LocalDateTime endDateTime, Long doctorId) {
        if (doctorId == null || startDateTime == null || endDateTime == null) {
            return new ResultData<>(false, Msg.INVALID_INPUT, "400", null);
        }

        List<Appointment> appointments = appointmentRepo.findByDoctor_DoctorIdAndAppointmentDateBetween(doctorId, startDateTime, endDateTime);
        List<AppointmentResponse> responses = appointments.stream()
                .map(appointment -> {
                    AppointmentResponse response = new AppointmentResponse();
                    response.setId(appointment.getId());
                    response.setDoctorId(appointment.getDoctor().getId());
                    response.setAnimalId(appointment.getAnimal().getId());
                    response.setAppointmentDate(appointment.getAppointmentDate());
                    response.setMessage(Msg.CREATED);
                    return response;
                })
                .collect(Collectors.toList());

        if (responses.isEmpty()) {
            return new ResultData<>(false, Msg.APPOINTMENTS_NOT_FOUND, "404", responses);
        }

        return new ResultData<>(true, Msg.APPOINTMENTS_FETCHED_BY_DOCTOR, "200", responses);
    }
    @Override
    public ResultData<List<AppointmentResponse>> getAppointmentsByAnimalAndDate(Long animalId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        if (animalId == null || startDateTime == null) {
            return new ResultData<>(false, Msg.INVALID_INPUT, "400", null);
        }

        List<Appointment> appointments = appointmentRepo.findByAppointmentDateAndAnimalIdBetween(animalId, startDateTime,endDateTime);
        List<AppointmentResponse> responses = appointments.stream()
                .map(appointment -> {
                    AppointmentResponse response = new AppointmentResponse();
                    response.setId(appointment.getId());
                    response.setDoctorId(appointment.getDoctor().getId());
                    response.setAnimalId(appointment.getAnimal().getId());
                    response.setAppointmentDate(appointment.getAppointmentDate());
                    response.setMessage(Msg.CREATED);
                    return response;
                })
                .collect(Collectors.toList());

        if (responses.isEmpty()) {
            return new ResultData<>(false, Msg.APPOINTMENTS_NOT_FOUND, "404", responses);
        }

        return new ResultData<>(true, Msg.APPOINTMENTS_FETCHED_BY_ANIMAL, "200", responses);
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
